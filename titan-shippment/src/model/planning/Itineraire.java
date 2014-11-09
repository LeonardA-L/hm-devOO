package model.planning;

import java.util.ArrayList;

import model.agglomeration.Noeud;
import model.agglomeration.Plan;
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
	
	public void computeTronconsFromNodes(Plan p, ArrayList<Integer> nodesIds){
		for(int i=0;i<nodesIds.size()-1;i++){
			Noeud n1 = p.getNoeudById(nodesIds.get(i));
			Noeud n2 = p.getNoeudById(nodesIds.get(i+1));
			
			for(Troncon t : n1.getTroncons()){
				if(t.getNoeudDestination() == n2){
					this.troncons.add(t);
					break;
				}
			}
		}
	}
}
