package planning;

import java.util.ArrayList;

public class Variable {
	private String name;
	private String value;
	private String returnType;
	private String[] parameters;
	private ArrayList<String> domain;

	public Variable(String name, String[] parameters, ArrayList<String> domain, String value, String returnType) {
		super();
		this.name = name;
		this.parameters = parameters;
		this.domain = domain;
		this.value = value;
		this.returnType = returnType;
	}

	public Variable(Variable other, String param, String value) {
		super();
		name = other.name;
		this.value = value;
		returnType = other.returnType;
		for (int i = 0; i < parameters.length; i++)
			parameters[i] = other.parameters[i];
		for (String string : domain)
			domain.add(string);
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

	public String[] getParameters() {
		return parameters;
	}

	public ArrayList<String> getDomain() {
		return domain;
	}

	public void updateValue(String val) {
		value = val;
	}
}
