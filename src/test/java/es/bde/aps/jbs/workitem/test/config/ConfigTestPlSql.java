package es.bde.aps.jbs.workitem.test.config;

import java.util.ArrayList;
import java.util.List;

import es.bde.aps.jbs.workitem.interfaces.IField;

public class ConfigTestPlSql extends ConfigTestAbstract {

	private String procedure;
	private List<IField> parametersInput;
	private List<IField> parametersOutput;
	private List<String> results;

	/**
	 * @return
	 */
	public List<IField> getParametersInput() {
		return parametersInput;
	}

	/**
	 * @param parametersInput
	 */
	public void setParametersInput(List<IField> parametersInput) {
		this.parametersInput = parametersInput;
	}

	/**
	 * @return
	 */
	public List<IField> getParametersOutput() {
		return parametersOutput;
	}

	/**
	 * @param parametersOutput
	 */
	public void setParametersOutput(List<IField> parametersOutput) {
		this.parametersOutput = parametersOutput;
	}

	/**
	 * 
	 * @param field
	 */
	public void addParameterInput(IField field) {
		if (parametersInput == null) {
			parametersInput = new ArrayList<IField>();
		}
		parametersInput.add(field);
	}

	/**
	 * 
	 * @param field
	 */
	public void addParameterOutput(IField field) {
		if (parametersOutput == null) {
			parametersOutput = new ArrayList<IField>();
		}
		parametersOutput.add(field);
	}

	/**
	 * @return
	 */
	public String getProcedure() {
		return procedure;
	}

	/**
	 * @param procedure
	 */
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	@Override
	public String toString() {
		return "ConfigTest [name=" + getName() + ", procedure=" + procedure + "]";
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getResults() {
		return results;
	}

	/**
	 * 
	 * @param value
	 */
	public void addResultValue(String value) {
		if (results == null) {
			results = new ArrayList<String>();
		}
		results.add(value);
	}

}
