package planning.core;

import java.util.ArrayList;

public class Plan {
	private ArrayList<Action> actions;

	public Plan(ArrayList<Action> actions) {
		super();
		this.actions = actions;
	}

	public Plan() {
		super();
		actions = new ArrayList<Action>();
	}

	public Plan(Plan other) {
		super();
		actions = new ArrayList<Action>();
		for (Action action : other.getActions()) {
			actions.add(action);
		}
	}

	public Plan(Plan other, Action action) {
		for (Action a : other.actions)
			actions.add(a);
		actions.add(action);
	}

	public void addAction(Action a) {
		actions.add(a);
	}

	public void removeAction(Action a) {
		actions.remove(a);
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	@Override
	public String toString() {
		String planStr = "";
		for (Action action : actions)
			planStr += action.toString();
		return planStr;
	}

	public Action pop() {
		Action act = actions.get(0);
		actions.remove(0);
		return act;
	}

	public void addActionToFront(Action a) {
		actions.add(0, a);
	}

	public int length() {
		return actions.size();
	}
}