package planning;

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

	public void addAction(Action a) {
		actions.add(a);
	}

	public void removeAction(Action a) {
		actions.remove(a);
	}

	public ArrayList<Action> getActions() {
		return actions;
	}
}
