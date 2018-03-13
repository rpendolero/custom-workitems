package es.bde.aps.jbs.workitem.test.mail;

import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.Assert;
import org.junit.Before;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.mail.EAIMailWorkItemHandler;
import es.bde.aps.jbs.workitem.test.TestWorkItemManager;
import es.bde.aps.jbs.workitem.test.config.ConfigTestMail;
import es.bde.aps.jbs.workitem.test.config.ConfigType;
import es.bde.aps.jbs.workitem.test.config.ConfigurationFactory;
import es.bde.aps.jbs.workitem.test.config.ConfigurationTestMail;

public class TestEAIMailWorkIteHandler {

	private ConfigurationTestMail configuration;

	private WorkItemImpl createWorkItem(String testName) {
		WorkItemImpl workItem = new WorkItemImpl();
		return workItem;
	}

	@Before
	public void setUp() {
		String directory = System.getProperty("user.dir");
		System.setProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR, directory + "/src/test/resources");
		try {
			configuration = (ConfigurationTestMail) ConfigurationFactory.getConfiguration(ConfigType.CONFIG_MAIL);
		} catch (Exception e) {
			String message = "Error al cargar la configuracion [" + e.getMessage() + "]";
			Assert.assertNotNull(message, null);
		}

	}

	// @Test
	public void testExecuteMailCorrect() {
		TestWorkItemManager manager = new TestWorkItemManager();
		String testName = "testExecuteMailCorrect";

		WorkItemImpl workItem = createWorkItem(testName);
		ConfigTestMail configTest = configuration.getConfigTest(testName);

		workItem.setParameter(EAIConstants.MAIL_FROM, configTest.getParameter(EAIConstants.MAIL_FROM));
		workItem.setParameter(EAIConstants.MAIL_TO, configTest.getParameter(EAIConstants.MAIL_TO));
		workItem.setParameter(EAIConstants.MAIL_SUBJECT, configTest.getParameter(EAIConstants.MAIL_SUBJECT));
		workItem.setParameter(EAIConstants.MAIL_BODY, configTest.getParameter(EAIConstants.MAIL_BODY));

		EAIMailWorkItemHandler workItemHandler = new EAIMailWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);
	}

	// @Test
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
