package planning;

public class Condition {

	Variable variable;
	String element;
	String value;

	public Condition(Variable variable, String element, String value) {
		super();
		this.variable = variable;
		PlanningObject[] parameters = variable.getParameters();// TODO
		this.value = value;
		this.element = element;
	}

	public Variable getVariable() {
		return variable;
	}

	public String getValue() {
		return value;
	}

	public String getElement() {
		return element;
	}
}
