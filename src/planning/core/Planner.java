package planning.core;

import java.util.ArrayList;
import java.util.Random;

import planning.utility.Helper;
import exceptions.NoPlanException;

public class Planner {
	static Random random = new Random();

	public static Plan solve(Problem problem, String alg) {
		try {
			if (alg.equals("AStar")) return AStar(problem);
			else if (alg.equals("DFS")) return DepthFirstSearch(problem, new ArrayList<State>());
			else return ForwardSearch(problem);
		} catch (NoPlanException e) {
			return null;
		}
	}

	public static Plan AStar(Problem problem) throws NoPlanException {
		Plan plan = new Plan();
		State s = problem.getInitialState();
		while (true) {
			if (Helper.satifies(s, problem.getGoalState())) return plan;
			ArrayList<Action> applicableActions = Helper.getApplicableActions(s, problem);
			if (applicableActions.size() == 0) { throw new NoPlanException("No applicable action found!"); }
			Action a = applicableActions.get(random.nextInt(applicableActions.size()));
			s = problem.getSystem().transition(s, a);
			plan.addAction(a);
		}
	}

	public static Plan ForwardSearch(Problem problem) throws NoPlanException {
		Plan plan = new Plan();
		State s = problem.getInitialState();
		while (true) {
			if (Helper.satifies(s, problem.getGoalState())) return plan;
			ArrayList<Action> applicableActions = Helper.getApplicableActions(s, problem);
			if (applicableActions.size() == 0) { throw new NoPlanException("No applicable action found!"); }
			Action a = applicableActions.get(random.nextInt(applicableActions.size()));
			s = problem.getSystem().transition(s, a);
			plan.addAction(a);
		}
	}

	public static Plan DepthFirstSearch(Problem problem, ArrayList<State> visited) throws NoPlanException {
		Plan plan = new Plan();
		State s = problem.getInitialState();
		if (Helper.satifies(s, problem.getGoalState())) return plan;
		ArrayList<Action> applicableActions = Helper.getApplicableActions(s, problem);
		while (applicableActions.size() != 0) {
			Action a = applicableActions.get(random.nextInt(applicableActions.size()));
			s = problem.getSystem().transition(s, a);
			plan.addAction(a);
		}
		throw new NoPlanException("No plan found!");
	}
}