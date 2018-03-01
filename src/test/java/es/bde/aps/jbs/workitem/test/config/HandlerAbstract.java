package es.bde.aps.jbs.workitem.test.config;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class HandlerAbstract extends DefaultHandler {

	protected static final String ATT_NAME = "name";
	protected static final String ATT_VALUE = "value";

	protected Map<String, ConfigTestAbstract> mapTest = new HashMap<String, ConfigTestAbstract>();
	protected StringBuilder sPath = new StringBuilder();
	protected ConfigTestAbstract configTest;
	private String nameTest;
	private String pathTest = "configuration/tests/test/";

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		sPath.append(qName).append("/");
		if (sPath.toString().equals(pathTest)) {
			nameTest = attributes.getValue(ATT_NAME);
			configTest = createConfigTest();
			configTest.setName(nameTest);
			mapTest.put(nameTest, configTest);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		int pos = sPath.toString().lastIndexOf(qName);
		String target = sPath.substring(0, pos);

		sPath = new StringBuilder(target);
	}

	protected abstract ConfigTestAbstract createConfigTest();

	public Map<String, ConfigTestAbstract> getMapTest() {
		return mapTest;
	}

}
