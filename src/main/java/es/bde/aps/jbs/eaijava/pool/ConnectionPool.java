/*
 * Creado el 23-oct-2007
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de c�digo
 */
package es.bde.aps.jbs.eaijava.pool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.Messages;
import es.bde.aps.jbs.eaijava.exception.EAIJavaException;
import es.bde.aps.jbs.eaijava.util.DESEncrypter;
import es.bde.aps.jbs.eaijava.util.PropertiesFactory;
import oracle.jdbc.pool.OracleDataSource;

/**
 * 
 * @author Roberto Pendolero Bonilla (infrpbx)
 * 
 */
public class ConnectionPool extends GenericObjectPool {

	private Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
	private String user;
	private String driverName;
	private String schemaOwner;
	private ConnectionFactory connectionFactory;

	/**
	 * Constructor
	 * 
	 * @param namePool
	 */
	protected ConnectionPool(String namePool) throws EAIJavaException {
		this.setTestOnBorrow(true);
		this.setTestOnReturn(true);

		// Se inicializa con el algoritmo de agotamiento de conexiones.
		this.setWhenExhaustedAction(WHEN_EXHAUSTED_GROW);

		// Se obtiene el n�mero de conexiones m�ximas abiertas
		setMaxActive(PropertiesFactory.getInt(namePool + PropertiesFactory.MAX_ACTIVE));

		// Se obtiene el n�mero de conexiones ocupadas a la vez.
		setMaxIdle(PropertiesFactory.getInt(namePool + PropertiesFactory.MAX_IDLE));

		// Se obtiene si las operaciones sobre la base de datos son autocommit
		boolean isAutoCommit = false;
		isAutoCommit = PropertiesFactory.getBoolean(namePool + PropertiesFactory.AUTO_COMMIT);

		schemaOwner = PropertiesFactory.getString(namePool + PropertiesFactory.SCHEMA_OWNER);

		// Se crea la factoria encargada para crear las conexiones a
		// base de datos.
		DataSource dataSource = createDataSource(namePool);
		connectionFactory = new ConnectionFactory(dataSource, isAutoCommit);

		// setFactory(connectionFactory);

	}

	/**
	 * @throws EncryptionException
	 * @throws CryptoException
	 * @throws EAIJavaException
	 * 
	 */
	private DataSource createDataSource(String namePool) throws EAIJavaException {
		// Se crea y se inicializa el dataSource para la conexi�n a base de
		// datos
		OracleDataSource dataSource;
		// dataSource.setDefaultAutoCommit(true);
		try {
			dataSource = new OracleDataSource();
		} catch (SQLException e1) {
			String message = Messages.getString("eaijava.errorDataSourceCreating", new String[] { e1.getMessage() });
			throw new EAIJavaException(message);
		}
		driverName = PropertiesFactory.getString(namePool + PropertiesFactory.DRIVER_NAME);
		if (driverName == null) {
			String message = Messages.getString("eaijava.errorDataSourceProperty", new String[] { namePool, PropertiesFactory.DRIVER_NAME });
			throw new EAIJavaException(message);
		}

		// dataSource.setDriverClassName(driverName);
		logger.debug(Messages.getString("eaijava.messageDataSourceDriver", new String[] { driverName }));

		user = PropertiesFactory.getString(namePool + PropertiesFactory.USER);
		if (user == null) {
			String message = Messages.getString("eaijava.errorDataSourceProperty", new String[] { namePool, PropertiesFactory.USER });
			throw new EAIJavaException(message);
		}
		dataSource.setUser(user);
		logger.debug(Messages.getString("eaijava.messageDataSourceUser", new String[] { user }));

		String connectUrl = PropertiesFactory.getString(namePool + PropertiesFactory.CONNECTION);
		if (connectUrl == null) {
			String message = Messages.getString("eaijava.errorDataSourceProperty", new String[] { namePool, PropertiesFactory.CONNECTION });
			throw new EAIJavaException(message);
		}
		dataSource.setURL(connectUrl);
		logger.debug(Messages.getString("eaijava.messageDataSourceUrl", new String[] { connectUrl }));
		// Se desencripta la password
		String passwordEncrypt = PropertiesFactory.getString(namePool + PropertiesFactory.PASSWORD);
		if (passwordEncrypt == null) {
			String message = Messages.getString("eaijava.errorDataSourceProperty", new String[] { namePool, PropertiesFactory.PASSWORD });
			throw new EAIJavaException(message);
		}
		DESEncrypter encrypter;
		String password = null;
		try {
			encrypter = DESEncrypter.instance(EAIConstants.SEED);
			password = encrypter.decrypt(passwordEncrypt);
		} catch (Exception e) {
			throw new EAIJavaException(e);
		}
		dataSource.setPassword(password);

		return dataSource;
	}

	public String getUser() {
		return user;
	}

	public String getDriverName() {
		return driverName;
	}

	public String getSchemaOwner() {
		return schemaOwner;
	}

	public Connection borrowObject() {
		// TODO Auto-generated method stub
		try {
			return (Connection) connectionFactory.makeObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void returnObject(Connection connection) {
		try {
			connectionFactory.destroyObject(connection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
