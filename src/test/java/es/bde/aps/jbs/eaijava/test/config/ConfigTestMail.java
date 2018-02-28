package es.bde.aps.jbs.eaijava.test.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigTestMail extends ConfigTestAbstract {
	private Map<String, String> parameters = new HashMap<String, String>();

	/**
	 * @param name
	 * @param value
	 */
	public void addParameter(String name, String value) {
		parameters.put(name, value);
	}

	/**
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

}
