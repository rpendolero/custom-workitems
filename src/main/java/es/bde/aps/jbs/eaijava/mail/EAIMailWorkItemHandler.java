package es.bde.aps.jbs.eaijava.mail;

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
	 * @see org.kie.api.runtime.process.WorkItemHandler#abortWorkItem(org.kie.api.
	 * runtime.process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kie.api.runtime.process.WorkItemHandler#executeWorkItem(org.kie.api.
	 * runtime.process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		Map<String, Object> results = null;
		String reference = String.valueOf(workItem.getProcessInstanceId());

		logger.info(Messages.getString("eaijava.messageExecuteMail", reference));
		try {

			Email email = createEmail(reference, workItem);
			SendHtml.sendHtml(email);

			logger.info(Messages.getString("eaijava.messageMailSendedCorrectly", reference));
			if (manager != null)
				manager.completeWorkItem(workItem.getId(), results);

		} catch (Exception e) {
			logger.error(Messages.getString("eaijava.errorExecuteMail", new String[] { e.getMessage() }), e);

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

		String parameter = (String) workItem.getParameter(EAIConstants.MAIL_TO);
		logger.info(Messages.getString("eaijava.messageMailTo", reference, parameter));
		recipients = addRecipients(recipients, parameter, javax.mail.Message.RecipientType.TO);
		parameter = (String) workItem.getParameter(EAIConstants.MAIL_CC);
		logger.info(Messages.getString("eaijava.messageMailAddressCC", reference, parameter));
		recipients = addRecipients(recipients, parameter, javax.mail.Message.RecipientType.CC);
		parameter = (String) workItem.getParameter(EAIConstants.MAIL_CCO);
		logger.info(Messages.getString("eaijava.messageMailAddressCCO", reference, parameter));
		recipients = addRecipients(recipients, parameter, javax.mail.Message.RecipientType.BCC);
		parameter = (String) workItem.getParameter(EAIConstants.MAIL_TO_GROUP);
		String addressMail = findUsersOfGroups(parameter);
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
}
