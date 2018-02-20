package es.bde.aps.jbs.eaijava.test.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import es.bde.aps.jbs.eaijava.interfaces.Field;
import es.bde.aps.jbs.eaijava.interfaces.FieldArray;
import es.bde.aps.jbs.eaijava.interfaces.FieldFactory;
import es.bde.aps.jbs.eaijava.interfaces.IField;

public class Configuration {

	private static final String FICHERO_CONFIG = "/config.xml";
	private static Configuration instance = null;
	private static Object mutex = new Object();

	private Map<String, ConfigTest> mapTest = new HashMap<String, ConfigTest>();
	private SAXParser parser;

	private class Handler extends DefaultHandler {
		private static final String ATT_NAME = "name";
		private static final String ATT_TYPE = "type";
		private static final String ATT_ARRAY = "isArray";
		private static final String ATT_VALUE = "value";

		private StringBuilder sPath = new StringBuilder();
		private String pathTest = "configuration/tests/test/";
		private String pathProcedure = "configuration/tests/test/procedure/";
		private String pathParameterInput = "configuration/tests/test/parameters/parametersInput/parameter/";
		private String pathParameterInputValue = "configuration/tests/test/parameters/parametersInput/parameter/value/";
		private String pathParameterOutput = "configuration/tests/test/parameters/parametersOutput/parameter/";
		private String nameTest;
		private IField field;
		private ConfigTest configTest;

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			sPath.append(qName).append("/");
			if (sPath.toString().equals(pathTest)) {
				nameTest = attributes.getValue(ATT_NAME);
				configTest = new ConfigTest();
				configTest.setName(nameTest);
				mapTest.put(nameTest, configTest);
			}
			if (sPath.toString().equals(pathProcedure)) {
				String nameProcedure = attributes.getValue(ATT_NAME);
				configTest.setProcedure(nameProcedure);
			}

			if (sPath.toString().equals(pathParameterInput)) {
				String nameAttribute = attributes.getValue(ATT_NAME);
				String typeAttribute = attributes.getValue(ATT_TYPE);
				String isArrayAttribute = attributes.getValue(ATT_ARRAY);
				field = createField(nameAttribute, typeAttribute, isArrayAttribute);
				configTest.addParameterInput(field);
			}

			if (sPath.toString().equals(pathParameterInputValue)) {
				String valueAttribute = attributes.getValue(ATT_VALUE);
				if (field instanceof FieldArray) {
					((FieldArray) field).addValue(valueAttribute);
				} else {
					((Field) field).setValue(valueAttribute);
				}

			}

			if (sPath.toString().equals(pathParameterOutput)) {
				String nameAttribute = attributes.getValue(ATT_NAME);
				String typeAttribute = attributes.getValue(ATT_TYPE);
				String isArrayAttribute = attributes.getValue(ATT_ARRAY);
				field = createField(nameAttribute, typeAttribute, isArrayAttribute);
				configTest.addParameterOutput(field);
			}
		}

		/**
		 * 
		 * @param nameAttribute
		 * @param typeAttribute
		 * @param isArrayAttribute
		 * @return
		 */
		private IField createField(String nameAttribute, String typeAttribute, String isArrayAttribute) {
			boolean isArray = Boolean.parseBoolean(isArrayAttribute);
			if (isArray) {
				return FieldFactory.createFieldArray(nameAttribute, null, typeAttribute.charAt(0));
			} else {
				return FieldFactory.createField(nameAttribute, null, typeAttribute.charAt(0));
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			int pos = sPath.toString().lastIndexOf(qName);
			String target = sPath.substring(0, pos);

			sPath = new StringBuilder(target);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	private Configuration() throws Exception {
		load();
	}

	/**
	 * 
	 * @throws Exception
	 */
	protected void load() throws Exception {
		// TODO Apéndice de método generado automáticamente

		InputStream input = Configuration.class.getResourceAsStream(FICHERO_CONFIG);
		if (input == null)
			return;

		Handler handler = new Handler();
		parse(input, handler);
	}

	/**
	 * 
	 * @param input
	 * @param handler
	 */
	private void parse(InputStream input, DefaultHandler handler) {
		try {
			getParser().parse(input, handler);
		} catch (SAXException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return
	 */
	private SAXParser getParser() {
		try {
			if (parser == null) {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				parser = factory.newSAXParser();
			}

		} catch (Exception saxe) {
			saxe.printStackTrace();
		}
		return parser;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Configuration getInstance() throws Exception {
		synchronized (mutex) {

			if (instance == null) {
				instance = new Configuration();
			}
		}
		return instance;
	}

	/**
	 * 
	 * @param testName
	 * @return
	 */
	public ConfigTest getConfigTest(String testName) {
		return mapTest.get(testName);
	}
}
