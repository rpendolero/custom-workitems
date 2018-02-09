package es.bde.aps.jbs.eaijava.util;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import es.bde.aps.jbs.eaijava.interfaces.IField;

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

	// Se utiliza para la conversi�n de los datos del tipo "String" a "Date"
	private static SimpleDateFormat oDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	// Se utiliza para la conversi�n de los datos del tipo "String" a "Time"
	private static SimpleDateFormat oTimeFormat = new SimpleDateFormat("HH:mm");

	// Se utiliza para la conversi�n de los datos del tipo "String" a "DateTime"
	private static SimpleDateFormat oDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private static DecimalFormat oDecimalFormat = new DecimalFormat("#.#");

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

		switch (field.getType()) {
		case 'A':
			// Tipo Texto
			return field.getValue();
		case 'D':
			// Tipo Fecha
			return new Date(oDateFormat.parse((String) field.getValue()).getTime());
		case 'T':
			// Tipo Hora
			return new Time(oTimeFormat.parse((String) field.getValue()).getTime());
		case 'O':
			// Tipo Timestamp (float)
			return new Timestamp(oDateTimeFormat.parse((String) field.getValue()).getTime());
		case 'N':
		case 'R':
			// Tipo Numerico (float)
			return new Double((Double) field.getValue());
		default:
			return field.getValue();
		}

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
		case 'A':
			return ARRAY_STRING;
		case 'D':
			return ARRAY_DATE;
		case 'T':
			return ARRAY_TIME;
		case 'R':
			return ARRAY_DOUBLE;
		case 'N':
			return ARRAY_INTEGER;
		default:
			return ARRAY_STRING;
		}
	}

	/**
	 * M�todo que convierte el objeto gen�rico que se recibe por par�metro, teniendo
	 * en cuenta el identificador de tipo del eaijava que tambi�n se recibe por
	 * par�metro.
	 * 
	 * @param type
	 *            identificador del tipo (A,D,T,O,N,R)
	 * @param obj
	 *            objeto que se realiza la conversi�n
	 * @return
	 */
	public static Object getObjectSSO(char type, Object obj) {

		if (obj == null)
			return "";

		String value = null;
		switch (type) {
		case 'A':
			// Tipo Texto
			value = obj.toString();
			break;
		case 'D':
			// Tipo Fecha
			if (obj instanceof Timestamp) {
				long time = ((Timestamp) obj).getTime();
				value = oDateFormat.format(new java.util.Date(time));
			} else {
				if (obj instanceof Date) {
					value = oDateFormat.format((Date) obj);

				} else {
					value = obj.toString();
				}
			}

			break;
		case 'T':
			// Tipo Hora
			if (obj instanceof Timestamp) {
				long time = ((Timestamp) obj).getTime();
				value = oTimeFormat.format(new java.util.Date(time));
			} else {
				if (obj instanceof Date) {
					value = oTimeFormat.format((Date) obj);

				} else {
					value = obj.toString();
				}
			}

			break;
		case 'O':
			// Tipo Timestamp
			if (obj instanceof Timestamp) {
				long time = ((Timestamp) obj).getTime();
				value = oDateTimeFormat.format(new java.util.Date(time));
			} else {
				if (obj instanceof Date) {
					value = oDateTimeFormat.format((Date) obj);

				} else {
					value = obj.toString();
				}
			}
			break;
		case 'N':
		case 'R':
			return oDecimalFormat.format(obj);
		default:
			// Tipo Texto
			value = obj.toString();
			break;
		}
		return value;

	}

	/**
	 * M�todo que devuelve el tipo de ORACLE al que correponde el tipo de TIBCO que
	 * se recibe por par�metro.
	 * 
	 * @param type
	 *            tipo de utilizado en los EAIJava de TIBCO.
	 * @return
	 * @throws ParseException
	 */
	public static int getTypeSQL(char type) {
		switch (type) {
		case 'A':
			// Tipo Texto
			return Types.VARCHAR;
		case 'D':
			// Tipo Fecha
			return Types.DATE;
		case 'T':
			// Tipo Hora
			return Types.TIME;
		case 'O':
			// Tipo Timestamp
			return Types.TIMESTAMP;
		case 'N':
		case 'R':
			// Tipo Numerico (float)
			return Types.DOUBLE;
		default:
			// Tipo Texto
			return Types.VARCHAR;
		}

	}

}