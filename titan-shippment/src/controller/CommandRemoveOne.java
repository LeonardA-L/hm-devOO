package controller;

import model.planning.InterfacePlanning;
import view.utils.InterfaceView;


public class CommandRemoveOne implements ICommand{

	private int idLivraison;
	private int idClient;
	private int idAdresse;
	private String heureDebut;
	private String heureFin;
	
	public CommandRemoveOne(int idLivraison) {
		this.idLivraison = idLivraison;
	}

	public boolean Execute(InterfacePlanning interfaceP, InterfaceView interfaceV) {
		
		return false;
	}

	public boolean Unexecute(InterfacePlanning interfaceP, InterfaceView interfaceV) {
		return false;
	}
}
