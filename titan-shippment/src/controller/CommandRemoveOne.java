package controller;

import model.planning.InterfacePlanning;
import view.utils.InterfaceView;

public class CommandRemoveOne implements ICommand {

	private int idLivraison;
	private int idClient;
	private int adresse;
	private String heureDebut;
	private String heureFin;
	private int adressePrecedente;

	/**
	 * Constructor w/param
	 * 
	 * @param adresse
	 *            Address of delivery to be deleted
	 */
	public CommandRemoveOne(int adresse) {
		this.adresse = adresse;
	}

	// See ICommand Interface
	public boolean Execute(InterfacePlanning interfaceP,
			InterfaceView interfaceV) {
		// save informations about soon to be deleted delivery
		idLivraison = interfaceP.getLivraisonByAdr(adresse).getIdLivraison();
		idClient = interfaceP.getLivraisonByAdr(adresse).getIdClient();
		heureDebut = interfaceP.getLivraisonByAdr(adresse).getPlageHoraire()
				.getHeureDebut();
		heureFin = interfaceP.getLivraisonByAdr(adresse).getPlageHoraire()
				.getHeureFin();
		// Updating 'ViewLivraionList'
		interfaceV.removeShippment(adresse);
		// Updating 'VueLivraison'
		interfaceV.removeAndUpdate(adresse);
		adressePrecedente = interfaceP.removeOneLivraison(adresse);
		if (adressePrecedente == -1) {
			return false;
		}
		// MUpdating 'ViewTournee'
		interfaceV.getVuePanel().resetTournee();
		interfaceV
				.getVuePanel()
				.getVue_tournee()
				.setTournee(
						Controller.getInstance().getInterfacePlanning()
								.getTournee());
		interfaceV.repaint();
		return true;
	}

	// See ICommand Interface
	public boolean Unexecute(InterfacePlanning interfaceP,
			InterfaceView interfaceV) {
		idLivraison = interfaceP.addLivraisonAfter(idLivraison, idClient,
				heureDebut, heureFin, adresse, adressePrecedente);
		if (idLivraison == -1) {
			return false;
		}
		// Updating 'VueLivraison'
		interfaceV.addAndUpdate(interfaceP.getLivraisonByAdr(adresse));
		// MUpdating 'ViewTournee'
		interfaceV.getVuePanel().resetTournee();
		interfaceV
				.getVuePanel()
				.getVue_tournee()
				.setTournee(
						Controller.getInstance().getInterfacePlanning()
								.getTournee());
		interfaceV.repaint();
		// Updating 'ViewLivraionList'
		interfaceV.addShippment(interfaceP.getLivraisonByAdr(adresse));
		return true;

	}
}
