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
		
		System.out.println("# ------ EXECUTING CommandRemoveOne ------ #");
		
		// first get all informations from the delivery we wanna delete in case of undo/redo	
		idLivraison = interfaceP.getLivraisonByAdr(adresse).getIdLivraison();
		idClient = interfaceP.getLivraisonByAdr(adresse).getIdClient();
		heureDebut = interfaceP.getLivraisonByAdr(adresse).getPlageHoraire().getHeureDebut();
		heureFin = interfaceP.getLivraisonByAdr(adresse).getPlageHoraire().getHeureFin();
		
		// MAJ DU TABLEAU
		int idNoeud = interfaceP.getLivraisonById(idLivraison).getAdresse().getId();
		interfaceV.removeShippment(idNoeud);
		
		//  MAJ VueLivraison
		interfaceV.removeAndUpdate(adresse);
		
		previousAdresseLivraison = interfaceP.removeOneLivraison(idLivraison);
		if (previousAdresseLivraison == -1) {
			System.out.println("# ------ Execute RemoveOne failed ------ #"); 	
			return false;
		}
		
		// MAJ VueTournee
		interfaceV.getVuePanel().resetTournee();
		interfaceV.getVuePanel().getVue_tournee().setTournee(Controller.getInstance().getInterfacePlanning().getTournee());
		interfaceV.repaint();
		
		System.out.println("# ------ DELIVERY REMOVED id = "+idLivraison+" ------ #"); 
		return true;
	}

	public boolean Unexecute(InterfacePlanning interfaceP, InterfaceView interfaceV) {
		
		System.out.println("# ------ UNEXECUTING CommandRemoveOne ------ #");
		
		// Add the delivery that was deleted to the model : 
		// will return -1 if problem or the id of this new delivery
		idLivraison = interfaceP.AddLivraisonAfter(idLivraison, idClient, heureDebut, heureFin, adresse, previousAdresseLivraison);
		if(idLivraison == -1) {
			System.out.println("Unexecute CommandRemoveOne : idLivraison = -1");	
			return false;
		}	
		
		// MAJ VueLivraison
		interfaceV.addAndUpdate(interfaceP.getLivraisonByAdr(adresse));
		
		// MAJ VueTournee
		interfaceV.getVuePanel().resetTournee();
		interfaceV.getVuePanel().getVue_tournee().setTournee(Controller.getInstance().getInterfacePlanning().getTournee());
		interfaceV.repaint();
		
		// MAJ DU TABLEAU
		interfaceV.addShippment(interfaceP.getLivraisonById(idLivraison));
		
		System.out.println("# ------ DELIVERY RE-CREATED id = "+idLivraison+" ------ #");
		return true;

	}
}
