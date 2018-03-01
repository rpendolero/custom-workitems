package es.bde.aps.jbs.workitem.init;

import org.drools.core.spi.ProcessContext;
import org.jbpm.process.workitem.AbstractLogOrThrowWorkItemHandler;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;

import es.bde.aps.jbs.workitem.exception.JBSException;
import es.bde.aps.jbs.workitem.util.ProcessContextFactory;

public class InitilizerProcessInstanceWorkItemHandler extends AbstractLogOrThrowWorkItemHandler {

	private static final String VAR_CASENUM = "SW_CASENUM";
	private static final String VAR_PROCNAME = "SW_PRONAME";
	private static final String VAR_STATUS = "SW_STATUS";

	private KieSession ksession;

	public InitilizerProcessInstanceWorkItemHandler(KieSession ksession) {
		this.ksession = ksession;
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		try {
			ProcessContext kcontext = ProcessContextFactory.createProcessContext(ksession, workItem);
			if (kcontext == null) {
				throw new JBSException("No se pudo crear el kie context");
			}
			ProcessInstance processInstance = kcontext.getProcessInstance();
			kcontext.setVariable(VAR_CASENUM, processInstance.getId());
			kcontext.setVariable(VAR_PROCNAME, processInstance.getProcessName());
			int state = processInstance.getState();
			switch (state) {
			case WorkflowProcessInstance.STATE_ACTIVE:
			case WorkflowProcessInstance.STATE_PENDING:
			case WorkflowProcessInstance.STATE_SUSPENDED:
				kcontext.setVariable(VAR_STATUS, "A");
				break;

			case WorkflowProcessInstance.STATE_ABORTED:
			case WorkflowProcessInstance.STATE_COMPLETED:
				kcontext.setVariable(VAR_STATUS, "C");
				break;

			default:
				break;
			}

		} catch (Exception e) {
			handleException(e);
		}

	}

}
