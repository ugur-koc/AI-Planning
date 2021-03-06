package test.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import planning.core.PlanningObject;
import planning.core.Problem;
import planning.core.State;
import planning.core.Variable;
import planning.utility.Helper;
import test.problems.RobotGridLayout;
import test.problems.RobotWithTrain;
import acting.Actor;
import exceptions.NoPlanException;

/*
 * Purpose of this class is to conduct the experiments to evaluate our approach in different configurations.
 * Here also compare our approach with existing approaches
 * Independent variables for this experiment: planning algorithm, refinement algorithm, #of robots, size of grid, dynamicity
 * Dependent variables for this experiment: #of planning calls, #of actions taken, average planning time, average acting time, overall time
 * 
 */
public class Experiment {
	public static int plannerCallCount, actionCount;
	public static long totalPlanningTime, totalActingTime, startTime, totalTime;;

	public static void main(String[] args) throws IOException {

		int gridSize[] = { 6, 8, 16, 25 }, robotCount[] = { 2, 4 }, dynamicities[] = { 0, 1, 2, 3, 4, 5 };
		String[] planningAlgs = { "FS", "DFS", "AStar" }, refMethods = { "AP_lazy", "AP_interleaved", "AP_mixed" };
		String exceptionMessage, stad = "run,planningAlg,refinementAlg,robotCount,gridSize,dynamicity,planningCallCount,totalPlanningTime,averagePlanningTime,"
				+ "takenActionCount,totalActingTime,averageActingTime,totalTime,executionCode\n";
		Helper.writeFile("stads.txt", stad, false);

		for (int gS : gridSize)
			for (String planningAlg : planningAlgs)
				for (int rC : robotCount)
					for (int dyn : dynamicities)
						for (String refinement : refMethods)
							for (int r = 0; r < 3; r++) {
								plannerCallCount = actionCount = 0;
								totalPlanningTime = totalActingTime = 0;
								startTime = System.currentTimeMillis();
								exceptionMessage = ",GOOD";
								try {
									Actor.act(new RobotGridLayout(gS, rC), refinement, planningAlg, dyn);
								} catch (NoPlanException e) {
									exceptionMessage = "," + e.getMessage();
									actionCount = 1;
								}
								totalTime = TimeUnit.MILLISECONDS.toMillis(Math.abs(startTime - System.currentTimeMillis()));
								stad = r + "," + planningAlg + "," + refinement + "," + rC + "," + gS + "," + dyn + ","
										+ plannerCallCount + "," + totalPlanningTime + ","
										+ ((long) totalPlanningTime / (long) plannerCallCount) + "," + actionCount + ","
										+ totalActingTime + "," + ((long) totalActingTime / (long) actionCount) + "," + totalTime
										+ exceptionMessage + "\n";
								Helper.writeFile("stads.txt", stad, true);
							}
		Helper.writeFile("stads.txt", "END\n", true);
	}

	public static void applyRandomChanges(Problem problem, State s) {
		Random random = new Random();
		PlanningObject planningObject;
		String directions[] = { "t", "b", "r", "l" }, toCell, fromCell;
		ArrayList<String> gRobots = new ArrayList<String>();
		int increments[] = { RobotWithTrain.boardSize, RobotWithTrain.boardSize * (-1), 1, -1 }, index;
		for (Variable v : problem.getGoalState().getVariables())
			if (v.getParameters().get(0).getType().equals("Robots")) gRobots.add(v.getParameters().get(0).getName());

		for (int i = 0; i < Actor.dynamicity; i++) {
			do {
				planningObject = s.getVariables().get(random.nextInt(s.getVariables().size())).getParameters().get(0);
			} while (!planningObject.getType().equals("Robots") || gRobots.contains(planningObject.getName()));
			fromCell = (String) planningObject.get("pos");
			do {
				index = random.nextInt(4);
				toCell = "c" + (Integer.parseInt(fromCell.substring(1)) + increments[index]);
			} while (s.getValueOf(directions[index] + "edge", fromCell).equals("yes")
					|| s.getValueOf("status", toCell).equals("occupied"));
			planningObject.addAttribute("pos", toCell);
			s.updateVariable(toCell, "status", "occupied");
			s.updateVariable(fromCell, "status", "empty");
		}
	}
}
