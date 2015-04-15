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
		preconditions = new ArrayList<Variable>();
		effects = new ArrayList<Variable>();
	}

	public Action(Action other, List<PlanningObject> parameters) {
		super();
		this.name = other.name;
		this.paramCount = other.paramCount;
		this.paramTypes = other.paramTypes;
		this.parameters = parameters;
		this.preconditions = generateConditions(parameters, other.preconditions);
		this.effects = generateConditions(parameters, other.effects);
	}

	private ArrayList<Variable> generateConditions(List<PlanningObject> parameters, ArrayList<Variable> preconditions) {
		ArrayList<Variable> variables = new ArrayList<Variable>();
		for (Variable variable : preconditions) {
			for (PlanningObject planningObject : parameters) {
				if (planningObject.getType().equals(variable.getParamTypes()[0])) {
					Object value = variable.getValue();
					if (((String) value).contains("placeholder")) {
						int index = Integer.parseInt(((String) value).substring(((String) value).indexOf("_") + 1)) - 1;
						value = parameters.get(index).getName();
					}
					variables.add(new Variable(variable, new PlanningObject(planningObject, variable.getName(), value)));// TODO
				}
			}
		}
		return variables;
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
		for (PlanningObject po : parameters)
			params += po.toString() + " ";
		return name + "(" + params + ")";
	}

	public void addPreCondition(Variable v, String value, int index) {
		Variable variable = new Variable(v, paramTypes[index]);
		variable.setValue(value);
		preconditions.add(variable);
	}

	public void addEffect(Variable v, String value, int index) {
		Variable variable = new Variable(v, paramTypes[index]);
		variable.setValue(value);
		effects.add(variable);
	}
}