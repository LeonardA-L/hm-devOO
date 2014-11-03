package controller;

import java.util.*;


public class UndoRedo {

	CommandAddOne addCmd;
	CommandRemoveOne removeCmd;
	
	Stack<ICommand> undoCmd;
	Stack<ICommand> redoCmd;
	
	public UndoRedo()
	{
		
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
	
	public boolean InsertAddCmd(int x, int y, int CLient, String heureDebut, String heureFin, int idNodeBefore)
	{
		ICommand add = new CommandAddOne();
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
