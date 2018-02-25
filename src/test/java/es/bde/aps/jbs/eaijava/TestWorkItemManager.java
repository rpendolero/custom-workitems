package es.bde.aps.jbs.eaijava;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class TestWorkItemManager implements WorkItemManager {
	private Map<Long, Map<String, Object>> results;

	public TestWorkItemManager() {
		results = new HashMap<Long, Map<String, Object>>();
	}

	public void abortWorkItem(long id) {
		// TODO Auto-generated method stub

	}

	public void completeWorkItem(long id, Map<String, Object> result) {
		Long key = Long.valueOf(id);
		results.put(key, result);
	}

	public void registerWorkItemHandler(String id, WorkItemHandler handler) {
		// TODO Auto-generated method stub

	}

	public Map<String, Object> getResult(long id) {
		Long key = Long.valueOf(id);
		return results.get(key);
	}
}
