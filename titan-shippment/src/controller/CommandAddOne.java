package controller;

import model.planning.InterfacePlanning;

public class CommandAddOne implements ICommand {
	
	private int idLivraison;
	private int idClient;
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
	public CommandAddOne (int idClient, String heureDebut, String heureFin, int adresse, int prevAdresse) {
		this.idClient = idClient;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.adresse = adresse;
		this.prevAdresse = prevAdresse;
	}

	public boolean Execute (InterfacePlanning interfaceP) {
		idLivraison = interfaceP.AddLivraisonAfter(idClient, heureDebut, heureFin, adresse, prevAdresse);
		if(idLivraison == -1) {
			return false;
		}
		System.out.println("# ------ DELIVERY CREATED id = "+idLivraison+" ------ #"); 	
		return true;
	}
	
	public boolean Unexecute (InterfacePlanning interfaceP) {
		// remove the livraison at coordinates
		interfaceP.removeOneLivraison(idLivraison);
		System.out.println("# ------ DELIVERY REMOVED id = "+idLivraison+" ------ #"); 	
		return true;
	}
	
	
}
