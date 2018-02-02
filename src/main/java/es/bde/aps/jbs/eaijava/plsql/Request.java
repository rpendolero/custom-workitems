package es.bde.aps.jbs.eaijava.plsql;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.spi.ProcessContext;

public class Request {
	private ProcessContext kContext;
	private List<Object> outputObjects;
	private List<Object> inputObjects;

	public Request() {
		// TODO Auto-generated constructor stub
	}

	public Request(ProcessContext kContext) {
		this.kContext = kContext;
	}

	/**
	 * @return the outputObjects
	 */
	public List<Object> getOutputObjects() {
		return outputObjects;
	}

	/**
	 * @return the inputObjects
	 */
	public List<Object> getInputObjects() {
		return inputObjects;
	}

	/**
	 * @param field
	 */
	public void addInputObject(Object field) {
		if (inputObjects == null) {
			inputObjects = new ArrayList<Object>();
		}
		inputObjects.add(field);
		System.out.println(field.getClass().getName());
	}

	/**
	 * @param field
	 */
	public void addOutputObject(Object field) {
		if (outputObjects == null) {
			outputObjects = new ArrayList<Object>();
		}
		outputObjects.add(field);
		System.out.println(field.getClass().getName());
	}

	public ProcessContext getContext() {
		return kContext;
	}
}
