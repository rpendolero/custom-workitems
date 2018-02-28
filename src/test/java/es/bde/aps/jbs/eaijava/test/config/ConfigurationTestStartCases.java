package es.bde.aps.jbs.eaijava.test.config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ConfigurationTestStartCases extends ConfigurationAbstract {

	private static final String FICHERO_CONFIG = "/configTestStartCases.xml";

	private class Handler extends HandlerAbstract {
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
		}

		@Override
		protected ConfigTestAbstract createConfigTest() {
			// TODO Auto-generated method stub
			return new ConfigTestStartCases();
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public ConfigurationTestStartCases() throws Exception {
		load(FICHERO_CONFIG);
	}

	@Override
	HandlerAbstract createHandler() {
		// TODO Auto-generated method stub
		return null;
	}

}
