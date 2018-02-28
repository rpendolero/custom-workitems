package es.bde.aps.jbs.eaijava.plsql;

import java.io.Serializable;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bde.aps.jbs.eaijava.EAIConstants;
import es.bde.aps.jbs.eaijava.Messages;
import es.bde.aps.jbs.eaijava.exception.EAIJavaException;
import es.bde.aps.jbs.eaijava.interfaces.FieldArray;
import es.bde.aps.jbs.eaijava.interfaces.IField;
import es.bde.aps.jbs.eaijava.util.ConvertUtil;
import es.bde.aps.jbs.eaijava.util.Properties;
import es.bde.aps.jbs.eaijava.util.PropertiesFactory;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

/**
 * Clase encargada de realizar el acceso a la base de datos para ejecutar un
 * procedimiento almacenado y recuperar la respuesta del mismo.
 * 
 * @author Roberto Pendolero Bonilla (infrpbx)
 * 
 */
public class EAISQLControlDAO {
	private Connection connection;

	private Logger logger = LoggerFactory.getLogger(EAISQLControlDAO.class);

	/**
	 * Constructor que recibe una conexi�n a una base de datos.
	 * 
	 * @param connection
	 *            Conexi�n a una base de datos
	 * @throws EAIJavaException
	 */
	public EAISQLControlDAO(Connection _connection) throws EAIJavaException {
		if (_connection == null) {
			String message = Messages.getString("eaijava.errorConnectionNull");
			throw new EAIJavaException(message);
		}
		connection = _connection;

		// TODO Ap�ndice de constructor generado autom�ticamente
	}

	/**
	 * M�todo que invoca al procedimiento almacenado que se recibe por
	 * par�metro
	 * 
	 * @param reference
	 * @param procedure
	 * @param user
	 * @param inputFields
	 * @param outputFields
	 * @return
	 * @throws EAIJavaException
	 */
	public Map<String, Object> executeProcedure(String reference, String procedure, List<Object> inputFields, List<Object> outputFields) throws EAIJavaException {

		String sql = generateSql(procedure, inputFields, outputFields);

		logger.info(Messages.getString("eaijava.messageExecuteSql",  reference, procedure, inputFields.toString(), outputFields.toString() ));
		OracleCallableStatement stmt = null;
		try {
			stmt = (OracleCallableStatement) connection.prepareCall(sql);
			registerParameters(stmt, inputFields, outputFields);
			stmt.execute();
			Map<String, Object> response = getParametersOut(stmt, inputFields, outputFields);
			logger.info(Messages.getString("eaijava.messageExecuteSqlResponse", reference, procedure, response.toString() ));
			return response;

		} catch (SQLException e) {
			throw new EAIJavaException(e);
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e1) {
					// TODO Bloque catch generado autom�ticamente
					logger.error("Error to close of statement", e1);
				}
		}
	}

	/**
	 * M�todo que registra los par�metros tanto de entradas como de salida.
	 * 
	 * @param stmt
	 * @param inputFields
	 * @param outputFields
	 * @throws EAIJavaException
	 */

	private void registerParameters(CallableStatement stmt, List<Object> inputFields, List<Object> outputFields) throws EAIJavaException {

		int numFields = 1;
		if (inputFields != null) {
			numFields = setInputParameters(stmt, inputFields, numFields);
		}

		if (outputFields != null) {
			registerOutParameters(stmt, outputFields, numFields);
		}

	}

	/**
	 * 
	 * @param stmt
	 * @param inputFields
	 * @param numFields
	 * @return
	 * @throws EAIJavaException
	 */
	private int setInputParameters(CallableStatement stmt, List<Object> inputFields, int numFields) throws EAIJavaException {

		for (int i = 0; i < inputFields.size(); i++) {
			IField field = (IField) inputFields.get(i);
			try {
				if (field instanceof FieldArray) {
					setInputParameterArray(stmt, field, numFields);
				} else {
					setInputParameter(stmt, field, numFields);
				}
				numFields++;

			} catch (Exception e) {
				String message = Messages.getString("eaijava.errorInputParameter", new String[] { field.getName() });
				throw new EAIJavaException(message, e);
			}
		}
		return numFields;

	}

	/**
	 * 
	 * @param stmt
	 * @param field
	 * @param numCampos
	 * @param valoresArray
	 * @throws SQLException
	 * @throws ParseException
	 */
	private void setInputParameterArray(CallableStatement stmt, IField field, int posicion) throws Exception {
		String name = field.getName();
		String type = ConvertUtil.getTypeArraySQL(field);

		logger.info(Messages.getString("eaijava.messageRegisteringInputParameters", new String[] { name, type }));

		@SuppressWarnings("unchecked")
		List<Object> valoresArray = (List<Object>) field.getValue();
		Object[] values = valoresArray.toArray(new Object[valoresArray.size()]);
		values = (Object[]) ConvertUtil.getObjectSQL(field.getType(), valoresArray);

		ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor(type, connection);
		ARRAY array = new ARRAY(descriptor, connection, values);

		stmt.setArray(posicion, array);
		logger.info(Messages.getString("eaijava.messageRegisteredInputParameters", new String[] { name }));
	}

	/**
	 * 
	 * @param stmt
	 * @param field
	 * @param numCampos
	 * @throws ParseException
	 * @throws SQLException
	 */
	private void setInputParameter(CallableStatement stmt, IField field, int posicion) throws ParseException, SQLException {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		Object value = ConvertUtil.getObjectSQL(field);
		char type = field.getType();
		logger.info(Messages.getString("eaijava.messageRegisteringInputParameters", new String[] { field.getName(), String.valueOf(type), String.valueOf(posicion) }));
		if (value == null) {
			stmt.setNull(posicion, ConvertUtil.getTypeSQL(type));
		} else {

			stmt.setObject(posicion, value);
		}

	}

	/**
	 * 
	 * @param posicion
	 * @param outputFields
	 * @param stmt
	 * @throws EAIJavaException
	 */
	private void registerOutParameters(CallableStatement stmt, List<Object> outputFields, int posicion) throws EAIJavaException {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		for (int i = 0; i < outputFields.size(); i++) {
			IField field = (IField) outputFields.get(i);
			char type = field.getType();
			logger.info(Messages.getString("eaijava.messageRegisteringOutputParameters", new String[] { field.getName(), String.valueOf(type), String.valueOf(posicion) }));

			try {
				if (field instanceof FieldArray) {
					String sqlType = ConvertUtil.getTypeArraySQL(field);
					stmt.registerOutParameter(posicion, OracleTypes.ARRAY, sqlType);
				} else {
					int sqlType = ConvertUtil.getTypeSQL(type);
					stmt.registerOutParameter(posicion, sqlType);
				}
				logger.info(Messages.getString("eaijava.messageRegisteredOutputParameters", new String[] { field.getName() }));
			} catch (SQLException e) {
				// TODO Bloque catch generado autom�ticamente
				String message = Messages.getString("eaijava.errorOutputParameter", new String[] { field.getName() });
				throw new EAIJavaException(message, e);
			}
			posicion++;

		}

	}

	/**
	 * Metodo que devuelve en un HashMap los valores de los parametros de la
	 * invocacion a PL/SQL.
	 * 
	 * @param stmt
	 *            Statement
	 * @param inputFields
	 * @param outputFields
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	private Map<String, Object> getParametersOut(CallableStatement stmt, List<Object> inputFields, List<Object> outputFields) throws EAIJavaException {
		Map<String, Object> hParametersOut = new HashMap<String, Object>();
		if (outputFields == null || outputFields.size() == 0)
			return hParametersOut;

		try {
			int pos = 0;
			if (inputFields.size() > 0) {
				for (int i = 0; i < inputFields.size(); i++) {
					IField field = (IField) inputFields.get(i);
					pos++;
				}
			}
			pos++;
			// int pos = (inputFields == null ? 1 : inputFields.size() + 1);
			for (int i = 0; i < outputFields.size(); i++) {
				IField field = (IField) outputFields.get(i);
				String nameParameter = field.getName();
				char type = field.getType();
				logger.info(Messages.getString("eaijava.messageGettingOutputParameters", new String[] { field.getName(), String.valueOf(type) }));
				if (field instanceof FieldArray) {
					Array arrayOracle = stmt.getArray(pos + i);
					List<Object> arrayValues = ConvertUtil.getObjectJava(type, arrayOracle);
					hParametersOut.put(nameParameter, arrayValues);
					logger.info(Messages.getString("eaijava.messageGettedOutputParameters", new String[] { nameParameter, arrayValues.toString() }));

				} else {
					Serializable valueOracle = (Serializable) stmt.getObject(pos + i);
					Object value = ConvertUtil.getObjectJava(type, valueOracle);
					hParametersOut.put(nameParameter, value);
					logger.info(Messages.getString("eaijava.messageGettedOutputParameters", new String[] { field.getName(), String.valueOf(valueOracle) }));
				}
			}
		} catch (Exception e) {
			throw new EAIJavaException("Error al obtener los parametros de salida [" + e.getMessage() + "]");
		}
		return hParametersOut;

	}

	/**
	 * 
	 * @param procedure
	 * @param user
	 * @param inputFields
	 * @param outputFields
	 * @return
	 * @throws EAIJavaException
	 */

	private String generateSql(String procedure, List<Object> inputFields, List<Object> outputFields) throws EAIJavaException {

		Properties properties = PropertiesFactory.getProperties(EAIConstants.PROPERTIES_DATABASE);
		String application = procedure.substring(0, 3).toLowerCase();
		String keyProperty = application + EAIConstants.SCHEMA_OWNER;
		String user = properties.getString(keyProperty);

		StringBuffer strSql = new StringBuffer("{call ");
		strSql.append(user).append(".").append(procedure.toUpperCase()).append("(");
		int numParameters = 0;
		if (inputFields != null && inputFields.size() > 0) {
			numParameters = inputFields.size();
		}
		if (outputFields != null) {
			numParameters = numParameters + outputFields.size();
		}
		for (int i = 0; i < numParameters; i++) {
			strSql.append("?");
			if (i != numParameters - 1) {
				strSql.append(",");
			}

		}
		strSql.append(")}");
		logger.debug(Messages.getString("eaijava.messageSqlGenerated", new String[] { String.valueOf(strSql) }));
		return strSql.toString();
	}

}
