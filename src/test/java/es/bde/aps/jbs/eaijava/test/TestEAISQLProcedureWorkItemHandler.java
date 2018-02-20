package es.bde.aps.jbs.eaijava.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.interfaces.Field;
import es.bde.aps.jbs.eaijava.interfaces.FieldFactory;
import es.bde.aps.jbs.eaijava.interfaces.IField;
import es.bde.aps.jbs.eaijava.plsql.EAISQLProcedureWorkItemHandler;

public class TestEAISQLProcedureWorkItemHandler {

	private static final Object PROCEDURE_VOID = "JBSPROCVOID";
	private static final Object PROCEDURE_VARCHAR_IN = "JBSPROCVARCHARIN";
	private static final Object PROCEDURE_VARCHAR_OUT = "JBSPROCVARCHAROUT";
	private static final Object PROCEDURE_VARCHAR_IN_OUT = "JBSPROCVARCHARINOUT";

	@Before
	public void setUp() {
		String workspaceLocation = "c:/trabajo/git";
		System.setProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR, workspaceLocation + "/eaijava/src/test/resources");
	}

	@Test
	public void testExecuteProcedureVoid() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = new WorkItemImpl();
		workItem.setId(0);
		workItem.setParameter(EAIConstants.PARAM_PROCEDURE, PROCEDURE_VOID);

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureVarcharIn() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = new WorkItemImpl();
		workItem.setId(0);
		workItem.setParameter(EAIConstants.PARAM_PROCEDURE, PROCEDURE_VARCHAR_IN);

		List<Object> listIn = new ArrayList<Object>();
		Field field = (Field) FieldFactory.createField("param1", "Parametro Input", IField.STRING);
		listIn.add(field);
		workItem.setParameter(EAIConstants.PARAM_LIST_IN, listIn);

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);

	}

	@Test
	public void testExecuteProcedureVarcharOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = new WorkItemImpl();
		workItem.setId(0);
		workItem.setParameter(EAIConstants.PARAM_PROCEDURE, PROCEDURE_VARCHAR_OUT);

		List<Object> listOut = new ArrayList<Object>();
		Field field = (Field) FieldFactory.createField("param2", null, IField.STRING);
		listOut.add(field);
		workItem.setParameter(EAIConstants.PARAM_LIST_OUT, listOut);

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		Assert.assertTrue(result != null);
	}

	@Test
	public void testExecuteProcedureVarcharInOut() {
		TestWorkItemManager manager = new TestWorkItemManager();
		WorkItemImpl workItem = new WorkItemImpl();
		workItem.setId(0);
		workItem.setParameter(EAIConstants.PARAM_PROCEDURE, PROCEDURE_VARCHAR_IN_OUT);

		List<Object> listIn = new ArrayList<Object>();
		Field fieldInput = (Field) FieldFactory.createField("param1", "Parametro Input", IField.STRING);
		listIn.add(fieldInput);
		workItem.setParameter(EAIConstants.PARAM_LIST_IN, listIn);

		List<Object> listOut = new ArrayList<Object>();
		Field fieldOutput = (Field) FieldFactory.createField("param2", null, IField.STRING);
		listOut.add(fieldOutput);
		workItem.setParameter(EAIConstants.PARAM_LIST_OUT, listOut);

		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);

		long id = workItem.getId();
		Map<String, Object> result = manager.getResult(id);
		if (result != null) {
			String valueInput = (String) fieldInput.getValue();
			String valueOutput = (String) result.get(fieldOutput.getName());

			Assert.assertTrue(valueInput.equals(valueOutput));
		} else {
			Assert.assertTrue(false);
		}

	}
}
