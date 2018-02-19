package es.bde.aps.jbs.eaijava.pool;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.eaijava.Messages;
import es.bde.aps.jbs.eaijava.exception.EAIJavaException;

/**
 * Clase cuya funci�n es la crear el conjunto de objetos del tipo
 * <code>ConnectionPool</code>. Dispone de un tabla Hash donde se guardan los
 * diferentes "pool" de conexiones a BBDD que se crean.
 * 
 * @author Roberto Pendolero Bonilla (infrpbx)
 * 
 */
public class ConnectionPoolFactory {

	private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

	private static Hashtable<String, ConnectionPool> hPools = new Hashtable<String, ConnectionPool>();

	private static Object mutex = new Object();

	/**
	 * M�todo que devuelve una referencia del pool asociado al identificador que se
	 * recibe por par�metro. Si no existe el "pool" lo intenta crear, devolviendo
	 * una excepci�n del tipo <code>EAIJavaException</code> en el caso que ocurra
	 * cualquier error.
	 * 
	 * @param namePool
	 * @return
	 * @throws EAIJavaException
	 */
	public static ConnectionPool getPool(String namePool) throws EAIJavaException {
		if (namePool == null) {
			String message = Messages.getString("eaijava.errorPoolReferenceNameNull");
			throw new EAIJavaException(message);
		}
		ConnectionPool pool = null;
		synchronized (mutex) {
			logger.debug(Messages.getString("eaijava.messageConnectionPoolGetting", new String[] { namePool }));
			pool = (ConnectionPool) hPools.get(namePool);
			if (pool == null) {
				logger.debug(Messages.getString("eaijava.messageConnectionPoolNotFound", new String[] { namePool }));
				pool = new ConnectionPool(namePool);
				hPools.put(namePool, pool);

			} else {
				logger.debug("Devolviendo el pool de la aplicacion [" + namePool + "]");
				return pool;

			}

		}
		return pool;

	}
}
