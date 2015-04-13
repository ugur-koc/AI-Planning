package planning;

import java.util.ArrayList;
import java.util.List;

public class Action {
	private String name;
	private int paramCount;
	private String[] paramTypes;
	private List<PlanningObject> parameters;
	private ArrayList<Variable> preconditions;
	private ArrayList<Variable> effects;

	public Action(String name, int paramCount, String[] paramTypes, ArrayList<PlanningObject> parameters,
			ArrayList<Variable> preconditions, ArrayList<Variable> effects) {
		this.name = name;
		this.paramCount = paramCount;
		this.paramTypes = paramTypes;
		this.parameters = parameters;
		this.preconditions = preconditions;
		this.effects = effects;
	}

	public Action(Action other, List<PlanningObject> parameters) {
		super();
		this.name = other.name;
		this.paramCount = other.paramCount;
		this.paramTypes = other.paramTypes;
		this.parameters = parameters;
		this.preconditions = other.preconditions;
		this.effects = other.effects;
	}

	public ArrayList<Variable> getPreconditions() {
		return preconditions;
	}

	public ArrayList<Variable> getEffects() {
		return effects;
	}

	public String getName() {
		return name;
	}

	public String[] getParameterTypes() {
		return paramTypes;
	}

	public int getParamCount() {
		return paramCount;
	}

	@Override
	public String toString() {
		String params = "";
		for (PlanningObject planningObject : parameters)
			params += planningObject.toString();// TODO here
		return name + "(" + params + ")";
	}
}