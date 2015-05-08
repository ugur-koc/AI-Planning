package test.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exceptions.NoPlanException;
import planning.core.Action;
import planning.core.PlanningObject;
import planning.core.Problem;
import planning.core.State;
import planning.core.StateTransitionSystem;
import planning.core.Variable;

public class RobotWithTrain extends Problem {
	public static int boardSize;
	HashMap<String, Set<PlanningObject>> B = new HashMap<String, Set<PlanningObject>>();
	ArrayList<String> occupiedCells = new ArrayList<String>();

	public RobotWithTrain(int boardSize, int robotCount) {
		RobotWithTrain.boardSize = boardSize;
		String[] names = { "Robots", "Cells", "Status", "Indexes", "Bool" }, status = { "empty", "occupied" };

		Set<PlanningObject> robotSet = new HashSet<PlanningObject>();
		for (int i = 1; i <= robotCount; i++)
			robotSet.add(new PlanningObject(names[0], "r" + i));
		B.put(names[0], robotSet);

		Set<PlanningObject> cellSet = new HashSet<PlanningObject>();
		for (int i = 1; i <= boardSize * boardSize; i++) {
			PlanningObject po = new PlanningObject(names[1], "c" + i);
			po.addAttribute("index", "" + i);
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
		Variable pos = new Variable("pos", 1, new String[] { names[0] });
		varDefs.add(pos);
		Variable stat = new Variable("status", 1, new String[] { names[1] });
		varDefs.add(stat);
		Variable index = new Variable("index", 1, new String[] { names[1] });
		varDefs.add(index);
		Variable tedge = new Variable("tedge", 1, new String[] { names[1] });
		varDefs.add(tedge);
		Variable bedge = new Variable("bedge", 1, new String[] { names[1] });
		varDefs.add(bedge);
		Variable ledge = new Variable("ledge", 1, new String[] { names[1] });
		varDefs.add(ledge);
		Variable redge = new Variable("redge", 1, new String[] { names[1] });
		varDefs.add(redge);

		ArrayList<Action> actionsDef = new ArrayList<Action>();
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
		right.addPreCondition(stat, "empty", 2);
		right.addPreCondition(pos, "placeholder_2", 0);
		right.addPreCondition(redge, "no", 1);
		right.addPreCondition(index, "placeholder_2+" + 1, 2);
		right.addEffect(stat, "empty", 1);
		right.addEffect(stat, "occupied", 2);
		right.addEffect(pos, "placeholder_3", 0);
		actionsDef.add(right);

		StateTransitionSystem robotSystem = new StateTransitionSystem(actionsDef, B);
		State s0 = new State(robotSystem.enumerateAllVariables(varDefs));

		String cell;
		Iterator<PlanningObject> iterator = robotSystem.getObjectMap().get("Robots").iterator();
		while (iterator.hasNext()) {
			PlanningObject po = iterator.next();
			if (po.getName().equals("r2")) {
				cell = "c44";
			} else if (po.getName().equals("r3")) {
				cell = "c45";
			} else if (po.getName().equals("r4")) {
				cell = "c46";
			} else if (po.getName().equals("r5")) {
				cell = "c38";
			} else if (po.getName().equals("r6")) {
				cell = "c30";
			} else cell = "c28";
			po.addAttribute("pos", cell);
			occupiedCells.add(cell);
		}

		iterator = robotSystem.getObjectMap().get("Cells").iterator();
		while (iterator.hasNext()) {
			PlanningObject pObject = iterator.next();
			pObject.addAttribute("status", occupiedCells.contains(pObject.getName()) ? "occupied" : "empty");
		}

		ArrayList<Variable> gVariables = new ArrayList<Variable>();
		PlanningObject p = new PlanningObject(names[0], "r1");
		p.addAttribute("pos", "c" + boardSize * boardSize);
		gVariables.add(new Variable(new Variable("pos", 1, new String[] { "Robots" }), p));

		robotSystem.getStateMap().put(s0.toString().hashCode(), s0.toString());
		system = robotSystem;
		initialState = s0;
		goalState = new State(gVariables);
	}

	@Override
	public Action heuristic(State s, Problem problem, ArrayList<Action> applicableActions) {
		double e = Double.POSITIVE_INFINITY;
		PlanningObject robot = problem.getGoalState().getVariables().get(0).getParameters().get(0);
		String destCell = (String) robot.get("pos");

		Action candidateAction = applicableActions.get(0);
		for (Action action : applicableActions) {
			State r = null;
			try {
				r = problem.getSystem().transition(s, action);
			} catch (NoPlanException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String d = r.getValueOf("pos", robot.getName());
			double e2 = distance(Integer.parseInt(destCell.substring(1)), Integer.parseInt(d.substring(1)));
			if (e2 < e) {
				candidateAction = action;
				e = e2;
			}
		}
		return candidateAction;
	}

	public static double distance(int c1, int d1) {
		int[] C1 = Co_ordinate(c1), D1 = Co_ordinate(d1);
		return Math.pow(C1[0] - D1[0], 2) + Math.pow(C1[1] - D1[1], 2);
	}

	public static double stepCount(int c1, int d1) {
		int[] C1 = Co_ordinate(c1), D1 = Co_ordinate(d1);
		return Math.abs(C1[0] - D1[0]) + Math.abs(C1[1] - D1[1]);
	}

	private static int[] Co_ordinate(int d1) {
		int y = 1 + d1 / RobotWithTrain.boardSize;
		while (d1 > RobotWithTrain.boardSize)
			d1 -= RobotWithTrain.boardSize;
		return new int[] { d1, y };
	}

	public static PlanningObject getRobot(Set<PlanningObject> set, String robotName) {
		for (PlanningObject planningObject : set)
			if (planningObject.getName().equals(robotName)) return planningObject;
		return null;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 1; i <= boardSize * boardSize; i++)
			result += ((getInitialState().getValueOf("status", "c" + i).equals("occupied")) ? "r " : "  ")
					+ ((i % boardSize == 0) ? "\n" : "");
		return result;
	}
}