package test.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import planning.core.Action;
import planning.core.PlanningObject;
import planning.core.Problem;
import planning.core.State;
import planning.core.StateTransitionSystem;
import planning.core.Variable;

public class RobotGridLonayout extends Problem {

	public RobotGridLonayout(int boardSize, int robotCount) {
		HashMap<String, Set<PlanningObject>> B = new HashMap<String, Set<PlanningObject>>();
		String[] names = { "Robots", "Cells" };

		String[] cells = new String[boardSize * boardSize];
		Set<PlanningObject> cellSet = new HashSet<PlanningObject>();
		for (String cell : cells)
			cellSet.add(new PlanningObject(names[0], cell));
		B.put(names[0], cellSet);

		String[] robots = new String[robotCount * robotCount];
		Set<PlanningObject> robotSet = new HashSet<PlanningObject>();
		for (String robot : robots)
			robotSet.add(new PlanningObject(names[1], robot));
		B.put(names[1], robotSet);

		ArrayList<Variable> varDefs = new ArrayList<Variable>();
		Variable color = new Variable("status", 1, new String[] { names[1] });
		varDefs.add(color);
		Variable stat = new Variable("pos", 1, new String[] { names[0] });
		varDefs.add(stat);

		ArrayList<Action> actionsDef = new ArrayList<Action>();
		Action dip1 = new Action("up", 3, new String[] { names[0], names[2], names[3] });
		dip1.addPreCondition(stat, "clean", 0);
		dip1.addPreCondition(color, "placeholder_3", 1);
		dip1.addEffect(stat, "loaded", 0);
		dip1.addEffect(color, "placeholder_3", 0);
		actionsDef.add(dip1);
		Action dip2 = new Action("dip2", 3, new String[] { names[0], names[2], names[3] });
		dip2.addPreCondition(color, "placeholder_3", 0);
		dip2.addPreCondition(color, "placeholder_3", 1);
		dip2.addEffect(stat, "loaded", 0);
		actionsDef.add(dip2);
		Action paint = new Action("paint", 3, new String[] { names[0], names[1], names[3] });
		paint.addPreCondition(stat, "loaded", 0);
		paint.addPreCondition(color, "placeholder_3", 0);
		paint.addEffect(stat, "used", 0);
		paint.addEffect(color, "placeholder_3", 1);
		actionsDef.add(paint);

		StateTransitionSystem system = new StateTransitionSystem(actionsDef, B);
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
		this.system = system;
		initialState = s0;
		goalState = g;
	}

	@Override
	public Action heuristic(ArrayList<Action> applicableActions) {

		return applicableActions.get(0);// TODO implement the heuristic function
	}
}
