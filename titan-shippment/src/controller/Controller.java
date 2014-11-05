package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import view.agglomeration.VuePlan;
import view.utils.InterfaceView;
import model.planning.Manager;
import model.planning.PlageHoraire;
import model.planning.InterfacePlanning;
import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Noeud;
import model.agglomeration.Plan;

public class Controller implements ActionListener {

	private static Controller INSTANCE = null;
	
	private Manager manager;
	private InterfaceAgglo interfaceAgglo;
	private InterfacePlanning interfacePlanning;
	private InterfaceView interfaceView;
	private UndoRedo undoRedo;

	private Controller()
	{
		
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
			//VuePlan view_plan = interfaceView.getPlan();
			
			// savoir qui est cliqué
			//Noeud noeud = view_plan.getWhoIsClicked(x, y);
			
			//if (noeud != null) {
				// un noeud a bien été cliqué
				// noeud.isLivraison() ? nothingToDo : addLivraisonWithThisNode;
			//}
			System.out.println("Clic recu en [" + x + "," + y + "]");
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
	
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
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
