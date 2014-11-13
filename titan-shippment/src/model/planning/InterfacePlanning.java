package model.planning;

import java.util.ArrayList;
import java.util.Iterator;

import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import model.agglomeration.Troncon;
import utils.DijkstraFinder;
import utils.Misc;
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
	 * Constructor w/o parameter
	 */
	public InterfacePlanning() {
		listeLivraisons = new ArrayList<Livraison>();
		setPlagesHoraires(new ArrayList<PlageHoraire>());
		tournee = null;
	}

	// ################################## PART ABOUT Livraisons #####################################

	/**
	 * Call a method in XMLBuilder, giving it the name of the file containing the plan. Then get back the array of elements to be used and build all livraisons from it
	 * 
	 * @param absFilePath
	 *            path to the xml File
	 * @return true or false depending on the success of method (i.d. all livraisons have been created successfully)
	 */
	public boolean GetPlanningsFromBuilder(String absFilePath) {
		boolean success = XMLBuilder.getLivraisons(absFilePath, this);
		if (success) {
			return true;
		} else {
			resetLivraisons();
			return false;
		}
	}

	/**
	 * Add a livraison in listeLivraisons (for use with the xml builder)
	 * 
	 * @param idClient
	 *            id of the client
	 * @param idLivraison
	 *            id of the delivery
	 * @param heureDebut
	 *            time from which the delivery can occur
	 * @param heureFin
	 *            time after which the delivery is late
	 * @param adresse
	 *            address where the the delivery takes place
	 * @return
	 */
	public boolean addLivraison(int idClient, int idLivraison, String heureDebut, String heureFin, int adresse) {
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
	 * 
	 * @param idClient	
	 * 			  id of the client
	 * @param heureDebut
	 * 			  time from which the delivery can occur
	 * @param heureFin
	 * 			  time after which the delivery is late
	 * @param adresse
	 * 			  address where the the delivery takes place
	 * @param prevAdresse
	 * 			  address of the delivery that occurs before the new one in Tournee
	 * @return ID of the delivery which was created
	 */
	public int addLivraisonAfter(int idLivraison, int idClient, String heureDebut, String heureFin, int adresse, int prevAdresse) {
		// If the delivery is added for the first time (and not through undo/redo), it doesn't have an id yet
		if (idLivraison == -1) {
			idLivraison = getNewDeliveryId(); // get an id
		}
		// Add new delivery into model :
		boolean deliveryCreation = addLivraison(idClient, idLivraison, heureDebut, heureFin, adresse);

		if (deliveryCreation) { // if creation went right in model
			Noeud nodeBefore = null; 
			Noeud nodeOfDelivery = null; 
			Noeud nodeAfter = null; 
			Livraison newDelivery = null;
			Livraison deliveryAfter = null; 

			// NODE BEFORE
			if (entrepot.getId() == prevAdresse) { // if prevAdresse is the same as warehouse
				nodeBefore = entrepot;
			} else { // else, it is a delivery, we find it.
				nodeBefore = this.getLivraisonByAdr(prevAdresse).getAdresse();
			}

			// NODE OF DELIVERY
			newDelivery = this.getLivraisonByAdr(adresse);
			nodeOfDelivery = newDelivery.getAdresse();

			// NODE AFTER
			int adresseAfter = tournee.addLivraisonAfter(newDelivery, nodeBefore.getId());
			if (adresseAfter == -1) { // adresse after is entrepot
				nodeAfter = entrepot;
			} else { // fetch delivery after and get its address
				deliveryAfter = getLivraisonByAdr(adresseAfter);
				nodeAfter = deliveryAfter.getAdresse();
			}

			Itineraire itBefore = findItineraire(nodeBefore, nodeOfDelivery, Controller.getInstance().getInterfaceAgglo().getPlan());
			Itineraire itAfter = findItineraire(nodeOfDelivery, nodeAfter, Controller.getInstance().getInterfaceAgglo().getPlan());
			tournee.removeItineraireAfter(nodeBefore.getId()); // on enlève l'ancien itinéraire entre nodeBefore et node after
			tournee.addItineraireAfter(itBefore);
			tournee.addItineraire(itAfter);

			calculLivraisonsSchedule();

			return idLivraison; // contains the chosen id for the new delivery (in case of undo/redo)
		}
		return -1;
	}

	/**
	 * At init, the method finds the higher delivery id Then it always increment the idLivraison var.
	 */
	public int getNewDeliveryId() {
		if (s_idLivraison == -1) { // init for the first time the method is called.
			int idMax = 0;
			for (Livraison l : listeLivraisons) {
				idMax = idMax < l.getIdLivraison() ? l.getIdLivraison() : idMax;
			}
			s_idLivraison = idMax++;
		}
		return ++s_idLivraison; // Returns an id that can't be used by any other delivery
	}

	/**
	 * Remove a delivery
	 * 
	 * @param idLivraison
	 * @return True or false depending on the success of the operation
	 */
	public int removeOneLivraison(int adresse) {
		Livraison toBeRemoved = null;
		for (Livraison l : listeLivraisons) { // Find delivery in model
			if (l.getAdresse().getId() == adresse) {
				toBeRemoved = l;
				break;
			}
		}
		if (toBeRemoved != null) { // delivery to be removed found
			// Remove itineraires from tournee
			int addAfter = tournee.removeItineraireAfter(toBeRemoved.getAdresse().getId());
			int addBefore = tournee.removeItineraireBefore(toBeRemoved.getAdresse().getId());
			if (addAfter == -1 || addBefore == -1) {
				return -1;
			}
			// remove former delivery from tournee
			boolean removed = tournee.removeLivraison(toBeRemoved.getAdresse().getId()); // delete delivery from Tournee
			if (!removed) {
				System.out.println("Problem when removing livraison from tournee");
			}
			// Find new itineraire
			Noeud nodeBefore = null;
			Noeud nodeAfter = null;
			if (addBefore == entrepot.getId()) {
				nodeBefore = entrepot;
			} else {
				nodeBefore = getLivraisonByAdr(addBefore).getAdresse();
			}
			if (addAfter == entrepot.getId()) {
				nodeAfter = entrepot;
			} else {
				nodeAfter = getLivraisonByAdr(addAfter).getAdresse();
			}
			// create newIt and add it to Tournee
			Itineraire newIt = findItineraire(nodeBefore, nodeAfter, Controller.getInstance().getInterfaceAgglo().getPlan());
			tournee.addItineraireAfter(newIt);
			listeLivraisons.remove(toBeRemoved); // delete delivery from model

			calculLivraisonsSchedule();
			return addBefore;
		} else {
			return -1;
		}
	}

	/**
	 * Return the PlageHoraire associated with
	 * 
	 * @param heureDebut
	 * @param heureFin
	 * @return The PlageHoraire object
	 */
	private PlageHoraire getPlageHoraire(String heureDebut, String heureFin) {
		Iterator<PlageHoraire> it = plagesHoraires.iterator();
		while (it.hasNext()) {
			PlageHoraire ph = it.next();
			if (ph.getHeureDebut().equals(heureDebut) && ph.getHeureFin().equals(heureFin)) {
				return ph;
			}
		}
		String format = "\\d{1,2}:\\d{1,2}:\\d{1,2}";
		if (!heureDebut.matches(format) || !heureFin.matches(format)) {
			return null;
		}

		PlageHoraire ph = new PlageHoraire(heureDebut, heureFin);
		plagesHoraires.add(ph);
		return ph;
	}

	public void resetLivraisons() {
		listeLivraisons.clear();
		plagesHoraires.clear();
	}

	public void resetTournee() {
		tournee.reset();
	}

	// #################################### Working with Tournee
	// ###############################

	/**
	 * calculates the path for delivery
	 */
	public void calculTournee() {

		InterfaceAgglo interfaceAgglo = Controller.getInstance().getInterfaceAgglo();
		Plan plan = interfaceAgglo.getPlan();
		ArrayList<Livraison> livraisons = getListeLivraisons();

		// Instanciate pathfinder
		PathFinder pf = new DijkstraFinder(plan.computeShippmentGraph());
		pf.setTimeout(30000);
		shGraph = (ShippmentGraph) ((DijkstraFinder) pf).getGraph();

		// Compute cycle (sorted list of livraison)
		ArrayList<Livraison> cycle = pf.findCycle(Integer.MAX_VALUE, livraisons, this.entrepot);

		// Print it out
		/*
		 * for (int i = 0; i < cycle.size(); ++i) { try { System.out.println(cycle.get(i).toString()); } catch(Exception e) { // Storehouse } }
		 */

		// Finding itineraires
		ArrayList<Itineraire> itineraires = new ArrayList<Itineraire>();
		for (int i = 0; i < cycle.size() - 1; i++) { // No for in !
			Livraison l1 = cycle.get(i);
			Livraison l2 = cycle.get(i + 1);
			Itineraire it = findItineraire(l1, l2, plan);
			itineraires.add(it);
		}
		// Loop the cycle
		Livraison l1 = cycle.get(cycle.size() - 1);
		Livraison l2 = cycle.get(0);
		Itineraire it = findItineraire(l1, l2, plan);
		itineraires.add(it);

		// Prepare the tournee
		Tournee tournees = new Tournee();
		tournees.setLivraisons(cycle);
		tournees.setItineraires(itineraires);

		this.setTournee(tournees);

		calculLivraisonsSchedule();
	}

	/**
	 * Computes an Itineraire object between two livraison points Note : can only be called once the tournee has been calculated
	 * 
	 * @param l1
	 *            start livraison
	 * @param l2
	 *            end livraison
	 * @param plan
	 *            the plan where it all happens
	 * @return an Itineraire object
	 */
	public Itineraire findItineraire(Livraison l1, Livraison l2, Plan plan) {
		Itineraire it = new Itineraire(l1.getAdresse(), l2.getAdresse(), new ArrayList<Troncon>());
		// Compute list of troncons
		String pathHash = "" + l1.getAdresse().getId() + "-" + l2.getAdresse().getId();
		it.computeTronconsFromNodes(plan, shGraph.getPaths().get(pathHash));
		return it;
	}

	public Itineraire findItineraire(Noeud start, Noeud end, Plan plan) {
		Itineraire it = new Itineraire(start, end, new ArrayList<Troncon>());
		// Compute list of troncons
		String pathHash = "" + start.getId() + "-" + end.getId();
		it.computeTronconsFromNodes(plan, shGraph.getPaths().get(pathHash));
		return it;
	}

	// ################################### Working with view
	// ####################################

	/**
	 * Check wether or not the node whose id is idNode is a delivery point
	 * @param idNode	ID of node (i.d. the delivery location)
	 * @return
	 */
	public boolean isNodeADelivery(int idNode) {

		if (isNodeEntrepot(idNode)) {
			return true;
		}

		for (Livraison l : listeLivraisons) {
			if (l.getAdresse().getId() == idNode) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check wether or not the node whose id is idNode is the warehouse
	 * @param idNode	ID of node
	 * @return
	 */
	public boolean isNodeEntrepot(int idNode) {
		if (entrepot == null) {
			return false;
		}

		if (entrepot.getId() == idNode) {
			return true;
		}
		return false;
	}

	// ---------------------------------------------------------------------------------
	// GETTERS - SETTERS - UTILITIES
	public ArrayList<Livraison> getListeLivraisons() {
		return listeLivraisons;
	}

	/**
	 * Return a delivery determined by its ID.
	 * @param id 
	 * 			Id of the delivery (should not be used unless you know
	 * 			exactly what you are doing, as they are not unique)
	 * @return
	 */
	public Livraison getLivraisonById(int id) {
		for (Livraison l : listeLivraisons) {
			if (l.getIdLivraison() == id) {
				return l;
			}
		}
		return null;
	}

	/**
	 * Return a delivery determined by its address.
	 * @param adresse
	 * 			Address of the delivery 
	 * @return
	 */
	public Livraison getLivraisonByAdr(int adresse) {

		if (adresse == entrepot.getId()) {
			return null;
		}

		for (Livraison l : listeLivraisons) {
			if (l.getAdresse().getId() == adresse) {
				return l;
			}
		}
		return null;
	}

	/**
	 * 	Add time constraints to the Tournee
	 */
	public void calculLivraisonsSchedule() {
		int bufTime = 0;
		try {
			bufTime = Misc.parseTimeStrToSec(tournee.getLivraisons().get(1).getPlageHoraire().getHeureDebut());
		} catch (NullPointerException e) {
			// Storehouse, not to be calculated
		}
		for (int i = 0; i < tournee.getLivraisons().size() - 1; ++i) {
			bufTime += tournee.getItineraires().get(i).getDurationSecondes();
			tournee.getLivraisons().get(i + 1).setIsDelayed(false);
			try {
				if (0 == i) { // first livraison append always at "HeureDebut"
					bufTime -= tournee.getItineraires().get(i).getDurationSecondes();
				}
				// livraison can't append before "HeureDebut"
				else if (bufTime < Misc.parseTimeStrToSec(tournee.getLivraisons().get(i + 1).getPlageHoraire().getHeureDebut())) {
					bufTime = Misc.parseTimeStrToSec(tournee.getLivraisons().get(i + 1).getPlageHoraire().getHeureDebut());
				}
				// if delay
				else if (bufTime > Misc.parseTimeStrToSec(tournee.getLivraisons().get(i + 1).getPlageHoraire().getHeureFin())) {
					tournee.getLivraisons().get(i + 1).setIsDelayed(true);
				}
				tournee.getLivraisons().get(i + 1).setHeureLivraison(Misc.parseTimeSecToStr(bufTime));
			} catch (NullPointerException e) {
				// Storehouse
			}
		}
	}

	
	public Livraison getLivraisonByIndex(int index) {
		return listeLivraisons.get(index);
	}

	public int getIndexOfLivraison(Livraison l) {
		return listeLivraisons.indexOf(l); // returns -1 if l is not in
											// listeLivraisons
	}

	public void setListeLivraisons(ArrayList<Livraison> listeLivraisons) {
		this.listeLivraisons = listeLivraisons;
	}

	public Tournee getTournee() {
		return tournee;
	}

	private void setTournee(Tournee tournee) {
		this.tournee = tournee;
	}

	public ArrayList<PlageHoraire> getPlagesHoraires() {
		return plagesHoraires;
	}

	public void setPlagesHoraires(ArrayList<PlageHoraire> plagesHoraires) {
		this.plagesHoraires = plagesHoraires;
	}


	public Noeud getEntrepot() {
		return entrepot;
	}

	public boolean setEntrepot(Noeud entrepot) {
		this.entrepot = entrepot;
		if (entrepot != null) {
			this.entrepot = entrepot;
			return true;
		} else {
			return false;
		}
	}

	public boolean setEntrepot(int id) {
		Noeud entrepot = Controller.getInstance().getInterfaceAgglo().getPlan().getNoeudById(id);
		return this.setEntrepot(entrepot);
	}

	
}
