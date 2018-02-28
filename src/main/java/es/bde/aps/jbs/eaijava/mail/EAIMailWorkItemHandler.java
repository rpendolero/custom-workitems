package es.bde.aps.jbs.eaijava.mail;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.Messages;
import es.bde.aps.jbs.eaijava.exception.EAIJavaException;

public class EAIMailWorkItemHandler implements WorkItemHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kie.api.runtime.process.WorkItemHandler#abortWorkItem(org.kie.api.
	 * runtime.process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kie.api.runtime.process.WorkItemHandler#executeWorkItem(org.kie.api.
	 * runtime.process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		Map<String, Object> results = new HashMap<String, Object>();
		String reference = String.valueOf(workItem.getProcessInstanceId());

		logger.info(Messages.getString("eaijava.messageExecuteMail", reference));
		try {

			Email email = createEmail(reference, workItem);
			MailSender.sendMail(email);
			results.put("Result", "Ok");

			logger.info(Messages.getString("eaijava.messageMailSendedCorrectly", reference));
		} catch (Exception e) {
			logger.error(Messages.getString("eaijava.errorExecuteMail", new String[] { e.getMessage() }), e);
			results.put("Result", e.getMessage());

		} finally {
			if (manager != null)
				manager.completeWorkItem(workItem.getId(), results);

		}

	}

	/**
	 * 
	 * @param reference
	 * @param workItem
	 * @return
	 * @throws EAIJavaException
	 */
	private Email createEmail(String reference, WorkItem workItem) throws EAIJavaException {

		Email email = new Email();
		Message message = new Message();

		// Set recipients
		Recipients recipients = new Recipients();

		String parameter = null;
		String mailTo = (String) workItem.getParameter(EAIConstants.MAIL_TO);
		String mailToGroup = (String) workItem.getParameter(EAIConstants.MAIL_TO_GROUP);
		parameter = validateAdresses(mailTo, mailToGroup);
		logger.info(Messages.getString("eaijava.messageMailTo", reference, parameter));
		recipients = addRecipients(recipients, parameter, javax.mail.Message.RecipientType.TO);
		parameter = (String) workItem.getParameter(EAIConstants.MAIL_CC);
		logger.info(Messages.getString("eaijava.messageMailAddressCC", reference, parameter));
		recipients = addRecipients(recipients, parameter, javax.mail.Message.RecipientType.CC);
		parameter = (String) workItem.getParameter(EAIConstants.MAIL_CCO);
		logger.info(Messages.getString("eaijava.messageMailAddressCCO", reference, parameter));
		recipients = addRecipients(recipients, parameter, javax.mail.Message.RecipientType.BCC);

		// Fill message
		message.setRecipients(recipients);
		parameter = (String) workItem.getParameter(EAIConstants.MAIL_FROM);
		message.setFrom(parameter);
		logger.info(Messages.getString("eaijava.messageAddressFrom", reference, parameter));
		parameter = (String) workItem.getParameter(EAIConstants.MAIL_SUBJECT);
		message.setSubject(parameter);
		logger.info(Messages.getString("eaijava.messageMailSubject", reference, parameter));
		parameter = (String) workItem.getParameter(EAIConstants.MAIL_BODY);
		message.setBody(parameter);
		logger.info(Messages.getString("eaijava.messageMailBody", reference, parameter));

		// setup email
		email.setMessage(message);

		return email;
	}

	/**
	 * 
	 * @param recipients
	 * @param parameter
	 * @param string
	 * @return
	 */
	private Recipients addRecipients(Recipients recipients, String parameter, javax.mail.Message.RecipientType type) {

		if (parameter != null && parameter.trim().length() > 0) {
			for (String adress : parameter.split(";")) {
				if (adress != null && !"".equals(adress)) {
					Recipient recipient = new Recipient();
					recipient.setEmail(adress);
					recipient.setType(type);
					recipients.addRecipient(recipient);
				}
			}
		}
		return recipients;
	}

	/**
	 * 
	 * @param fieldMailToGroup
	 * @return
	 * @throws EAIJavaException
	 */
	private String findUsersOfGroups(String fieldMailToGroup) throws EAIJavaException {
		return null;
	}

	/**
	 * M�todo que valida y devuelve las direccciones destiunatrias del correo.
	 * 
	 * @param fieldMailTo
	 *            Valor del campo MAIL_TO
	 * @param fieldMailToGroup
	 *            Valor del campo MAIL_TO_GROUP
	 * @return
	 * @throws EAIJavaException
	 */
	private String validateAdresses(String fieldMailTo, String fieldMailToGroup) throws EAIJavaException {
		String fieldTo = null;
		// Se valida que los destinatarios bvengan vacios, en este caso se
		// enviar� un error de ejecuci�n
		if (fieldMailTo == null || "".equals(fieldMailTo)) {
			if (fieldMailToGroup == null || "".equals(fieldMailToGroup)) {
				String message = Messages.getString("eaijava.errorMailRecipientNotDefined");
				throw new EAIJavaException(message);
			}
			fieldTo = "";
		} else {

			fieldTo = fieldMailTo + ";";
		}

		if (fieldMailToGroup != null) {
			// Se busca los usuarios que pertenecen los grupos que se reciben
			// como destinatarios del correo.
			fieldTo += findUsersOfGroups(fieldMailToGroup);

			// Si el valor de los usuarios destinarios es vacio se lanza un
			// error
			if (fieldTo == null || "".equals(fieldTo)) {
				logger.info(Messages.getString("eaijava.errorMailUsersNotAssigned", new String[] { fieldMailToGroup }));

				// Si no existen nung�n destinatario en el campo MAIL_TO se
				// propaga una excepcion.
				if (fieldMailTo == null || "".equals(fieldMailTo)) {
					String message = Messages.getString("eaijava.errorMailRecipientNotFound");
					throw new EAIJavaException(message);
				}

			}
		}

		return fieldTo;
	}
}
