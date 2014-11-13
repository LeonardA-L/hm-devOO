package tests;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import model.agglomeration.Troncon;

import org.junit.Before;
import org.junit.Test;

public class PlanTest {

	Plan dummy;

	/**
	 * This Plan returns a dummy graph used for testing.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	public static Plan DummyPlanCreate() {

		Noeud storeHouse = new Noeud(0, 1, 0, new ArrayList<Troncon>());
		Noeud n1 = new Noeud(1, 0, 1, new ArrayList<Troncon>());
		Noeud n2 = new Noeud(1, 2, 2, new ArrayList<Troncon>());
		Noeud n3 = new Noeud(2, 0, 3, new ArrayList<Troncon>());
		Noeud n4 = new Noeud(2, 2, 4, new ArrayList<Troncon>());
		Noeud n5 = new Noeud(3, 1, 5, new ArrayList<Troncon>());

		Plan gigaAnton = new Plan(new ArrayList<Noeud>());

		gigaAnton.addNoeud(n1);
		gigaAnton.addNoeud(n2);
		gigaAnton.addNoeud(n3);
		gigaAnton.addNoeud(n4);
		gigaAnton.addNoeud(n5);
		gigaAnton.addNoeud(storeHouse);

		storeHouse.addTroncon(new Troncon("r1", 1, 1, n1));
		storeHouse.addTroncon(new Troncon("r2", 1, 1, n2));

		n1.addTroncon(new Troncon("r3", 1, 1, n4));
		n2.addTroncon(new Troncon("r4", 1, 2, n3));
		n3.addTroncon(new Troncon("r5", 1, 3, n5));
		n4.addTroncon(new Troncon("r6", 1, 3, n5));
		n5.addTroncon(new Troncon("r6", 1, 5, storeHouse));

		return gigaAnton;
	}

	@Before
	public void setUp() {
		int nbNodes = 20;
		int maxX = 100;
		int maxY = 100;
		dummy = UtilsTest.DummyPlanCreate(nbNodes, maxX, maxY);
	}

	@Test
	public void weHaveAPlan() {
		assertNotNull(this.dummy);
	}

}
