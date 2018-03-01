package es.bde.aps.jbs.workitem.interfaces;

import java.util.List;

public class FieldFactory {

	/**
	 * @param name
	 * @param value
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IField createField(String name, Object value, char type, boolean isArray) {
		if (isArray) {
			return new FieldArray(name, (List<Object>) value, type);
		} else {
			return new Field(name, value, type);
		}
	}

	/**
	 * @param name
	 * @param value
	 * @param type
	 * @return
	 */
	public static IField createField(String name, char type, boolean isArray) {
		if (isArray) {
			return new FieldArray(name, type);
		} else {
			return new Field(name, type);
		}
	}
}
