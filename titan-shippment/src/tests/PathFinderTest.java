package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

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
	
	//@Test
	public void noPathAvailable(){
		ArrayList<Integer> actualPath = f.findShortestPath(3, 0);
		assertNull(actualPath);
	}
	
	@Test
	public void findCycle(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(4);
		list.add(5);
		ArrayList<Integer> e1 = new ArrayList<Integer>();
		list.add(4);
		list.add(5);
		list.add(0);
		ArrayList<Integer> e2 = new ArrayList<Integer>();
		list.add(0);
		list.add(4);
		list.add(5);
		ArrayList<Integer> e3 = new ArrayList<Integer>();
		list.add(5);
		list.add(0);
		list.add(4);
		
		ArrayList<Integer> cycle = f.findCycle(1000000, list);
		System.out.println(cycle);
		
		
		boolean s1 = true;
		for (int i = 0; i < e1.size(); i++) {
			s1 &= e1.get(i) == cycle.get(i);
		}
		
		boolean s2 = true;
		for (int i = 0; i < e2.size(); i++) {
			s2 &= e2.get(i) == cycle.get(i);
		}
		
		boolean s3 = true;
		for (int i = 0; i < e3.size(); i++) {
			s3 &= e3.get(i) == cycle.get(i);
		}
		
		
		assertTrue(s1|s2|s3);
		
	}

}
