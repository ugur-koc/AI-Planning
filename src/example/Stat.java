package example;

import java.util.ArrayList;

import planning.core.PlanningObject;
import planning.core.Variable;

public class Stat extends Variable {

	public Stat(String name, int paramCount, String[] paramTypes, ArrayList<PlanningObject> parameters, String[] domain) {
		super(name, paramCount, paramTypes);
	}
}