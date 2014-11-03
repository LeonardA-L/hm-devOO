package controller;

import model.planning.Manager;
import model.planning.PlageHoraire;
//import model.

public class Controller {

	Manager manager;
	
	UndoRedo undoRedo;
	
	public Controller()
	{
		
	}

	
	public boolean LoadPlanXML(String absFilePath) {
		
		return true;
	}
	
	public boolean LoadLivraisonsXML(String absFilePath) {
		return true;
	}
	
	public boolean CalculatePath () {
		return true;
	}
	
	public boolean InsertIntoUndoRedoAdd (int X, int Y, PlageHoraire plageHoraire, int idClient, int idLivraisonBefore) {
		return true;
	}
	
	public boolean AddLivraison (int X, int Y, PlageHoraire plageHoraire, int idClient) {
		return true;
	}
	
	public boolean RemoveLivraison (int idLivraison) {
		return true;
	}
	
	public boolean ValidateLivraison (int idLivraison) {
		return true;
	}
	
	public boolean UnvalidateLivraison (int idLivraison) {
		return true;
	}
	
	public boolean Undo() {
		return true;
	}
	
	public boolean Redo() {
		return true;
	}
	
	public void titan () {
		System.out.println("JAVA-TITAN");
	}
	
	
	
}
