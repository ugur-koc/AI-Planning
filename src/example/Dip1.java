package example;

import java.util.List;

import planning.core.Action;
import planning.core.PlanningObject;
import planning.core.State;

public class Dip1 extends Action {

	public Dip1(Action other, List<PlanningObject> parameters) {
		super(other, parameters);
		// TODO Auto-generated constructor stub
	}

	public boolean checkPreCondition(State s) {
		
		return false;
		
	}
}
