package controller;

import model.planning.Manager;

public class CommandAddOne implements ICommand {
	private int newX;		// x coordinate of new livraison
	private int newY;		// y coordinate of new livraison
	private int previousX;	// x coordinate of livraison before the newly added livraisons in the itineraire
	private int previousY;	// y coordinate ...
	private int client;	// id client 
	private String heureDebut;	// plage Horaire
	private String heureFin;
	
	Manager manager;
	
	public boolean Execute () {
		// Call manager and add a livraison
		return manager.AddOneLivraison(newX, newY, heureDebut, heureFin, client, previousX, previousY);
	}
	
	public boolean Unexecute () {
		// remove the livraison at coordinates
		return true;
	}
	
	public CommandAddOne (int x, int y, String heureDebut,String heureFin, int idClient, int prevX, int prevY) {
		newX = x;
		newY = y;
		previousX = prevX;
		previousY = prevY;
		client = idClient;
		heureDebut = this.heureDebut;
		heureFin = this.heureFin;
	}

}
