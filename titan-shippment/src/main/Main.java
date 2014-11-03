package main;

import java.util.ArrayList;

import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import model.agglomeration.Troncon;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// creating map
		String[] rues = {"Avenue des titans", "Rue de Léonard", "wess c ma ru, ru de yaannnn", "Anton Long Avenue", "GIGA AVENUE", "SSSSSSSS-GBD street"};
		
		Noeud entrepot = new Noeud(20,30,-1, new ArrayList<Troncon>());
		Plan plan = new Plan(entrepot, new ArrayList<Noeud>());
		
		// generate random nodes
		int nbNodes = 20;
		int maxX = 100;
		int maxY = 100;
		
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
			int longueur = (int)Math.floor(Math.random()*30);
			String rue = rues[(int)Math.floor(Math.random()*rues.length)];
			
			double doubleSens = Math.random();
			
			Troncon troncon1 = new Troncon(rue, vitesse, longueur, nodeOut);
			nodeIn.addTroncon(troncon1);
			
			if (doubleSens < 0.5) {
				Troncon troncon2 = new Troncon(rue, vitesse, longueur, nodeIn);
				nodeOut.addTroncon(troncon2);
			}			
		}
		
		// display MAP
		System.out.println(plan.toString());
		
	}
}
