package controller;

import model.planning.InterfacePlanning;
import view.utils.InterfaceView;

// Comments here

public interface ICommand {
	/**
	 * Execute a command
	 * 
	 * @param interfaceP
	 * @return
	 */
	public boolean Execute(InterfacePlanning interfaceP,
			InterfaceView interfaceV);

	/**
	 * Un-execute a command
	 * 
	 * @param interfaceP
	 * @return
	 */
	public boolean Unexecute(InterfacePlanning interfaceP,
			InterfaceView interfaceV);
}
