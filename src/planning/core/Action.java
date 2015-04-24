package planning.core;

import java.util.ArrayList;
import java.util.List;

public class Action {
	private String name, paramTypes[];
	private int paramCount;
	private List<PlanningObject> parameters;
	private ArrayList<Variable> preconditions, effects;

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

	private ArrayList<Variable> generateConditions(List<PlanningObject> parameters, ArrayList<Variable> conditions) {
		ArrayList<Variable> variables = new ArrayList<Variable>();
		for (Variable cond : conditions) {
			for (PlanningObject param : parameters) {
				if (param.getType().equals(cond.getParamTypes()[0]) && cond.getParamIndx() == parameters.indexOf(param)) {
					String value = cond.getValue();
					if (value.contains("placeholder")) {
						int index = Integer.parseInt(value.charAt(12) + "") - 1;
						if (value.length() > 13) {
							Integer fromIndex = (Integer) parameters.get(index).get(cond.getName());
							int increment = Integer.parseInt(value.substring(14));
							if (value.charAt(13) == '+') {
								value = "" + (fromIndex + increment);
							} else value = "" + (fromIndex - increment);
						} else value = parameters.get(index).getName();
					}
					variables.add(new Variable(cond, new PlanningObject(param, cond.getName(), value)));// TODO
				}
			}
		}
		return variables;
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
		String params = "", pre = "\nPre:", eff = "\nEff:";
		for (PlanningObject po : parameters)
			params += po.toString() + " ";
		for (Variable variable : preconditions)
			pre += variable.toString() + ",";
		for (Variable variable : effects)
			eff += variable.toString() + ",";
		return name + "(" + params + ")" + pre.substring(0, pre.length() - 1) + eff.substring(0, eff.length() - 1) + "\n";
	}

	public void addPreCondition(Variable v, String value, int index) {
		Variable variable = new Variable(v, paramTypes[index], index);
		variable.setValue(value);
		preconditions.add(variable);
	}

	public void addEffect(Variable v, String value, int index) {
		Variable variable = new Variable(v, paramTypes[index], index);
		variable.setValue(value);
		effects.add(variable);
	}
}