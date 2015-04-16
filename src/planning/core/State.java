package planning.core;

import java.util.ArrayList;
import java.util.List;

public class State {

	private ArrayList<Variable> variables;

	public State(ArrayList<Variable> variables) {
		super();
		this.variables = variables;
	}

	public State(State other) {
		variables = new ArrayList<Variable>();
		for (Variable variable : other.getVariables())
			variables.add(new Variable(variable, variable.getParameters()));
	}

	public String toString() {
		String variableStr = "";
		for (Variable variable : variables)
			variableStr += variable + ",\n";
		return "{" + variableStr.substring(0, variableStr.length() - 1) + "}";
	}

	public String getValueOf(Variable v) {
		for (Variable var : variables)
			if (var.getName().equals(v.getName())) return (String) var.apply();// TODO
		return null;
	}

	public void updateVariable(Variable v) {
		for (Variable variable : variables)
			if (variable.getSignature().equals(v.getSignature())) {
				List<PlanningObject> parameters = variable.getParameters();
				for (int i = 0; i < parameters.size(); i++)
					parameters.get(i).addAttribute(v.getName(), v.getParameters().get(i).get(v.getName()));
				break;
			}
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}

	public void updateObject(String string, String string2, String string3) {
	}

	public boolean satifies(Variable cond) {
		for (Variable var : variables)
			if (var.getSignature().equals(cond.getSignature()) && var.apply().equals(cond.apply())) return true;
		return false;
	}
}