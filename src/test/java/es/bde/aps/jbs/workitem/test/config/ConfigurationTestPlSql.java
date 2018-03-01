package es.bde.aps.jbs.workitem.test.config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import es.bde.aps.jbs.workitem.interfaces.Field;
import es.bde.aps.jbs.workitem.interfaces.FieldArray;
import es.bde.aps.jbs.workitem.interfaces.FieldFactory;
import es.bde.aps.jbs.workitem.interfaces.IField;

public class ConfigurationTestPlSql extends ConfigurationAbstract {

	private static final String FICHERO_CONFIG = "/configTestPlSql.xml";

	private class Handler extends HandlerAbstract {

		private static final String ATT_TYPE = "type";
		private static final String ATT_ARRAY = "isArray";
		private static final String ATT_VALUE = "value";

		private String pathProcedure = "configuration/tests/test/procedure/";
		private String pathParameterInput = "configuration/tests/test/parameters/parametersInput/parameter/";
		private String pathParameterInputValue = "configuration/tests/test/parameters/parametersInput/parameter/value/";
		private String pathParameterOutput = "configuration/tests/test/parameters/parametersOutput/parameter/";
		private String pathResultValue = "configuration/tests/test/result/value/";

		private IField field;

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			super.startElement(uri, localName, qName, attributes);

			if (sPath.toString().equals(pathProcedure)) {
				String nameProcedure = attributes.getValue(ATT_NAME);
				((ConfigTestPlSql) configTest).setProcedure(nameProcedure);
			}

			if (sPath.toString().equals(pathParameterInput)) {
				String nameAttribute = attributes.getValue(ATT_NAME);
				String typeAttribute = attributes.getValue(ATT_TYPE);
				String isArrayAttribute = attributes.getValue(ATT_ARRAY);

				field = createField(nameAttribute, typeAttribute, isArrayAttribute);
				((ConfigTestPlSql) configTest).addParameterInput(field);
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
				((ConfigTestPlSql) configTest).addParameterOutput(field);
			}

			if (sPath.toString().equals(pathResultValue)) {
				String valueAttribute = attributes.getValue(ATT_VALUE);
				((ConfigTestPlSql) configTest).addResultValue(valueAttribute);

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
			return FieldFactory.createField(nameAttribute, typeAttribute.charAt(0), isArray);
		}

		@Override
		protected ConfigTestAbstract createConfigTest() {
			return new ConfigTestPlSql();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	protected ConfigurationTestPlSql() throws Exception {
		load(FICHERO_CONFIG);
	}

	/**
	 * 
	 * @param testName
	 * @return
	 */
	public ConfigTestPlSql getConfigTest(String testName) {
		return (ConfigTestPlSql) handler.getMapTest().get(testName);
	}

	@Override
	HandlerAbstract createHandler() {
		// TODO Auto-generated method stub
		return new Handler();

	}
}
