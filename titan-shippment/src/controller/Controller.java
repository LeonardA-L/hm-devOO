package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import view.agglomeration.VueNoeud;
import view.utils.InterfaceView;

import model.planning.InterfacePlanning;
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
			
			// clic sur la carte aux coordonnées (x,y) and 
			VueNoeud view_noeud = interfaceView.getVuePanel().getVue_plan().getWhoIsClicked(x, y);
			
			if (view_noeud != null) {	//user has clicked on a node
				
				if (!livraisonsLoaded) {	
					interfaceView.displayAlert("Chargement des livraisons", "Vous devez charger les livraisons avant de pouvoir les modifier.", "warning");
					return;
				}
				
				interfaceView.displayAlert("Ajouter une livraison", "Vous avez cliqué sur le noeud : " + view_noeud.getNoeud().toString(), "info");
				
				if (addingNewLivraison) {	// true if there was a click on an empty node before that. 
					// problem if the previous node is not already a delivery
					if (!interfacePlanning.isNodeADelivery(view_noeud.getNoeud().getId())) {
						interfaceView.displayAlert("Ajouter une livraison", "Ce noeud n'a pas de livraison", "warning");
						interruptAddingNewLivraison();
						return;
					}
				
					// Otherwise, it's ok, we can set the new delivery
					// The id of node where the new delivery will take place was already saved with last trigger 
					// -> now we save the id of the previous delivery in the Tournee
					int prevAdresse = view_noeud.getNoeud().getId();
					// The client id, the heureDebut and the heureFin ar asked to the user.	
					String[] retour = interfaceView.askPlageHoraire(); // 0 : heure début / 1 : heure fin
					if (retour[0] == null || retour[1] == null) {
						interruptAddingNewLivraison();
						return;
					}
					
					String heureDebut = retour[0];
					String heureFin = retour[1];		
					int idClient = Integer.parseInt(retour[2]);
					// addLivraison
					undoRedo.InsertAddCmd(idClient, heureDebut, heureFin, newDeliveryAdress, prevAdresse);
					
					// end process
					interfaceView.repaint();
					interruptAddingNewLivraison();
				}
				else if (!addingNewLivraison) {
					if (interfacePlanning.isNodeADelivery(view_noeud.getNoeud().getId())) {
						interfaceView.displayAlert("Ajouter une livraison", "Ce noeud a déjà une livraison", "warning");
						return;
					}
					// premier clic sur un noeud
					addingNewLivraison = true;
					// sauvegarde du view_noeud temporairement
					// wait for new click
				}
				else {
					interfaceView.displayAlert("Ajouter une livraison", "", "warning");
				}
				// node HL
				interfaceView.highlight(view_noeud);
			}
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
								interfaceAgglo.GetEntrepotFromBuilder(); // if the file was read w/o problem, the entrepot was found, time to fetch it
							}
							else {
								interfaceView.displayAlert("Erreur au chargement des livraisons", "Les livraisons n'ont pas été chargées correctement", "error");
								interfaceView.repaint();
								return;
							}
							
							
							// set views
							boolean creatingViewOk = interfaceView.genererVueLivraisons(interfacePlanning.getListeLivraisons());
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
						// reset tournee
						// calcul tournee
						tourneeCalculed = true;
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
		undoRedo = new UndoRedo(this.interfacePlanning);
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
