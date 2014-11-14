package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import model.planning.Livraison;
import model.planning.PlageHoraire;
import model.planning.Tournee;

import org.junit.Before;
import org.junit.Test;

import view.agglomeration.VueNoeud;
import view.agglomeration.VuePlan;

public class VuePlanTest {

	private VuePlan vuePlan;
	private ArrayList<VueNoeud> vueNoeuds;
	
	@Before
	public void create() {
		Plan plan = UtilsTest.planCreate();
		vuePlan = new VuePlan();
		vuePlan.setPlan(plan);
		
		vueNoeuds = new ArrayList<VueNoeud>();
		
		Iterator<Noeud> it = plan.getNoeuds().iterator();
		while (it.hasNext()) {
			vueNoeuds.add(new VueNoeud(it.next()));
		}
	}

	@Test
	public void estCliquee() {
		int x = 10;
		int y = 20;
		assertNull(vuePlan.getWhoIsClicked(x, y));
		
		x = 0;
		y = 7;
		assertNotNull(vuePlan.getWhoIsClicked(x, y));
	}
	
	@Test
	public void reset() {
		vuePlan.reset();
		assertNull(vuePlan.getVueEntrepot());
		assertTrue(vuePlan.getVueNoeuds().isEmpty());
	}

}
