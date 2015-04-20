package acting;

import java.util.ArrayList;
import java.util.Random;

import planning.core.Action;
import planning.core.Plan;
import planning.core.Planner;
import planning.core.PlanningObject;
import planning.core.Problem;
import planning.core.State;
import planning.core.StateTransitionSystem;
import planning.core.Variable;
import planning.utility.Helper;

public class Actor {

	public static void act(Problem problem, String refinement) {
		if (refinement.equals("AP_lazy")) AP_lazy(problem);
		else if (refinement.equals("AP_interleaved")) AP_interleaved(problem);
		else AP_mixed(problem);
	}

	// the actor's transition system and planner's
	// transition system are same in this case.
	public static void AP_lazy(Problem problem) {
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			Plan plan = Planner.solve(problem, "Astar");
			if (plan.getActions().size() == 0) return;
			while (plan.getActions().size() > 0 && Simulate(problem, s, plan, problem.getGoalState())) {
				s = problem.getSystem().transition(s, plan.pop());
				applyRandomChanges(problem, s);// mimicking dynamic environment
			}
			problem.setInitialState(s);
		}
	}

	// the actor's transition system and planner's
	// transition system are same in this case.
	public static void AP_interleaved(Problem problem) {
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			StateTransitionSystem ST = new StateTransitionSystem();
			Problem P = new Problem(ST, s, problem.getGoalState());
			Plan plan = Planner.solve(P, "AStar");
			ArrayList<Action> actions = plan.getActions();
			s = P.getSystem().transition(s, actions.get(0));
		}
	}

	public static void AP_mixed(Problem problem) {
		int n = 5;
		int i = 0;
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			Plan plan = Planner.solve(problem, "Astar");
			while (i++ < n && Simulate(problem, s, plan, problem.getGoalState())) {
				ArrayList<Action> actions = plan.getActions();
				s = problem.getSystem().transition(s, actions.get(0));
				// the actor's transition system and planner's transition system are
				// same in this case.
				plan.removeAction(actions.get(0));
				StateTransitionSystem ST = new StateTransitionSystem();
				Problem P = new Problem(ST, s, problem.getGoalState());
				problem = P;
			}
		}
	}

	private static boolean Simulate(Problem problem, State s, Plan plan, State goalState) {
		State g = s;
		ArrayList<Action> actions = plan.getActions();
		for (Action a : actions) {
			g = problem.getSystem().transition(s, a);
			s = g;
		}
		if (Helper.satifies(s, goalState)) return true;
		else return false;
	}

	private static void applyRandomChanges(Problem problem, State s) {
		Random random = new Random();
		Variable variable = s.getVariables().get(random.nextInt(s.getVariables().size()));
		PlanningObject planningObject = variable.getParameters().get(random.nextInt(variable.getParameters().size()));
		// planningObject
		// .addAttribute(variable.getName(),
		// variable.getDomain()[random.nextInt(variable.getDomain().length)]);
	}
}