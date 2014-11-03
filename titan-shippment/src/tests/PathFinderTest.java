package tests;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import utils.DijkstraFinder;
import utils.PathFinder;

public class PathFinderTest {
	
	PathFinder f;
	
	@Before
	public void createPF() {
		f = new DijkstraFinder(PlanTest.DummyPlanCreate().computeShippmentGraph());
	}
	
	@Test
	public void findShortestPathOfGigaPlAn() {
		int[] expected = {0,1,4,5};
		int[] actualPath = f.findShortestPath(0, 5);
		
		
		for (int i = 0; i < actualPath.length; i++) {
			assertEquals(expected[i], actualPath[i]);
		}
		
	}

}