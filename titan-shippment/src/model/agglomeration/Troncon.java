package model.agglomeration;

public class Troncon {
	
	private String nomRue;
	private int vitesse;
	private int longueur;
	private Noeud noeudDestination;
	
	Troncon() {
		this.nomRue = "";
		this.vitesse = 0;
		this.longueur = 0;
		this.noeudDestination = new Noeud();
	}
	
	public Troncon(String nomRue, int vitesse, int longueur,
			Noeud noeudDestination) {
		this.nomRue = nomRue;
		this.vitesse = vitesse;
		this.longueur = longueur;
		this.noeudDestination = noeudDestination;
	}

	public String getNomRue() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public int getLongueur() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}
	
	public int GetTime(){
		return longueur/vitesse;
	}
	
	public Noeud getNoeudDestination() {
		return noeudDestination;
	}

	public void setNoeudDestination(Noeud noeudDestination) {
		this.noeudDestination = noeudDestination;
	}

	@Override
	public String toString() {
		return nomRue + " [vitesse=" + vitesse
				+ ", longueur=" + longueur + ", idNoeudDestination="
				+ noeudDestination.getId() + "]";
	}
	
	

}
