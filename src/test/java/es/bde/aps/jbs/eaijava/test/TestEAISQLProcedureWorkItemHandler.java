package es.bde.aps.jbs.eaijava.test;

import org.drools.core.process.instance.impl.DefaultWorkItemManager;
import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.process.WorkItemManager;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.plsql.EAISQLProcedureWorkItemHandler;

public class TestEAISQLProcedureWorkItemHandler {

	@Before
	public void setUp() {
		System.setProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR, "/home/roberto/developer/jboss/jboss-eap-7.1/standalone/configuration/");
	}

	@Test
	public void testExecute() {
		WorkItemManager manager = new DefaultWorkItemManager(null);
		WorkItemImpl workItem = new WorkItemImpl();
		workItem.setParameter(EAIConstants.PARAM_PROCEDURE, "JBSPROCVOID");
		EAISQLProcedureWorkItemHandler workItemHandler = new EAISQLProcedureWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);
	}

}
