package planning;

public class Action {
	private String name;
	private int paramCount;
	private String[] paramTypes;
	private String[] parameters;
	private Condition[] preconditions;
	private Condition[] effects;

	public Action(String name, int paramCount, String[] parameterTypes, Condition[] preconditions, Condition[] effects) {
		super();
		this.name = name;
		paramTypes = parameterTypes;
		this.paramCount = paramCount;
		this.preconditions = preconditions;
		this.effects = effects;
	}

	public Action(Action other, String[] parameters) {
		super();
		this.name = other.name;
		this.parameters = parameters;
		this.preconditions = other.preconditions;
		this.effects = other.effects;
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

	public String[] getParameterTypes() {
		return paramTypes;
	}

	public int getParamCount() {
		return paramCount;
	}
}
