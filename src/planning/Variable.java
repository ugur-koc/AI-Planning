package planning;

import java.util.ArrayList;

public class Variable {
	private String name;
	private String value;
	private String returnType;
	private Parameter[] parameters;
	private ArrayList<String> domain;

	public Variable(String name, Parameter[] parameters, ArrayList<String> domain, String value, String returnType) {
		super();
		this.name = name;
		this.parameters = parameters;
		this.domain = domain;
		this.value = value;
		this.returnType = returnType;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getReturnType() {
		return returnType;
	}

	public Parameter[] getParameters() {
		return parameters;
	}

	public ArrayList<String> getDomain() {
		return domain;
	}

	public void updateValue(String val) {
		value = val;
	}
}

class Parameter {
	public String type;
	public String name;

	public Parameter(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
}