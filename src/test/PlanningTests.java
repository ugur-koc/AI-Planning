package test;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import planning.Action;
import planning.Condition;
import planning.Plan;
import planning.Planner;
import planning.PlanningObject;
import planning.Problem;
import planning.State;
import planning.StateTransitionSystem;
import planning.Variable;

public class PlanningTests {

	@Test
	public void testPlan() throws Exception {
		Problem problem = initializeProblem1();
		Plan plan = Planner.solve(problem, "AStar");
		System.out.println(plan.toString());
	}

	@Test
	public void testPlanFromFile() throws Exception {
		Problem problem = new Problem(null, null, null);// Helper.parseInputFile("sample_csv_input.txt");
		Plan plan = Planner.solve(problem, "AStar");
		System.out.println(plan.toString());
	}

	private Problem initializeProblem1() {
		ArrayList<PlanningObject> Objects = new ArrayList<PlanningObject>();
		ArrayList<PlanningObject> B = new ArrayList<PlanningObject>();
		String[] names = { "Brushes", "Toys", "Paintcans", "Colors", "Statuses" };
		String[][] elements = { { "b1", "b2" }, { "ball", "block" }, { "pc1" }, { "red", "natural" },
				{ "clean", "loaded", "used" } };
		PlanningObject brushesPO = new PlanningObject(names[0], elements[0]);
		PlanningObject toysPO = new PlanningObject(names[1], elements[1]);
		PlanningObject paintcansPO = new PlanningObject(names[2], elements[2]);
		PlanningObject colorsPO = new PlanningObject(names[3], elements[3]);
		PlanningObject statusesPO = new PlanningObject(names[4], elements[4]);
		Objects.add(brushesPO);
		Objects.add(toysPO);
		Objects.add(paintcansPO);
		B.add(brushesPO);
		B.add(toysPO);
		B.add(paintcansPO);
		B.add(colorsPO);
		B.add(statusesPO);
		ArrayList<Variable> variables = new ArrayList<Variable>();
		Variable cVar = new Variable("color", new String[] { names[0], names[1], names[2] }, colorsPO.getElements(),
				null, "Colors");
		Variable sVar = new Variable("stat", new String[] { names[0] }, statusesPO.getElements(), null, "Statuses");
		variables.add(sVar);
		variables.add(cVar);

		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new Action("dip1", new String[] { names[0], names[2], names[3] }, new Condition[] {
				new Condition(sVar, "Brushes", "clean"), new Condition(cVar, "Paintcans", "c") }, new Condition[] {
				new Condition(sVar, "Brushes", "loaded"), new Condition(cVar, "Brushes", "c") }));

		actions.add(new Action("dip2", new String[] { names[0], names[2], names[3] }, new Condition[] {
				new Condition(cVar, "Brushes", "c"), new Condition(cVar, "Paintcans", "c") },
				new Condition[] { new Condition(sVar, "Brushes", "loaded") }));

		actions.add(new Action("paint", new String[] { names[0], names[1], names[3] }, new Condition[] {
				new Condition(sVar, "Brushes", "loaded"), new Condition(cVar, "Paintcans", "c") }, new Condition[] {
				new Condition(cVar, "Brushes", "c"), new Condition(cVar, "Brushes", "c") }));

		ArrayList<Variable> s0Vars = new ArrayList<Variable>();
		s0Vars.add(new Variable(cVar, "ball", "natural"));
		s0Vars.add(new Variable(cVar, "block", "natural"));
		s0Vars.add(new Variable(cVar, "pc1", "red"));
		s0Vars.add(new Variable(cVar, "b1", "natural"));
		s0Vars.add(new Variable(cVar, "b2", "natural"));
		s0Vars.add(new Variable(sVar, "b1", "clean"));
		s0Vars.add(new Variable(sVar, "b2", "clean"));
		State s0 = new State(s0Vars);

		ArrayList<Variable> goalVars = new ArrayList<Variable>();
		goalVars.add(new Variable(cVar, "ball", "red"));
		goalVars.add(new Variable(cVar, "block", "red"));

		HashMap<Integer, String> stateMap = new HashMap<Integer, String>();
		stateMap.put(s0.hashCode(), s0.toString());
		return new Problem(new StateTransitionSystem(stateMap, actions), s0, new State(goalVars));
	}
}