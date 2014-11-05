package tests;

import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import utils.BreadthFirstFinder;
import utils.DijkstraFinder;
import utils.PathFinder;

public class PathFinderTest {

	PathFinder f;

	@Before
	public void createPF() {
		//f = new BreadthFirstFinder(PlanTest.DummyPlanCreate().computeShippmentGraph());
		f = new DijkstraFinder(PlanTest.DummyPlanCreate().computeShippmentGraph());
	}

	@Test
	public void findShortestPathOfGigaPlAnton() {
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		expected.add(4);
		expected.add(5);
		ArrayList<Integer> actualPath = f.findShortestPath(0, 5);
		// popping the total distance of the path
		actualPath.remove(actualPath.size()-1);

		for (int i = 0; i < actualPath.size(); i++) {
			assertEquals(expected.get(i), actualPath.get(i));
		}

	}
	
	@Test
	public void noPathAvailable(){
		ArrayList<Integer> actualPath = f.findShortestPath(3, 0);
		assertNull(actualPath);
	}

}
