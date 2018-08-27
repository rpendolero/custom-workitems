package es.bde.aps.jbs.workitem.init;

import org.drools.core.spi.ProcessContext;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import es.bde.aps.jbs.workitem.exception.JBSException;
import es.bde.aps.jbs.workitem.util.ProcessContextFactory;

public class LogVariablesWorkItemHandler implements WorkItemHandler {
	private KieSession ksession;
	private ProcessContext kcontext;
	public LogVariablesWorkItemHandler(KieSession ksession) {
		this.ksession = ksession;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("Executing ["+this.getClass().getSimpleName()+"] ...");
		kcontext = ProcessContextFactory.createProcessContext(ksession, workItem);
		try {
			printProcessVariables(workItem);
		} catch (JBSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}

	private void printProcessVariables(WorkItem workItem) throws JBSException {
	
		if (kcontext == null) {
			throw new JBSException("No se pudo crear el kie context");
		}
		ProcessInstance processInstance = kcontext.getProcessInstance();
		Object variable = kcontext.getVariable("PARAMETER");
		System.out.println(variable);
		variable = kcontext.getVariable("PARAMETER2");
		System.out.println(variable);
	}

}
