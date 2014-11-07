package controller;

import model.planning.InterfacePlanning;
import model.planning.PlageHoraire;

public class CommandRemoveOne implements ICommand{
	private int oldAdresse;
	private int oldClient;
	private PlageHoraire oldPlageHoraire;

	public boolean CommandAddOne (int adr, PlageHoraire pH, int idClient) {
		
		return true;
	}

	public boolean Execute(InterfacePlanning interfaceP) {
		return false;
	}

	public boolean Unexecute(InterfacePlanning interfaceP) {
		return false;
	}
}
