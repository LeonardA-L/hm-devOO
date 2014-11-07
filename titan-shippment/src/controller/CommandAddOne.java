package controller;

import model.planning.InterfacePlanning;

public class CommandAddOne implements ICommand {
	
	private int idClient;
	private int idLivraison;
	private String heureDebut;
	private String heureFin;
	private int adresse;		// id of node where delivery occur
	private int prevAdresse;

	/**
	 * Constructor w/param  (Parameters are explicited in UndoRedo)
	 * @param idClient		
	 * @param idLivraison
	 * @param heureDebut
	 * @param heureFin
	 * @param adresse		
	 * @param prevAdresse
	 */
	public CommandAddOne (int idClient, int idLivraison, String heureDebut, String heureFin, int adresse, int prevAdresse) {
		this.idClient = idClient;
		this.idLivraison = idLivraison;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.adresse = adresse;
		this.prevAdresse = prevAdresse;
	}

	public boolean Execute (InterfacePlanning interfaceP) {
		boolean step1 = interfaceP.AddLivraisonAfter(idClient, idLivraison, heureDebut, heureFin, adresse, prevAdresse);
		if(step1)	// if livraison added right, update tournee
		{
			interfaceP.CalculTournee();
		}
		return false;
	}
	
	public boolean Unexecute (InterfacePlanning interfaceP) {
		// remove the livraison at coordinates
		interfaceP.removeOneLivrison(idLivraison);
		return true;
	}
	
	
}
