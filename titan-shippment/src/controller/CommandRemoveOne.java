package controller;

import model.planning.InterfacePlanning;
import model.planning.PlageHoraire;

public class CommandRemoveOne implements ICommand{

	private int idLivraison;
	private int idClient;
	private int idAdresse;
	private String heureDebut;
	private String heureFin;
	
	public CommandRemoveOne(int idLivraison) {
		idLivraison = this.idLivraison;
	}

	public boolean Execute(InterfacePlanning interfaceP) {
		
		return false;
	}

	public boolean Unexecute(InterfacePlanning interfaceP) {
		return false;
	}
}
