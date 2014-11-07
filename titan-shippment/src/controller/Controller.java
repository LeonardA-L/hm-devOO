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
		undoRedo = new UndoRedo(interfacePlanning);	// will be passed to the command by undoRedo
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
			
			// clic sur la carte aux coordonnées (x,y)
			VueNoeud view_noeud = interfaceView.getVuePanel().getVue_plan().getWhoIsClicked(x, y);
			
			if (view_noeud != null) {
				
				if (!livraisonsLoaded) {
					interfaceView.displayAlert("Chargement des livraisons", "Vous devez charger les livraisons avant de pouvoir les modifier.", "warning");
					return;
				}
				
				if(!interfacePlanning.isNodeADelivery(view_noeud.getNoeud().getId()))	// check if node has a delivery already
				{
					interfaceView.displayAlert("Ajouter une livraison", "Vous avez cliqué sur le noeud : " + view_noeud.getNoeud().toString(), "info");
					
					if (addingNewLivraison) {
						// on a déjà cliqué sur un noeud, on précise après quel noeud on ajoute le nouveau
						
						// dummies (will not be instantiated or declared here later)
						int idClient = 23;				// user input 
						int adresse = 34;				// = view_noeud.getNoeud().getId() but user should be able to change it .. ?
						int prevAdresse = 35;			// = view_noeud.getNoeud().getId() but user should be able to change it .. ?
						int idLivraison = 18;			// call a method from interfacePlanning to get one, giving as paramters heureDebut and heureFin
						String heureDebut = "8:0:0";	// user input
						String heureFin = "12:0:0";		// user input
						
						// addLivraison
						undoRedo.InsertAddCmd(idClient, idLivraison, heureDebut, heureFin, adresse, prevAdresse);
						// end process
						addingNewLivraison = false;
					}
					else {
						// premier clic sur un noeud
						addingNewLivraison = true;
						// check
						// addPlageHoraire
						// interfaceView.askPlageHoraire();
						// wait for new click
					}	
					// node HL
					view_noeud.highlight();
					interfaceView.repaint();
				}
				else {
					interfaceView.displayAlert("Ajouter une livraison", "Ce noeud a déjà une livraison.", "info");
				}
			}
		}
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
						interfaceAgglo.getPlan().reset();
						interfaceView.getVuePanel().getVue_plan().reset();
						mapLoaded = false;
						
						boolean buildOk = interfaceAgglo.BuildPlanFromXml(filename);
						
						if (buildOk) {
							mapLoaded = true;
						}
						else {
							interfaceView.displayAlert("Erreur au chargement de la carte", "La carte n'a pas été chargée correctement.", "error");
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
							// reset livraisons + éventuellement tournees
							if (tourneeCalculed) {
								interfacePlanning.getTournee().reset();
								interfaceView.getVuePanel().getVue_tournee().reset();
								tourneeCalculed = false;
							}
							interfacePlanning.reset();
							livraisonsLoaded = false;
							
							// load livraisons
							boolean buildOk = interfacePlanning.GetPlanningsFromBuilder(filename);
							if (buildOk) {
								livraisonsLoaded = true;
								interfaceAgglo.GetEntrepotFromBuilder(); // if the file was read w/o problem, the entrepot was found, time to fetch it
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
				if (!mapLoaded || !livraisonsLoaded) {
					interfaceView.displayAlert("Impossible de calculer la tournée", "Vous devez charger une carte et une livraison au préalable.", "warning");
				}
				else {
					interfaceView.displayAlert("Tournée", "Calcul de la tournée en cours ...", "info");
					// reset tournee
					// calcul tournee
					tourneeCalculed = true;
				}
				
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		//appel des autres methodes en fonction de l'action event 
	 }
	
	public  InterfaceAgglo getReferenceToInterfaceAgglo() {
		return interfaceAgglo;
	}
	
	private boolean undo() {
		return undoRedo.Undo();
	}
	
	private boolean redo() {
		return undoRedo.Redo();
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
