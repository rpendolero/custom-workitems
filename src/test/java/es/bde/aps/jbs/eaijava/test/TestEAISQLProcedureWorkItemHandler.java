package es.bde.aps.jbs.eaijava.test;

import java.util.List;
import java.util.Map;

import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.interfaces.IField;
import es.bde.aps.jbs.eaijava.plsql.EAISQLProcedureWorkItemHandler;
import es.bde.aps.jbs.eaijava.test.config.ConfigTest;
import es.bde.aps.jbs.eaijava.test.config.Configuration;

public class TestEAISQLProcedureWorkItemHandler {

	private Configuration configuration;
	private long id;

	/**
	 * @param testName
	 * @return
	 */
	private WorkItemImpl createWorkItem(String testName) {
		ConfigTest configTest = configuration.getConfigTest(testName);
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

	@Before
	public void setUp() {

		String directory = System.getProperty("user.dir");
		System.setProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR, directory + "/src/test/resources");
		try {
			configuration = Configuration.getInstance();
		} catch (Exception e) {
			String message = "Error al cargar la configuraci�n [" + e.getMessage() + "]";
			Assert.assertNotNull(message, null);
		}
	}

	@Test
	public void testExecuteProcedureVoid() {
		TestWorkItemManager manager = new TestWorkItemManager();

		WorkItemImpl workItem = createWorkItem("testExecuteProcedureVoid");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureVarcharIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureVarcharIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureVarcharOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureVarcharOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureVarcharInOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureVarcharInOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		if (result != null) {
			// IField fieldInput = null;
			// String valueInput = (String) fieldInput.getValue();
			// IField fieldOutput = null;
			// String valueOutput = (String) result.get(fieldOutput.getName());

			// Assert.assertTrue(valueInput.equals(valueOutput));
		} else {
			Assert.assertTrue(false);
		}

	}

	@Test
	public void testExecuteProcedureIntegerIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureIntegerIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureIntegerOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureIntegerOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureIntegerInOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureIntegerInOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		if (result != null) {
			// IField fieldInput = null;
			// String valueInput = (String) fieldInput.getValue();
			// IField fieldOutput = null;
			// String valueOutput = (String) result.get(fieldOutput.getName());

			// Assert.assertTrue(valueInput.equals(valueOutput));
		} else {
			Assert.assertTrue(false);
		}

	}

	@Test
	public void testExecuteProcedureDoubleIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDoubleIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureDoubleOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDoubleOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureDoubleInOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDoubleInOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		if (result != null) {
			// IField fieldInput = null;
			// String valueInput = (String) fieldInput.getValue();
			// IField fieldOutput = null;
			// String valueOutput = (String) result.get(fieldOutput.getName());

			// Assert.assertTrue(valueInput.equals(valueOutput));
		} else {
			Assert.assertTrue(false);
		}

	}

	@Test
	public void testExecuteProcedureDateIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDateIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureDateOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDateOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureDateInOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDateInOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		if (result != null) {
			// IField fieldInput = null;
			// String valueInput = (String) fieldInput.getValue();
			// IField fieldOutput = null;
			// String valueOutput = (String) result.get(fieldOutput.getName());

			// Assert.assertTrue(valueInput.equals(valueOutput));
		} else {
			Assert.assertTrue(false);
		}

	}

	@Test
	public void testExecuteProcedureDateTimeIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDateTimeIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureDateTimeOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDateTimeOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureDateTimeInOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureDateTimeInOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		if (result != null) {
			// IField fieldInput = null;
			// String valueInput = (String) fieldInput.getValue();
			// IField fieldOutput = null;
			// String valueOutput = (String) result.get(fieldOutput.getName());

			// Assert.assertTrue(valueInput.equals(valueOutput));
		} else {
			Assert.assertTrue(false);
		}

	}

	@Test
	public void testExecuteProcedureTimeIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureTimeIn");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureTimeOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureTimeOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureTimeInOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = createWorkItem("testExecuteProcedureTimeInOut");

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		if (result != null) {
			// IField fieldInput = null;
			// String valueInput = (String) fieldInput.getValue();
			// IField fieldOutput = null;
			// String valueOutput = (String) result.get(fieldOutput.getName());

			// Assert.assertTrue(valueInput.equals(valueOutput));
		} else {
			Assert.assertTrue(false);
		}

	}
}
