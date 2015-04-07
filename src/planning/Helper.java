package planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Helper {

	public static boolean satifies(State s, State g) {
		Set<Entry<String, String>> entrySet = g.getAssignments().entrySet();
		for (Entry<String, String> entry : entrySet)
			if (!s.getValueOf(entry.getKey()).equals(entry.getValue())) return false;
		return true;
	}

	public static HashMap<String, Action> getApplicableActions(State s, Problem problem) {
		HashMap<String, Action> allcations = enumerateAllActions(problem);
		HashMap<String, Action> applicableActions = new HashMap<String, Action>();
		ArrayList<PlanningObject> b = problem.getSystem().getB();
		for (Action action : problem.getSystem().getActions()) {
			for (String parameter : action.getParameterTypes()) {
				for (PlanningObject planningObject : b) {
					if (planningObject.getName().equals(parameter) && satifies(s, action, parameter)) {

					}
				}

			}
		}
		return applicableActions;
	}

	private static HashMap<String, Action> enumerateAllActions(Problem problem) {
		HashMap<String, Action> hashMap=new HashMap<String, Action>();
		ArrayList<Action> actions = problem.getSystem().getActions();
		for (Action action : actions) {
			
		}
		return null;
	}

	private static boolean satifies(State s, Action action, String parameter) {
		Set<Entry<String, String>> entrySet = s.getAssignments().entrySet();
		for (Entry<String, String> entry : entrySet)
			for (Condition cond : action.getPreconditions())
				if (!s.getValueOf(entry.getKey()).equals(cond.value)) return false; // TODO

		return false;
	}
}