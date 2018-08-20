package es.bde.aps.jbs.workitem.test.mail;

import java.util.HashMap;
import java.util.Map;

import org.drools.core.process.instance.impl.WorkItemImpl;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.WorkflowProcessInstance;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.mail.EAIMailWorkItemHandler;
import es.bde.aps.jbs.workitem.test.TestWorkItemManager;
import es.bde.aps.jbs.workitem.test.config.ConfigTestMail;
import es.bde.aps.jbs.workitem.test.config.ConfigType;
import es.bde.aps.jbs.workitem.test.config.ConfigurationFactory;
import es.bde.aps.jbs.workitem.test.config.ConfigurationTestMail;

public class EAIMailWorkItemHandlerTest extends JbpmJUnitBaseTestCase {

	private static final String PROCESS_MAIL = "TSTEMAIL";
	private static final String PARAM_MAIL_FROM = "TSTVFROM";
	private static final String PARAM_MAIL_TO = "TSTVTO";
	private static final String PARAM_SUBJECT = "TSTVSUBJECT";
	private static final String PARAM_BODY = "TSTVBODY";
	private static final String PARAM_RESULT = "TSTVRESULT";
	private ConfigurationTestMail configuration;
	private KieSession ksession;

	public EAIMailWorkItemHandlerTest() {
		super();
	}

	private WorkItemImpl createWorkItem(String testName) {
		WorkItemImpl workItem = new WorkItemImpl();
		return workItem;
	}

	@Before
	public void setUp() {
		// super.setUp();
		try {
			String directory = System.getProperty("user.dir");
			System.setProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR, directory + "/src/test/resources/config");
			configuration = (ConfigurationTestMail) ConfigurationFactory.getConfiguration(ConfigType.CONFIG_MAIL);

			Map<String, ResourceType> resources = new HashMap<String, ResourceType>();
			resources.put("TSTEMAIL.bpmn2", ResourceType.BPMN2);
			createRuntimeManager(resources, "simple-no-cdi-test");
			RuntimeEngine engine = getRuntimeEngine();
			assertNotNull(engine);

			ksession = engine.getKieSession();
			ksession.getWorkItemManager().registerWorkItemHandler("EMailTask", new EAIMailWorkItemHandler());
		} catch (Exception e) {
			String message = "Error al cargar la configuracion [" + e.getMessage() + "]";
			Assert.assertNotNull(message, null);
		}

	}

	@Test
	public void testExecuteMailCorrect() {
		// TestWorkItemManager manager = new TestWorkItemManager();
		String testName = "testExecuteMailCorrect";
		ConfigTestMail configTest = configuration.getConfigTest(testName);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_MAIL_FROM, configTest.getParameter(EAIConstants.MAIL_FROM));
		params.put(PARAM_MAIL_TO, configTest.getParameter(EAIConstants.MAIL_TO));
		params.put(PARAM_SUBJECT, configTest.getParameter(EAIConstants.MAIL_SUBJECT));
		params.put(PARAM_BODY, configTest.getParameter(EAIConstants.MAIL_BODY));

		WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.startProcess(PROCESS_MAIL, params);
		Object variable = processInstance.getVariable(PARAM_RESULT);
		System.out.println(variable);
	}

	@Test
	public void testExecuteMailGroupCorrect() {
		TestWorkItemManager manager = new TestWorkItemManager();
		String testName = "testExecuteMailGroupCorrect";

		WorkItemImpl workItem = createWorkItem(testName);
		ConfigTestMail configTest = configuration.getConfigTest(testName);

		workItem.setParameter(EAIConstants.MAIL_FROM, configTest.getParameter(EAIConstants.MAIL_FROM));
		workItem.setParameter(EAIConstants.MAIL_TO_GROUP, configTest.getParameter(EAIConstants.MAIL_TO_GROUP));
		workItem.setParameter(EAIConstants.MAIL_SUBJECT, configTest.getParameter(EAIConstants.MAIL_SUBJECT));
		workItem.setParameter(EAIConstants.MAIL_BODY, configTest.getParameter(EAIConstants.MAIL_BODY));

		EAIMailWorkItemHandler workItemHandler = new EAIMailWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);
	}
}
