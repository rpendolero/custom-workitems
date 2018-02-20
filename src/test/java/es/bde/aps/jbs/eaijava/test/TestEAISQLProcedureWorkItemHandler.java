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

	private static final Object PROCEDURE_VOID = "JBSPROCVOID";
	private static final Object PROCEDURE_VARCHAR_IN = "JBSPROCVARCHARIN";
	private static final Object PROCEDURE_VARCHAR_OUT = "JBSPROCVARCHAROUT";
	private static final Object PROCEDURE_VARCHAR_IN_OUT = "JBSPROCVARCHARINOUT";
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
		workItem.setId(id++);
		workItem.setParameter(EAIConstants.PARAM_LIST_IN, parametersInput);
		workItem.setParameter(EAIConstants.PARAM_LIST_OUT, parametersOuput);

		return workItem;
	}

	@Before
	public void setUp() {
		String workspaceLocation = "c:/trabajo/git";
		System.setProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR, workspaceLocation + "/eaijava/src/test/resources");
		try {
			configuration = Configuration.getInstance();
		} catch (Exception e) {
			String message = "Error al cargar la configuración [" + e.getMessage() + "]";
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
}
