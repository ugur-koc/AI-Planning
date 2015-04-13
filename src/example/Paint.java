package example;

import java.util.List;

import planning.core.Action;
import planning.core.PlanningObject;

public class Paint extends Action {

	public Paint(Action other, List<PlanningObject> parameters) {
		super(other, parameters);
	}
}