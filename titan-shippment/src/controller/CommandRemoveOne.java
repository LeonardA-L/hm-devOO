package controller;

import model.planning.InterfacePlanning;
import view.utils.InterfaceView;


public class CommandRemoveOne implements ICommand{

	private int idLivraison;
	private int idClient;
	private int adresse;
	private String heureDebut;
	private String heureFin;
	private int previousAdresseLivraison;
	
	public CommandRemoveOne(int adresse) {
		this.adresse = adresse;		
	}

	public boolean Execute(InterfacePlanning interfaceP, InterfaceView interfaceV) {
		// first get all informations from the delivery we wanna delete in case of undo/redo	
		idLivraison = interfaceP.getLivraisonByAdr(adresse).getIdLivraison();
		idClient = interfaceP.getLivraisonByAdr(adresse).getIdClient();
		heureDebut = interfaceP.getLivraisonByAdr(adresse).getPlageHoraire().getHeureDebut();
		heureFin = interfaceP.getLivraisonByAdr(adresse).getPlageHoraire().getHeureFin();
		// #### NEED TO KNOW WHICH WAS THE DELIVERY BEFORE THE ONE WE DELETE
		// THE INFORMATION IS KNOWN BY THE TOURNEE ONLY SO IT WILL BE FETCHED WHEN THIS PART IS IMPLEMENTED
		// previousAdresseLivraison = interfaceP.
		
		//  Now tell the model to delete delivery number "idLivraison"
		interfaceV.removeAndUpdate(adresse);
		boolean success = interfaceP.removeOneLivraison(idLivraison);
		if (!success) {
			System.out.println("# ------ Execute RemoveOne failed ------ #"); 	
			return false;
		}
		System.out.println("# ------ DELIVERY REMOVED id = "+idLivraison+" ------ #"); 
		return success;
	}

	public boolean Unexecute(InterfacePlanning interfaceP, InterfaceView interfaceV) {
		// Add the delivery that was deleted to the model : 
		// will return -1 if problem or the id of this new delivery
		idLivraison = interfaceP.AddLivraisonAfter(idLivraison, idClient, heureDebut, heureFin, adresse, previousAdresseLivraison);
		if(idLivraison == -1) {
			System.out.println("# ------ Unexecute RemoveOne failed ------ #"); 	
			return false;
		}	
		interfaceV.addAndUpdate(interfaceP.getLivraisonByAdr(adresse));
		System.out.println("# ------ DELIVERY RE-CREATED id = "+idLivraison+" ------ #");
		return true;

	}
}
