package test;

import org.junit.Test;

import test.problems.Coloring;
import acting.Actor;

public class ActingTests {

	@Test
	public void testActor1() throws Exception {
		Actor.act(new Coloring(), "AP_lazy", "AStar", 0);
	}

	@Test
	public void testActor2() throws Exception {
		Actor.act(new Coloring(), "AP_interleaved", "AStar", 0);
	}

	@Test
	public void testActor3() throws Exception {
		Actor.act(new Coloring(), "AP_mixed", "AStar", 0);
	}
}