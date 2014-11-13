package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

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
		assertFalse(tournee.getLivraisons().isEmpty());
		assertTrue(tournee.getLivraisons().size() == 5);
	}
	
	@Test
	public void addAndRemoveLivraison() {
		int sizeBefore = tournee.getLivraisons().size();
		tournee.addLivraison(new Livraison(new PlageHoraire("10:00:00", "14:00:00"), plan.getNoeuds().get(4), 2, 1000));
		tournee.removeLivraison(4);
		int sizeAfter = tournee.getLivraisons().size();
		assertTrue(sizeBefore == sizeAfter);
		Iterator<Livraison> it = tournee.getLivraisons().iterator();
		while (it.hasNext()) {
			int idLivraison = it.next().getIdLivraison();
			assertFalse(idLivraison == 1000);
		}
	}
	
	@Test
	public void addNext() {
		tournee.addLivraisonAfter(new Livraison(new PlageHoraire("18:00:00", "19:00:00"), plan.getNoeuds().get(0), 4, 100), 20);
		Iterator<Livraison> it = tournee.getLivraisons().iterator();
		while (it.hasNext()) {
			int id = it.next().getAdresse().getId();
			if (!it.hasNext()) {
				break;
			}
			if (id == 20) {
				assertTrue(it.next().getAdresse().getId() == 100);
			}
		}
	}
	
	@Test
	public void reset() {
		tournee.reset();
		assertTrue(tournee.getItineraires().size() == 0);
		assertTrue(tournee.getLivraisons().size() == 0);
	}

}
