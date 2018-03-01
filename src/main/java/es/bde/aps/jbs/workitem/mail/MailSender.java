/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.bde.aps.jbs.workitem.mail;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.Messages;
import es.bde.aps.jbs.workitem.exception.JBSException;
import es.bde.aps.jbs.workitem.util.Properties;
import es.bde.aps.jbs.workitem.util.PropertiesFactory;

public class MailSender {

	private static final String CONTENT_TYPE = "text/html";
	private static final String TRANSPORT_TYPE = "smtp";

	/**
	 * @param email
	 * @throws JBSException
	 */
	public static void sendMail(Email email) throws JBSException {

		Session session = SessionFactory.getSession();
		boolean debug = false;
		session.setDebug(debug);

		try {
			javax.mail.Message msg = fillMessage(email, session);

			// send the thing off
			Transport transport = (Transport) session.getTransport(TRANSPORT_TYPE);
			try {
				// transport.connect(mailhost, port, username, password);
				Properties properties = PropertiesFactory.getProperties(EAIConstants.PROPERTIES_MAIL);
				String user = (String) properties.getProperty(EAIConstants.PROP_SMTP_USER);
				if (user == null || "".equals(user)) {
					transport.connect();
				} else {
					String password = properties.getString(EAIConstants.PROP_SMTP_PWD);
					transport.connect(user, password);
				}
				transport.sendMessage(msg, msg.getAllRecipients());
			} catch (Exception e) {
				throw new RuntimeException("Connection failure", e);
			} finally {
				transport.close();
			}

		} catch (Exception e) {
			throw new RuntimeException("Unable to send email", e);
		}
	}

	/**
	 * @param email
	 * @param session
	 * @return
	 */
	private static MimeMessage fillMessage(Email email, Session session) {
		Message message = email.getMessage();

		String subject = message.getSubject();
		String from = message.getFrom();
		String replyTo = message.getReplyTo();

		String mailer = "sendhtml";

		if (from == null) {
			throw new RuntimeException("Email must have 'from' address");
		}

		if (replyTo == null) {
			replyTo = from;
		}

		// Construct and fill the Message
		MimeMessage msg = null;
		try {
			msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setReplyTo(new InternetAddress[] { new InternetAddress(replyTo) });

			for (Recipient recipient : message.getRecipients().getRecipients()) {
				RecipientType type = recipient.getType();
				msg.addRecipients(type, InternetAddress.parse(recipient.getEmail(), false));
			}

			if (message.hasAttachment()) {
				Multipart multipart = new MimeMultipart();
				// prepare body as first mime body part
				MimeBodyPart messageBodyPart = new MimeBodyPart();

				messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(message.getBody(), CONTENT_TYPE)));
				multipart.addBodyPart(messageBodyPart);

				List<String> attachments = message.getAttachments();
				attachFiles(multipart, attachments);

				// Put parts in message
				msg.setContent(multipart);
			} else {
				msg.setDataHandler(new DataHandler(new ByteArrayDataSource(message.getBody(), "text/html")));
			}

			msg.setSubject(subject);

			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new Date());
		} catch (Exception e) {
			throw new RuntimeException("Unable to send email", e);
		}

		return msg;
	}

	/**
	 * 
	 * @param multipart
	 * @param attachments
	 * @throws MalformedURLException
	 */
	private static void attachFiles(Multipart multipart, List<String> attachments) throws JBSException {
		for (String attachment : attachments) {
			MimeBodyPart attachementBodyPart = new MimeBodyPart();

			try {
				URL attachmentUrl = getAttachemntURL(attachment);

				String contentType = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(attachmentUrl.getFile());
				attachementBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(attachmentUrl.openStream(), contentType)));
				String fileName = new File(attachmentUrl.getFile()).getName();
				attachementBodyPart.setFileName(fileName);
				attachementBodyPart.setContentID("<" + fileName + ">");

				multipart.addBodyPart(attachementBodyPart);

			} catch (Exception e) {
				throw new JBSException(Messages.getString("eaijava.errorMailAddAtachFiles", attachment));
			}
		}

	}

	/**
	 * @param body
	 * @param msg
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void collect(String body, Message msg) throws MessagingException, IOException {
		// String subject = msg.getSubject();
		StringBuffer sb = new StringBuffer();
		// sb.append( "<HTML>\n" );
		// sb.append( "<HEAD>\n" );
		// sb.append( "<TITLE>\n" );
		// sb.append( subject + "\n" );
		// sb.append( "</TITLE>\n" );
		// sb.append( "</HEAD>\n" );
		// sb.append( "<BODY>\n" );
		// sb.append( "<H1>" + subject + "</H1>" + "\n" );
		sb.append(body);
		// sb.append( "</BODY>\n" );
		// sb.append( "</HTML>\n" );

	}

	/**
	 * @param attachment
	 * @return
	 * @throws MalformedURLException
	 */
	protected static URL getAttachemntURL(String attachment) throws MalformedURLException {
		if (attachment.startsWith("classpath:")) {
			String location = attachment.replaceFirst("classpath:", "");
			return MailSender.class.getResource(location);
		} else {
			URL attachmentUrl = new URL(attachment);

			return attachmentUrl;
		}
	}

	private static class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator(String username, String password) {
			authentication = new PasswordAuthentication(username, password.toCharArray());
		}
	}
}
