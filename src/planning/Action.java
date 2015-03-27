package planning;

public class Action {
	private Variable[] preconditions;
	private Variable[] effects;
	private String name;
	private String[] parameters;

	public Action(String name, String[] parameters, Variable[] preconditions, Variable[] effects) {
		super();
		this.name = name;
		this.parameters = parameters;
		this.preconditions = preconditions;
		this.effects = effects;
	}

	public Action(String name2, String[] parameters2, String[] preconditions2, String[] effects2) {
		// TODO Auto-generated constructor stub
	}

	public Variable[] getPreconditions() {
		return preconditions;
	}

	public Variable[] getEffects() {
		return effects;
	}

	public String getName() {
		return name;
	}

	public String[] getParameters() {
		return parameters;
	}
}
