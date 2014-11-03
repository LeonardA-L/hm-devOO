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
	
	public boolean InsertIntoUndoRedoAdd (int X, int Y, PlageHoraire plageHoraire, int idClient, int idLivraisonBefore) 
	{
		return true;
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
	
	public void titan () {
		System.out.println("JAVA-TITAN");
	}
	
	
	
}
