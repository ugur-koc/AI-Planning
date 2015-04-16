package acting;

import java.util.ArrayList;

import planning.core.Action;
import planning.core.Plan;
import planning.core.Planner;
import planning.core.Problem;
import planning.core.State;
import planning.core.StateTransitionSystem;
import planning.utility.Helper;

public class Actor {

	public static void act(Problem problem, String refinement) {
		if (refinement.equals("ARP_lazy")) ARP_lazy(problem);
		else if (refinement.equals("ARP_interleaved")) ARP_interleaved(problem);
		else ARP_mixed(problem);
	}

	public static void ARP_lazy(Problem problem) {
		// actually AP_lazy
		State s = problem.getInitialState();

		while (!Helper.satifies(s, problem.getGoalState())) {
			Plan plan = Planner.solve(problem, "Astar");
			while (Simulate(problem, s, plan, problem.getGoalState())) {
				ArrayList<Action> actions = plan.getActions();
				// the actor's transition system and planner's transition system are
				// same in this case.
				s = problem.getSystem().transition(s, actions.get(0));
				plan.removeAction(actions.get(0));
				StateTransitionSystem ST = new StateTransitionSystem();
				Problem P = new Problem(ST, s, problem.getGoalState());
				problem = P;
			}

		}
	}

	public static void ARP_interleaved(Problem problem) {
		// actually AP_interleaved
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			StateTransitionSystem ST = new StateTransitionSystem();
			Problem P = new Problem(ST, s, problem.getGoalState());
			Plan plan = Planner.solve(P, "AStar");
			ArrayList<Action> actions = plan.getActions();
			// the actor's transition system and planner's transition system are
			// same in this case.
			s = P.getSystem().transition(s, actions.get(0));
		}
	}

	public static void ARP_mixed(Problem problem) { // Actuallly AP_mixed
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

}