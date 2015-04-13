package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import planning.Action;
import planning.Plan;
import planning.Planner;
import planning.PlanningObject;
import planning.Problem;
import planning.State;
import planning.StateTransitionSystem;
import planning.Variable;
import exceptions.NoVariableException;

public class PlanningTests {

	@Test
	public void testPlan() throws Exception {
		Problem problem = initializeProblem1();
		Plan plan = Planner.solve(problem, "AStar");
		System.out.println(plan.toString());
	}

	@Test
	public void testPlanFromFile() throws Exception {
		// Helper.parseInputFile("sample_csv_input.txt");
		Problem problem = new Problem(null, null, null);
		Plan plan = Planner.solve(problem, "AStar");
		System.out.println(plan.toString());
	}

	private Problem initializeProblem1() throws NoVariableException {
		HashMap<String, Set<PlanningObject>> B = new HashMap<String, Set<PlanningObject>>();
		String[] names = { "Brushes", "Toys", "Paintcans", "Colors", "Statuses" };
		String[][] elt = { { "b1", "b2" }, { "ball", "block" }, { "pc1" }, { "red", "natural" },
				{ "clean", "loaded", "used" } };
		for (int i = 0; i < names.length; i++) {
			Set<PlanningObject> planningObjects = new HashSet<PlanningObject>();
			for (int j = 0; j < elt[i].length; j++)
				planningObjects.add(new PlanningObject(names[i], elt[i][j]));
			B.put(names[i], planningObjects);
		}
		ArrayList<Variable> variables = new ArrayList<Variable>();
		variables.add(new Variable("color", 1, new String[] { "Brushes", "Toys", "Paintcans" }, null, null));
		variables.add(new Variable("stat", 1, new String[] { "Brushes" }, null, null));

		ArrayList<Action> actions = new ArrayList<Action>();
		ArrayList<Variable> preCond = new ArrayList<Variable>();
		ArrayList<Variable> effects = new ArrayList<Variable>();
		actions.add(new Action("dip1", 3, new String[] { "Brushes", "Paintcans", "Colors" }, null, preCond, effects));
		actions.add(new Action("dip2", 3, new String[] { "Brushes", "Paintcans", "Colors" }, null, null, null));
		actions.add(new Action("paint", 3, new String[] { "Brushes", "Toys", "Colors" }, null, null, null));

		StateTransitionSystem system = new StateTransitionSystem(actions, variables, B);
		State s0 = new State(system.enumerateAllVariables(variables));

		Iterator<PlanningObject> iterator = system.getObjectMap().get("Toys").iterator();
		iterator.next().addAttribute("color", "natural");
		iterator.next().addAttribute("color", "natural");

		iterator = system.getObjectMap().get("Paintcans").iterator();
		iterator.next().addAttribute("color", "red");

		iterator = system.getObjectMap().get("Brushes").iterator();
		while (iterator.hasNext()) {
			PlanningObject po = iterator.next();
			po.addAttribute("color", "natural");
			po.addAttribute("stat", "clean");
		}

		ArrayList<Variable> gVariables = new ArrayList<Variable>();
		ArrayList<PlanningObject> planningObjects = new ArrayList<PlanningObject>();
		PlanningObject e = new PlanningObject("Toys", "ball");
		e.addAttribute("color", "red");
		planningObjects.add(e);
		gVariables.add(new Variable("color", 1, new String[] { "Toys" }, planningObjects, null));
		ArrayList<PlanningObject> planningObjects2 = new ArrayList<PlanningObject>();
		PlanningObject e2 = new PlanningObject("Toys", "ball");
		e2.addAttribute("color", "red");
		planningObjects.add(e2);
		gVariables.add(new Variable("color", 1, new String[] { "Toys" }, planningObjects2, null));
		State g = new State(gVariables);

		system.getStateMap().put(s0.toString().hashCode(), s0.toString());
		return new Problem(system, s0, g);
	}
}