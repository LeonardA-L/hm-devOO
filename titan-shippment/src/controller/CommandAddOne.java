package controller;

import model.planning.PlageHoraire;

public class CommandAddOne implements ICommand {
	private int newAdresse;
	private int newClient;
	private PlageHoraire newPlageHoraire;
	
	public boolean Execute () {
		return true;
	}
	
	public boolean Unexecute () {
		return true;
	}
	
	public boolean CommandAddOne (int adr, PlageHoraire pH, int idClient) {
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
