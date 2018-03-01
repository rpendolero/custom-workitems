package es.bde.aps.jbs.workitem.plsql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drools.core.spi.ProcessContext;
import org.jbpm.process.workitem.AbstractLogOrThrowWorkItemHandler;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.Messages;
import es.bde.aps.jbs.workitem.interfaces.IField;
import es.bde.aps.jbs.workitem.pool.ConnectionPool;
import es.bde.aps.jbs.workitem.pool.ConnectionPoolFactory;
import es.bde.aps.jbs.workitem.util.ProcessContextFactory;

/**
 * 
 * @author roberto
 *
 */
public class EAISQLProcedureWorkItemHandler extends AbstractLogOrThrowWorkItemHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ConnectionPool pool;
	private KieSession ksession;

	public EAISQLProcedureWorkItemHandler(KieSession ksession) {
		this.ksession = ksession;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kie.api.runtime.process.WorkItemHandler#abortWorkItem(org.kie.api.
	 * runtime .process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		long id = workItem.getId();
		manager.abortWorkItem(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kie.api.runtime.process.WorkItemHandler#executeWorkItem(org.kie.api.
	 * runtime.process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Connection connection = null;

		String reference = String.valueOf(workItem.getProcessInstanceId());
		logger.info(Messages.getString("eaijava.messageExecute", reference));
		try {
			String procedure = (String) workItem.getParameter(EAIConstants.PARAM_PROCEDURE);
			String application = procedure.substring(0, 3).toLowerCase();

			List<IField> fieldsInput = loadFields(workItem, EAIConstants.PARAM_LIST_IN);
			List<IField> fieldsOuput = loadFields(workItem, EAIConstants.PARAM_LIST_OUT);

			// Se obtiene el pool y una conexion a la base de datos
			pool = ConnectionPoolFactory.getPool(application);

			connection = (Connection) pool.borrowObject();
			EAISQLControlDAO controlDAO = new EAISQLControlDAO(connection);
			Map<String, Object> results = controlDAO.executeProcedure(reference, procedure, fieldsInput, fieldsOuput);
			assignFieldValues(workItem, fieldsOuput, results);

			logger.info(Messages.getString("eaijava.messageExecuteProcedureOK", reference, procedure, application));
			manager.completeWorkItem(workItem.getId(), results);

		} catch (Exception e) {
			logger.error(Messages.getString("eaijava.errorExecute", reference, e.getMessage()));
			handleException(e);
		} finally {
			if (pool != null) {
				if (connection != null)
					try {
						pool.returnObject(connection);
					} catch (Exception e) {
						handleException(e);
					}
			}
		}

	}

	/**
	 * 
	 * @param fieldsOuput
	 * @param results
	 */
	private void assignFieldValues(WorkItem workItem, List<IField> fieldsOuput, Map<String, Object> results) {
		ProcessContext kcontext = ProcessContextFactory.createProcessContext(ksession, workItem);
		for (IField field : fieldsOuput) {
			String fieldName = field.getName();
			Object value = results.get(fieldName);
			kcontext.setVariable(fieldName, value);
		}
	}

	/**
	 * 
	 * @param workItem
	 * @param parameterName
	 * @return
	 */
	private List<IField> loadFields(WorkItem workItem, String parameterName) {

		@SuppressWarnings("unchecked")
		List<IField> listFields = (List<IField>) workItem.getParameter(parameterName);
		if (listFields == null) {
			listFields = new ArrayList<IField>();
			logger.info("Lista de campos de entrada vacios");
		} else {
			logger.info("Lista de campos de entrada [" + listFields.toString() + "]");
		}
		return listFields;
	}

}
