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
		String[] names = { "Robots", "Cells", "Status" };
		String[] status = { "empty", "occupied" };

		Set<PlanningObject> cellSet = new HashSet<PlanningObject>();
		String[] cells = new String[boardSize * boardSize];
		for (int i = 1; i <= cells.length; i++) {
			cells[i] = "c" + i;
			cellSet.add(new PlanningObject(names[0], cells[i]));
		}
		B.put(names[0], cellSet);

		String[] robots = new String[robotCount * robotCount];
		Set<PlanningObject> robotSet = new HashSet<PlanningObject>();
		for (int i = 1; i <= robots.length; i++) {
			robots[i] = "r" + i;
			robotSet.add(new PlanningObject(names[1], robots[i]));
		}
		B.put(names[1], robotSet);

		Set<PlanningObject> statusSet = new HashSet<PlanningObject>();
		for (String string : status)
			statusSet.add(new PlanningObject(names[2], string));
		B.put(names[2], statusSet);

		ArrayList<Variable> varDefs = new ArrayList<Variable>();
		Variable stat = new Variable("status", 1, new String[] { names[1] });
		Variable pos = new Variable("pos", 1, new String[] { names[0] });
		varDefs.add(stat);
		varDefs.add(pos);

		ArrayList<Action> actionsDef = new ArrayList<Action>();// TODO actions NOK
		Action up = new Action("up", 3, new String[] { names[0], names[1], names[1] });
		up.addPreCondition(stat, "empty", 1);
		up.addPreCondition(pos, "placeholder_2", 0);
		up.addEffect(stat, "empty", 1);
		up.addEffect(stat, "occupied", 2);
		actionsDef.add(up);

		StateTransitionSystem system = new StateTransitionSystem(actionsDef, B);
		State s0 = new State(system.enumerateAllVariables(varDefs));

		Iterator<PlanningObject> iterator = system.getObjectMap().get("Robots").iterator();
		iterator.next().addAttribute("pos", "c1");
		iterator.next().addAttribute("pos", "c4");

		iterator = system.getObjectMap().get("Cells").iterator();
		while (iterator.hasNext()) {
			PlanningObject pObject = iterator.next();
			if (pObject.getName().equals("c1") || pObject.getName().equals("c4"))
				pObject.addAttribute("stat", "occupied");
			pObject.addAttribute("stat", "empty");
		}

		ArrayList<Variable> gVariables = new ArrayList<Variable>(); // TODO
		PlanningObject p = new PlanningObject(names[0], "r1");
		p.addAttribute("pos", "c16");

		gVariables.add(new Variable(new Variable("color", 1, new String[] { "Toys" }), p));

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
