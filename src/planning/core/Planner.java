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
			else if (alg.equals("DFS")) return DepthFirstSearch(problem, new ArrayList<Integer>());
			else return ForwardSearch(problem);
		} catch (NoPlanException e) {
			return null;
		}
	}

	public static Plan AStar(Problem problem) throws NoPlanException {
		Plan plan = new Plan();
		ArrayList<Action> applicableActions = null;
		State s = problem.getInitialState();
		while (true) {
			if (Helper.satifies(s, problem.getGoalState())) return plan;
			applicableActions = Helper.getApplicableActions(s, problem);
			if (applicableActions.size() == 0) { throw new NoPlanException("AStar: No applicable action found!"); }
			Action a = problem.heuristic(s, problem, applicableActions);
			s = problem.getSystem().transition(s, a);
			plan.addAction(a);
		}
	}

	public static Plan ForwardSearch(Problem problem) throws NoPlanException {
		Plan plan = new Plan();
		State s = problem.getInitialState();
		ArrayList<Action> applicableActions = null;
		while (true) {
			if (Helper.satifies(s, problem.getGoalState())) return plan;
			applicableActions = Helper.getApplicableActions(s, problem);
			if (applicableActions.size() == 0) { throw new NoPlanException("ForwardSearch: No applicable action found!"); }
			Action a = applicableActions.get(random.nextInt(applicableActions.size()));
			s = problem.getSystem().transition(s, a);
			plan.addAction(a);
		}
	}

	public static Plan DepthFirstSearch(Problem problem, ArrayList<Integer> visited) throws NoPlanException {
		Plan plan = new Plan();
		State s = problem.getInitialState();
		ArrayList<Action> applicableActions;
		if (Helper.satifies(s, problem.getGoalState())) return plan;
		applicableActions = Helper.getApplicableActions(s, problem);
		for (int i = 0; i < applicableActions.size(); i++)
			if (visited.contains(problem.getSystem().transition(s, applicableActions.get(i)).hashCode()))
				applicableActions.remove(i--);
		while (applicableActions.size() != 0) {
			Action a = problem.heuristic(s, problem, applicableActions);
			visited.add(s.hashCode());
			s = problem.getSystem().transition(s, a);
			problem.setInitialState(s);
			plan = DepthFirstSearch(problem, visited);
			plan.addAction(a);
		}
		throw new NoPlanException("DepthFirstSearch: No plan found!");
	}
}