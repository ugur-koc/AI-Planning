package planning.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import planning.core.Action;
import planning.core.Problem;
import planning.core.State;
import planning.core.Variable;

public class Helper {

	public static ArrayList<Action> getApplicableActions(State s, Problem problem) {
		ArrayList<Action> applicableActions = new ArrayList<Action>();
		for (Action action : problem.getSystem().getActions())
			if (satifies(s, action.getPreconditions())) 
				applicableActions.add(action);
		return applicableActions;
	}

	public static boolean satifies(State s, State g) {
		ArrayList<Variable> variables = g.getVariables();
		for (Variable variable : variables)
			if (!s.satifies(variable)) return false;
		return true;
	}

	public static boolean satifies(State s, ArrayList<Variable> preconditions) {
		for (Variable cond : preconditions)
			if (!s.satifies(cond)) return false;
		return true;
	}

	public static void writeFile(String path, String str, boolean append) throws IOException {
		OutputStreamWriter stadFile1 = new OutputStreamWriter(new FileOutputStream(path, append), "UTF-8");
		stadFile1.write(str);
		stadFile1.flush();
		stadFile1.close();
	}
}