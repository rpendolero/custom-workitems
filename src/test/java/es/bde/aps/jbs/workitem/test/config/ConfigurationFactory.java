package es.bde.aps.jbs.workitem.test.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationFactory {

	private static Map<String, IConfiguration> mapConfigurations = new HashMap<String, IConfiguration>();

	private static Object mutex = new Object();

	/**
	 * 
	 * @param configName
	 * @return
	 * @throws Exception
	 */
	public static IConfiguration getConfiguration(ConfigType configType) throws Exception {

		IConfiguration config = null;
		synchronized (mutex) {
			config = mapConfigurations.get(configType);

			if (config == null) {
				String configName = configType.getFileName();
				config = createConfiguration(configType);
				mapConfigurations.put(configName, config);
			}
		}
		return config;
	}

	/**
	 * 
	 * @param configName
	 * @return
	 * @throws Exception
	 */
	private static IConfiguration createConfiguration(ConfigType configType) throws Exception {
		switch (configType) {
		case CONFIG_PL_SQL:
			return new ConfigurationTestPlSql();

		case CONFIG_MAIL:

			return new ConfigurationTestMail();
		case CONFIG_START_CASE:

			return new ConfigurationTestStartCases();

		}
		throw new Exception("No se pudo crear la configuracion del tipo [" + configType + "]");
	}

}
