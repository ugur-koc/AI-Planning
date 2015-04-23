package test;

import org.junit.Test;

import test.problems.Coloring;
import acting.Actor;

public class ActingTests {

	@Test
	public void testActor() throws Exception {
		Actor.act(new Coloring(), "ARP_lazy");
	}
}