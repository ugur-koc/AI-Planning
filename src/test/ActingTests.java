package test;

import org.junit.Test;

import test.problems.Coloring;
import test.problems.RobotWithTrain;
import acting.Actor;

public class ActingTests {

	@Test
	public void testActor1() throws Exception {
		Actor.act(new Coloring(), "AP_lazy", "AStar", 0);
	}

	@Test
	public void testActor2() throws Exception {
		Actor.act(new Coloring(), "AP_interleaved", "DFS", 0);
	}

	@Test
	public void testActor3() throws Exception {
		Actor.act(new Coloring(), "AP_mixed", "AStar", 1);
	}

	@Test
	public void testActor4() throws Exception {
		Actor.act(new RobotWithTrain(4, 4), "AP_lazy", "AStar", 0);
	}

	@Test
	public void testActor5() throws Exception {
		Actor.act(new RobotWithTrain(6, 2), "AP_interleaved", "DFS", 0);
	}

	@Test
	public void testActor6() throws Exception {
		Actor.act(new RobotWithTrain(6, 2), "AP_mixed", "AStar", 0);
	}
}