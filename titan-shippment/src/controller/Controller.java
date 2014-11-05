package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.agglomeration.VueNoeud;
import view.utils.InterfaceView;


import model.planning.InterfacePlanning;
import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Noeud;


public class Controller implements ActionListener {

	private static Controller INSTANCE = null;
	
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
			VueNoeud view_noeud = interfaceView.getVue_plan().getWhoIsClicked(x, y);
			
			if (view_noeud != null) {
				// un noeud a bien été cliqué
				JOptionPane.showMessageDialog(null, "Vous avez cliqué sur le noeud : " + view_noeud.getNoeud().toString(), "Ajouter une livraison", JOptionPane.INFORMATION_MESSAGE);
				view_noeud.highlight();
			}
		}
	}
	
	public  InterfaceAgglo getReferenceToInterfaceAgglo() {
		return interfaceAgglo;
	}
	
	public void trigger(String action, String name) {
		if (action.equals("click_button")) {
			
		}
	}
	public void trigger(String action, String name, String filename) {
		// TODO Auto-generated method stub
		if (action.equals("loadFile")) {
			if (name.equals("loadMap")) {
				if (filename != null && filename.length() > 0) {
					
					// remove former map
					interfaceAgglo.getPlan().reset();
					interfaceView.getVue_plan().reset();
					
					boolean buildOk = interfaceAgglo.BuildPlanFromXml(filename);
					interfaceView.getVue_plan().setPlan(interfaceAgglo.getPlan());
					interfaceView.getVue_plan().repaint();
				}
			}
			else if (name.equals("loadLivraisons")) {
				//interfacePlanning ...
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
