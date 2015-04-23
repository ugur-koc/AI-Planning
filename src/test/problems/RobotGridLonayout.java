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
		Variable index = new Variable("index", 1, new String[] { names[1] });
		varDefs.add(stat);
		varDefs.add(pos);
		varDefs.add(index);

		ArrayList<Action> actionsDef = new ArrayList<Action>();// TODO actions NOK
		Action up = new Action("up", 3, new String[] { names[0], names[1], names[1] });
		up.addPreCondition(stat, "empty", 1);
		up.addPreCondition(stat, "empty", 2);
		//up.addPreCondition(index, "i", 1);
		up.addPreCondition(index, "placeholder_2 + 4", 2);
		up.addPreCondition(pos, "placeholder_2", 0);
		up.addEffect(stat, "empty", 1);
		up.addEffect(stat, "occupied", 2);
		actionsDef.add(up);

		Action down = new Action("down", 3, new String[] { names[0], names[1], names[1] });
		down.addPreCondition(stat, "empty", 1);
		down.addPreCondition(stat, "empty", 2);
		down.addPreCondition(index, "i+4", 1);
		down.addPreCondition(index, "i", 2);
		down.addPreCondition(pos, "placeholder_2", 0);
		down.addEffect(stat, "empty", 1);
		down.addEffect(stat, "occupied", 2);
		actionsDef.add(down);
		
		Action left = new Action("left", 3, new String[] { names[0], names[1], names[1] });
		left.addPreCondition(stat, "empty", 1);
		left.addPreCondition(stat, "empty", 2);
		left.addPreCondition(index, "i+1", 1);
		left.addPreCondition(index, "i", 2);
		left.addPreCondition(pos, "placeholder_2", 0);
		left.addEffect(stat, "empty", 1);
		left.addEffect(stat, "occupied", 2);
		actionsDef.add(left);
		
		Action right = new Action("right", 3, new String[] { names[0], names[1], names[1] });
		right.addPreCondition(stat, "empty", 1);
		right.addPreCondition(stat, "empty", 2);
		right.addPreCondition(index, "i", 1);
		right.addPreCondition(index, "i+1", 2);
		right.addPreCondition(pos, "placeholder_2", 0);
		right.addEffect(stat, "empty", 1);
		right.addEffect(stat, "occupied", 2);
		actionsDef.add(right);
		
		
		
		
		
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
	public Action heuristic(State s, Problem problem) {
		ArrayList<Action> applicableActions = Helper.getApplicableActions(s, problem);
		Action action1= applicableActions.get(0);
		State g =problem.getGoalState();
		PlanningObject p = new PlanningObject("Robots", "r1");
		Variable v = new Variable(new Variable("pos", 1, new String[] {"Robots" }), p);
		String c= g.getValueOf(v);
		
		
		double e =Double.POSITIVE_INFINITY;
				
		for (Action action: applicableActions){
			State r = problem.getSystem().transition(s, action);
			PlanningObject p2 = new PlanningObject("Robots", "r1");
			Variable v2 = new Variable(new Variable("pos", 1, new String[] {"Robots" }), p2);
			String d= r.getValueOf(v2);
			double e2=Distance(c,d);
			if (e2< e) {
				action1=action;
				e=e2;
			}
		}

		return action1;// TODO implement the heuristic function
	}


	private double Distance(String c, String d) {
		// TODO Auto-generated method stub
		int c1= Integer.parseInt(c);
		int d1= Integer.parseInt(d);
		int[] C1=Co_ordinate(c1);
		int[] D1=Co_ordinate(d1);
		return Math.pow(C1[0]-D1[0],2)+Math.pow(C1[1]-D1[1],2 );
	}


	private int[] Co_ordinate(int d1) {
		// TODO parameter 'boardsize' has to be made global, here I assumed boardsize is 4.
		
		int y=1;
		while (d1>4){
			d1-=4;
			y+=1;
		}
		
		return new int[] {d1,y};
	}
}
