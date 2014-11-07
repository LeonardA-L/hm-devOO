package controller;

import model.planning.InterfacePlanning;


// Comments here

public interface ICommand {
	/**
	 * Execute a command 
	 * @param interfaceP
	 * @return
	 */
	public boolean Execute(InterfacePlanning interfaceP);
	
	/**
	 * Un-execute a command
	 * @param interfaceP
	 * @return
	 */
	public boolean Unexecute(InterfacePlanning interfaceP);
}
