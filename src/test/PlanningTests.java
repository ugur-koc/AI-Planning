package test;

import org.junit.Test;

import planning.core.Plan;
import planning.core.Planner;
import test.problems.Coloring;

public class PlanningTests {

	@Test
	public void testPlan() throws Exception {
		Plan plan = Planner.solve(new Coloring(), "AStar");
		System.out.println(plan.toString());
	}
}