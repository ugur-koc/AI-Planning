package planning;

import java.util.ArrayList;
import java.util.HashMap;

public class StateTransitionSystem {

	private HashMap<Integer, String> stateMap;
	private ArrayList<Action> actions;
	private ArrayList<PlanningObject> B;
	private ArrayList<PlanningObject> Objects;
	private ArrayList<Variable> variables;

	public StateTransitionSystem() {
		super();
		stateMap = new HashMap<Integer, String>();
		actions = new ArrayList<Action>();
		B = new ArrayList<PlanningObject>();
		Objects = new ArrayList<PlanningObject>();
		variables = new ArrayList<Variable>();
	}

	public State transition(State s, Action a) {
		if (!stateMap.containsKey(s.hashCode())) stateMap.put(s.hashCode(), s.toString());
		for (Condition condition : a.getEffects())
			s.updateVariable(condition.getVariable(), condition.getElement(), condition.getValue());
		return s;
	}

	public HashMap<Integer, String> getStateMap() {
		return stateMap;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void addActions(Action action) {
		actions.add(action);
	}

	public void addState(State s) {
		stateMap.put(s.hashCode(), s.toString());
	}

	public String getState(State s) {
		return stateMap.get(s.hashCode());
	}

	public ArrayList<PlanningObject> getObjects() {
		return Objects;
	}

	public ArrayList<PlanningObject> getB() {
		return B;
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}
}
