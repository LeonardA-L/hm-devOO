package tests;

import static org.junit.Assert.*;

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
	 * @return plan
	 */
	public static Plan DummyPlanCreate() {

		Noeud storeHouse = new Noeud(0, 1, 0, new ArrayList<Troncon>());
		Noeud n1 = new Noeud(1, 0, 1, new ArrayList<Troncon>());
		Noeud n2 = new Noeud(1, 2, 2, new ArrayList<Troncon>());
		Noeud n3 = new Noeud(2, 0, 3, new ArrayList<Troncon>());
		Noeud n4 = new Noeud(2, 2, 4, new ArrayList<Troncon>());
		Noeud n5 = new Noeud(3, 1, 5, new ArrayList<Troncon>());

		Plan plan = new Plan(new ArrayList<Noeud>());

		plan.addNoeud(n1);
		plan.addNoeud(n2);
		plan.addNoeud(n3);
		plan.addNoeud(n4);
		plan.addNoeud(n5);
		plan.addNoeud(storeHouse);

		storeHouse.addTroncon(new Troncon("r1", 1, 1, n1));
		storeHouse.addTroncon(new Troncon("r2", 1, 1, n2));

		n1.addTroncon(new Troncon("r3", 1, 1, n4));
		n2.addTroncon(new Troncon("r4", 1, 2, n3));
		n3.addTroncon(new Troncon("r5", 1, 3, n5));
		n4.addTroncon(new Troncon("r6", 1, 3, n5));
		n5.addTroncon(new Troncon("r6", 1, 5, storeHouse));
		
		plan.addTronconToNoeud(4, "Rue des Oliviers", 10.0F, 5.0F, 3);

		return plan;
	}

	@Before
	public void setUp() {
		dummy = PlanTest.DummyPlanCreate();
	}

	@Test
	public void weHaveAPlan() {
		assertNotNull(this.dummy);
	}
	
	@Test
	public void nodes() {
		assertTrue(this.dummy.getNoeuds().size() == 6);
		assertNotNull(this.dummy.getNoeudById(4));
	}
	
	@Test
	public void troncons() {
		assertTrue(this.dummy.getNoeudById(4).getTroncons().size() == 2);
		assertTrue(this.dummy.getNoeudById(4).getTroncons().get(1).getNomRue() == "Rue des Oliviers");
	}

}
