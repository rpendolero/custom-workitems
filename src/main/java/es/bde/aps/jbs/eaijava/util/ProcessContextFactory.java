package es.bde.aps.jbs.eaijava.util;

import org.drools.core.spi.ProcessContext;
import org.jbpm.workflow.instance.node.WorkItemNodeInstance;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.NodeInstanceContainer;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkflowProcessInstance;

public class ProcessContextFactory {

	/**
	 * 
	 * @param workItem
	 * @return
	 */
	public static ProcessContext createProcessContext(KieSession ksession, WorkItem workItem) {
		ProcessContext kcontext = new ProcessContext(ksession);
		WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.getProcessInstance(workItem.getProcessInstanceId());
		kcontext.setProcessInstance(processInstance);
		WorkItemNodeInstance nodeInstance = findNodeInstance(workItem.getId(), processInstance);
		kcontext.setNodeInstance(nodeInstance);
		return kcontext;
	}

	/**
	 * 
	 * @param workItemId
	 * @param container
	 * @return
	 */
	private static WorkItemNodeInstance findNodeInstance(long workItemId, NodeInstanceContainer container) {
		for (NodeInstance nodeInstance : container.getNodeInstances()) {
			if (nodeInstance instanceof WorkItemNodeInstance) {
				WorkItemNodeInstance workItemNodeInstance = (WorkItemNodeInstance) nodeInstance;
				if (workItemNodeInstance.getWorkItem().getId() == workItemId) {
					return workItemNodeInstance;
				}
			}
			if (nodeInstance instanceof NodeInstanceContainer) {
				WorkItemNodeInstance result = findNodeInstance(workItemId, ((NodeInstanceContainer) nodeInstance));
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

}
