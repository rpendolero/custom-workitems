/*
 * Creado el 16-oct-2007
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de c�digo
 */
package es.bde.aps.jbs.eaijava;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author infrpbx
 *
 *         TODO Para cambiar la plantilla de este comentario generado, vaya a
 *         Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de
 *         c�digo
 */
public class Messages {

	private static final String BUNDLE_NAME = "es.bde.aps.jbs.eaijava.messages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 *
	 */
	private Messages() {
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public static String getString(String key, String args[]) {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		try {
			String formatkey = RESOURCE_BUNDLE.getString(key);
			if (formatkey == null)
				return null;
			MessageFormat messageFormat = new MessageFormat(formatkey);
			return messageFormat.format(args);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}
