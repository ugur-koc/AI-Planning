package planning;

import java.util.ArrayList;
import java.util.HashMap;

public class B {
	HashMap<String, PlanningObject> objectsMap;

	public B(HashMap<String, PlanningObject> objectsMap) {
		super();
		this.objectsMap = objectsMap;
	}

	public B() {
		objectsMap = new HashMap<String, PlanningObject>();
	}

	public HashMap<String, PlanningObject> getObjects() {
		return objectsMap;
	}

	public PlanningObject getPlanningObject(String key) {
		return objectsMap.get(key);
	}

	public void addPlanningObject(String key, PlanningObject planningObject) {
		objectsMap.put(key, planningObject);
	}
}

class PlanningObject {
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
