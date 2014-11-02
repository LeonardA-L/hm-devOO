package model.planning;

public class PlageHoraire {
	
	private String heureDebut;
	private String heureFin;
	
	public PlageHoraire() {
		this.setHeureDebut("");
		this.setHeureFin("");
	}
	
	public PlageHoraire(String heureDebut, String heureFin) {
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
	}

	public String getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}

}
