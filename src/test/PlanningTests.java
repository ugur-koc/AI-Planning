package test;

import org.junit.Test;

import planning.core.Plan;
import planning.core.Planner;
import test.problems.Coloring;
import test.problems.RobotGridLonayout;

public class PlanningTests {
	
	public static void main(String[] args) throws Exception{
		testPlan_2();
	}

	@Test
	public void testPlan_1() throws Exception {
		Plan plan = Planner.solve(new Coloring(), "AStar");
		System.out.println(plan.toString());
	}

	@Test
	public static void testPlan_2() throws Exception {
		RobotGridLonayout problem = new RobotGridLonayout(6, 2);
		Plan plan = Planner.solve(problem, "AStar");
		System.out.println(plan.toString());
	}
	
	@Test
	public void testPlan_3() throws Exception {
		RobotGridLonayout problem = new RobotGridLonayout(6, 2);
		Plan plan = Planner.solve(problem, "FS");
		System.out.println(plan.toString());
	}
}