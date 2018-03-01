package es.bde.aps.jbs.workitem.test.config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ConfigurationTestMail extends ConfigurationAbstract {
	private static final String FICHERO_CONFIG = "/configTestMail.xml";

	private class Handler extends HandlerAbstract {

		private String pathParameter = "configuration/tests/test/parameters/parameter/";

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			if (sPath.toString().equals(pathParameter)) {
				String nameAttribute = attributes.getValue(ATT_NAME);
				String valueAttribute = attributes.getValue(ATT_VALUE);
				((ConfigTestMail) configTest).addParameter(nameAttribute, valueAttribute);
			}
		}

		@Override
		protected ConfigTestAbstract createConfigTest() {
			// TODO Auto-generated method stub
			return new ConfigTestMail();
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public ConfigurationTestMail() throws Exception {
		load(FICHERO_CONFIG);
	}

	@Override
	HandlerAbstract createHandler() {
		// TODO Auto-generated method stub
		return new Handler();
	}

}
