package test;

import org.junit.Test;

import planning.core.Plan;
import planning.core.Planner;
import test.problems.Coloring;
import test.problems.RobotGridLayout;

public class PlanningTests {

	@Test
	public void testPlan_1() throws Exception {
		Plan plan = Planner.solve(new Coloring(), "AStar");
		System.out.println(plan.toString());
	}

	@Test
	public void testPlan_2() throws Exception {
		RobotGridLayout problem = new RobotGridLayout(16, 2);
		Plan plan = Planner.solve(problem, "AStar");
		System.out.println(plan.toString());
	}
	
	@Test
	public void testPlan_3() throws Exception {
		RobotGridLayout problem = new RobotGridLayout(6, 4);
		Plan plan = Planner.solve(problem, "FS");
		System.out.println(plan.toString());
	}
	@Test
	public void testPlan_4() throws Exception {
		RobotGridLayout problem = new RobotGridLayout(6, 2);
		Plan plan = Planner.solve(problem, "DFS");
		System.out.println(plan.toString());
	}}