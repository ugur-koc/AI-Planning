package planning;

import java.util.ArrayList;
import java.util.List;

public class Variable {
	private String name;
	private int paramCount;
	private String[] paramTypes;
	private List<PlanningObject> parameters;
	private ArrayList<String> domain;

	public Variable(String name, int paramCount, String[] paramTypes, ArrayList<PlanningObject> parameters,
			ArrayList<String> domain) {
		super();
		this.name = name;
		this.paramCount = paramCount;
		this.parameters = parameters;
		this.domain = domain;
		this.paramTypes = paramTypes;
	}

	public Variable(Variable other, List<PlanningObject> parameters) {
		this.name = other.name;
		this.parameters = parameters;
		this.paramCount = other.paramCount;
		this.domain = other.domain;
		this.paramTypes = other.paramTypes;
	}

	public Variable(Variable other, PlanningObject parameter) {
		this.name = other.name;
		ArrayList<PlanningObject> po = new ArrayList<PlanningObject>();
		po.add(parameter);
		this.parameters = po;
		this.paramCount = other.paramCount;
		this.domain = other.domain;
		this.paramTypes = other.paramTypes;
	}

	public String getName() {
		return name;
	}

	public String[] getParamTypes() {
		return paramTypes;
	}

	public List<PlanningObject> getParameters() {
		return parameters;
	}

	public ArrayList<String> getDomain() {
		return domain;
	}

	public int getParamCount() {
		return paramCount;
	}

	public String getSignature() {
		String result = name + "(";
		for (PlanningObject planningObject : parameters)
			result += planningObject.getName() + ":" + planningObject.getType() + ",";
		return result.substring(0, result.length() - 1);
	}

	public Object apply() {
		if (paramCount == 2) return parameters.get(0).get(name) == parameters.get(1).get(name);
		return parameters.get(0).get(name);
	}

	@Override
	public String toString() {
		String result = name + "(";
		int count = 0;
		for (int i = 0; i < parameters.size() && count++ < paramCount; i++)
			result += parameters.get(i).getName() + ":" + parameters.get(i).getType() + ",";
		return result.substring(0, result.length() - 1) + ")=" + apply().toString();
	}
}