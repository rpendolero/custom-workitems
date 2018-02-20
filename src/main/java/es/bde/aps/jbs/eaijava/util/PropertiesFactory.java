/*
 * Creado el 23-oct-2007
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de c�digo
 */
package es.bde.aps.jbs.eaijava.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.exception.EAIJavaException;

/**
 * @author infrpbx
 * 
 *         TODO Para cambiar la plantilla de este comentario generado, vaya a
 *         Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de
 *         c�digo
 */
public class PropertiesFactory {

	public static final String PROPERTIES_FILE = "database.properties";

	// Clave que identifica el driver JDBC
	public static String DRIVER_NAME = ".db_driver";
	// Clave que identifica la cadena de conexi�n JDBC
	public static String CONNECTION = ".db_connection";
	// Clave que identifica el usurio de la base de datos
	public static String USER = ".db_user";
	// Clave que identifica la password encriptada del usuario de base de datos
	public static String PASSWORD = ".db_password";
	// Clave que identifica el propietario del esquema de la base de datos
	public static String SCHEMA_OWNER = ".db_schemaowner";
	// Clave que identifica si la conexiones a la base de datos son autocommit.
	public static String AUTO_COMMIT = ".db_autocommit";
	// Clave que identifica el n�mero de conexiones activas en el pool a la base
	// de datos
	public static String MAX_ACTIVE = ".db_max_active";
	// Clave que identifica el n�mero de conexiones ocupadas en el pool a la
	// base de datos
	public static String MAX_IDLE = ".db_max_idle";

	// Nombre del driver de Oracle
	public static final String DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";

	// Referencia del objeto de propiedades
	private static Properties properties;

	private static Object mutex = new Object();

	/**
	 * M�todo que asigna la referencia al "Properties" que gestionar�
	 * PropertiesFactory.
	 * 
	 * @param properties
	 *            Referencia de un objeto del tipo java.util.Properties
	 */
	public static void setProperties(Properties _properties) {
		synchronized (mutex) {
			if (properties == null)
				properties = _properties;
		}

	}

	/**
	 * Devuelve el valor de una propiedad que se recibe por par�metro.
	 * 
	 * @param key
	 *            Clave de una propiedad
	 * @return
	 * @throws EAIJavaException
	 */
	public static Object getProperty(String key) throws EAIJavaException {
		if (properties == null) {
			loadProperties();
		}
		return properties.get(key);
	}

	/**
	 * @throws EAIJavaException
	 * 
	 */
	private static void loadProperties() throws EAIJavaException {
		String configDir = System.getProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR);
		if (configDir == null) {
			throw new EAIJavaException("Variable [" + EAIConstants.PROP_JBOSS_CONFIG_DIR + "] no definida");
		}
		StringBuilder fileNameConfig = new StringBuilder();
		fileNameConfig.append(configDir).append(File.separatorChar).append(PROPERTIES_FILE);

		properties = new Properties();
		try {
			Reader inStream = new BufferedReader(new FileReader(fileNameConfig.toString()));
			properties.load(inStream);
		} catch (IOException e) {
			throw new EAIJavaException("Error al cargar el fichero [" + fileNameConfig.toString() + "] [" + e.getMessage() + "]");
		}

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
	public static String getString(String propertyName) throws EAIJavaException {
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
	public static int getInt(String propertyName) throws EAIJavaException {
		String property = checkProperty(propertyName);

		try {
			return Integer.parseInt(property);
		} catch (NumberFormatException e) {
			throw new EAIJavaException("Entero erroneo", e);
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
	private static String checkProperty(String propertyName) throws EAIJavaException {
		String propertyValue = (String) getProperty(propertyName);

		if ((propertyValue == null) || (propertyValue.length() == 0)) {
			throw new EAIJavaException(propertyName + " es null.");
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
	 * @throws EAIJavaException
	 * @throws Exception
	 *             Si la propiedad es nula o vacia.
	 */
	public static boolean getBoolean(String propertyName) throws EAIJavaException {
		String propertyValue = checkProperty(propertyName);

		return new Boolean(propertyValue).booleanValue();
	}
}
