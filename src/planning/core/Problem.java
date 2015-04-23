package planning.core;

import java.util.ArrayList;

public abstract class Problem {
	protected StateTransitionSystem system;
	protected State initialState;
	protected State goalState;

	public Problem() {
	}

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

	public abstract Action heuristic(State s, Problem problem);
	
}