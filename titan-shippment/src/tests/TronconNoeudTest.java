package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.agglomeration.Noeud;
import model.agglomeration.Troncon;

import org.junit.Before;
import org.junit.Test;

public class TronconNoeudTest {

	private Noeud noeudIn;
	private Noeud noeudOut;
	
	@Before
	public void setup() {
		int x = 10;
		int y = 20;
		int id = 30;
		noeudIn = new Noeud(x, y, id, new ArrayList<Troncon>());
		
		int xPrime = 20;
		int yPrime = 100;
		int idPrime = 40;
		noeudOut = new Noeud(xPrime, yPrime, idPrime, new ArrayList<Troncon>());
		
		String nomRue = "Avenue Henry Martin";
		float vitesse = 1.2F;
		float longueur = (float) Math.sqrt(Math.pow(xPrime-x, 2)+Math.pow(yPrime-y, 2));
		noeudIn.addTroncon(new Troncon(nomRue, vitesse, longueur, noeudOut));
	}
	
	@Test
	public void testNoeud() {
		assertTrue(noeudIn.getId() == 30);
		assertTrue(noeudOut.getCoordX() == 20);
	}

	@Test
	public void testTroncons() {
		assertTrue(noeudIn.getTroncons().size() == 1);
		assertTrue(noeudOut.getTroncons().size() == 0);
		
		Troncon troncon = noeudIn.getTroncons().get(0);
		assertNotNull(troncon);
		assertTrue(troncon.getNoeudDestination().equals(noeudOut));
		assertTrue(troncon.getLongueur() > 80.0F);
	}

}
