package planning.core;

import java.util.ArrayList;
import java.util.List;

public class Variable {

	private String name;
	private String value;
	private int paramCount;
	private String[] paramTypes;
	private List<PlanningObject> parameters;
	private String[] domain;

	public Variable(String name, int paramCount, String[] paramTypes) {
		super();
		this.name = name;
		this.paramCount = paramCount;
		this.paramTypes = paramTypes;
	}

	public Variable(Variable other, List<PlanningObject> parameters) {
		name = other.name;
		paramCount = other.paramCount;
		domain = other.domain;
		paramTypes = other.paramTypes;
		value = other.value;
		this.parameters = new ArrayList<PlanningObject>();
		for (PlanningObject planningObject : parameters)
			this.parameters.add(new PlanningObject(planningObject));
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

	public Variable(Variable other, String paramType) {
		this.name = other.name;
		this.parameters = other.parameters;
		this.paramCount = other.paramCount;
		this.domain = other.domain;
		this.paramTypes = new String[] { paramType };
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

	public String[] getDomain() {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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