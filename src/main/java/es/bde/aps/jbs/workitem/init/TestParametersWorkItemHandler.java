package es.bde.aps.jbs.workitem.init;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.drools.core.spi.ProcessContext;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.workitem.exception.JBSException;
import es.bde.aps.jbs.workitem.util.ProcessContextFactory;

public class TestParametersWorkItemHandler implements WorkItemHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private KieSession ksession;

	public TestParametersWorkItemHandler(KieSession ksession) {
		this.ksession = ksession;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Map<String, Object> parameters = workItem.getParameters();
		Set<Entry<String, Object>> entrySet = parameters.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			logger.info("Parametro name [" + key + "]");
			Object parameter = parameters.get(key);
			logger.info("Parametro value [" + parameter + "]");
		}

		Map<String, Object> results = workItem.getResults();
		entrySet = results.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			logger.info("Result name [" + key + "]");
			Object parameter = parameters.get(key);
			logger.info("Result value [" + parameter + "]");
		}

		try {
			printProcessVariables(workItem);
		} catch (JBSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printProcessVariables(WorkItem workItem) throws JBSException {
		ProcessContext kcontext = ProcessContextFactory.createProcessContext(ksession, workItem);
		if (kcontext == null) {
			throw new JBSException("No se pudo crear el kie context");
		}
		ProcessInstance processInstance = kcontext.getProcessInstance();
		Object variable = kcontext.getVariable("PARAMETER");
		System.out.println(variable);
		variable = kcontext.getVariable("PARAMETER2");
		System.out.println(variable);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}

}
