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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	public static Plan DummyPlanCreate(){

		Noeud storeHouse = new Noeud(0,1,0,new ArrayList<Troncon>());
		Noeud n1 = new Noeud(1,0,1,new ArrayList<Troncon>());
		Noeud n2 = new Noeud(1,2,2,new ArrayList<Troncon>());
		Noeud n3 = new Noeud(2,0,3,new ArrayList<Troncon>());
		Noeud n4 = new Noeud(2,2,4,new ArrayList<Troncon>());
		Noeud n5 = new Noeud(3,1,5,new ArrayList<Troncon>());
		
		Plan gigaAnton = new Plan(storeHouse, new ArrayList<Noeud>());
		
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
		n5.addTroncon(new Troncon("r6", 1, 0, storeHouse));
		
		return gigaAnton;
	}
	
	public static Plan DummyPlanCreate(int nbNodes, int maxX, int maxY){
		// creating map
		String[] rues = {"Avenue des titans", "Rue de L�onard", "wess c ma ru, ru de yaannnn", "Anton Long Avenue", "GIGA AVENUE", "SSSSSSSS-GBD street"};
		
		Noeud entrepot = new Noeud(20,30,-1, new ArrayList<Troncon>());
		Plan plan = new Plan(entrepot, new ArrayList<Noeud>());
		
		// generate random nodes
		for (int i = 0; i < nbNodes; ++i) {
			int x = (int)Math.floor(Math.random()*maxX);
			int y = (int)Math.floor(Math.random()*maxY);
			int id = i;
			Noeud noeud = new Noeud(x, y, id, new ArrayList<Troncon>());
			plan.addNoeud(noeud);
		}
		
		// generate random troncons
		int nbTroncons = 10;
		for (int i = 0; i < nbTroncons; ++i) {
			int nodeInIndex = (int)Math.floor(Math.random()*nbNodes);
			int nodeOutIndex = (int)Math.floor(Math.random()*nbNodes);
			
			// no boucle
			if (nodeInIndex == nodeOutIndex) {
				continue;
			}
			
			Noeud nodeIn = plan.getNoeudById(nodeInIndex);
			Noeud nodeOut = plan.getNoeudById(nodeOutIndex);
			
			int vitesse = (int)Math.floor(Math.random()*50);
			int longueur = (int)Math.floor(Math.random()*30) +1;
			String rue = rues[(int)Math.floor(Math.random()*rues.length)];
			
			double doubleSens = Math.random();
			
			Troncon troncon1 = new Troncon(rue, vitesse, longueur, nodeOut);
			nodeIn.addTroncon(troncon1);
			
			if (doubleSens < 0.5) {
				Troncon troncon2 = new Troncon(rue, vitesse, longueur, nodeIn);
				nodeOut.addTroncon(troncon2);
			}			
		}
		return plan;
	}

	@Before
	public void setUp() {
		int nbNodes = 20;
		int maxX = 100;
		int maxY = 100;
		dummy = DummyPlanCreate(nbNodes, maxX, maxY);
	}
	
	
	@Test
	public void weHaveAPlan() {
		assertNotNull(this.dummy);
	}

}
