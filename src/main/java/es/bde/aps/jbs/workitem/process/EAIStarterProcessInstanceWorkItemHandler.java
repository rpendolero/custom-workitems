package es.bde.aps.jbs.workitem.process;

import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.Messages;

public class EAIStarterProcessInstanceWorkItemHandler extends EAIWorkItemHandler {

	public EAIStarterProcessInstanceWorkItemHandler(KieSession ksession) {
		super(ksession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kie.api.runtime.process.WorkItemHandler#executeWorkItem(org.kie.api.
	 * runtime.process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		String reference = String.valueOf(workItem.getProcessInstanceId());
		String processName = null;
		Map<String, Object> parameters = null;
		try {
			logger.info(Messages.getString("eaijava.messageExecute", reference));
			processName = (String) workItem.getParameter(EAIConstants.PARAM_PROCESS_NAME);
			verifyProcessName(reference, processName, workItem);

			parameters = getParameters(workItem);
			logger.info(Messages.getString("eaijava.messageExecuteCaseStart", reference, processName, parameters.toString()));
			ProcessInstance processInstance = ksession.startProcess(processName, parameters);
			long processInstanceId = processInstance.getId();

			results.put(PROCESS_ID, processInstance);
			manager.completeWorkItem(workItem.getId(), results);
			logger.info(Messages.getString("eaijava.messageExecuteCaseStartOK", reference, processInstanceId + "", processName, parameters.toString()));
		} catch (Exception e) {
			logger.error(Messages.getString("eaijava.errorExecuteCaseStart", processName, parameters.toString(), e.getMessage()));
			handleException(e);
		}

	}

}
