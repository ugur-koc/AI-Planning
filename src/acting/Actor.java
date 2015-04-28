package acting;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import planning.core.Action;
import planning.core.Plan;
import planning.core.Planner;
import planning.core.PlanningObject;
import planning.core.Problem;
import planning.core.State;
import planning.core.Variable;
import planning.utility.Helper;
import test.experiment.Experiment;
import test.problems.RobotGridLayout;

public class Actor {
	private static String planning = null;
	private static int dynamicity = 0;
	private static long sTime;

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
			Experiment.plannerCallCount++;
			sTime = System.currentTimeMillis();
			Plan plan = Planner.solve(problem, planning);
			Experiment.totalPlanningTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
			//System.out.println(plan.toString());
			if (plan.getActions().size() == 0) return;
			while (plan.getActions().size() > 0 && Simulate(problem, s, plan, problem.getGoalState())) {
				Experiment.actionCount++;
				sTime = System.currentTimeMillis();
				s = problem.getSystem().transition(s, plan.pop());
				Experiment.totalActingTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
				applyRandomChanges(problem, s);// mimicking dynamic environment
			}
			problem.setInitialState(s);
		}
	}

	public static void AP_interleaved(Problem problem) {
		Plan plan = null;
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			Experiment.plannerCallCount++;
			sTime = System.currentTimeMillis();
			plan = Planner.solve(problem, planning);
			Experiment.totalPlanningTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
			// System.out.println(plan.toString());
			Experiment.actionCount++;
			sTime = System.currentTimeMillis();
			s = problem.getSystem().transition(s, plan.getActions().get(0));
			Experiment.totalActingTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
			applyRandomChanges(problem, s);// mimicking dynamic environment
			problem.setInitialState(s);
		}
	}

	public static void AP_mixed(Problem problem) {
		int n = 5, i = 0;
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			i = 0;
			Experiment.plannerCallCount++;
			sTime = System.currentTimeMillis();
			Plan plan = Planner.solve(problem, planning);
			Experiment.totalPlanningTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
			// System.out.println(plan.toString());
			while (i++ < n && Simulate(problem, s, plan, problem.getGoalState())) {
				Experiment.actionCount++;
				sTime = System.currentTimeMillis();
				s = problem.getSystem().transition(s, plan.pop());
				Experiment.totalActingTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
				applyRandomChanges(problem, s);// mimicking dynamic environment
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
		PlanningObject planningObject;
		String directions[] = { "t", "b", "r", "l" }, toCell, fromCell;
		ArrayList<String> gRobots = new ArrayList<String>();
		int increments[] = { RobotGridLayout.boardSize, RobotGridLayout.boardSize * (-1), 1, -1 }, index;
		for (Variable v : problem.getGoalState().getVariables())
			if (v.getParameters().get(0).getType().equals("Robots")) gRobots.add(v.getParameters().get(0).getName());

		for (int i = 0; i < dynamicity; i++) {
			do {
				planningObject = s.getVariables().get(random.nextInt(s.getVariables().size())).getParameters().get(0);
			} while (!planningObject.getType().equals("Robots") || gRobots.contains(planningObject.getName()));
			fromCell = (String) planningObject.get("pos");
			do {
				index = random.nextInt(4);
				toCell = "c" + (Integer.parseInt(fromCell.substring(1)) + increments[index]);
			} while (s.getValueOf(directions[index] + "edge", fromCell).equals("yes")
					|| s.getValueOf("status", toCell).equals("occupied"));
			planningObject.addAttribute("pos", toCell);
			s.updateVariable(toCell, "status", "occupied");
			s.updateVariable(fromCell, "status", "empty");
		}
	}
}