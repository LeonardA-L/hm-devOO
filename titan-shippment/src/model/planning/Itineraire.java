package model.planning;

import java.util.ArrayList;
import java.util.Iterator;

import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import model.agglomeration.Troncon;

public class Itineraire {

	private Noeud depart;
	private Noeud arrivee;
	private ArrayList<Troncon> troncons;
	private int durationSecondes;

	/**
	 * Constructor w/oparameters
	 */
	public Itineraire() {
		this.depart = new Noeud();
		this.arrivee = new Noeud();
		this.troncons = new ArrayList<Troncon>();
	}
	
	/**
	 * Constructor w/parameters
	 * @param depart
	 * @param arrivee
	 * @param troncons
	 */
	public Itineraire(Noeud depart, Noeud arrivee, ArrayList<Troncon> troncons) {
		this.depart = depart;
		this.arrivee = arrivee;
		this.troncons = troncons;
	}

	/**
	 * 
	 * @param p 	Plan 
	 * @param nodesIds		Nodes 
	 */
	public void computeTronconsFromNodes(Plan p, ArrayList<Integer> nodesIds) {
		this.durationSecondes = 0;
		for (int i = 0; i < nodesIds.size() - 1; i++) {
			Noeud n1 = p.getNoeudById(nodesIds.get(i));
			Noeud n2 = p.getNoeudById(nodesIds.get(i + 1));

			for (Troncon t : n1.getTroncons()) {
				if (t.getNoeudDestination() == n2) {
					this.durationSecondes += t.getLongueur() / t.getVitesse();
					this.troncons.add(t);
					break;
				}
			}
		}
	}


	// ------------------------------------------------------------------
	// GETTERS - SETTERS
	public Noeud getDepart() {
		return depart;
	}

	public void setDepart(Noeud depart) {
		this.depart = depart;
	}

	public Noeud getArrivee() {
		return arrivee;
	}

	public void setArrivee(Noeud arrivee) {
		this.arrivee = arrivee;
	}

	public ArrayList<Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(ArrayList<Troncon> troncons) {
		this.troncons = troncons;
	}

	public int getDurationSecondes() {
		return this.durationSecondes;
	}
	

	@Override
	public String toString() {
		String texte = "\n\t\tD�part : " + depart.toString() + "\n\t\tArriv�e : " + arrivee.toString() + "\n";
		Iterator<Troncon> it = troncons.iterator();
		while (it.hasNext()) {
			texte += "\t\t\t" + it.next().toString() + "\n";
		}
		return texte;
	}
}

