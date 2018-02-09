package es.bde.aps.jbs.eaijava.interfaces;

public class Field implements IField {
	private String name;
	private String value;
	private char type;
	/**
	 * 
	 */
	private boolean isArray;

	public Field(String name, String value, FieldType type) {

		// TODO Auto-generated constructor stub
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return
	 */
	public char getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(char type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

}
