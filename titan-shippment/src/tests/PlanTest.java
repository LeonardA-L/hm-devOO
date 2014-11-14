package tests;

import static org.junit.Assert.*;

import model.agglomeration.Plan;

import org.junit.Before;
import org.junit.Test;

public class PlanTest {

	Plan dummy;

	@Before
	public void setUp() {
		dummy = TestUtils.planCreate();
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
