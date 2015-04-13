package planning.core;

import java.util.ArrayList;
import java.util.List;

public class Action {
	private String name;
	private int paramCount;
	private String[] paramTypes;
	private List<PlanningObject> parameters;
	private ArrayList<Variable> preconditions;
	private ArrayList<Variable> effects;

	public Action(String name, int paramCount, String[] paramTypes) {
		this.name = name;
		this.paramCount = paramCount;
		this.paramTypes = paramTypes;
	}

	public Action(Action other, List<PlanningObject> parameters) {
		super();
		this.name = other.name;
		this.paramCount = other.paramCount;
		this.paramTypes = other.paramTypes;
		this.parameters = parameters;
		this.preconditions = other.preconditions;// TODO
		this.effects = other.effects;
	}

	public void doIt(State s) {
		if (checkPreConditions(s)) for (Variable eff : effects)
			s.updateVariable(eff);
	}

	public boolean checkPreConditions(State s) {
		for (Variable pre : preconditions)
			if (!pre.apply().equals(s.getValueOf(pre))) return false;
		return true;
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