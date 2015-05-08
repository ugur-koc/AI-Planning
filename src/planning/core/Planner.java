package planning.core;

import java.util.ArrayList;
import java.util.Random;

import planning.utility.Helper;
import test.problems.RobotWithTrain;
import exceptions.NoPlanException;

public class Planner {
	static Random random = new Random();

	public static Plan solve(Problem problem, String alg) throws NoPlanException {
		if (alg.equals("AStar")) return AStar(problem);
		else if (alg.equals("DFS")) return DepthFirstSearch(problem, new ArrayList<Integer>());
		else return ForwardSearch(problem);
	}

	public static Plan ForwardSearch(Problem problem) throws NoPlanException {
		Plan plan = new Plan();
		ArrayList<Action> applicableActions = null;
		State s = problem.getInitialState();
		while (true) {
			if (Helper.satifies(s, problem.getGoalState())) return plan;
			applicableActions = Helper.getApplicableActions(s, problem);
			for (int i = 0; i < applicableActions.size(); i++)
				if (plan.getActions().contains(applicableActions.get(i))) applicableActions.remove(i--);
			if (applicableActions.size() == 0) { throw new NoPlanException("ForwardSearch: No applicable action found!"); }
			Action a = problem.heuristic(s, problem, applicableActions);
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
			if (visited.contains(problem.getSystem().transition(s, applicableActions.get(i)).toString().hashCode()))
				applicableActions.remove(i--);
		while (applicableActions.size() != 0) {
			Action a = problem.heuristic(s, problem, applicableActions);
			visited.add(s.toString().hashCode());
			s = problem.getSystem().transition(s, a);
			problem.setInitialState(s);
			plan = DepthFirstSearch(problem, visited);
			plan.addActionToFront(a);
			return plan;
		}
		throw new NoPlanException("DepthFirstSearch: No plan found!");
	}

	public static Plan AStar(Problem problem) throws NoPlanException {
		Tuple<Plan, State> minTuple, t2;
		ArrayList<Tuple<Plan, State>> fringe = new ArrayList<Tuple<Plan, State>>(), expanded = new ArrayList<Tuple<Plan, State>>();
		fringe.add(new Tuple<Plan, State>(new Plan(), problem.getInitialState()));
		while (fringe.size() > 0) {
			minTuple = min(fringe, problem.getGoalState());
			fringe.remove(minTuple);
			expanded.add(minTuple);
			if (Helper.satifies(minTuple.s, problem.getGoalState())) return minTuple.pi;
			ArrayList<Action> applicableActions = Helper.getApplicableActions(minTuple.s, problem);
			for (Action action : applicableActions) {
				State s1 = problem.getSystem().transition(minTuple.s, action);
				Plan p1 = new Plan(minTuple.pi, action);
				t2 = contains(fringe, expanded, s1);
				if (t2 != null) {
					if (t2.pi.length() > p1.length()) {
						fringe.remove(t2);
						expanded.remove(t2);
					} else continue;
				}
				fringe.add(new Tuple<Plan, State>(p1, s1));
			}
		}
		throw new NoPlanException("AStar: No plan found!");
	}

	private static Tuple<Plan, State> contains(ArrayList<Tuple<Plan, State>> fringe,
			ArrayList<Tuple<Plan, State>> expanded, State s1) {
		for (Tuple<Plan, State> tuple : fringe)
			if (tuple.s.toString().hashCode() == s1.toString().hashCode()) return tuple;
		for (Tuple<Plan, State> tuple : expanded)
			if (tuple.s.toString().hashCode() == s1.toString().hashCode()) return tuple;
		return null;
	}

	private static Tuple<Plan, State> min(ArrayList<Tuple<Plan, State>> fringe, State g) {
		int minCost = Integer.MAX_VALUE, cost;
		Tuple<Plan, State> result = null;
		for (Tuple<Plan, State> tuple : fringe) {
			cost = tuple.pi.length() + h(tuple.s, g);
			if (minCost > cost) {
				minCost = cost;
				result = tuple;
			}
		}
		return result;
	}

	private static int h(State s, State g) {
		PlanningObject robot = g.getVariables().get(0).getParameters().get(0);
		String destCell = (String) robot.get("pos");
		String d = s.getValueOf("pos", robot.getName());
		return (int) RobotWithTrain.distance(Integer.parseInt(destCell.substring(1)), Integer.parseInt(d.substring(1)));
	}
}

class Tuple<P, S> {
	public P pi;
	public S s;

	public Tuple(P p, S s) {
		this.pi = p;
		this.s = s;
	}
}
