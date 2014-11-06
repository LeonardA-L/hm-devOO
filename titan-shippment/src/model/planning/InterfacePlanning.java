package model.planning;

import java.util.ArrayList;

import controller.Controller;


import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Noeud;

public class InterfacePlanning {

	private ArrayList<Livraison> listeLivraisons;
	private Tournee tournee;
	
	
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
	
	
	private boolean generateLivraisons () {
		return true;
	}
	public boolean addLivraison(int idClient, int idLivraison,String heureDebut, String heureFin, int adresse) {
		return true;
	}
	
	/**
	 * Add a livraison in listeLivraisons (for use with the xml builder)
	 * @param idClient			id of the client
	 * @param idLivraison		id of the delivery
	 * @param heureDebut		time from which the delivery can occur
	 * @param heureFin			time after which the delivery is late 
	 * @param adresse			address where the the delivery takes place
	 * @return
	 */
	public boolean AddLivraison(int idClient, int idLivraison, String heureDebut, String heureFin, int adresse) {
		PlageHoraire ph = new PlageHoraire(heureDebut, heureFin);
		Noeud nd = Controller.getInstance().getInterfaceAgglo().getPlan().getNoeudById(adresse);
		if (nd == null || ph == null) {
			return false;
		}
		Livraison liv = new Livraison(ph, nd, idClient, idLivraison);
		listeLivraisons.add(liv);
		return true;
	}
	
	/**
	 * 	Celle là elle est pour Quentin -> comment tu bind les clics souris et un point sur le graphique (id qui sert d'adresse)
	 * @param idCLient
	 * @param idLivraison
	 * @param heureDebut
	 * @param heureFin
	 * @param xNew
	 * @param yNew
	 * @param xPrev
	 * @param yPrev
	 * @return
	 */
	public boolean AddLivraison(int idCLient, int idLivraison, String heureDebut, String heureFin, float xNew, float yNew, float xPrev, float yPrev) {
		return false;
	}
	
	/**
	 * calculates the path for delivery
	 */
	public void CalculTournee(){
		InterfaceAgglo interfaceAgglo = Controller.getInstance().getInterfaceAgglo();
		float[][] matriceAdjacence = interfaceAgglo.GetFormatedMap();
		
		Tournee tournee = new Tournee();
		//TODO - Calcul Tournee
		
		this.setTournee(tournee);
	}
	
	private void setTournee(Tournee tournee){
		this.tournee = tournee;
	}
	
}