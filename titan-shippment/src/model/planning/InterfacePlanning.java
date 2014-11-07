package model.planning;

import java.util.ArrayList;
import java.util.Iterator;

import controller.Controller;


import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Noeud;
import utils.XMLBuilder;

public class InterfacePlanning {

	private ArrayList<Livraison> listeLivraisons;
	private ArrayList<PlageHoraire> plagesHoraires;
	private Tournee tournee;
	
	/**
	 * 	Constructor w/o parameter
	 */
	public InterfacePlanning () {
		listeLivraisons = new ArrayList<Livraison>();
		setPlagesHoraires(new ArrayList<PlageHoraire>());
		tournee = null;
	}
	
	
	//################################## Working with Livraisons #####################################
	
	/**	Call a method in XMLBuilder, giving it the name of the file
	 * 	containing the plan. Then get back the array of elements to 
	 *  be used and build all livraisons from it 
	 * 	@param 		absFilePath	path to the xml File
	 *  @return 	true or false depending on the success of method (i.d. 
	 *  			all livraisons have been created successfully)
	 */
	public boolean GetPlanningsFromBuilder(String absFilePath) {	
		boolean success = XMLBuilder.getLivraisons(absFilePath, this);
		if(success)
		{
			return true;
		}
		else {
			reset();
			return false;
		}
	}
	
	/**
	 * Add a livraison in listeLivraisons (for use with the xml builder)
	 * @param idClient			id of the client
	 * @param idLivraison		id of the delivery
	 * @param heureDebut		time from which the delivery can occur
	 * @param heureFin			time after which the delivery is late 
	 * @param adresse			address where the the delivery takes place
	 * @return
	 */
	public boolean AddLivraison(int idClient, int idLivraison, String heureDebut, String heureFin, int adresse) {
		PlageHoraire ph = this.getPlageHoraire(heureDebut, heureFin);
		Noeud nd = Controller.getInstance().getInterfaceAgglo().getPlan().getNoeudById(adresse);
		if (nd == null || ph == null) {
			return false;
		}
		Livraison liv = new Livraison(ph, nd, idClient, idLivraison);
		listeLivraisons.add(liv);
		return true;
	}
	
	public boolean AddLivraisonAfter(int idClient, int idLivraison, String heureDebut, String heureFin, int adresse, int prevAdresse)
	{
		return false;
	}
	
	// called to unexecute add command
	// or to execute remove command
	public boolean removeOneLivrison(int idLivraison)
	{
		return false;
	}
	
	private PlageHoraire getPlageHoraire(String heureDebut, String heureFin) {
		Iterator<PlageHoraire> it = plagesHoraires.iterator();
		while (it.hasNext()) {
			PlageHoraire ph = it.next();
			if (ph.getHeureDebut() == heureDebut && ph.getHeureFin() == heureFin) {
				return ph;
			}
		}
		PlageHoraire ph = new PlageHoraire(heureDebut, heureFin);
		plagesHoraires.add(ph);
		return ph;
	}

	public void reset() {
		listeLivraisons.clear();
		plagesHoraires.clear();
	}

	
	//#################################### Working with Tournee ###############################
	
	/**
	 * calculates the path for delivery
	 */
	public void CalculTournee(){
		InterfaceAgglo interfaceAgglo = Controller.getInstance().getInterfaceAgglo();
		float[][] matriceAdjacence = interfaceAgglo.GetFormatedMap();
		
		Tournee tournee = new Tournee();
		//TODO - Calcul Tournee
		
		this.setTournee(tournee);
	}
	
	//################################### Working with view ####################################
	
	public boolean isNodeADelivery(int idNode){
		for(Livraison l : listeLivraisons)
		{
			if (l.getAdresse().getId() == idNode){
				return true;
			}
		}
		return false;
	}
	
	//---------------------------------------------------------------------------------
	// GETTERS - SETTERS
	public ArrayList<Livraison> getListeLivraisons() {
		return listeLivraisons;
	}

	public void setListeLivraisons(ArrayList<Livraison> listeLivraisons) {
		this.listeLivraisons = listeLivraisons;
	}

	public Tournee getTournee() {
		return tournee;
	}

	private void setTournee(Tournee tournee){
		this.tournee = tournee;
	}

	public ArrayList<PlageHoraire> getPlagesHoraires() {
		return plagesHoraires;
	}

	public void setPlagesHoraires(ArrayList<PlageHoraire> plagesHoraires) {
		this.plagesHoraires = plagesHoraires;
	}
	
}