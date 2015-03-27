package planning;

import java.util.ArrayList;

public class State {

	private ArrayList<Variable> variables;

	public State(ArrayList<Variable> variables) {
		super();
		this.variables = variables;
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}

	public String toString() {
		String rslt = "";
		for (Variable variable : variables)
			rslt += variable.getName() + variable.getValue();
		return rslt;
	}

	public String getValueOf(Variable v) {
		return v.getValue();// TODO does this make sense
	}
}