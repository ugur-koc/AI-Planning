package test;

import org.junit.Test;

import planning.core.Plan;
import planning.core.Planner;
import test.problems.RobotGridLayout;

public class PlanningTests {

	@Test
	public void testPlan_1() throws Exception {
		RobotGridLayout problem = new RobotGridLayout(4, 4);
		Plan plan = Planner.solve(problem, "DFS");
		System.out.println(plan.toString());
	}

	@Test
	public void testPlan_2() throws Exception {
		RobotGridLayout problem = new RobotGridLayout(4, 4);
		Plan plan = Planner.solve(problem, "AStar");
		System.out.println(plan.toString());
	}

	@Test
	public void testPlan_3() throws Exception {
		RobotGridLayout problem = new RobotGridLayout(4, 4);
		Plan plan = Planner.solve(problem, "FS");
		System.out.println(plan.toString());
	}

}