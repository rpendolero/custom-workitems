package es.bde.aps.jbs.workitem.interfaces;

import java.io.Serializable;

public class Field implements IField, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3383436822069513598L;
	private String name;
	private Object value;
	private char type;

	/**
	 * 
	 * @param name
	 * @param value
	 * @param type
	 */
	public Field(String name, Object value, char type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	/**
	 * 
	 * @param name
	 * @param type
	 */
	public Field(String name, char type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(Object value) {
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

	@Override
	public String toString() {
		return "Field [name=" + name + ", value=" + value + ", type=" + type + "]";
	}

}
