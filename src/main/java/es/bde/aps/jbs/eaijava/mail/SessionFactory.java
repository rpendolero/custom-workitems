package es.bde.aps.jbs.eaijava.mail;

import java.util.Properties;

import javax.mail.Session;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.exception.EAIJavaException;
import es.bde.aps.jbs.eaijava.util.PropertiesFactory;

public class SessionFactory {
	private static Session session;

	/**
	 * 
	 * @return
	 * @throws EAIJavaException
	 */
	public static final Session getSession() throws EAIJavaException {
		if (session == null) {
			session = createSession();
		}
		return session;
	}

	/**
	 * 
	 * @return
	 * @throws EAIJavaException
	 */
	private static Session createSession() throws EAIJavaException {

		Properties properties = PropertiesFactory.getProperties(EAIConstants.PROPERTIES_MAIL).getReference();
		session = Session.getInstance(properties);
		return session;
	}

}
