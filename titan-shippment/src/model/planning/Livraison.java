package model.planning;

import model.agglomeration.Noeud;

public class Livraison {

	private PlageHoraire plageHoraire;
	private Noeud adresse;
	private int idClient;
	private int idLivraison;
	private String heureLivraison;
	private Boolean isDelayed;

	/**
	 * Constructor w/o parameters
	 */
	public Livraison() {
		this.plageHoraire = new PlageHoraire();
		this.adresse = new Noeud();
		this.setIdClient(0);
		this.setIdLivraison(0);
		this.heureLivraison = "";
		this.isDelayed = false;
	}

	/**
	 * Constructors w parameters
	 * @param plageHoraire		Time schedule for the delivery
	 * @param adresse			Address of the delivery
	 * @param idClient			Id of client 
	 * @param idLivraison		Id of delivery
	 */
	public Livraison(PlageHoraire plageHoraire, Noeud adresse, int idClient, int idLivraison) {
		this.plageHoraire = plageHoraire;
		this.adresse = adresse;
		this.idClient = idClient;
		this.idLivraison = idLivraison;
		this.heureLivraison = "";
		this.isDelayed = false;
	}

	// ------------------------------------------------------------------
	// GETTERS - SETTERS
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

	public String getHeureLivraison() {
		return heureLivraison;
	}

	public void setHeureLivraison(String heureLivraison) {
		this.heureLivraison = heureLivraison;
	}

	public void setIsDelayed(Boolean bool) {
		this.isDelayed = bool;
	}

	public Boolean isDelayed() {
		return this.isDelayed;
	}

	public String toString() {
		String msg = "";
		msg += "Delivery to node no " + adresse.getId() + "<br>";
		msg += "Time slot " + plageHoraire.toString() + "<br>";
		msg += "At " + ((!heureLivraison.isEmpty()) ? heureLivraison : "[Not calculed yet]") + "<br>";
		return msg;
	}
}
