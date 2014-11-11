package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.util.ArrayList;

import view.agglomeration.VueNoeud;
import view.utils.InterfaceView;
import model.planning.InterfacePlanning;
import model.planning.Livraison;
import model.agglomeration.InterfaceAgglo;


public class Controller implements ActionListener {

	private static Controller INSTANCE = null;

	private InterfaceAgglo interfaceAgglo;
	private InterfacePlanning interfacePlanning;
	private InterfaceView interfaceView;
	private UndoRedo undoRedo;

	private boolean mapLoaded;
	private boolean livraisonsLoaded;
	private boolean tourneeCalculed;

	private boolean addingNewLivraison;

	private int newDeliveryAdress = -1;

	/**
	 * 	Constructor
	 *  Private in order to implement the singleton
	 */
	private Controller()
	{
		mapLoaded = false;
		livraisonsLoaded = false;
		addingNewLivraison = false;
		tourneeCalculed = false;
	}

	/**
	 * Return the singleton
	 * @return	Controller
	 */
	public static Controller getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Controller();	
		}
		return INSTANCE;
	}

	/**
	 * Called by the view in order to process user input on map
	 * @param action	Action triggered
	 * @param x			X Coord of the point clicked
	 * @param y			Y Coord of the point clicked
	 */
	public void trigger(String action, int x, int y) {
		if (action.equals("click_map")) {

			if (!mapLoaded) {
				return;
			}

			// clic sur la carte aux coordonnées (x,y) and get the associated point (if any)
			VueNoeud view_noeud = interfaceView.getVuePanel().getVue_plan().getWhoIsClicked(x, y);

			if (!livraisonsLoaded) {	
				interfaceView.displayAlert("Chargement des livraisons", "Vous devez charger les livraisons avant de pouvoir les modifier.", "warning");
				return;
			}

			if (view_noeud != null) {	// user has clicked on a node and not on an empty part of map		
				interfaceView.displayAlert("Ajouter une livraison", "Vous avez cliqué sur le noeud : " + view_noeud.getNoeud().toString(), "info");

				// 1st STEP : Click on a node where you want to add a delivery
				if (!addingNewLivraison) {	
					if (!interfacePlanning.isNodeEntrepot(view_noeud.getNoeud().getId()) && interfacePlanning.isNodeADelivery(view_noeud.getNoeud().getId())) { 	// if node is already a delivery, stop.
						boolean suppr = interfaceView.confirmUserInput("Suppression", "Supprimer cette livraison ? ");
						if (suppr) {
							undoRedo.InsertRemoveCmd(view_noeud.getNoeud().getId());
							return;
						}
						return;
					}
					addingNewLivraison = true;  							// start process of adding new delivery
					newDeliveryAdress = view_noeud.getNoeud().getId();		// saving the node id (if it has no delivery)
				}
				// 2nd STEP : Click on a node where there is a delivery
				else if (addingNewLivraison) {	// true if there was a click on an empty node before that. 
					if (!interfacePlanning.isNodeADelivery(view_noeud.getNoeud().getId())) { // if node is already a delivery, stop.
						interfaceView.displayAlert("Ajouter une livraison", "Ce noeud n'a pas de livraison", "warning");
						interruptAddingNewLivraison();
						return;
					}

					// Fetch all data needed to create delivery.
					int prevAdresse = view_noeud.getNoeud().getId();	// Node where previous delivery occurs.
					String[] retour = interfaceView.askPlageHoraire(); 	// 0 : heureDebut / 1 : heureFin / 2 = client ID
					if (retour[0] == null || retour[1] == null) {
						interruptAddingNewLivraison();
						return;
					}				
					String heureDebut = retour[0];
					String heureFin = retour[1];		
					int idClient = Integer.parseInt(retour[2]);			// format is checked in InterfaceView (but not fully tested)
					// DELIVERY CREATION
					boolean success = undoRedo.InsertAddCmd(idClient, heureDebut, heureFin, newDeliveryAdress, prevAdresse);
					if(!success) {
						return;
					}
					//interfaceView.addAndUpdate(interfacePlanning.getLivraisonByAdr(newDeliveryAdress));
					interruptAddingNewLivraison();
				}
				else {
					interfaceView.displayAlert("Ajouter une livraison", "", "warning");
				}
				// node HL during creation process (in red)
				interfaceView.highlight(view_noeud);
			}
		}
		else if (action.equals("mouse_moved_on_map")) {
			VueNoeud view_noeud = interfaceView.getVuePanel().getVue_plan().getWhoIsClicked(x, y);
			String infos = "";
			if (view_noeud != null) {
				infos = view_noeud.getNoeud().toString();
			}
			interfaceView.setInfoPoint(infos);
		}
	}

	private void interruptAddingNewLivraison() {
		addingNewLivraison = false;
		interfaceView.clearHighlightedNodes();
	}

	/**
	 * Called by the view in order to process user input on buttons
	 * @param action		Type of action
	 * @param name			SubType of action
	 */
	public void trigger(String action, String name) {
		if (addingNewLivraison) {
			interfaceView.displayAlert("Impossible d'effectuer l'action demandée", "Vous ne pouvez pas charger de fichier ou calculer une nouvelle tournée pendant l'ajout d'un point de livraison.", "warning");
		}
		else {
			if (action.equals("loadFile")) {
				if (name.equals("loadMap")) {
					String filename = interfaceView.loadFile();

					if (filename != null && filename.length() > 0) {

						// remove former map
						resetPlan();
						resetLivraisons();
						resetTournee();

						boolean buildOk = interfaceAgglo.BuildPlanFromXml(filename);

						if (buildOk) {
							mapLoaded = true;
						}
						else {
							interfaceView.displayAlert("Erreur au chargement de la carte", "La carte n'a pas été chargée correctement.", "error");
							interfaceView.repaint();
							return;
						}

						// set views
						interfaceAgglo.getPlan().fitJPanel(interfaceView.getVuePanel().getHeight(),interfaceView.getVuePanel().getWidth());
						interfaceView.getVuePanel().getVue_plan().setPlan(interfaceAgglo.getPlan());
						interfaceView.repaint();
					}
				}
				else if (name.equals("loadLivraisons")) {
					if (!mapLoaded) {
						interfaceView.displayAlert("Impossible de charger les livraisons", "Vous devez charger une carte au préalable.", "warning");
					}
					else {
						String filename = interfaceView.loadFile();

						if (filename != null && filename.length() > 0) {

							resetLivraisons();
							resetTournee();

							// load livraisons
							boolean buildOk = interfacePlanning.GetPlanningsFromBuilder(filename);
							if (buildOk) {
								livraisonsLoaded = true;
							}
							else {
								interfaceView.displayAlert("Erreur au chargement des livraisons", "Les livraisons n'ont pas été chargées correctement", "error");
								interfaceView.repaint();
								return;
							}


							// set views
							boolean creatingViewOk = interfaceView.genererVueLivraisons(interfacePlanning.getListeLivraisons(), interfacePlanning.getEntrepot());
							// pour les tournées, rien à voir
							// .getVue_tournee().setTournee(interfacePlanning.getTournee());

							if (!creatingViewOk) {
								livraisonsLoaded = false;
								interfaceView.displayAlert("Impossible de charger les livraisons", "Un noeud est introuvable.", "error");
							}

							interfaceView.repaint();
						}
					}
				}
			}
			else if (action.equals("click_button")) {
				if (name.equals("calculTournee")) {
					if (!mapLoaded || !livraisonsLoaded) {
						interfaceView.displayAlert("Impossible de calculer la tournée", "Vous devez charger une carte et une livraison au préalable.", "warning");
					}
					else {
						resetTournee();
						interfaceView.displayAlert("Tournée", "Calcul de la tournée en cours ...", "info");
						interfacePlanning.CalculTournee();
						interfaceView.getVuePanel().getVue_tournee().setTournee(interfacePlanning.getTournee());
						tourneeCalculed = true;

						System.out.println("Tournee : " + interfacePlanning.getTournee().toString());
						interfaceView.repaint();
					}
				}
				else if (name.equals("undo")) {
					if(!undoRedo.Undo()) {
						interfaceView.displayAlert("UNDO", "Rien à annuler", "info");
					}
				}
				else if (name.equals("redo")) {
					if(!undoRedo.Redo()) {
						interfaceView.displayAlert("REDO", "Rien à rétablir", "info");
					}
				}

			}
		}
	}

	private void resetTournee() {
		if (tourneeCalculed) {
			interfacePlanning.resetTournee();
			interfaceView.getVuePanel().resetTournee();
			tourneeCalculed = false;
		}
	}

	private void resetPlan() {
		if (mapLoaded) {
			interfaceAgglo.resetPlan();
			interfaceView.getVuePanel().resetPlan();
			mapLoaded = false;
		}
	}

	private void resetLivraisons() {
		if (livraisonsLoaded) {
			interfacePlanning.resetLivraisons();
			interfaceView.getVuePanel().resetLivraisons();
			interfaceView.resetShippmentTable();
			livraisonsLoaded = false;
		}
	}

	public void actionPerformed(ActionEvent e) {
		//appel des autres methodes en fonction de l'action event 
	}

	public  InterfaceAgglo getReferenceToInterfaceAgglo() {
		return interfaceAgglo;
	}

	//------------------------------------------------------------------
	// GETTERS - SETTERS
	public InterfaceAgglo getInterfaceAgglo() {
		return interfaceAgglo;
	}

	public void setInterfaceAgglo(InterfaceAgglo interfaceAgglo) {
		this.interfaceAgglo = interfaceAgglo;
	}

	public InterfacePlanning getInterfacePlanning() {
		return interfacePlanning;
	}

	public void setInterfacePlanning(InterfacePlanning interfacePlanning) {		
		this.interfacePlanning = interfacePlanning;
	}

	public void setUndoRedo()
	{
		undoRedo = new UndoRedo(interfacePlanning, interfaceView);
	}

	public UndoRedo getUndoRedo() {
		return undoRedo;
	}

	public void setUndoRedo(UndoRedo undoRedo) {
		this.undoRedo = undoRedo;
	}

	public InterfaceView getInterfaceView() {
		return interfaceView;
	}

	public void setInterfaceView(InterfaceView interfaceView) {
		this.interfaceView = interfaceView;
	}

}
