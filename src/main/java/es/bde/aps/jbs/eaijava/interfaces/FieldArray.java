package es.bde.aps.jbs.eaijava.interfaces;

import java.util.ArrayList;
import java.util.List;

public class FieldArray implements IField {
	private String name;
	private List<Object> values;
	private char type;

	/**
	 * 
	 * @param name
	 * @param value
	 * @param type
	 */
	public FieldArray(String name, List<Object> values, char type) {
		this.name = name;
		this.values = values;
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
		return values;
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

	/**
	 * 
	 * @param value
	 */
	public void addValue(Object value) {
		if (value == null) {
			values = new ArrayList<Object>();
		}
		values.add(value);

	}

}
