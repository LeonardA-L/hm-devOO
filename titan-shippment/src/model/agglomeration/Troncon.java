package model.agglomeration;

public class Troncon {

	private String nomRue;
	private float vitesse;
	private float longueur;
	private Noeud noeudDestination;

	/**
	 * Constructor wo/parameters
	 */
	Troncon() {
		this.nomRue = "";
		this.vitesse = 0;
		this.longueur = 0;
		this.noeudDestination = new Noeud();
	}

	/**
	 * Constructor w/params
	 * 
	 * @param nomRue
	 *            Name of street
	 * @param vitesse
	 *            Speed in the street
	 * @param longueur
	 *            Length of street
	 * @param noeudDestination
	 *            out node
	 */
	public Troncon(String nomRue, float vitesse, float longueur, Noeud noeudDestination) {
		this.nomRue = nomRue;
		this.vitesse = vitesse;
		this.longueur = longueur;
		this.noeudDestination = noeudDestination;
	}

	// ------------------------------------------------------------------
	// GETTERS - SETTERS
	public String getNomRue() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	public float getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public float getLongueur() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}

	public float getTime() {
		return longueur / vitesse;
	}

	public Noeud getNoeudDestination() {
		return noeudDestination;
	}

	public void setNoeudDestination(Noeud noeudDestination) {
		this.noeudDestination = noeudDestination;
	}

	@Override
	public String toString() {
		return "Rue " + nomRue + " - Vitesse limite : " + vitesse + ", longueur : " + longueur + ", A destination du noeud : " + noeudDestination.getId() + ".";
	}

}
