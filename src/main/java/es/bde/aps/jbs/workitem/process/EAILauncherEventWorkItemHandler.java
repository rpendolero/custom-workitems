package es.bde.aps.jbs.workitem.process;

import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.Messages;

public class EAILauncherEventWorkItemHandler extends EAIWorkItemHandler {

	public EAILauncherEventWorkItemHandler(KieSession ksession) {
		super(ksession);
		// TODO Auto-generated constructor stub
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		String reference = String.valueOf(workItem.getProcessInstanceId());
		String processInstanceId = null;
		Map<String, Object> parameters = null;
		String processName = null;
		try {
			logger.info(Messages.getString("eaijava.messageExecute", reference));
			processInstanceId = (String) workItem.getParameter(EAIConstants.PARAM_PROCESS_INSTANCE_ID);
			verifyProcessName(reference, processName, workItem);
			String eventName = (String) workItem.getParameter(EAIConstants.PARAM_EVENT_NAME);

			parameters = getParameters(workItem);
			logger.info(Messages.getString("eaijava.messageExecuteLauncherEvent", reference, processInstanceId, parameters.toString()));
			ksession.signalEvent(eventName, parameters, Long.parseLong(processInstanceId));

			manager.completeWorkItem(workItem.getId(), results);
			logger.info(Messages.getString("eaijava.messageExecuteLauncherEventOK", reference, processInstanceId + "", processName, parameters.toString()));
		} catch (Exception e) {
			logger.error(Messages.getString("eaijava.errorExecuteCaseStart", processName, parameters.toString(), e.getMessage()));
			handleException(e);
		}
	}

}
