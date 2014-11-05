package model.planning;

import controller.Controller;
import utils.XMLBuilder;
import model.agglomeration.InterfaceAgglo;
import model.planning.Manager;


public class InterfacePlanning {

	Manager manager;
	
	/*	Call a method in XMLBuilder, giving it the name of the file
	 * 	containing the plan. Then get back the array of elements to 
	 *  be used and build all livraisons from it 
	 * 	@param absFilePath	path to the xml File
	 *  @return true or false depending on the success of method (i.d. 
	 *  all livraisons have been created successfully)
	 */
	public boolean GetPlanningsFromBuilder(String absFilePath) {
		
		return true;
	}
	
	/**
	 * calculates the path for delivery
	 */
	public void CalculTournee(){
		InterfaceAgglo interfaceAgglo = Controller.getInstance().getInterfaceAgglo();
		int[][] matriceAdjacence = interfaceAgglo.GetFormatedMap();
		
		//TODO - Calcul Tournee
		Tournee tournee = new Tournee();
		
		manager.setTournee(tournee);
	}
	
	private boolean generateLivraisons () {
		return true;
	}
}