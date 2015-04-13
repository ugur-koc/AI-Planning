package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import planning.core.Action;
import planning.core.Plan;
import planning.core.Planner;
import planning.core.PlanningObject;
import planning.core.Problem;
import planning.core.State;
import planning.core.StateTransitionSystem;
import planning.core.Variable;
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

		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new Action("dip1", 3, new String[] { names[0], names[2], names[3] }));
		actions.add(new Action("dip2", 3, new String[] { names[0], names[2], names[3] }));
		actions.add(new Action("paint", 3, new String[] { names[0], names[1], names[3] }));

		ArrayList<Variable> varDefs = new ArrayList<Variable>();
		varDefs.add(new Variable("color", 1, new String[] { names[0], names[1], names[2] }));
		varDefs.add(new Variable("stat", 1, new String[] { "Brushes" }));

		StateTransitionSystem system = new StateTransitionSystem(actions, B);
		State s0 = new State(system.enumerateAllVariables(varDefs));

		Iterator<PlanningObject> iterator = system.getObjectMap().get("Toys").iterator();
		iterator.next().addAttribute("color", "natural");
		iterator.next().addAttribute("color", "natural");

		iterator = system.getObjectMap().get("Paintcans").iterator();
		iterator.next().addAttribute("color", "red");

		iterator = system.getObjectMap().get("Brushes").iterator();
		while (iterator.hasNext()) {
			PlanningObject pObject = iterator.next();
			pObject.addAttribute("color", "natural");
			pObject.addAttribute("stat", "clean");
		}

		ArrayList<Variable> gVariables = new ArrayList<Variable>(); // TODO
		PlanningObject p = new PlanningObject("Toys", "ball");
		PlanningObject p2 = new PlanningObject("Toys", "block");
		p.addAttribute("color", "red");
		p2.addAttribute("color", "red");

		gVariables.add(new Variable(new Variable("color", 1, new String[] { "Toys" }), p));
		gVariables.add(new Variable(new Variable("color", 1, new String[] { "Toys" }), p2));

		State g = new State(gVariables);

		system.getStateMap().put(s0.toString().hashCode(), s0.toString());
		return new Problem(system, s0, g);
	}
}