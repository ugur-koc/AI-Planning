package acting;

import java.util.Random;

import planning.core.Action;
import planning.core.Plan;
import planning.core.Planner;
import planning.core.PlanningObject;
import planning.core.Problem;
import planning.core.State;
import planning.core.Variable;
import planning.utility.Helper;

public class Actor {
	private static String planning = null;
	private static int dynamicity = 0;

	public static void act(Problem problem, String refinement, String planningStr, int dynamic) {
		planning = planningStr;
		dynamicity = dynamic;
		if (refinement.equals("AP_lazy")) AP_lazy(problem);
		else if (refinement.equals("AP_interleaved")) AP_interleaved(problem);
		else AP_mixed(problem);
	}

	public static void AP_lazy(Problem problem) {
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			Plan plan = Planner.solve(problem, planning);
			if (plan.getActions().size() == 0) return;
			while (plan.getActions().size() > 0 && Simulate(problem, s, plan, problem.getGoalState())) {
				s = problem.getSystem().transition(s, plan.pop());
				applyRandomChanges(problem, s);// mimicking dynamic environment
			}
			problem.setInitialState(s);
		}
	}

	public static void AP_interleaved(Problem problem) {
		Plan plan = null;
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			plan = Planner.solve(problem, planning);
			System.out.println(plan.toString());
			s = problem.getSystem().transition(s, plan.getActions().get(0));
			problem.setInitialState(s);
		}
	}

	public static void AP_mixed(Problem problem) {
		int n = 5, i = 0;
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			Plan plan = Planner.solve(problem, planning);
			while (i++ < n && Simulate(problem, s, plan, problem.getGoalState())) {
				s = problem.getSystem().transition(s, plan.pop());
			}
			problem.setInitialState(s);
		}
	}

	private static boolean Simulate(Problem problem, State s, Plan plan, State goalState) {
		for (Action action : plan.getActions())
			s = problem.getSystem().transition(s, action);
		return Helper.satifies(s, goalState);
	}

	private static void applyRandomChanges(Problem problem, State s) {
		Random random = new Random();
		for (int i = 0; i < dynamicity; i++) {
			Variable variable = s.getVariables().get(random.nextInt(s.getVariables().size()));
			PlanningObject planningObject = variable.getParameters().get(random.nextInt(variable.getParameters().size()));
			planningObject.addAttribute(variable.getName(),
					variable.getDomain()[random.nextInt(variable.getDomain().length)]);
		}
	}
}