package test;

import java.util.ArrayList;

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
		StateTransitionSystem system = new StateTransitionSystem();
		String[] names = { "Brushes", "Toys", "Paintcans", "Colors", "Statuses" };
		String[][] elements = { { "b1", "b2" }, { "ball", "block" }, { "pc1" }, { "red", "natural" },
				{ "clean", "loaded", "used" } };
		PlanningObject brushesPO = new PlanningObject(names[0], elements[0]);
		PlanningObject toysPO = new PlanningObject(names[1], elements[1]);
		PlanningObject paintcansPO = new PlanningObject(names[2], elements[2]);
		PlanningObject colorsPO = new PlanningObject(names[3], elements[3]);
		PlanningObject statusesPO = new PlanningObject(names[4], elements[4]);
		system.getObjects().add(brushesPO);
		system.getObjects().add(toysPO);
		system.getObjects().add(paintcansPO);
		system.getB().add(brushesPO);
		system.getB().add(toysPO);
		system.getB().add(paintcansPO);
		system.getB().add(colorsPO);
		system.getB().add(statusesPO);
		ArrayList<Variable> variables = new ArrayList<Variable>();
		Variable cVar = new Variable("color", new PlanningObject[] { brushesPO, toysPO, paintcansPO },
				colorsPO.getElements(), "Colors");
		Variable sVar = new Variable("stat", new PlanningObject[] { brushesPO }, statusesPO.getElements(), "Statuses");
		variables.add(sVar);
		variables.add(cVar);

		system.addActions(new Action("dip1", 3, new String[] { names[0], names[2], names[3] }, new Condition[] {
				new Condition(sVar, "Brushes", "clean"), new Condition(cVar, "Paintcans", "c") }, new Condition[] {
				new Condition(sVar, "Brushes", "loaded"), new Condition(cVar, "Brushes", "c") }));

		system.addActions(new Action("dip2", 3, new String[] { names[0], names[2], names[3] }, new Condition[] {
				new Condition(cVar, "Brushes", "c"), new Condition(cVar, "Paintcans", "c") },
				new Condition[] { new Condition(sVar, "Brushes", "loaded") }));

		system.addActions(new Action("paint", 3, new String[] { names[0], names[1], names[3] }, new Condition[] {
				new Condition(sVar, "Brushes", "loaded"), new Condition(cVar, "Paintcans", "c") }, new Condition[] {
				new Condition(cVar, "Brushes", "c"), new Condition(cVar, "Brushes", "c") }));

		State s0 = new State();
		s0.addVariable(cVar, "ball", "natural");
		s0.addVariable(cVar, "block", "natural");
		s0.addVariable(cVar, "pc1", "red");
		s0.addVariable(cVar, "b1", "natural");
		s0.addVariable(cVar, "b2", "natural");
		s0.addVariable(sVar, "b1", "clean");
		s0.addVariable(sVar, "b2", "clean");

		State g = new State();
		g.addVariable(cVar, "ball", "natural");
		g.addVariable(cVar, "block", "natural");

		system.getStateMap().put(s0.hashCode(), s0.toString());
		return new Problem(system, s0, g);
	}
}