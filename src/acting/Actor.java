package acting;

import java.util.concurrent.TimeUnit;

import planning.core.Action;
import planning.core.Plan;
import planning.core.Planner;
import planning.core.Problem;
import planning.core.State;
import planning.utility.Helper;
import test.experiment.Experiment2;
import exceptions.NoPlanException;

public class Actor {
	private static String planning = null;
	public static int dynamicity = 0;
	private static long sTime;

	public static void act(Problem problem, String refinement, String planningStr, int dynamic) throws NoPlanException {
		planning = planningStr;
		dynamicity = dynamic;
		if (refinement.equals("AP_lazy")) AP_lazy(problem);
		else if (refinement.equals("AP_interleaved")) AP_interleaved(problem);
		else AP_mixed(problem);
	}

	public static void AP_lazy(Problem problem) throws NoPlanException {
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			Experiment2.plannerCallCount++;
			sTime = System.currentTimeMillis();
			Plan plan = Planner.solve(problem, planning);
			Experiment2.totalPlanningTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
			//System.out.println("AP_lazy:" + (Experiment2.plannerCallCount) + "\n" + plan.toString());
			if (plan.getActions().size() == 0) return;
			while (plan.getActions().size() > 0 && Simulate(problem, s, plan, problem.getGoalState())) {
				Experiment2.actionCount++;
				sTime = System.currentTimeMillis();
				s = problem.getSystem().transition(s, plan.pop());
				Experiment2.totalActingTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
				Experiment2.moveTrain(problem, s);// mimicking dynamic environment
			}
			problem.setInitialState(s);
			//System.out.println(problem.toString());
		}
	}

	public static void AP_interleaved(Problem problem) throws NoPlanException {
		Plan plan = null;
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			Experiment2.plannerCallCount++;
			sTime = System.currentTimeMillis();
			plan = Planner.solve(problem, planning);
			Experiment2.totalPlanningTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
			//System.out.println("AP_interleaved:" + (Experiment2.plannerCallCount) + "\n" + plan.toString());
			Experiment2.actionCount++;
			sTime = System.currentTimeMillis();
			s = problem.getSystem().transition(s, plan.getActions().get(0));
			Experiment2.totalActingTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
			Experiment2.moveTrain(problem, s);// mimicking dynamic environment
			problem.setInitialState(s);
			// System.out.println(problem.toString());
		}
	}

	public static void AP_mixed(Problem problem) throws NoPlanException {
		State s = problem.getInitialState();
		while (!Helper.satifies(s, problem.getGoalState())) {
			// int i = 0;
			Experiment2.plannerCallCount++;
			sTime = System.currentTimeMillis();
			Plan plan = Planner.solve(problem, planning);
			Experiment2.rePlan = false;
			Experiment2.totalPlanningTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
			//System.out.println("AP_mixed:" + (Experiment2.plannerCallCount) + "\n" + plan.toString());
			while (!Experiment2.rePlan && Simulate(problem, s, plan, problem.getGoalState())
					&& plan.getActions().size() > 0) {
				Experiment2.actionCount++;
				sTime = System.currentTimeMillis();
				s = problem.getSystem().transition(s, plan.pop());
				Experiment2.totalActingTime += TimeUnit.MILLISECONDS.toMillis(Math.abs(sTime - System.currentTimeMillis()));
				problem.setInitialState(s);
				Experiment2.moveTrain(problem, s);// mimicking dynamic environment
			}
			// System.out.println(problem.toString());
		}
	}

	private static boolean Simulate(Problem problem, State s, Plan plan, State goalState) {
		for (Action action : plan.getActions())
			try {
				s = problem.getSystem().transition(s, action);
			} catch (NoPlanException e) {
				return false;
			}
		return Helper.satifies(s, goalState);
	}
}