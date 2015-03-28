package planning;


public class Condition {

	Variable variable;
	String obj;
	String value;

	public Condition(Variable variable, String obj, String value) {
		super();
		this.variable = variable;
		this.value = value;
	}

	public Variable getVariable() {
		return variable;
	}

	public String getValue() {
		return value;
	}
}
