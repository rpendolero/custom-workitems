package es.bde.aps.jbs.eaijava.plsql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.eaijava.Messages;
import es.bde.aps.jbs.eaijava.pool.ConnectionPool;
import es.bde.aps.jbs.eaijava.pool.ConnectionPoolFactory;
import es.bde.aps.jbs.eaijava.util.PropertiesFactory;

/**
 * 
 * @author roberto
 *
 */
public class EAISQLProcedureWorkItemHandler implements WorkItemHandler {
	private static final String PARAM_PROCEDURE = "Procedure";
	private static final Object PARAM_LIST_IN = "Input Fields";
	private static final Object PARAM_LIST_OUT = "Output Fields";

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ConnectionPool pool;

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		try {

			String reference = String.valueOf(workItem.getProcessInstanceId());
			logger.info(Messages.getString("eaijava.messageExecute", new String[] { reference }));

			String procedure = (String) workItem.getParameter(PARAM_PROCEDURE);
			String application = workItem.getName().substring(0, 3);
			Map<String, Object> parameters = workItem.getParameters();

			List<Object> listInputFields = (List<Object>) parameters.get(PARAM_LIST_IN);
			if (listInputFields == null) {
				logger.info("Lista de campos de entrada vacios");
			} else {
				logger.info("Lista de campos de entrada [" + listInputFields.toString() + "]");
			}
			List<Object> listOutputFields = (List<Object>) parameters.get(PARAM_LIST_OUT);
			if (listOutputFields == null) {
				logger.info("Lista de campos de salida vacios");
			} else {
				logger.info("Lista de campos de salida [" + listOutputFields.toString() + "]");
			}

			// Se busca la propiedad que define el usuario de la base de datos

			String keyProperty = application + PropertiesFactory.SCHEMA_OWNER;
			String user = PropertiesFactory.getString(keyProperty);

			// Se obtiene el pool y una conexi�n a la base de datos
			pool = ConnectionPoolFactory.getPool(application);

			Connection connection = (Connection) pool.borrowObject();
			EAISQLControlDAO controlDAO = new EAISQLControlDAO(connection);
			Map<String, Object> results = controlDAO.executeProcedure(reference, procedure, user, listInputFields, listOutputFields);

			logger.info(Messages.getString("eaijava.messageExecuteProcedureOK", new String[] { reference, procedure, application }));
			manager.completeWorkItem(workItem.getId(), results);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
