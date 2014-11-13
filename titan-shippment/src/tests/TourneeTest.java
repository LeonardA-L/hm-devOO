package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.agglomeration.Plan;
import model.planning.Livraison;
import model.planning.PlageHoraire;
import model.planning.Tournee;

import org.junit.Before;
import org.junit.Test;

public class TourneeTest {

	private Plan plan;
	private Tournee tournee;
	private ArrayList<Livraison> livraisons;

	@Before
	public void create() {
		plan = UtilsTest.planCreate();
		livraisons = UtilsTest.deliveriesListCreate(plan);
		tournee = new Tournee();
		tournee.setLivraisons(livraisons);
		tournee.addLivraison(new Livraison(new PlageHoraire("18:00:00", "19:00:00"), plan.getNoeuds().get(0), 4, 20));
	}

	@Test
	public void livraisons() {
		assertTrue(tournee.getLivraisons().size() == 5);
	}

}
