package test;

import org.junit.Test;

import planning.core.Plan;
import planning.core.Planner;
import test.problems.Coloring;
import test.problems.RobotGridLonayout;

public class PlanningTests {

	@Test
	public void testPlan_1() throws Exception {
		Plan plan = Planner.solve(new Coloring(), "AStar");
		System.out.println(plan.toString());
	}

	@Test
	public void testPlan_2() throws Exception {
		RobotGridLonayout problem = new RobotGridLonayout(6, 2);
		Plan plan = Planner.solve(problem, "AStar");
		System.out.println(plan.toString());
	}
}