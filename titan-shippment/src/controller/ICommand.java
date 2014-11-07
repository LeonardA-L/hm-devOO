package controller;

import model.planning.InterfacePlanning;


// Comments here

public interface ICommand {
	public boolean Execute(InterfacePlanning interfaceP);
	public boolean Unexecute(InterfacePlanning interfaceP);
}
