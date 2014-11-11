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

	/**
	 * Parses the String dates of the object into boundary timestamps
	 * @return
	 */
	public int[] getBounds(){
		int[] bounds = new int[2];
		String[] startArray = heureDebut.split(":");
		// Her Majesty's Service
		
		int h = Integer.parseInt(startArray[0]);
		int m = Integer.parseInt(startArray[1]);
		int s = Integer.parseInt(startArray[2]);
		m*=60;
		h*=60*60;
		bounds[0] = h+m+s;
		
		String[] endArray = heureFin.split(":");
		h = Integer.parseInt(endArray[0]);
		m = Integer.parseInt(endArray[1]);
		s = Integer.parseInt(endArray[2]);
		m*=60;
		h*=60*60;
		bounds[1] = h+m+s;
		
		return bounds;
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

	@Override
	public String toString() {
		String msg = "";
		msg += heureDebut + " ==> " + heureFin;
		return msg;
	}
}
