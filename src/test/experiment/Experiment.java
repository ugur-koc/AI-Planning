package test.experiment;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import planning.utility.Helper;
import test.problems.RobotGridLonayout;
import acting.Actor;

/*
 * Purpose of this class is to conduct the experiments to evaluate our approach in different configurations.
 * Here also compare our approach with existing approaches
 * Independent variables for this experiment: planning algorithm, refinement algorithm, #of robots, size of grid, dynamicity
 * Dependent variables for this experiment: #of planning calls, #of actions taken, average planning time, average acting time, overall time
 * 
 */
public class Experiment {

	public static void main(String[] args) throws IOException {
		int dynamicities[] = { 1, 2, 3, 4, 5 };
		String[] planningAlgs = { "AStar", "DFS", "FS" }, refMethods = { "AP_lazy", "AP_interleaved", "AP_mixed" };
		RobotGridLonayout[] problems = { new RobotGridLonayout(3, 2), new RobotGridLonayout(4, 2) };

		long startTime, totalTime;
		String stad = "pAlg,rAlg,robotCount,gridSize,dynamicity,planningCallCount,takenActionCount,pTime,aTime,totalTime\n";
		Helper.writeFile("stads.txt", stad, false);

		for (String planningAlg : planningAlgs) {
			for (String refinement : refMethods) {
				for (RobotGridLonayout problem : problems) {
					for (int dyn : dynamicities) {
						for (int r = 0; r < 5; r++) {
							startTime = System.currentTimeMillis();
							Actor.act(problem, refinement, planningAlg, dyn);
							totalTime = TimeUnit.MILLISECONDS.toSeconds(Math.abs(startTime - System.currentTimeMillis()));
							// TODO missing variables: robotCount, gridSize,
							stad = r + "," + planningAlg + "," + "," + refinement + "," + dyn + "," + totalTime + "\n";
							Helper.writeFile("stads.txt", stad, true);
						}
					}
				}
			}
		}
	}
}
