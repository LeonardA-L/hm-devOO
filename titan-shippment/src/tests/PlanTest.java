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
   Generates the following graph with weights in parens.
Ex.
		       ____ _  	    ____  _    _       	  _
		      /	___(_) __ _|  _	\| |  /	\   _ __ | |_ ___  _ __
		     | |  _| |/	_` | |_) | | / _ \ | '_	\| __/ _ \| '_ \
		     | |_| | | (_| |  __/| |/ ___ \| | | | || (_) | | |	|
		      \____|_|\__, |_| 	 |_/_/ 	 \_\_| |_|\__\___/|_| |_|
		       	      |___/



			     ->1\
			   /-	 \-
		    (1)	./-	   \- (1)		 ->3\
		      /--	     \--	       --|   --\ (3)
		   /--		        \-	    --/		-\
		 /-		          \-     --/		  ----|
	        O-\    		            \---/		      V
	          -\   			    -/ \-		    ->5
	            -\  	         --/     \-		    |   
		      --\ 	      --/	   \--		 --/
		   (1)   -\        --/	(2)	      \-      --/ (3)
			   -\    --/		        \-  -/
			     ->2/			 ->4


*/ 

	public static Plan DummyPlanCreate(int nbNodes, int maxX, int maxY){
		// creating map
		String[] rues = {"Avenue des titans", "Rue de Léonard", "wess c ma ru, ru de yaannnn", "Anton Long Avenue", "GIGA AVENUE", "SSSSSSSS-GBD street"};
		
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
