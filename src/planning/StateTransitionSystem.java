package planning;

import java.util.ArrayList;
import java.util.HashMap;

public class StateTransitionSystem {

	HashMap<Integer, String> stateMap;
	ArrayList<Action> actionList;

	public StateTransitionSystem(HashMap<Integer, String> stateMap, ArrayList<Action> actions) {
		super();
		this.stateMap = stateMap;
		actionList = actions;
	}

	public State transition(State s, Action a) {
		if (stateMap.containsKey(s.hashCode()))

		for (Variable variable : a.getEffects())
			updateVariable(variable, s);
		return s;
	}

	public void updateVariable(Variable v, State s) {
		ArrayList<Variable> variables = s.getVariables();
		for (Variable variable : variables)
			if (variable.getName().equals(v.getName())) {
				variable.updateValue(v.getValue());
				break;
			}
	}

	public HashMap<Integer, String> getStateMap() {
		return stateMap;
	}

	public ArrayList<Action> getActionList() {
		return actionList;
	}

	public void addState(State s) {
		stateMap.put(s.hashCode(), s.toString());
	}

	public String getState(State s) {
		return stateMap.get(s.hashCode());
	}
}
