package es.bde.aps.jbs.eaijava.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.spi.ProcessContext;
import org.jbpm.process.workitem.AbstractLogOrThrowWorkItemHandler;
import org.kie.api.definition.process.Process;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.Messages;
import es.bde.aps.jbs.eaijava.util.ProcessContextFactory;

public class EAIProcessInstanceWorkItemHandler extends AbstractLogOrThrowWorkItemHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private KieSession ksession;

	public EAIProcessInstanceWorkItemHandler(KieSession ksession) {
		this.ksession = ksession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kie.api.runtime.process.WorkItemHandler#abortWorkItem(org.kie.api.runtime
	 * .process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kie.api.runtime.process.WorkItemHandler#executeWorkItem(org.kie.api.
	 * runtime.process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		String reference = String.valueOf(workItem.getProcessInstanceId());
		try {
			logger.info(Messages.getString("eaijava.messageExecute", reference));
			String processName = (String) workItem.getParameter(EAIConstants.PARAM_PROCEDURE);
			verifyProcessName(processName, workItem);

			Map<String, Object> parameters = getParameters(workItem);
			logger.info(Messages.getString("eaijava.messageExecuteCaseStart", reference, processName, parameters.toString()));
			ProcessInstance processInstance = ksession.startProcess(processName, parameters);
			long processInstanceId = processInstance.getId();

			logger.info(Messages.getString("eaijava.messageExecuteCaseStartOK", reference, processInstanceId + "", processName, parameters.toString()));
		} catch (Exception e) {

		}

	}

	/**
	 * @param workItem
	 * @return
	 */
	private Map<String, Object> getParameters(WorkItem workItem) {
		ProcessContext context = ProcessContextFactory.createProcessContext(ksession, workItem);
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<String> listInputFields = (List<String>) workItem.getParameter(EAIConstants.PARAM_LIST_IN);
		for (String fieldName : listInputFields) {
			Object value = context.getVariable(fieldName);
			parameters.put(fieldName, value);
		}
		return parameters;
	}

	/**
	 * @param procedureName
	 * @param workItem
	 */
	private void verifyProcessName(String procedureName, WorkItem workItem) {
		long processInstanceId = workItem.getProcessInstanceId();
		ProcessInstance processInstance = ksession.getProcessInstance(processInstanceId);
		Process process = processInstance.getProcess();
		String processName = process.getName();

	}

}
