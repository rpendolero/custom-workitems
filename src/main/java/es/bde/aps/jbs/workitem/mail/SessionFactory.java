package es.bde.aps.jbs.workitem.mail;

import java.util.Properties;

import javax.mail.Session;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.exception.JBSException;
import es.bde.aps.jbs.workitem.util.PropertiesFactory;

public class SessionFactory {
	private static Session session;

	/**
	 * 
	 * @return
	 * @throws JBSException
	 */
	public static final Session getSession() throws JBSException {
		if (session == null) {
			session = createSession();
		}
		return session;
	}

	/**
	 * 
	 * @return
	 * @throws JBSException
	 */
	private static Session createSession() throws JBSException {

		Properties properties = PropertiesFactory.getProperties(EAIConstants.PROPERTIES_MAIL).getReference();
		session = Session.getInstance(properties);
		return session;
	}

}
