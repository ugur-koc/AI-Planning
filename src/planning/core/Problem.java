package planning.core;

public class Problem {
	private StateTransitionSystem system;
	private State initialState;
	private State goalState;

	public Problem(StateTransitionSystem stateTransitionSystem, State initialState, State goalState) {
		super();
		this.system = stateTransitionSystem;
		this.initialState = initialState;
		this.goalState = goalState;
	}

	public StateTransitionSystem getSystem() {
		return system;
	}

	public State getInitialState() {
		return initialState;
	}

	public State getGoalState() {
		return goalState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}
}