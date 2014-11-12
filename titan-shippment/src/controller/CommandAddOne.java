package controller;

import model.planning.InterfacePlanning;
import view.utils.InterfaceView;

public class CommandAddOne implements ICommand {

	private int idLivraison;
	private int idClient;
	private String heureDebut;
	private String heureFin;
	private int adresse;		
	private int adressePrecedente;

	/**
	 * Constructor w/param 
	 * @param idClient					Client Id for new delivery
	 * @param heureDebut				Time from which delivery can take place
	 * @param heureFin					Time before which delivery must take place
	 * @param adresse					Address for delivery
	 * @param prevAdresse				Address of the delivery that must be take place before the new one
	 */
	public CommandAddOne (int idClient, String heureDebut, String heureFin, int adresse, int adressePrecedente) {
		this.idClient = idClient;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.adresse = adresse;
		this.adressePrecedente = adressePrecedente;
		this.idLivraison = -1;
	}

	// See ICommand Interface
	public boolean Execute (InterfacePlanning interfaceP, InterfaceView interfaceV) {
		idLivraison = interfaceP.AddLivraisonAfter(idLivraison, idClient, heureDebut, heureFin, adresse, adressePrecedente);
		if(idLivraison == -1) { 
			// Problem occured when trying to add new delivery to model
			return false;
		}	
		// Updating 'ViewLivraison'
		interfaceV.addAndUpdate(interfaceP.getLivraisonByAdr(adresse));	
		// Updating 'ViewTournee'
		interfaceV.getVuePanel().resetTournee();
		interfaceV.getVuePanel().getVue_tournee().setTournee(Controller.getInstance().getInterfacePlanning().getTournee());
		interfaceV.repaint();
		// Updating 'ViewLivraisonList'
		interfaceV.addShippment(interfaceP.getLivraisonByAdr(adresse));
		return true;
	}

	// See ICommand Interface
	public boolean Unexecute (InterfacePlanning interfaceP, InterfaceView interfaceV) {		
		// Updating 'ViewLivraisonList'
		interfaceV.removeShippment(adresse);
		// Updating 'ViewLivraison'
		interfaceV.removeAndUpdate(adresse);
		// Deleting delivery from model
		int success = interfaceP.removeOneLivraison(adresse);
		if (success == -1) {	
			return false;
		}	
		// Updating 'ViewTournee'
		interfaceV.getVuePanel().resetTournee();
		interfaceV.getVuePanel().getVue_tournee().setTournee(Controller.getInstance().getInterfacePlanning().getTournee());
		interfaceV.repaint();
		return true;
	}


}
