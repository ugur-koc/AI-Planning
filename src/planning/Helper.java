package planning;

import java.util.ArrayList;

public class Helper {

	public static ArrayList<Action> getApplicableActions(State s, Problem problem) {
		ArrayList<Action> applicableActions = new ArrayList<Action>();
		for (Action action : problem.getSystem().getActions())
			if (satifies(s, action.getPreconditions())) applicableActions.add(action);
		return applicableActions;
	}

	public static boolean satifies(State s, State g) {
		ArrayList<Variable> variables = g.getVariables();
		for (Variable variable : variables)
			if (!s.getValueOf(variable).equals(variable.apply())) return false;
		return true;
	}

	public static boolean satifies(State s, ArrayList<Variable> preconditions) {
		ArrayList<Variable> variables = s.getVariables();
		for (Variable variable : variables)
			for (Variable cond : preconditions)
				if (!s.getValueOf(variable).equals(cond.apply())) return false; // TODO
		return true;
	}
}