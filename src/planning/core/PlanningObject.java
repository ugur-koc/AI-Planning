package planning.core;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class PlanningObject {
	private String name;
	private String type;

	private HashMap<String, Object> attributes = new HashMap<String, Object>();

	public PlanningObject(String type, String name) {
		super();
		this.name = name;
		this.type = type;
	}

	public PlanningObject(PlanningObject other, String att, Object value) {
		super();
		this.name = other.name;
		this.type = other.type;
		attributes.put(att, value);
	}

	public PlanningObject(PlanningObject planningObject) {
		this.name = planningObject.name;
		this.type = planningObject.type;
		Set<Entry<String, Object>> entrySet = planningObject.attributes.entrySet();
		for (Entry<String, Object> entry : entrySet)
			attributes.put(entry.getKey(), entry.getValue());

	}

	public void addAttribute(String att, Object value) {
		attributes.put(att, value);
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Object get(String string) {
		return attributes.get(string);
	}

	@Override
	public String toString() {
		String rslt = name + ":" + type + "{";
		Set<Entry<String, Object>> entrySet = attributes.entrySet();
		for (Entry<String, Object> entry : entrySet)
			rslt += entry.getKey() + ":" + entry.getValue().toString() + " ";
		return rslt + "}";
	}
}