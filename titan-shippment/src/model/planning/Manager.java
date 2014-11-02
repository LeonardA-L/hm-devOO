package model.planning;

import java.util.ArrayList;

public class Manager {

	private ArrayList<Livraison> livraisons;
	private ArrayList<Livraison> livraisonsEffectuees;
	private Tournee tournee;
	
	public Manager() {
		this.livraisons = new ArrayList<Livraison>();
		this.livraisonsEffectuees = new ArrayList<Livraison>();
		this.tournee = new Tournee();
	}

	public Manager(ArrayList<Livraison> livraisons,
			ArrayList<Livraison> livraisonsEffectuees, Tournee tournee) {
		this.livraisons = livraisons;
		this.livraisonsEffectuees = livraisonsEffectuees;
		this.tournee = tournee;
	}

	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public ArrayList<Livraison> getLivraisonsEffectuees() {
		return livraisonsEffectuees;
	}

	public void setLivraisonsEffectuees(ArrayList<Livraison> livraisonsEffectuees) {
		this.livraisonsEffectuees = livraisonsEffectuees;
	}

	public Tournee getTournee() {
		return tournee;
	}

	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
	}	
	
}
