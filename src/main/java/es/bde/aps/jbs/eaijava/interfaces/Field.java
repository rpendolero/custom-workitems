package es.bde.aps.jbs.eaijava.interfaces;

public class Field implements IField {
	private String name;
	private String value;
	private FieldType type;

	/**
	 * 
	 * @param name
	 * @param value
	 * @param type
	 */
	public Field(String name, String value, FieldType type) {
		this.name = name;
		this.value = value;
		this.type = type;
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
	public FieldType getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(FieldType type) {
		this.type = type;
	}

	/**
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
