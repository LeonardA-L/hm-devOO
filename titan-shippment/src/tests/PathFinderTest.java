package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.agglomeration.Noeud;
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
		p = UtilsTest.planCreate();
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

	@Test
	public void findCycle() {
		// The list of nodes the cycle must go through
		ArrayList<Livraison> list = new ArrayList<Livraison>();

		list.add(new Livraison(new PlageHoraire("00:00:00", "00:00:50"), p.getNoeudById(0), 10, 0));
		list.add(new Livraison(new PlageHoraire("00:00:00", "00:00:50"), p.getNoeudById(4), 10, 0));
		Noeud storeHouse = p.getNoeudById(5);

		ArrayList<Integer> e2 = new ArrayList<Integer>();
		// the cycle starts with the storehouse (5), then 0 then 4
		e2.add(5);
		e2.add(0);
		e2.add(4);

		ArrayList<Livraison> cycle = f.findCycle(1000000, list, storeHouse);
		// System.out.println(cycle);

		boolean s2 = true;
		for (int i = 0; i < e2.size(); i++) {
			s2 &= e2.get(i) == cycle.get(i).getAdresse().getId();
		}

		assertTrue(s2);

	}

}
