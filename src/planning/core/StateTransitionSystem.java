package planning.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

public class StateTransitionSystem {

	private ArrayList<Action> actions;
	private ArrayList<Variable> variables;
	private HashMap<Integer, String> stateMap;
	private HashMap<String, Set<PlanningObject>> objectMap;

	public StateTransitionSystem() {
		super();
		stateMap = new HashMap<Integer, String>();
		objectMap = new HashMap<String, Set<PlanningObject>>();
		actions = new ArrayList<Action>();
		variables = new ArrayList<Variable>();
	}

	public StateTransitionSystem(ArrayList<Action> actions, HashMap<String, Set<PlanningObject>> objects) {
		stateMap = new HashMap<Integer, String>();
		this.objectMap = objects;
		this.actions = enumerateAllActions(actions);
	}

	public State transition(State s, Action a) {
		if (!stateMap.containsKey(s.hashCode())) stateMap.put(s.toString().hashCode(), s.toString());
		for (Variable effect : a.getEffects())
			s.updateVariable(effect);
		return s;
	}

	public HashMap<Integer, String> getStateMap() {
		return stateMap;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void addState(State s) {
		stateMap.put(s.toString().hashCode(), s.toString());
	}

	public String getState(State s) {
		return stateMap.get(s.toString().hashCode());
	}

	public HashMap<String, Set<PlanningObject>> getObjectMap() {
		return objectMap;
	}

	private ArrayList<Action> enumerateAllActions(ArrayList<Action> actions) {
		ArrayList<Action> allActions = new ArrayList<Action>();
		for (Action action : actions) {
			ArrayList<Set<PlanningObject>> list = new ArrayList<Set<PlanningObject>>();
			for (String type : action.getParameterTypes())
				list.add(objectMap.get(type));
			Set<List<PlanningObject>> cartesianProduct = Sets.cartesianProduct(list);
			for (List<PlanningObject> parameters : cartesianProduct)
				allActions.add(new Action(action, parameters));
		}
		return allActions;
	}

	public ArrayList<Variable> enumerateAllVariables(ArrayList<Variable> variables) {
		ArrayList<Variable> allVariables = new ArrayList<Variable>();
		for (Variable variable : variables) {
			for (String string : variable.getParamTypes()) {
				Set<PlanningObject> set = objectMap.get(string);
				for (PlanningObject planningObject : set)
					allVariables.add(new Variable(variable, planningObject));
			}
		}
		return allVariables;
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}
}