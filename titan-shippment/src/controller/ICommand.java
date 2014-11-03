package controller;


// Comments here

public interface ICommand {
	public boolean Execute();
	public boolean Unexecute();
}
