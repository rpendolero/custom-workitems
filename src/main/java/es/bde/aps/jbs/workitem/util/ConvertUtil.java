package es.bde.aps.jbs.workitem.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.bde.aps.jbs.workitem.interfaces.IField;

/**
 * Clase encargada de realiza las conversiones de tipo utilizados en los EAIJava
 * a los tipos utilizados por ORACLE.
 * 
 * @author infrpbx - Roberto Pendolero
 * 
 */
public class ConvertUtil {
	public static final String ARRAY_STRING = "VARCHARARRAY";

	public static final String ARRAY_INTEGER = "INTEGERARRAY";

	public static final String ARRAY_DOUBLE = "DOUBLEARRAY";

	public static final String ARRAY_DATE = "DATEARRAY";

	public static final String ARRAY_TIME = "TIMEARRAY";

	public static final String ARRAY_DATETIME = "DATETIMEARRAY";

	// Se utiliza para la conversi�n de los datos del tipo "String" a "Date"
	private static SimpleDateFormat oDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	// Se utiliza para la conversi�n de los datos del tipo "String" a "Time"
	private static SimpleDateFormat oTimeFormat = new SimpleDateFormat("HH:mm");

	// Se utiliza para la conversi�n de los datos del tipo "String" a
	// "DateTime"
	private static SimpleDateFormat oDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	/**
	 * M�todo que devuelve el valor de un objeto Field en el formato interpretable
	 * en SQL.
	 * 
	 * @param field
	 *            objeto del tipo com.staffare.eaijava.Field
	 * @return
	 * @throws ParseException
	 */
	public static Object getObjectSQL(IField field) throws ParseException {
		if (field == null || field.getValue() == null || "".equals(field.getValue()))
			return null;

		return getObjectSQL(field.getType(), field.getValue());

	}

	/**
	 * M�todo que devuelve el tipo ARRAY de SQL que corresponde el tipo del campo
	 * que se recibe por par�metro.
	 * 
	 * @param field
	 *            objeto del tipo com.staffare.eaijava.Field
	 * @return
	 */
	public static String getTypeArraySQL(IField field) {

		char type = field.getType();
		switch (type) {
		case IField.STRING:
			return ARRAY_STRING;
		case IField.DATE:
			return ARRAY_DATE;
		case IField.DATETIME:
			return ARRAY_DATETIME;
		case IField.TIME:
			return ARRAY_TIME;
		case IField.DOUBLE:
			return ARRAY_DOUBLE;
		case IField.INTEGER:
			return ARRAY_INTEGER;
		default:
			return ARRAY_STRING;
		}
	}

	/**
	 * M�todo que convierte el objeto gen�rico que se recibe por par�metro,
	 * teniendo en cuenta el identificador de tipo del eaijava que tambi�n se
	 * recibe por par�metro.
	 * 
	 * @param type
	 *            identificador del tipo (A,D,T,O,N,R)
	 * @param obj
	 *            objeto que se realiza la conversi�n
	 * @return
	 * @throws ParseException
	 */
	public static Object getObjectJava(char type, Serializable obj) throws ParseException {

		if (obj == null)
			return "";

		Object value = null;
		switch (type) {
		case IField.STRING:
			// Tipo Texto
			value = obj.toString();
			break;
		case IField.DATE:
			// Tipo Fecha
			if (obj instanceof Timestamp) {
				long time = ((Timestamp) obj).getTime();
				value = new java.util.Date(time);
			} else {
				if (obj instanceof Date) {
					long time = ((Date) obj).getTime();
					value = new java.util.Date(time);

				} else {
					if (obj instanceof String)
						value = oDateFormat.parse((String) obj);
				}
			}

			break;
		case IField.TIME:
			// Tipo Hora
			if (obj instanceof Timestamp) {
				long time = ((Timestamp) obj).getTime();
				value = new java.util.Date(time);
			} else {
				if (obj instanceof Date) {
					long time = ((Date) obj).getTime();
					value = new java.util.Date(time);

				} else {
					if (obj instanceof Time) {
						long time = ((Time) obj).getTime();
						value = new java.util.Date(time);
					} else {
						if (obj instanceof String) {
							value = oTimeFormat.parse((String) obj);
						}
					}
				}
			}

			break;
		case IField.DATETIME:
			// Tipo Timestamp
			if (obj instanceof Timestamp) {
				long time = ((Timestamp) obj).getTime();
				value = new java.util.Date(time);
			} else {
				if (obj instanceof Date) {
					long time = ((Date) obj).getTime();
					value = new java.util.Date(time);

				} else {
					if (obj instanceof String) {
						value = oDateTimeFormat.parse((String) obj);
					}
				}
			}
			break;
		case IField.INTEGER:
			return new Integer(((BigDecimal) obj).intValue());
		case IField.DOUBLE:
			return new Double(((BigDecimal) obj).doubleValue());
		default:
			// Tipo Texto
			value = obj.toString();
			break;
		}
		return value;

	}

	/**
	 * M�todo que devuelve el tipo de ORACLE al que correponde el tipo de TIBCO
	 * que se recibe por par�metro.
	 * 
	 * @param type
	 *            tipo de utilizado en los EAIJava de TIBCO.
	 * @return
	 * @throws ParseException
	 */
	public static int getTypeSQL(char type) {
		switch (type) {
		case IField.STRING:
			// Tipo Texto
			return Types.VARCHAR;
		case IField.DATE:
			// Tipo Fecha
			return Types.DATE;
		case IField.TIME:
			// Tipo Hora
			return Types.TIME;
		case IField.DATETIME:
			// Tipo Timestamp
			return Types.TIMESTAMP;
		case IField.INTEGER:
			// Tipo Numerico
			return Types.NUMERIC;
		case IField.DOUBLE:
			// Tipo Numerico (float)
			return Types.DECIMAL;
		default:
			// Tipo Texto
			return Types.VARCHAR;
		}

	}

	/**
	 * 
	 * @param type
	 * @param arrayOracle
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getObjectJava(char type, Array arrayOracle) throws Exception {
		List<Object> listValues = new ArrayList<Object>();
		if (arrayOracle != null) {
			Object[] array = (Object[]) arrayOracle.getArray();
			if (array == null)
				return listValues;

			for (int k = 0; k < array.length; k++) {
				Object value = getObjectJava(type, (Serializable) array[k]);
				listValues.add(value);
			}

		}
		return listValues;
	}

	/**
	 * 
	 * @param valoresArray
	 * @return
	 * @throws ParseException
	 */
	public static Object[] getObjectSQL(char type, List<Object> valoresArray) throws ParseException {
		Object[] valores = new Object[valoresArray.size()];
		int i = 0;
		for (Object valor : valoresArray) {
			valores[i] = getObjectSQL(type, valor);
			i++;
		}
		return valores;
	}

	/**
	 * 
	 * @param type
	 * @param valor
	 * @return
	 * @throws ParseException
	 */
	private static Object getObjectSQL(char type, Object valor) throws ParseException {
		switch (type) {
		case IField.STRING:
			// Tipo Texto
			return valor;
		case IField.DATE:
			// Tipo Fecha
			return new Date(oDateFormat.parse((String) valor).getTime());
		case IField.TIME:
			// Tipo Hora
			return new Time(oTimeFormat.parse((String) valor).getTime());
		case IField.DATETIME:
			// Tipo Timestamp (float)
			return new Timestamp(oDateTimeFormat.parse((String) valor).getTime());
		case IField.INTEGER:
			return new Integer((String) valor);
		case IField.DOUBLE:
			// Tipo Numerico (float)
			return new Double((String) valor);
		default:
			return valor;
		}
	}

	/**
	 * 
	 * @param type
	 * @param valueReceived
	 * @return
	 */
	public static String getString(char type, Object valueReceived) {
		switch (type) {
		case IField.STRING:
			// Tipo Texto
			return valueReceived.toString();
		case IField.DATE:
			// Tipo Fecha
			return oDateFormat.format((java.util.Date) valueReceived);
		case IField.TIME:
			// Tipo Hora
			return oTimeFormat.format((java.util.Date) valueReceived);
		case IField.DATETIME:
			// Tipo Timestamp (float)
			return oDateTimeFormat.format((java.util.Date) valueReceived);
		case IField.INTEGER:
			return valueReceived.toString();
		case IField.DOUBLE:
			// Tipo Numerico (float)
			return valueReceived.toString();
		default:
			return valueReceived.toString();
		}
	}

	public static List<String> getString(char type, List<Object> values) {
		List<String> listValuesString = new ArrayList<String>();
		for (Object value : values) {
			listValuesString.add(getString(type, value));
		}
		return listValuesString;

	}
}
