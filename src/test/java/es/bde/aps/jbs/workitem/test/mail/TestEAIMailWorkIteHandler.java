package es.bde.aps.jbs.workitem.test.mail;

import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.mail.EAIMailWorkItemHandler;
import es.bde.aps.jbs.workitem.test.TestWorkItemManager;
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

	@Test
	public void testEmailCorrect() {
		TestWorkItemManager manager = new TestWorkItemManager();

		WorkItemImpl workItem = createWorkItem("testEmailCorrect");
		workItem.setParameter(EAIConstants.MAIL_FROM, "qinrpbx@correo.interno");
		workItem.setParameter(EAIConstants.MAIL_TO, "qinrpbx@correo.interno");

		workItem.setParameter(EAIConstants.MAIL_SUBJECT, "Prueba");
		workItem.setParameter(EAIConstants.MAIL_BODY, "Prueba");
		EAIMailWorkItemHandler workItemHandler = new EAIMailWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);
	}
}
