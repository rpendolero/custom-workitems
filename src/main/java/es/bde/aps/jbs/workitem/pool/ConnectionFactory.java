/*
 * Creado el 08-ene-2010
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de c�digo
 */
package es.bde.aps.jbs.workitem.pool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.pool.BasePoolableObjectFactory;

import oracle.jdbc.driver.OracleConnection;

/**
 * @author infrpbx
 * 
 *         TODO Para cambiar la plantilla de este comentario generado, vaya a
 *         Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de
 *         c�digo
 */
public class ConnectionFactory extends BasePoolableObjectFactory {

	private DataSource dataSource;
	private boolean isAutoCommit;

	/**
	 * @param dataSource
	 * @param isAutoCommit
	 */
	public ConnectionFactory(DataSource _dataSource, boolean _isAutoCommit) {
		dataSource = _dataSource;
		isAutoCommit = _isAutoCommit;
		// TODO Ap�ndice de constructor generado autom�ticamente
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see org.apache.commons.pool.PoolableObjectFactory#makeObject()
	 */
	public Object makeObject() throws Exception {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		OracleConnection conex = (OracleConnection) dataSource.getConnection();
		conex.setAutoCommit(isAutoCommit);

		return conex;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * org.apache.commons.pool.PoolableObjectFactory#destroyObject(java.lang.Object)
	 */
	public void destroyObject(Object conex) throws Exception {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		if (conex != null)
			((Connection) conex).close();

		conex = null;

	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see org.apache.commons.pool.PoolableObjectFactory#validateObject(java.lang.
	 * Object)
	 */

	public boolean validateObject(Object conex) {

		try {
			if (conex == null)
				return false;

			// TODO Ap�ndice de m�todo generado autom�ticamente
			if (((Connection) conex).isClosed())
				return false;
			else
				return true;

		} catch (SQLException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		return false;
	}

}
