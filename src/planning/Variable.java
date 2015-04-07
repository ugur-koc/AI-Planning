package planning;

import java.util.ArrayList;

public class Variable {
	private String name;
	private String returnType;
	private PlanningObject[] parameters;
	private ArrayList<String> domain;

	public Variable(String name, PlanningObject[] parameters, ArrayList<String> domain, String returnType) {
		super();
		this.name = name;
		this.parameters = parameters;
		this.domain = domain;
		this.returnType = returnType;
	}

	public String getName() {
		return name;
	}

	public String getReturnType() {
		return returnType;
	}

	public PlanningObject[] getParameters() {
		return parameters;
	}

	public ArrayList<String> getDomain() {
		return domain;
	}
}
