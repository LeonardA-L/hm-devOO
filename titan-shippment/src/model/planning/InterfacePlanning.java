package model.planning;

import java.util.ArrayList;
import java.util.Iterator;

import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import model.agglomeration.Troncon;
import utils.DijkstraFinder;
import utils.PathFinder;
import utils.ShippmentGraph;
import utils.XMLBuilder;
import controller.Controller;

public class InterfacePlanning {

	private ArrayList<Livraison> listeLivraisons;
	private ArrayList<PlageHoraire> plagesHoraires;
	private Tournee tournee;
	private Noeud entrepot;
	private ShippmentGraph shGraph;
	
	private static int s_idLivraison = -1;
	
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
			resetLivraisons();
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
	
	/**
	 * Add a delivery from a click on the map.
	 * @param idClient
	 * @param heureDebut
	 * @param heureFin
	 * @param adresse
	 * @param prevAdresse
	 * @return			ID of the delivery which was created
	 */
	public int AddLivraisonAfter(int idLivraison, int idClient, String heureDebut, String heureFin, int adresse, int prevAdresse) {
		// If the delivery is added for the first time (and not through undo/redo, it doesn't have an id yet
		if (idLivraison == -1) {
			idLivraison = getNewDeliveryId();	// get an id
			System.out.println("New delivery id chosen : "+s_idLivraison);
		}
		boolean deliveryCreation = AddLivraison(idClient, idLivraison, heureDebut, heureFin, adresse);
		if(deliveryCreation) {
			Livraison deliveryBefore = this.getLivraisonByAdr(prevAdresse);
			Livraison newDelivery = this.getLivraisonByAdr(adresse);
			Livraison deliveryAfter = tournee.addLivraisonAfter(newDelivery, deliveryBefore);
			Itineraire itBefore = findItineraire(deliveryBefore, newDelivery, Controller.getInstance().getInterfaceAgglo().getPlan());
			Itineraire itAfter = findItineraire(newDelivery, deliveryAfter, Controller.getInstance().getInterfaceAgglo().getPlan());
			return idLivraison; // contains the chosen id for the new delivery
		}
		return -1;		// in case of problem
	}
	
	/**
	 *  At init, the method finds the higher delivery id
	 *  Then it always increment the idLivraison var.
	 */
	public int getNewDeliveryId() {
		if (s_idLivraison == -1) {	// init for the first time the method is called.
			int idMax = 0;
			for(Livraison l : listeLivraisons) {
				idMax = idMax < l.getIdLivraison() ? l.getIdLivraison() : idMax;
			}
			s_idLivraison = idMax++;
		}
		return ++s_idLivraison;		// returns an id that can't be used by any other delivery
	}

	/**
	 * Remove a delivery 
	 * @param idLivraison
	 * @return  True or false depending on the success of the operation
	 */
	public boolean removeOneLivraison(int idLivraison) {
		for(Livraison l : listeLivraisons) {
			if(l.getIdLivraison() == idLivraison) {
				listeLivraisons.remove(l);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return the PlageHoraire associated with 
	 * @param heureDebut
	 * @param heureFin
	 * @return
	 */
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
	
	public Noeud getEntrepot() {
		return entrepot;
	}

	public boolean setEntrepot(Noeud entrepot) {
		this.entrepot = entrepot;
		if(entrepot != null)
		{
			this.entrepot = entrepot;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean setEntrepot(int id) {
		Noeud entrepot = Controller.getInstance().getInterfaceAgglo().getPlan().getNoeudById(id);
		return this.setEntrepot(entrepot);
	}

	public void resetLivraisons() {
		listeLivraisons.clear();
		plagesHoraires.clear();
	}
	
	public void resetTournee() {
		tournee.reset();
	}

	
	//#################################### Working with Tournee ###############################
	
	/**
	 * calculates the path for delivery
	 */
	public void CalculTournee() {
		InterfaceAgglo interfaceAgglo = Controller.getInstance().getInterfaceAgglo();
		float[][] matriceAdjacence = interfaceAgglo.GetFormatedMap();
		Plan plan = interfaceAgglo.getPlan();
		ArrayList<Livraison> livraisons = getListeLivraisons();
		
		// Instanciate pathfinder
		PathFinder pf = new DijkstraFinder(plan.computeShippmentGraph());
		shGraph = (ShippmentGraph)((DijkstraFinder)pf).getGraph();
		// Compute cycle (sorted list of livraison)
		ArrayList<Livraison> cycle = pf.findCycle(100000, livraisons,this.entrepot);
		
		// Finding itineraires
		ArrayList<Itineraire> itineraires = new ArrayList<Itineraire>();
		for(int i=0;i<cycle.size() - 1;i++){	// No for in !	
			Livraison l1 = cycle.get(i);
			Livraison l2 = cycle.get(i+1);
			Itineraire it = findItineraire(l1, l2, plan);
			itineraires.add(it);
		}
		// Loop the cycle
		Livraison l1 = cycle.get(cycle.size() - 1);
		Livraison l2 = cycle.get(0);
		Itineraire it = findItineraire(l1, l2, plan);
		itineraires.add(it);
		
		// Prepare the tournee
		Tournee tournee = new Tournee();
		tournee.setLivraisons(cycle);
		tournee.setItineraires(itineraires);
		
		this.setTournee(tournee);
	}
	
	/**
	 * Computes an Itineraire object between two livraison points
	 * Note : can only be called once the tournee has been calculated
	 * @param l1 start livraison
	 * @param l2 end livraison
	 * @param plan the plan where it all happens
	 * @return an Itineraire object
	 */
	public Itineraire findItineraire(Livraison l1, Livraison l2, Plan plan){
		Itineraire it = new Itineraire(l1.getAdresse(),l2.getAdresse(),new ArrayList<Troncon>());
		// Compute list of troncons
		String pathHash = ""+l1.getAdresse().getId()+"-"+l2.getAdresse().getId();
		it.computeTronconsFromNodes(plan, shGraph.getPaths().get(pathHash));
		return it;
	}
	
	//################################### Working with view ####################################
	
	public boolean isNodeADelivery(int idNode) {
		
		if (isNodeEntrepot(idNode)) {
			return true;
		}
		
		for(Livraison l : listeLivraisons) {
			if (l.getAdresse().getId() == idNode) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isNodeEntrepot(int idNode) {
		if (entrepot == null) {
			return false;
		}

		if (entrepot.getId() == idNode) {
			return true;
		}
		return false;
	}
	
	//---------------------------------------------------------------------------------
	// GETTERS - SETTERS - UTILITIES
	public ArrayList<Livraison> getListeLivraisons() {
		return listeLivraisons;
	}

	public Livraison getLivraisonById(int id) {
		for(Livraison l : listeLivraisons) {
			if (l.getIdLivraison() == id) {
				return l;
			}
		}
		return null;
	}
	
	public Livraison getLivraisonByAdr(int adresse) {
		
		if (adresse == entrepot.getId()) {
			return null;
		}
		
		for(Livraison l : listeLivraisons) {
			if (l.getAdresse().getId() == adresse) {
				return l;
			}
		}
		return null;
	}
	public Livraison getLivraisonByIndex(int index) {
		return listeLivraisons.get(index);
	}
	
	public int getIndexOfLivraison(Livraison l) {
		return listeLivraisons.indexOf(l);	// returns -1 if l is not in listeLivraisons
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
