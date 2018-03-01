package es.bde.aps.jbs.workitem.plsql;

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

import es.bde.aps.jbs.workitem.EAIConstants;
import es.bde.aps.jbs.workitem.Messages;
import es.bde.aps.jbs.workitem.exception.JBSException;
import es.bde.aps.jbs.workitem.interfaces.FieldArray;
import es.bde.aps.jbs.workitem.interfaces.IField;
import es.bde.aps.jbs.workitem.util.ConvertUtil;
import es.bde.aps.jbs.workitem.util.Properties;
import es.bde.aps.jbs.workitem.util.PropertiesFactory;
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
	 * @throws JBSException
	 */
	public EAISQLControlDAO(Connection _connection) throws JBSException {
		if (_connection == null) {
			String message = Messages.getString("eaijava.errorConnectionNull");
			throw new JBSException(message);
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
	 * @param fieldsInput
	 * @param fieldsOuput
	 * @return
	 * @throws JBSException
	 */
	public Map<String, Object> executeProcedure(String reference, String procedure, List<IField> fieldsInput, List<IField> fieldsOuput) throws JBSException {

		String sql = generateSql(procedure, fieldsInput, fieldsOuput);

		logger.info(Messages.getString("eaijava.messageExecuteSql",  reference, procedure, fieldsInput.toString(), fieldsOuput.toString() ));
		OracleCallableStatement stmt = null;
		try {
			stmt = (OracleCallableStatement) connection.prepareCall(sql);
			registerParameters(stmt, fieldsInput, fieldsOuput);
			stmt.execute();
			Map<String, Object> response = getParametersOut(stmt, fieldsInput, fieldsOuput);
			logger.info(Messages.getString("eaijava.messageExecuteSqlResponse", reference, procedure, response.toString() ));
			return response;

		} catch (SQLException e) {
			throw new JBSException(e);
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
	 * @param fieldsInput
	 * @param fieldsOuput
	 * @throws JBSException
	 */

	private void registerParameters(CallableStatement stmt, List<IField> fieldsInput, List<IField> fieldsOuput) throws JBSException {

		int numFields = 1;
		if (fieldsInput != null) {
			numFields = setInputParameters(stmt, fieldsInput, numFields);
		}

		if (fieldsOuput != null) {
			registerOutParameters(stmt, fieldsOuput, numFields);
		}

	}

	/**
	 * 
	 * @param stmt
	 * @param fieldsInput
	 * @param numFields
	 * @return
	 * @throws JBSException
	 */
	private int setInputParameters(CallableStatement stmt, List<IField> fieldsInput, int numFields) throws JBSException {

		for (int i = 0; i < fieldsInput.size(); i++) {
			IField field = (IField) fieldsInput.get(i);
			try {
				if (field instanceof FieldArray) {
					setInputParameterArray(stmt, field, numFields);
				} else {
					setInputParameter(stmt, field, numFields);
				}
				numFields++;

			} catch (Exception e) {
				String message = Messages.getString("eaijava.errorInputParameter", new String[] { field.getName() });
				throw new JBSException(message, e);
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
	 * @param fieldsOuput
	 * @param stmt
	 * @throws JBSException
	 */
	private void registerOutParameters(CallableStatement stmt, List<IField> fieldsOuput, int posicion) throws JBSException {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		for (int i = 0; i < fieldsOuput.size(); i++) {
			IField field = (IField) fieldsOuput.get(i);
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
				throw new JBSException(message, e);
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
	 * @param fieldsInput
	 * @param fieldsOuput
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	private Map<String, Object> getParametersOut(CallableStatement stmt, List<IField> fieldsInput, List<IField> fieldsOuput) throws JBSException {
		Map<String, Object> hParametersOut = new HashMap<String, Object>();
		if (fieldsOuput == null || fieldsOuput.size() == 0)
			return hParametersOut;

		try {
			int pos = 0;
			if (fieldsInput.size() > 0) {
				for (int i = 0; i < fieldsInput.size(); i++) {
					IField field = (IField) fieldsInput.get(i);
					pos++;
				}
			}
			pos++;
			// int pos = (inputFields == null ? 1 : inputFields.size() + 1);
			for (int i = 0; i < fieldsOuput.size(); i++) {
				IField field = (IField) fieldsOuput.get(i);
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
			throw new JBSException("Error al obtener los parametros de salida [" + e.getMessage() + "]");
		}
		return hParametersOut;

	}

	/**
	 * 
	 * @param procedure
	 * @param user
	 * @param fieldsInput
	 * @param fieldsOuput
	 * @return
	 * @throws JBSException
	 */

	private String generateSql(String procedure, List<IField> fieldsInput, List<IField> fieldsOuput) throws JBSException {

		Properties properties = PropertiesFactory.getProperties(EAIConstants.PROPERTIES_DATABASE);
		String application = procedure.substring(0, 3).toLowerCase();
		String keyProperty = application + EAIConstants.SCHEMA_OWNER;
		String user = properties.getString(keyProperty);

		StringBuffer strSql = new StringBuffer("{call ");
		strSql.append(user).append(".").append(procedure.toUpperCase()).append("(");
		int numParameters = 0;
		if (fieldsInput != null && fieldsInput.size() > 0) {
			numParameters = fieldsInput.size();
		}
		if (fieldsOuput != null) {
			numParameters = numParameters + fieldsOuput.size();
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
