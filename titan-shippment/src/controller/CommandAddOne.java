package controller;

import model.planning.InterfacePlanning;
import view.utils.InterfaceView;

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
		this.idLivraison = -1;
	}

	public boolean Execute (InterfacePlanning interfaceP, InterfaceView interfaceV) {
		// will return -1 if problem or the id of this new delivery
		idLivraison = interfaceP.AddLivraisonAfter(idLivraison, idClient, heureDebut, heureFin, adresse, prevAdresse);
		if(idLivraison == -1) {
			System.out.println("# ------ Execute AddOne failed ------ #"); 	
			return false;
		}	
		interfaceV.addAndUpdate(interfaceP.getLivraisonById(idLivraison));
		System.out.println("# ------ DELIVERY CREATED id = "+idLivraison+" ------ #");
		return true;
	}
	
	public boolean Unexecute (InterfacePlanning interfaceP, InterfaceView interfaceV) {
		// remove the livraison at coordinates
		boolean success = interfaceP.removeOneLivraison(idLivraison);
		if (!success) {
			System.out.println("# ------ Unexecute AddOne failed ------ #"); 	
			return false;
		}
		interfaceV.removeAndUpdate(idLivraison);
		System.out.println("# ------ DELIVERY REMOVED id = "+idLivraison+" ------ #"); 
		return success;
	}
	
	
}
