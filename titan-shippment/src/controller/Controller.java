package controller;

import model.planning.Manager;
import model.planning.PlageHoraire;
import model.planning.InterfacePlanning;
import model.agglomeration.InterfaceAgglo;

public class Controller {

	Manager manager;
	InterfaceAgglo interfaceAgglo;
	InterfacePlanning interfacePlanning;
	UndoRedo undoRedo;
	
	public Controller()
	{
		
	}

	
	public boolean LoadPlanXML(String absFilePath) 
	{
		return true;
	}
	
	public boolean LoadLivraisonsXML(String absFilePath) 
	{
		return true;
	}
	
	public boolean CalculatePath () {
		return true;
	}
	
	// Add 1 livraison (by clicking on the map on the location of a new livraison)
	public boolean InsertIntoUndoRedoAdd (int x, int y, String heureDebut, String heureFin, int idClient, int prevX, int prevY) 
	{
		return undoRedo.InsertAddCmd(x, y, idClient, heureDebut, heureFin, prevX, prevY);
	}
	
	// Remove on livraison by clicking on it on the map
	public boolean InsertIntoUndoRedoRemove()
	{
		return false;
	}
	
	public boolean AddLivraison (int X, int Y, String heureDebut, String heureFin, int idClient) 
	{
		return true;
	}
	
	public boolean RemoveLivraison (int x, int y) 
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
	public boolean Undo() {
		return undoRedo.Undo();
	}
	
	public boolean Redo() {
		return undoRedo.Redo();
	}

	
}
