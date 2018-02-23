package es.bde.aps.jbs.eaijava.mail;

import java.util.Properties;

import javax.mail.Session;

public class SessionFactory {
	private static Session session;

	/**
	 * 
	 * @return
	 */
	public static final Session getSession() {
		if (session == null) {
			session = createSession();
		}
		return session;
	}

	/**
	 * 
	 * @return
	 */
	private static Session createSession() {

		Properties properties = new Properties();
		session = Session.getInstance(properties);
		return session;
	}

}
