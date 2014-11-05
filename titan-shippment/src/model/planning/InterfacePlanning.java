package model.planning;

import java.util.ArrayList;

import controller.Controller;
import utils.XMLBuilder;

import model.agglomeration.Noeud;
import model.planning.*;

public class InterfacePlanning {

	ArrayList<Livraison> listeLivraisons;
	
	
	/**	Call a method in XMLBuilder, giving it the name of the file
	 * 	containing the plan. Then get back the array of elements to 
	 *  be used and build all livraisons from it 
	 * 	@param 		absFilePath	path to the xml File
	 *  @return 	true or false depending on the success of method (i.d. 
	 *  			all livraisons have been created successfully)
	 */
	
	
	public boolean GetPlanningsFromBuilder(String absFilePath) {
		//XMLBuiler.getLivraison(absFilePath, this);
		return true;
	}
	
	public boolean addLivraison(int idClient, int idLivraison,String heureDebut, String heureFin, int adresse) {
		PlageHoraire ph = new PlageHoraire(heureDebut, heureFin);
		Noeud nd = Controller.getInstance().getNoeudById(adresse);
		Livraison l = new Livraison(ph, nd, idClient, idLivraison);
		return false;
	}
	
}