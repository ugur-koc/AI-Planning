package test.experiment;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import planning.core.PlanningObject;
import planning.core.Problem;
import planning.core.State;
import planning.utility.Helper;
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
public class Experiment2 {
	public static int plannerCallCount, actionCount;
	public static long totalPlanningTime, totalActingTime, startTime, totalTime;;
	public static boolean rePlan = false;

	public static void main(String[] args) throws IOException {

		int gridSize[] = { 8 }, robotCount[] = { 6 }, dynamicities[] = { 1 };
		String[] planningAlgs = { "AStar" }, refMethods = { "AP_lazy", "AP_interleaved", "AP_mixed" };
		String exceptionMessage, stad = "run,planningAlg,refinementAlg,robotCount,gridSize,dynamicity,planningCallCount,totalPlanningTime,averagePlanningTime,"
				+ "takenActionCount,totalActingTime,averageActingTime,totalTime,executionCode\n";
		Helper.writeFile("stads.txt", stad, false);

		for (int gS : gridSize)
			for (String planningAlg : planningAlgs)
				for (int rC : robotCount)
					for (int dyn : dynamicities)
						for (String refinement : refMethods)
							for (int r = 0; r < 10; r++) {
								plannerCallCount = actionCount = 0;
								totalPlanningTime = totalActingTime = 0;
								startTime = System.currentTimeMillis();
								exceptionMessage = ",GOOD";
								try {
									Actor.act(new RobotWithTrain(gS, rC), refinement, planningAlg, dyn);
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

	public static void moveTrain(Problem problem, State s) {
		int toCell, fromCell, index, r1Cell, incs[] = { RobotWithTrain.boardSize, RobotWithTrain.boardSize * (-1), 1, -1 };
		Set<PlanningObject> set = problem.getSystem().getObjectMap().get("Robots");
		PlanningObject robot = RobotWithTrain.getRobot(set, "r2");
		fromCell = Integer.parseInt(((String) robot.get("pos")).substring(1));
		if (fromCell >= 44 && fromCell <= 46) index = 3;
		else if (fromCell == 43 || fromCell == 35 || fromCell == 27) index = 1;
		else if (fromCell >= 19 && fromCell <= 22) index = 2;
		else index = 0;
		toCell = (fromCell + incs[index]);
		robot.addAttribute("pos", "c" + toCell);
		s.updateVariable("c" + toCell, "status", "occupied");
		for (int i = 3; i <= 6; i++) {
			toCell = fromCell;
			robot = RobotWithTrain.getRobot(set, "r" + i);
			fromCell = Integer.parseInt(((String) robot.get("pos")).substring(1));
			robot.addAttribute("pos", "c" + toCell);
		}
		s.updateVariable("c" + fromCell, "status", "empty");
		r1Cell = Integer.parseInt(s.getValueOf("pos", "r1").substring(1));
		rePlan = RobotWithTrain.stepCount(r1Cell, 64) > RobotWithTrain.stepCount(fromCell, 64);
	}
}