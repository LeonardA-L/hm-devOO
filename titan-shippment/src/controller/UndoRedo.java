package controller;

import model.planning.InterfacePlanning;

import java.util.*;


public class UndoRedo {
	
	// Undo and Redo stack for ICommand
	private Stack<ICommand> undoCmd;
	private Stack<ICommand> redoCmd;
	// Interface reference to be passed to the command
	private InterfacePlanning interfaceP;
	
	/**
	 * Constructor w/param
	 * @param interfaceP	Reference to the interface planning 
	 * 						that will be used by the command
	 */
	public UndoRedo(InterfacePlanning interfaceP)
	{
		this.interfaceP = interfaceP;
		undoCmd = new Stack<ICommand>();
		redoCmd = new Stack<ICommand>();
	}
	
	//-------------------------------------------------
	//  Undo - Redo
	//-------------------------------------------------
	/**
	 * Undo Command : undo the last command if it exists
	 * the undo will fail if the call to Unexecute() fails...
	 * @return	True or false depending on the success of the methods
	 */
	public boolean Undo()
	{
		if(!undoCmd.empty())
		{
			ICommand undoedCmd = undoCmd.pop();
			boolean success = undoedCmd.Unexecute(interfaceP);
			if (success) {
				redoCmd.push(undoedCmd);
				return true;
			}
			else {	// if the unexecute fails, we do not push the command into the redo stack
				return false;
			}
		}
		else
			return false;
	}
	
	/**
	 * Redo command : redo the last undone command, if it exists
	 * Will fail if the call to Execute() fails.
	 * @return True or false depending on the success of the methods
	 */
	public boolean Redo()
	{
		if(!redoCmd.empty())
		{
			ICommand redoedCmd = redoCmd.pop();
			boolean success = redoedCmd.Execute(interfaceP);
			if (success) {
				undoCmd.push(redoedCmd);
				return true;
			}
			else {	// if the unexecute fails, we do not push the command into the redo stack
				return false;
			}
		}
		else
			return false;
	}
	
	//-----------------------------------------------
	// Insert into stack methods
	//-----------------------------------------------
	
	/**
	 * Create and execute a new CommandAddOne. This method is used to add a delivery
	 * only if the Tournee HAS BEEN CALCULTED, and the Tournee is calculated again and updated.
	 * @param idClient				From user input, via a popup
	 * @param idLivraison			Automatically calculated when the new Livraison is created
	 * @param heureDebut			From user input (tip : check the format before sending to the Interface)
	 * @param heureFin				From user input
	 * @param adresse				From user input when click on point occurs -> new delivery location
	 * @param prevAdresse			From user input (click) -> location of delivery before (see spec) 
	 * @return True or False 		Depending on the success of method
	 */
	public boolean InsertAddCmd(int idClient, String heureDebut, String heureFin, int adresse, int prevAdresse)
	{
		ICommand add = new CommandAddOne(idClient, heureDebut, heureFin, adresse, prevAdresse);
		add.Execute(interfaceP);
		return true;
	}
	
	/**
	 * Create and execute a new CommandRemoveOne. This method can be used to delete any Livraison
	 * from the list of Livraisons.
	 * @param idLivraison		From user input (click on map)
	 * @return	True or False	Depending on the success (possible problem with id)
	 */
	public boolean InsertRemoveCmd(int idLivraison)
	{
		ICommand rmv = new CommandRemoveOne(idLivraison);
		rmv.Execute(interfaceP);
		return true;
	}
	
	
}
