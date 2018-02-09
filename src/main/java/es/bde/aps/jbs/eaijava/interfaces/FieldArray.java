package es.bde.aps.jbs.eaijava.interfaces;

public class FieldArray implements IField {
	private String name;
	private String value;
	private FieldType type;

	/**
	 * 
	 * @param name
	 * @param value
	 * @param type
	 */
	public FieldArray(String name, String value, FieldType type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public Object getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public FieldType getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
