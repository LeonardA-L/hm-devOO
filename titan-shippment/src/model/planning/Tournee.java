package model.planning;

import java.util.ArrayList;


public class Tournee {
	
	
	private ArrayList<Livraison> livraisons;
	private ArrayList<Itineraire> itineraires;
	
	public Tournee() {
		this.livraisons = new ArrayList<Livraison>();
		this.itineraires = new ArrayList<Itineraire>();
	}
	
	public Tournee(ArrayList<Livraison> livraisons,
			ArrayList<Itineraire> itineraires) {
		this.livraisons = livraisons;
		this.itineraires = itineraires;
	}
	
	// no sure this is still useful ... ?
	public Tournee calcul() {
		// do math stuff
		return this;
	}
	
	
	public int addItineraire(Itineraire itineraire) {
		itineraires.add(itineraire);
		return itineraires.size()-1;
	}
	
	public int addLivraison(Livraison livraison) {
		livraisons.add(livraison);
		return livraisons.size()-1;
	}
	

	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public ArrayList<Itineraire> getItineraires() {
		return itineraires;
	}

	public void setItineraires(ArrayList<Itineraire> itineraires) {
		this.itineraires = itineraires;
	}

	public void reset() {
		livraisons.clear();
		itineraires.clear();
	}

}
