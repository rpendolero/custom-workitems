package es.bde.aps.jbs.eaijava.mail;

import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.eaijava.Messages;

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

		Map<String, Object> results = null;
		String reference = String.valueOf(workItem.getProcessInstanceId());

		logger.info(Messages.getString("eaijava.messageExecuteMail", reference));
		try {

			Email email = createEmail(workItem);
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
	 * @param workItem
	 * @return
	 */
	private Email createEmail(WorkItem workItem) {

		Email email = new Email();
		Message message = new Message();

		// Set recipients
		Recipients recipients = new Recipients();

		String parameter = (String) workItem.getParameter("To");
		recipients = addRecipients(recipients, parameter, javax.mail.Message.RecipientType.TO);
		parameter = (String) workItem.getParameter("Cc");
		recipients = addRecipients(recipients, parameter, javax.mail.Message.RecipientType.CC);
		parameter = (String) workItem.getParameter("Bcc");
		recipients = addRecipients(recipients, parameter, javax.mail.Message.RecipientType.BCC);

		// Fill message
		message.setRecipients(recipients);
		message.setSubject((String) workItem.getParameter("Subject"));
		message.setBody((String) workItem.getParameter("Body"));

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

}
