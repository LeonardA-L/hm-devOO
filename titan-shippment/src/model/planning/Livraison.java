package model.planning;

import model.agglomeration.Noeud;

public class Livraison {
	
	private PlageHoraire plageHoraire;
	private Noeud adresse;
	private int idClient;
	private int idLivraison;
	
	public Livraison() {
		this.plageHoraire = new PlageHoraire();
		this.adresse = new Noeud();
		this.setIdClient(0);
		this.setIdLivraison(0);
	}

	public Livraison(PlageHoraire plageHoraire, Noeud adresse, int idClient,
			int idLivraison) {
		this.plageHoraire = plageHoraire;
		this.adresse = adresse;
		this.idClient = idClient;
		this.idLivraison = idLivraison;
	}

	public PlageHoraire getPlageHoraire() {
		return plageHoraire;
	}

	public void setPlageHoraire(PlageHoraire plageHoraire) {
		this.plageHoraire = plageHoraire;
	}

	public Noeud getAdresse() {
		return adresse;
	}

	public void setAdresse(Noeud adresse) {
		this.adresse = adresse;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public int getIdLivraison() {
		return idLivraison;
	}

	public void setIdLivraison(int idLivraison) {
		this.idLivraison = idLivraison;
	}
}
