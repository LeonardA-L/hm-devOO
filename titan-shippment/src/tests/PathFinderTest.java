package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.agglomeration.Plan;
import model.planning.Livraison;
import model.planning.PlageHoraire;

import org.junit.Before;
import org.junit.Test;

import utils.DijkstraFinder;
import utils.PathFinder;

public class PathFinderTest {

	PathFinder f;
	Plan p;

	@Before
	public void createPF() {
		// f = new
		// BreadthFirstFinder(PlanTest.DummyPlanCreate().computeShippmentGraph());
		p = PlanTest.DummyPlanCreate();
		f = new DijkstraFinder(p.computeShippmentGraph());
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
		actualPath.remove(actualPath.size() - 1);

		for (int i = 0; i < actualPath.size(); i++) {
			assertEquals(expected.get(i), actualPath.get(i));
		}

	}

	// This test has been disabled due to a lack of test sets
	// @Test
	public void noPathAvailable() {
		ArrayList<Integer> actualPath = f.findShortestPath(3, 0);
		assertNull(actualPath);
	}

	// Won't work now that the storehouse is supposed to be outside of the plan
	// @Test
	public void findCycle() {
		// The list of nodes the cycle must go through
		ArrayList<Livraison> list = new ArrayList<Livraison>();

		list.add(new Livraison(new PlageHoraire("00:00:00", "00:00:50"), p
				.getNoeudById(0), 10, 0));
		list.add(new Livraison(new PlageHoraire("00:00:01", "00:00:50"), p
				.getNoeudById(4), 10, 0));
		list.add(new Livraison(new PlageHoraire("00:00:04", "00:00:50"), p
				.getNoeudById(5), 10, 0));

		// several results are accepted (0,4,5 and 4,5,0 ...)
		ArrayList<Integer> e1 = new ArrayList<Integer>();
		e1.add(4);
		e1.add(5);
		e1.add(0);
		ArrayList<Integer> e2 = new ArrayList<Integer>();
		e2.add(0);
		e2.add(4);
		e2.add(5);
		ArrayList<Integer> e3 = new ArrayList<Integer>();
		e3.add(5);
		e3.add(0);
		e3.add(4);

		ArrayList<Livraison> cycle = f.findCycle(1000000, list,
				p.getNoeudById(0));
		System.out.println(cycle);

		// check if the result is one of the following
		/*
		 * boolean s1 = true; for (int i = 0; i < e1.size(); i++) { s1 &=
		 * e1.get(i) == cycle.get(i); }
		 * 
		 * boolean s2 = true; for (int i = 0; i < e2.size(); i++) { s2 &=
		 * e2.get(i) == cycle.get(i); }
		 * 
		 * boolean s3 = true; for (int i = 0; i < e3.size(); i++) { s3 &=
		 * e3.get(i) == cycle.get(i); }
		 */

		// assertTrue(s1|s2|s3);

	}

}
