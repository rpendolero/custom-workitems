package es.bde.aps.jbs.eaijava.plsql;

import org.apache.log4j.Logger;
import org.drools.core.spi.ProcessContext;

public class EAISQLProcedurePlugin {

	private Logger logger = Logger.getLogger(EAISQLProcedurePlugin.class);

	/**
	 * 
	 * @param requestlvmtso
	 * @return
	 */
	public Response execute(Request request) {
		ProcessContext context = request.getContext();

		return new Response();
	}
}
