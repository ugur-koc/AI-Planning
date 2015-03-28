package planning;

import java.util.ArrayList;

public class Helper {
	public static boolean satifies(Condition[] variables, State state) {
		for (Condition aVariable : variables)
			if (!state.getVariables().contains(aVariable)) return false;//TODO
		return true;
	}

	public static boolean satifies(State s, State g) {
		for (Variable variable : g.getVariables())
			if (!s.getVariables().contains(variable)) return false;
		return true;
	}

	public static ArrayList<Action> getApplicableActions(State s, ArrayList<Action> actions) {
		ArrayList<Action> arrayList = new ArrayList<Action>();
		for (Action action : actions)
			if (satifies(action.getPreconditions(), s)) arrayList.add(action);
		return arrayList;
	}

//	public static Problem parseInputFile(String string) throws Exception {
//		HashMap<Integer, String> states = new HashMap<Integer, String>();
//		ArrayList<Action> actions = new ArrayList<Action>();
//		ArrayList<Variable> variables = new ArrayList<Variable>();
//		State initialState = null, goalState = null;
//		B b = new B();
//
//		String line;
//		Scanner scanner = new Scanner(new FileInputStream(string), "UTF-8");
//		while (scanner.hasNext()) {
//			line = scanner.nextLine();
//			while (!line.startsWith("Objects")) {
//				addPlanningObject(b, line);
//				line = scanner.nextLine();
//			}
//			for (; !line.equalsIgnoreCase("StateVariables") && scanner.hasNext(); line = scanner.nextLine());
//			line = scanner.nextLine();
//			while (!line.equals("")) {
//				variables.add(createVariable(b, line, scanner.nextLine()));
//				line = scanner.nextLine();
//			}
//			for (; !line.equalsIgnoreCase("Actions") && scanner.hasNext(); line = scanner.nextLine());
//			line = scanner.nextLine();
//			while (!line.equals("")) {
//				actions.add(createAction(line, scanner.nextLine(), scanner.nextLine()));
//				line = scanner.nextLine();
//			}
//			for (; !line.startsWith("s0") && scanner.hasNext(); line = scanner.nextLine());
//			initialState = createState(line);
//			line = scanner.nextLine();
//			goalState = createState(line);
//		}
//		scanner.close();
//		return new Problem(new StateTransitionSystem(states, actions), initialState, goalState);
//	}
//
//	private static State createState(String line) {
//		ArrayList<Variable> variables = new ArrayList<Variable>();
//		String substring = line.substring(line.indexOf("{"), line.indexOf("}"));
//		String[] variableArr = substring.split(",");
//		for (String string : variableArr) {
//			String[] split = string.split("=");
//			String name = split[0].substring(0, split[0].indexOf("("));
//			String[] param = split[0].substring(split[0].indexOf("("), split[0].indexOf(")")).split(",");
//			Parameter[] parameters = new Parameter[param.length];
//			for (int i = 0; i < param.length; i++) {
//				String[] str = param[i].split(":");
//				parameters[i] = new Parameter(str[1], str[0]);
//			}
//			// variables.add(new Variable(name, parameters, null, split[1], null));
//			// // TODO
//		}
//		return new State(variables);
//	}
//
//	private static Action createAction(String line, String preLine, String effLine) {
//		String name = line.substring(0, line.indexOf("("));
//		String[] parameters = line.substring(line.indexOf("("), line.indexOf(")")).split(",");
//		String[] preconditions = preLine.split(",");// TODO
//		String[] effects = effLine.split(",");
//		return new Action(name, null, null, null);// TODO
//	}
//
//	private static void addPlanningObject(B b, String line) {
//		String[] lineArr = line.split("=");
//		String[] elements = lineArr[1].split(",");
//		elements[0] = elements[0].substring(1);
//		elements[elements.length - 1] = elements[elements.length - 1].substring(0,
//				elements[elements.length - 1].length() - 1);
//		b.addPlanningObject(lineArr[0], new PlanningObject(lineArr[0], elements));
//	}
//
//	private static Variable createVariable(B b, String line, String domLine) {
//		String name = line.substring(0, line.indexOf("("));
//		String[] param = line.substring(line.indexOf("("), line.indexOf(")")).split(",");
//		Parameter[] parameters = new Parameter[param.length];
//		for (int i = 0; i < param.length; i++) {
//			String[] str = param[i].split(":");
//			parameters[i] = new Parameter(str[1], str[0]);
//		}
//		return new Variable(name, null, b.getPlanningObject(domLine.split("=")[1]).getElements(), null, null);// TODO
//	}
}