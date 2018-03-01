package es.bde.aps.jbs.workitem.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.definition.process.Process;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.WorkflowProcessInstance;

public class TestProcessInstance implements WorkflowProcessInstance {
	private Process process;
	private Map<String, Object> variables = new HashMap<String, Object>();

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getParentProcessInstanceId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Process getProcess() {
		// TODO Auto-generated method stub
		return process;
	}

	public String getProcessId() {
		// TODO Auto-generated method stub
		return process.getId();
	}

	public String getProcessName() {
		// TODO Auto-generated method stub
		return process.getName();
	}

	public int getState() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String[] getEventTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	public void signalEvent(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public NodeInstance getNodeInstance(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<NodeInstance> getNodeInstances() {
		// TODO Auto-generated method stub
		return new ArrayList<NodeInstance>();
	}

	public Object getVariable(String name) {
		// TODO Auto-generated method stub
		return variables.get(name);
	}

	public void setVariable(String name, Object value) {
		variables.put(name, value);

	}

}
