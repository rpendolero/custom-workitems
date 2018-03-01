/*
 * Creado el 23-oct-2007
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de c�digo
 */
package es.bde.aps.jbs.workitem.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.exception.JBSException;

/**
 * @author infrpbx
 * 
 *         TODO Para cambiar la plantilla de este comentario generado, vaya a
 *         Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de
 *         c�digo
 */
public class PropertiesFactory {

	private static Map<String, Properties> mapProperties = null;

	private static Object mutex = new Object();

	/**
	 * 
	 * @return
	 * @throws JBSException
	 */
	public static Properties getProperties(String propertiesName) throws JBSException {
		Properties prop = null;
		synchronized (mutex) {
			if (mapProperties == null) {
				mapProperties = new HashMap<String, Properties>();
			}

			prop = mapProperties.get(propertiesName);
			if (prop == null) {
				prop = loadProperties(propertiesName);
				mapProperties.put(propertiesName, prop);
			}
		}
		return prop;
	}

	/**
	 * @throws JBSException
	 * 
	 */
	private static Properties loadProperties(String propertiesName) throws JBSException {
		String configDir = System.getProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR);
		if (configDir == null) {
			throw new JBSException("Variable [" + EAIConstants.PROP_JBOSS_CONFIG_DIR + "] no definida");
		}
		StringBuilder fileNameConfig = new StringBuilder();
		fileNameConfig.append(configDir).append(File.separatorChar).append(propertiesName);

		Properties properties = new Properties();
		try {
			Reader inStream = new BufferedReader(new FileReader(fileNameConfig.toString()));
			properties.load(inStream);
		} catch (IOException e) {
			throw new JBSException("Error al cargar el fichero [" + fileNameConfig.toString() + "] [" + e.getMessage() + "]");
		}
		return properties;
	}
}
