package es.bde.aps.jbs.eaijava.mail.test;

import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.Before;
import org.junit.Test;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.TestWorkItemManager;
import es.bde.aps.jbs.eaijava.mail.EAIMailWorkItemHandler;

public class TestEAIMailWorkIteHandler {

	private WorkItemImpl createWorkItem(String testName) {
		WorkItemImpl workItem = new WorkItemImpl();
		return workItem;
	}

	@Before
	public void setUp() {
		String directory = System.getProperty("user.dir");
		System.setProperty(EAIConstants.PROP_JBOSS_CONFIG_DIR, directory + "/src/test/resources");

	}

	@Test
	public void testEmailCorrect() {
		TestWorkItemManager manager = new TestWorkItemManager();

		WorkItemImpl workItem = createWorkItem("testEmailCorrect");
		workItem.setParameter(EAIConstants.MAIL_FROM, "pendolero@gmail.com");
		workItem.setParameter(EAIConstants.MAIL_TO, "pendolero@gmail.com");

		workItem.setParameter(EAIConstants.MAIL_SUBJECT, "Prueba");
		workItem.setParameter(EAIConstants.MAIL_BODY, "Prueba");
		EAIMailWorkItemHandler workItemHandler = new EAIMailWorkItemHandler();
		workItemHandler.executeWorkItem(workItem, manager);
	}
}
