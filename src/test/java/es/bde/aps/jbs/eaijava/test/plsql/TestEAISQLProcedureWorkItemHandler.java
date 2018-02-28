package es.bde.aps.jbs.eaijava.test.plsql;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.interfaces.Field;
import es.bde.aps.jbs.eaijava.interfaces.IField;
import es.bde.aps.jbs.eaijava.plsql.EAISQLProcedureWorkItemHandler;
import es.bde.aps.jbs.eaijava.test.TestKieSession;
import es.bde.aps.jbs.eaijava.test.TestWorkItemManager;
import es.bde.aps.jbs.eaijava.test.config.ConfigTestPlSql;
import es.bde.aps.jbs.eaijava.test.config.ConfigType;
import es.bde.aps.jbs.eaijava.test.config.ConfigurationFactory;
import es.bde.aps.jbs.eaijava.test.config.ConfigurationTestPlSql;
import es.bde.aps.jbs.eaijava.util.ConvertUtil;

public class TestEAISQLProcedureWorkItemHandler {

	private ConfigurationTestPlSql configuration;
	private KieSession ksession;
	private long id;

	/**
	 * 
	 * @param testName
	 * @param result
	 * @return
	 */
	private boolean isResultCorrect(String testName, Map<String, Object> result) {
		ConfigTestPlSql configTest = configuration.getConfigTest(testName);
		List<String> resultExpected = configTest.getResults();
		List<IField> parametersOutput = configTest.getParametersOutput();
		IField field = parametersOutput.get(0);
		if (field instanceof Field) {
			Object valueReceived = result.get(field.getName());
			String valueExpected = resultExpected.get(0);
			return (valueReceived.toString().equals(valueExpected));
		} else {
			Object valueReceived = result.get(field.getName());
			return (valueReceived.toString().equals(resultExpected.toString()));
		}

	}

	/**
	 * @param testName
	 * @return
	 */
	private WorkItemImpl createWorkItem(String testName) {
		ConfigTestPlSql configTest = configuration.getConfigTest(testName);
		String procedureName = configTest.getProcedure();
		WorkItemImpl workItem = new WorkItemImpl();
		workItem.setParameter(EAIConstants.PARAM_PROCEDURE, procedureName);

		List<IField> parametersInput = configTest.getParametersInput();
		List<IField> parametersOuput = configTest.getParametersOutput();
		id = id + 1;
		workItem.setId(id);
		workItem.setProcessInstanceId(id);
		workItem.setParameter(EAIConstants.PARAM_LIST_IN, parametersInput);
		workItem.setParameter(EAIConstants.PARAM_LIST_OUT, parametersOuput);

		return workItem;
	}

	/**
	 * 
	 * @param testName
	 * @return
	 * @throws ParseException 
	 */
	private KieSession createKieSession(String testName) throws ParseException {
		ConfigTestPlSql configTest = configuration.getConfigTest(testName);
		ksession = new TestKieSession();

		List<IField> parametersInput = configTest.getParametersInput();
		for (IField field : parametersInput) {
			setVariable(ksession, field);
		}
		
		List<IField> parametersOuput = configTest.getParametersOutput();
		for (IField field : parametersOuput) {
			setVariable(ksession, field);
		}
		return ksession;
	}
	
	/**
	 * 
	 * @param ksession2
	 * @param field
	 * @throws ParseException 
	 */
	private void setVariable(KieSession ksession, IField field) throws ParseException {
		if (field instanceof Field) {
			char type = field.getType();
			Serializable obj = (Serializable) field.getValue();
			Object objectJava = ConvertUtil.getObjectJava(type, obj);
			ksession.setGlobal(field.getName(), objectJava);
		} else {
			
		}
	}

	/**
	 * 
	 * @param testName
	 */
	private void verifyTestOutput(String testName) {
		TestWorkItemManager manager = new TestWorkItemManager();

		WorkItemImpl workItem = createWorkItem(testName);

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		if (result != null) {
			Assert.assertTrue("Resultado no esperado [" + result.toString() + "]", isResultCorrect(testName, result));
		} else {
			Assert.assertTrue("Resultado vacio", false);
		}

	}

	@Before
	public void setUp() {

		String directory = System.getProperty("user.dir");
		System.setProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR, directory + "/src/test/resources");
		try {
			configuration = (ConfigurationTestPlSql) ConfigurationFactory.getConfiguration(ConfigType.CONFIG_PL_SQL);
		} catch (Exception e) {
			String message = "Error al cargar la configuraci�n [" + e.getMessage() + "]";
			Assert.assertNotNull(message, null);
		}
	}

	@Test
	public void testExecuteProcedureVoid() {
		TestWorkItemManager manager = new TestWorkItemManager();

		WorkItemImpl workItem = createWorkItem("testExecuteProcedureVoid");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureVarcharIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureVarcharIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureVarcharOut() {
		String testName = "testExecuteProcedureVarcharOut";
		verifyTestOutput(testName);

	}

	@Test
	public void testExecuteProcedureVarcharInOut() {
		String testName = "testExecuteProcedureVarcharInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureIntegerIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureIntegerIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureIntegerOut() {
		String testName = "testExecuteProcedureIntegerOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureIntegerInOut() {
		String testName = "testExecuteProcedureIntegerInOut";
		verifyTestOutput(testName);

	}

	@Test
	public void testExecuteProcedureDoubleIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDoubleIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureDoubleOut() {
		String testName = "testExecuteProcedureDoubleOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureDoubleInOut() {
		String testName = "testExecuteProcedureDoubleInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureDateIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDateIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureDateOut() {
		String testName = "testExecuteProcedureDateOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureDateInOut() {
		String testName = "testExecuteProcedureDateInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureDateTimeIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDateTimeIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureDateTimeOut() {
		String testName = "testExecuteProcedureDateTimeOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureDateTimeInOut() {
		String testName = "testExecuteProcedureDateTimeInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureTimeIn() {

		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureTimeIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureTimeOut() {
		String testName = "testExecuteProcedureTimeOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureTimeInOut() {
		String testName = "testExecuteProcedureTimeInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayVarcharIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureArrayVarcharIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureArrayVarcharOut() {
		String testName = "testExecuteProcedureArrayVarcharOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayVarcharInOut() {
		String testName = "testExecuteProcedureArrayVarcharInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayIntegerIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureArrayIntegerIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureArrayIntegerOut() {
		String testName = "testExecuteProcedureArrayIntegerOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayIntegerInOut() {
		String testName = "testExecuteProcedureArrayIntegerInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayDoubleIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureArrayDoubleIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureArrayDoubleOut() {
		String testName = "testExecuteProcedureArrayDoubleOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayDoubleInOut() {
		String testName = "testExecuteProcedureArrayDoubleInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayDateIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureArrayDateIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureArrayDateOut() {
		String testName = "testExecuteProcedureArrayDateOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayDateInOut() {
		String testName = "testExecuteProcedureArrayDateInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayDateTimeIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureArrayDateTimeIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureArrayDateTimeOut() {
		String testName = "testExecuteProcedureArrayDateTimeOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayDateTimeInOut() {
		String testName = "testExecuteProcedureArrayDateTimeInOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayTimeIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureArrayTimeIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler(ksession);
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureArrayTimeOut() {
		String testName = "testExecuteProcedureArrayTimeOut";
		verifyTestOutput(testName);
	}

	@Test
	public void testExecuteProcedureArrayTimeInOut() {
		String testName = "testExecuteProcedureArrayTimeInOut";
		verifyTestOutput(testName);
	}
}
