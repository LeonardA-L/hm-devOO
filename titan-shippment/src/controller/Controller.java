package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.planning.Manager;
import model.planning.PlageHoraire;
import model.planning.InterfacePlanning;
import model.agglomeration.InterfaceAgglo;

public class Controller implements ActionListener {

	Manager manager;
	InterfaceAgglo interfaceAgglo;
	InterfacePlanning interfacePlanning;
	UndoRedo undoRedo;
	
	public Controller()
	{
		
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

	
}
