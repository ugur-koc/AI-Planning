package planning;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class State {

	private HashMap<String, String> assignments;

	public State() {
		super();
		assignments = new HashMap<String, String>();
	}

	public String toString() {
		String rslt = "{";
		Set<Entry<String, String>> entrySet = assignments.entrySet();
		// TODO maybe not sorted
		for (Entry<String, String> entry : entrySet)
			rslt += entry.getKey() + ":" + entry.getValue();
		return rslt + "}";
	}

	public String getValueOf(Variable v, String param) {
		return assignments.get(v.getName() + ":" + param);
	}

	public String getValueOf(String key) {
		return assignments.get(key);
	}

	public void updateVariable(Variable v, String param, String value) {
		assignments.put(v.getName() + ":" + param, value);
	}

	public void addVariable(Variable v, String param, String value) {
		assignments.put(v.getName() + ":" + param, value);
	}

	public HashMap<String, String> getAssignments() {
		return assignments;
	}
}