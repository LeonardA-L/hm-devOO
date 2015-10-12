package main;

import model.agglomeration.InterfaceAgglo;
import model.planning.InterfacePlanning;
import view.utils.InterfaceView;
import controller.Controller;

public class Main {

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		Main m = new Main();
		m.go();
	}

	/**
	 *  Method responsible for the initialization of the application,
	 *  by instantiating the controller and the interfaces
	 */
	public void go() {
		Controller controller = Controller.getInstance();
		controller.setInterfaceAgglo(new InterfaceAgglo());
		controller.setInterfacePlanning(new InterfacePlanning());
		controller.setInterfaceView(new InterfaceView());
		controller.setUndoRedo(); // should always be called after setInterfaceView and setInterfacePlanning
	}
}
