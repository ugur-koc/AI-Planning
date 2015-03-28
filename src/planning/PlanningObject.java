package planning;

import java.util.ArrayList;

public class PlanningObject {
	String name;
	ArrayList<String> elements;

	public PlanningObject(String name) {
		super();
		elements = new ArrayList<String>();
		this.name = name;
	}

	public PlanningObject(String name, String[] elementArr) {
		this.name = name;
		elements = new ArrayList<String>();
		for (String string : elementArr)
			elements.add(string);
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getElements() {
		return elements;
	}
}