package es.bde.aps.jbs.workitem.test.init;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.init.LogVariablesWorkItemHandler;
import es.bde.aps.jbs.workitem.init.TestParametersWorkItemHandler;

public class ServiceTaskTest extends JbpmJUnitBaseTestCase {

	private KieSession ksession;

	@Override
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		try {
			String directory = System.getProperty("user.dir");
			System.setProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR, directory + "/src/test/resources/config");

			Map<String, ResourceType> resources = new HashMap<String, ResourceType>();
			resources.put("TSTTEST.bpmn2", ResourceType.BPMN2);
			createRuntimeManager(resources, "simple-no-cdi-test");
			RuntimeEngine engine = getRuntimeEngine();
			assertNotNull(engine);

			ksession = engine.getKieSession();
			WorkItemManager workItemManager = ksession.getWorkItemManager();
			workItemManager.registerWorkItemHandler("TestParametersTask", new TestParametersWorkItemHandler(ksession));
			workItemManager.registerWorkItemHandler("LogVariablesTask", new LogVariablesWorkItemHandler(ksession));
		} catch (Exception e) {
			String message = "Error al cargar la configuracion [" + e.getMessage() + "]";
			Assert.assertNotNull(message, null);
		}
	}

	@Test
	public void test1() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("PARAMETER", "valor1");
		params.put("PARAMETER2", 2222);

		WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess("TSTTEST", params);
		System.out.println(processInstance.getState()+" "+ProcessInstance.STATE_ACTIVE);
		//assertThat(processInstance.getState()).isEqualTo(ProcessInstance.STATE_ACTIVE);
	}

}
