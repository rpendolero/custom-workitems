package es.bde.aps.jbs.workitem.util;

import java.io.IOException;
import java.io.Reader;

import es.bde.aps.jbs.workitem.exception.JBSException;

public class Properties {

	// Referencia del objeto de propiedades
	private static java.util.Properties properties;

	/**
	 * 
	 */
	protected Properties() {
		properties = new java.util.Properties();
	}

	/**
	 * Devuelve el valor de una propiedad obligatoria de tipo cadena de texto.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad.
	 * @throws Exception
	 *             Si la propiedad no existe.
	 */
	public static String getString(String propertyName) throws JBSException {
		return checkProperty(propertyName);
	}

	/**
	 * Devuelve el valor de una propiedad obligatoria de tipo entero.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad.
	 * @throws Exception
	 *             Si la propiedad no existe o si est� mal formada.
	 */
	public static int getInt(String propertyName) throws JBSException {
		String property = checkProperty(propertyName);

		try {
			return Integer.parseInt(property);
		} catch (NumberFormatException e) {
			throw new JBSException("Entero erroneo", e);
		}
	}

	/**
	 * Comprueba la existencia de una propiedad en el archivo de configuraci�n. En
	 * caso contrario lanza una excepci�n.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad
	 * @return el valor asignado a la propiedad
	 * @throws Exception
	 *             Si la propiedad es nula o vacia.
	 */
	private static String checkProperty(String propertyName) throws JBSException {
		String propertyValue = (String) getProperty(propertyName);

		if ((propertyValue == null) || (propertyValue.length() == 0)) {
			throw new JBSException(propertyName + " es null.");
		}

		return propertyValue.trim();
	}

	/**
	 * Comprueba la existencia de una propiedad en el archivo de configuraci�n. En
	 * caso contrario lanza una excepci�n.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad
	 * @return el valor asignado a la propiedad
	 * @throws JBSException
	 * @throws Exception
	 *             Si la propiedad es nula o vacia.
	 */
	public static boolean getBoolean(String propertyName) throws JBSException {
		String propertyValue = checkProperty(propertyName);

		return new Boolean(propertyValue).booleanValue();
	}

	/**
	 * Devuelve el valor de una propiedad que se recibe por par�metro.
	 * 
	 * @param key
	 *            Clave de una propiedad
	 * @return
	 * @throws JBSException
	 */
	public static Object getProperty(String key) throws JBSException {
		return properties.get(key);
	}

	/**
	 * 
	 * @param inStream
	 * @throws IOException
	 */
	protected void load(Reader inStream) throws IOException {
		properties.load(inStream);

	}

	public java.util.Properties getReference() {
		// TODO Auto-generated method stub
		return properties;
	}

}
