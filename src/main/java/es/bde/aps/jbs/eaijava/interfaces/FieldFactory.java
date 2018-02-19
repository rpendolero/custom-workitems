package es.bde.aps.jbs.eaijava.interfaces;

import java.util.List;

public class FieldFactory {

	/**
	 * @param name
	 * @param value
	 * @param type
	 * @return
	 */
	public static IField createField(String name, Object value, char type) {
		return new Field(name, value, type);
	}

	/**
	 * @param name
	 * @param value
	 * @param type
	 * @return
	 */
	public static IField createFieldArray(String name, List<Object> value, char type) {
		return new FieldArray(name, value, type);
	}

}
