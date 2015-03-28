package planning;


public class Action {
	private Condition[] preconditions;
	private Condition[] effects;
	private String name;
	private String[] parameters;

	public Action(String name, String[] parameters, Condition[] preconditions, Condition[] effects) {
		super();
		this.name = name;
		this.parameters = parameters;
		this.preconditions = preconditions;
		this.effects = effects;
	}

	public Condition[] getPreconditions() {
		return preconditions;
	}

	public Condition[] getEffects() {
		return effects;
	}

	public String getName() {
		return name;
	}

	public String[] getParameters() {
		return parameters;
	}
}
