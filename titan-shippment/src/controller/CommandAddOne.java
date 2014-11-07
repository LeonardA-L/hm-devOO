package controller;

import model.planning.InterfacePlanning;

public class CommandAddOne implements ICommand {
	
	private int idClient;
	private int idLivraison;
	private String heureDebut;
	private String heureFin;
	private int adresse;		// id of node where delivery occur
	private int prevAdresse;

	
	public CommandAddOne (int idClient, int idLivraison, String heureDebut, String heureFin, int adresse, int prevAdresse) {
		idClient = this.idClient;
		idLivraison = this.idLivraison;
		heureDebut = this.heureDebut;
		heureFin = this.heureFin;
		adresse = this.adresse;
		prevAdresse = this.prevAdresse;
	}

	public boolean Execute (InterfacePlanning interfaceP) {
		
		return false;
	}
	
	public boolean Unexecute (InterfacePlanning interfaceP) {
		// remove the livraison at coordinates
		return true;
	}
	
	
}
