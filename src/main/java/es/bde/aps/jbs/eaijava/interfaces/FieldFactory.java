package es.bde.aps.jbs.eaijava.interfaces;

public class FieldFactory {

	public static IField createField(String name, String value, FieldType type) {
		return new Field(name, value, type);
	}

	public static IField createFieldArray(String name, String value, FieldType type) {
		return null;
	}

}
