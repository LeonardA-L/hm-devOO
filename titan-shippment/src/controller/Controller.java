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

	private Controller()
	{
		mapLoaded = false;
		livraisonsLoaded = false;
		addingNewLivraison = false;
		tourneeCalculed = false;
	}
	
	// implement singleton
	public static Controller getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Controller();	
		}
		return INSTANCE;
	}
	
	public void trigger(String action, int x, int y) {
		if (action.equals("click_map")) {
			// clic sur la carte aux coordonnées (x,y)
			VueNoeud view_noeud = interfaceView.getVue_plan().getWhoIsClicked(x, y);
			
			if (view_noeud != null) {
				// un noeud a bien été cliqué
				interfaceView.displayAlert("Ajouter une livraison", "Vous avez cliqué sur le noeud : " + view_noeud.getNoeud().toString(), "info");
				
				if (addingNewLivraison) {
					// on a déjà cliqué sur un noeud, on précise après quel noeud on ajoute le nouveau
					
					// addLivraison
					
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
		}
	}
	
	public  InterfaceAgglo getReferenceToInterfaceAgglo() {
		return interfaceAgglo;
	}
	
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
						interfaceView.getVue_plan().reset();
						mapLoaded = false;
						
						boolean buildOk = interfaceAgglo.BuildPlanFromXml(filename);
						
						if (buildOk) {
							mapLoaded = true;
						}
						else {
							interfaceView.displayAlert("Erreur au chargement de la carte", "La carte n'a pas été chargée correctement.", "error");
						}
						
						// set views
						interfaceView.getVue_plan().setPlan(interfaceAgglo.getPlan());
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
							interfaceView.displayAlert("Livraisons", "Chargement des livraisons en cours ...", "info");
							
							// reset livraisons + éventuellement tournees
							if (tourneeCalculed) {
								interfacePlanning.getTournee().reset();
								interfaceView.getVue_tournee().reset();
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
							boolean creatingViewOk = interfaceView.getVue_tournee().setTournee(interfacePlanning.getTournee());
							
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
	
	private boolean loadPlanXML(String absFilePath) 
	{
		return true;
	}
	
	private boolean loadLivraisonsXML(String absFilePath) 
	{
		return true;
	}
	
	private boolean calculatePath () {
		return true;
	}
	
	// Add 1 livraison (by clicking on the map on the location of a new livraison)
	private boolean insertIntoUndoRedoAdd (int x, int y, String heureDebut, String heureFin, int idClient, int prevX, int prevY) 
	{
		return undoRedo.InsertAddCmd(x, y, idClient, heureDebut, heureFin, prevX, prevY);
	}
	
	// Remove on livraison by clicking on it on the map
	private boolean insertIntoUndoRedoRemove()
	{
		return false;
	}
	
	private boolean addLivraison (int X, int Y, String heureDebut, String heureFin, int idClient) 
	{
		return true;
	}
	
	private boolean removeLivraison (int x, int y) 
	{
		return true;
	}
	
	/*public boolean ValidateLivraison (int idLivraison) {
		return true;
	}
	
	public boolean UnvalidateLivraison (int idLivraison) {
		return true;
	}
	*/
	private boolean undo() {
		return undoRedo.Undo();
	}
	
	private boolean redo() {
		return undoRedo.Redo();
	}
	

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
