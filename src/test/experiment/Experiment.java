package test.experiment;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import planning.utility.Helper;
import test.problems.RobotGridLayout;
import acting.Actor;

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
		String[] planningAlgs = { "DFS", "AStar" }, refMethods = { "AP_lazy", "AP_interleaved", "AP_mixed" };
		// , "FS"
		String stad = "run,planningAlg,refinementAlg,robotCount,gridSize,dynamicity,planningCallCount,totalPlanningTime,averagePlanningTime,"
				+ "takenActionCount,totalActingTime,averageActingTime,totalTime\n";
		Helper.writeFile("stads.txt", stad, false);

		for (String planningAlg : planningAlgs)
			for (int rC : robotCount)
				for (int gS : gridSize)
					for (String refinement : refMethods)
						for (int r = 0; r < 5; r++)
							for (int dyn : dynamicities) {
								plannerCallCount = actionCount = 0;
								totalPlanningTime = totalActingTime = 0;
								startTime = System.currentTimeMillis();
								Actor.act(new RobotGridLayout(gS, rC), refinement, planningAlg, dyn);
								totalTime = TimeUnit.MILLISECONDS.toMillis(Math.abs(startTime - System.currentTimeMillis()));
								stad = r + "," + planningAlg + "," + refinement + "," + rC + "," + gS + "," + dyn + ","
										+ plannerCallCount + "," + totalPlanningTime + ","
										+ ((long) totalPlanningTime / (long) plannerCallCount) + "," + actionCount + ","
										+ totalActingTime + "," + ((long) totalActingTime / (long) actionCount) + "," + totalTime
										+ "\n";
								Helper.writeFile("stads.txt", stad, true);
							}
	}
}
