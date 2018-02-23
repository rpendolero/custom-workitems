package es.bde.aps.jbs.eaijava.plsql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.Messages;
import es.bde.aps.jbs.eaijava.pool.ConnectionPool;
import es.bde.aps.jbs.eaijava.pool.ConnectionPoolFactory;
import es.bde.aps.jbs.eaijava.util.ClassSearchUtils;

/**
 * 
 * @author roberto
 *
 */
public class EAISQLProcedureWorkItemHandler implements WorkItemHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ConnectionPool pool;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kie.api.runtime.process.WorkItemHandler#abortWorkItem(org.kie.api.
	 * runtime .process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		long id = workItem.getId();
		manager.abortWorkItem(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kie.api.runtime.process.WorkItemHandler#executeWorkItem(org.kie.api.
	 * runtime.process.WorkItem, org.kie.api.runtime.process.WorkItemManager)
	 */
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Connection connection = null;

		String reference = String.valueOf(workItem.getProcessInstanceId());
		logger.info(Messages.getString("eaijava.messageExecute", new String[] { reference }));
		try {
			String procedure = (String) workItem.getParameter(EAIConstants.PARAM_PROCEDURE);
			String application = procedure.substring(0, 3).toLowerCase();
			Map<String, Object> parameters = workItem.getParameters();

			@SuppressWarnings("unchecked")
			List<Object> listInputFields = (List<Object>) parameters.get(EAIConstants.PARAM_LIST_IN);
			if (listInputFields == null) {
				listInputFields = new ArrayList<Object>();
				logger.info("Lista de campos de entrada vacios");
			} else {

				logger.info("Lista de campos de entrada [" + listInputFields.toString() + "]");
			}
			@SuppressWarnings("unchecked")
			List<Object> listOutputFields = (List<Object>) parameters.get(EAIConstants.PARAM_LIST_OUT);
			if (listOutputFields == null) {
				listOutputFields = new ArrayList<Object>();
				logger.info("Lista de campos de salida vacios");
			} else {

				logger.info("Lista de campos de salida [" + listOutputFields.toString() + "]");
			}
			// Se obtiene el pool y una conexion a la base de datos
			pool = ConnectionPoolFactory.getPool(application);

			connection = (Connection) pool.borrowObject();
			EAISQLControlDAO controlDAO = new EAISQLControlDAO(connection);
			Map<String, Object> results = controlDAO.executeProcedure(reference, procedure, listInputFields, listOutputFields);

			logger.info(Messages.getString("eaijava.messageExecuteProcedureOK", new String[] { reference, procedure, application }));
			manager.completeWorkItem(workItem.getId(), results);

		} catch (Exception e) {
			logger.error(Messages.getString("eaijava.errorExecute", new String[] { reference, e.getMessage() }));
			abortWorkItem(workItem, manager);
		} finally {
			if (pool != null) {
				if (connection != null)
					try {
						pool.returnObject(connection);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}

	}

	private void checkClassLoader() {
		ClassLoader classLoader = getClass().getClassLoader();
		String nameClass = "oracle.jdbc.pool.OracleDataSource";
		try {
			List list = ClassSearchUtils.searchClassPath("", "");
			for (Object object : list) {
				System.out.println(object);
			}
		} catch (Exception e) {
			System.out.println("Error al cargar la clase [" + nameClass + "] [" + e.getMessage() + "]");
		}
	}

}
