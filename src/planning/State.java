package planning;

import java.util.ArrayList;

public class State {

	private ArrayList<Variable> variables;

	public State(ArrayList<Variable> variables) {
		super();
		this.variables = variables;
	}

	public String toString() {
		String variableStr = "";
		for (Variable variable : variables)
			variableStr += variable + ",";
		return "{" + variableStr.substring(0, variableStr.length() - 1) + "}";
	}

	public String getValueOf(Variable v) {
		for (Variable variable : variables)
			if (variable.getName().equals(v.getName())) return (String) variable.apply();// TODO
		return null;
	}

	public void updateVariable(Variable v) {
		for (Variable variable : variables)
			if (variable.getSignature().equals(v.getSignature())) {
				variable = v;
				break;
			}
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}

	public void updateObject(String string, String string2, String string3) {
	}
}