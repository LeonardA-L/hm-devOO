package controller;

import model.planning.InterfacePlanning;

import java.util.*;


public class UndoRedo {
	
	Stack<ICommand> undoCmd;
	Stack<ICommand> redoCmd;
	
	InterfacePlanning interfaceP;
	
	public UndoRedo(InterfacePlanning interfaceP)
	{
		interfaceP = this.interfaceP;
	}
	
	
	public boolean Undo()
	{
		if(!undoCmd.empty())
		{
			ICommand undoedCmd = undoCmd.pop();
			redoCmd.push(undoedCmd);
			return true;
		}
		else
			return false;
	}
	
	public boolean Redo()
	{
		if(!redoCmd.empty())
		{
			ICommand redoedCmd = redoCmd.pop();
			undoCmd.push(redoedCmd);
			return true;
		}
		else
			return false;
	}
	
	public boolean InsertAddCmd(InterfacePlanning itfP, int idClient, int idLivraison, String heureDebut, String heureFin, int adresse, int prevAdresse)
	{
		ICommand add = new CommandAddOne(idClient, idLivraison, heureDebut, heureFin, adresse, prevAdresse);
		// execute, add to stack if it worked
		return true;
	}
	
	public boolean InsertRemoveCmd(int x, int y)
	{
		ICommand rmv = new CommandRemoveOne();
		// execute, add to stack if it worked
		return true;
	}
	
	
}
