package es.bde.aps.jbs.eaijava.interfaces;

import java.util.List;

public class FieldArray implements IField {
	private String name;
	private List<Object> value;
	private char type;

	/**
	 * 
	 * @param name
	 * @param value
	 * @param type
	 */
	public FieldArray(String name, List<Object> value, char type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.bde.aps.jbs.eaijava.interfaces.IField#getName()
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.bde.aps.jbs.eaijava.interfaces.IField#getValue()
	 */
	public List<Object> getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.bde.aps.jbs.eaijava.interfaces.IField#getType()
	 */
	public char getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
