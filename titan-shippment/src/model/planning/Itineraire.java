package model.planning;

import java.util.ArrayList;

import model.agglomeration.Noeud;
import model.agglomeration.Troncon;

public class Itineraire {
	
	private Noeud depart;
	private Noeud arrivee;
	private ArrayList<Troncon> troncons;

	public Itineraire(Noeud depart, Noeud arrivee, ArrayList<Troncon> troncons) {
		this.depart = depart;
		this.arrivee = arrivee;
		this.troncons = troncons;
	}

	public Itineraire() {
		this.depart = new Noeud();
		this.arrivee = new Noeud();
		this.troncons = new ArrayList<Troncon>();
	}

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
	
}
