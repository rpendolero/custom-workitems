package es.bde.aps.jbs.eaijava.plsql;

import java.util.Iterator;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class ProcedureWorkItemHandler implements WorkItemHandler {

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Map<String, Object> parameters = workItem.getParameters();

		for (Iterator<String> iterator = parameters.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object value = parameters.get(key);
			System.out.println("parameter: " + key + " value: " + value.toString());

		}

		Map<String, Object> results = null;
		manager.completeWorkItem(workItem.getId(), results);
	}

}
