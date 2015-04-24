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
import planning.utility.Helper;

public class RobotGridLonayout extends Problem {

	public RobotGridLonayout(int boardSize, int robotCount) {
		HashMap<String, Set<PlanningObject>> B = new HashMap<String, Set<PlanningObject>>();
		String[] names = { "Robots", "Cells", "Status", "Indexes", "Bool" }, status = { "empty", "occupied" };

		Set<PlanningObject> robotSet = new HashSet<PlanningObject>();
		for (int i = 1; i <= robotCount * robotCount; i++)
			robotSet.add(new PlanningObject(names[0], "r" + i));
		B.put(names[0], robotSet);

		Set<PlanningObject> cellSet = new HashSet<PlanningObject>();
		for (int i = 1; i <= boardSize * boardSize; i++) {
			PlanningObject po = new PlanningObject(names[1], "c" + i);
			po.addAttribute("index", new Integer(i));
			po.addAttribute("tedge", (i > boardSize * (boardSize - 1)) ? "yes" : "no");
			po.addAttribute("bedge", (i <= boardSize) ? "yes" : "no");
			po.addAttribute("ledge", (i % boardSize == 1) ? "yes" : "no");
			po.addAttribute("redge", (i % boardSize == 0) ? "yes" : "no");
			cellSet.add(po);
		}
		B.put(names[1], cellSet);

		Set<PlanningObject> statusSet = new HashSet<PlanningObject>();
		statusSet.add(new PlanningObject(names[2], status[0]));
		statusSet.add(new PlanningObject(names[2], status[1]));
		B.put(names[2], statusSet);

		ArrayList<Variable> varDefs = new ArrayList<Variable>();
		Variable stat = new Variable("status", 1, new String[] { names[1] });
		Variable pos = new Variable("pos", 1, new String[] { names[0] });
		Variable index = new Variable("index", 1, new String[] { names[1] });
		Variable tedge = new Variable("tedge", 1, new String[] { names[1] });
		Variable bedge = new Variable("tedge", 1, new String[] { names[1] });
		Variable ledge = new Variable("tedge", 1, new String[] { names[1] });
		Variable redge = new Variable("tedge", 1, new String[] { names[1] });
		varDefs.add(stat);
		varDefs.add(pos);
		varDefs.add(index);
		varDefs.add(tedge);
		varDefs.add(bedge);
		varDefs.add(ledge);
		varDefs.add(redge);
		ArrayList<Action> actionsDef = new ArrayList<Action>();// TODO actions NOK
		Action up = new Action("up", 3, new String[] { names[0], names[1], names[1] });
		up.addPreCondition(stat, "empty", 2);
		up.addPreCondition(pos, "placeholder_2", 0);
		up.addPreCondition(tedge, "no", 1);
		up.addPreCondition(index, "placeholder_2+" + boardSize, 2);
		up.addEffect(stat, "empty", 1);
		up.addEffect(stat, "occupied", 2);
		up.addEffect(pos, "placeholder_3", 0);
		actionsDef.add(up);

		Action down = new Action("down", 3, new String[] { names[0], names[1], names[1] });
		down.addPreCondition(stat, "empty", 2);
		down.addPreCondition(pos, "placeholder_2", 0);
		down.addPreCondition(bedge, "no", 1);
		down.addPreCondition(index, "placeholder_2-" + boardSize, 2);
		down.addEffect(stat, "empty", 1);
		down.addEffect(stat, "occupied", 2);
		down.addEffect(pos, "placeholder_3", 0);
		actionsDef.add(down);

		Action left = new Action("left", 3, new String[] { names[0], names[1], names[1] });
		left.addPreCondition(stat, "empty", 2);
		left.addPreCondition(pos, "placeholder_2", 0);
		left.addPreCondition(ledge, "no", 1);
		left.addPreCondition(index, "placeholder_2-" + 1, 2);
		left.addEffect(stat, "empty", 1);
		left.addEffect(stat, "occupied", 2);
		left.addEffect(pos, "placeholder_3", 0);
		actionsDef.add(left);

		Action right = new Action("right", 3, new String[] { names[0], names[1], names[1] });
		left.addPreCondition(stat, "empty", 2);
		left.addPreCondition(pos, "placeholder_2", 0);
		left.addPreCondition(redge, "no", 1);
		left.addPreCondition(index, "placeholder_2+" + 1, 2);
		left.addEffect(stat, "empty", 1);
		left.addEffect(stat, "occupied", 2);
		left.addEffect(pos, "placeholder_3", 0);
		actionsDef.add(right);

		StateTransitionSystem system = new StateTransitionSystem(actionsDef, B);
		State s0 = new State(system.enumerateAllVariables(varDefs));

		Iterator<PlanningObject> iterator = system.getObjectMap().get("Robots").iterator();
		iterator.next().addAttribute("pos", "c1");
		iterator.next().addAttribute("pos", "c4");

		iterator = system.getObjectMap().get("Cells").iterator();
		while (iterator.hasNext()) {
			PlanningObject pObject = iterator.next();
			pObject.addAttribute("stat", (pObject.getName().equals("c1") || pObject.getName().equals("c4")) ? "occupied"
					: "empty");
		}

		ArrayList<Variable> gVariables = new ArrayList<Variable>(); // TODO
		PlanningObject p = new PlanningObject(names[0], "r1");
		p.addAttribute("pos", "c16");

		gVariables.add(new Variable(new Variable("pos", 1, new String[] { "Robots" }), p));

		State g = new State(gVariables);

		system.getStateMap().put(s0.toString().hashCode(), s0.toString());
		this.system = system;
		initialState = s0;
		goalState = g;
	}

	@Override
	public Action heuristic(State s, Problem problem) {
		ArrayList<Action> applicableActions = Helper.getApplicableActions(s, problem);
		Action action1 = applicableActions.get(0);
		State g = problem.getGoalState();
		PlanningObject p = new PlanningObject("Robots", "r1");
		Variable v = new Variable(new Variable("pos", 1, new String[] { "Robots" }), p);
		String c = g.getValueOf(v);

		double e = Double.POSITIVE_INFINITY;

		for (Action action : applicableActions) {
			State r = problem.getSystem().transition(s, action);
			PlanningObject p2 = new PlanningObject("Robots", "r1");
			Variable v2 = new Variable(new Variable("pos", 1, new String[] { "Robots" }), p2);
			String d = r.getValueOf(v2);
			double e2 = Distance(c, d);
			if (e2 < e) {
				action1 = action;
				e = e2;
			}
		}

		return action1;// TODO implement the heuristic function
	}

	private double Distance(String c, String d) {
		// TODO Auto-generated method stub
		int c1 = Integer.parseInt(c);
		int d1 = Integer.parseInt(d);
		int[] C1 = Co_ordinate(c1);
		int[] D1 = Co_ordinate(d1);
		return Math.pow(C1[0] - D1[0], 2) + Math.pow(C1[1] - D1[1], 2);
	}

	private int[] Co_ordinate(int d1) {
		// TODO parameter 'boardsize' has to be made global, here I assumed
		// boardsize is 4.

		int y = 1;
		while (d1 > 4) {
			d1 -= 4;
			y += 1;
		}

		return new int[] { d1, y };
	}
}