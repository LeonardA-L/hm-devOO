package model.agglomeration;

import java.util.ArrayList;
import java.util.Iterator;

import utils.ShippmentGraph;

public class Plan {
	
	private Noeud entrepot;
	private ArrayList<Noeud> noeuds;
	
	
	/**
	 * 	Constructor - no parameter
	 */
	Plan() {
		this.entrepot = null;
		this.noeuds = new ArrayList<Noeud>();
	}

	/**
	 * 	Constructor - no parameter
	 *  @param entrepot  special node found in "livraison.xml"
	 *  @param noeuds 	 all the nodes in the graph
	 *  @see Noeud.java Troncon.java
	 */
	public Plan(Noeud entrepot, ArrayList<Noeud> noeuds) {
		this.entrepot = entrepot;
		this.noeuds = noeuds;
	}
	
	
	/**
	 * Add a node in the Plan object
	 * @param noeud
	 * @return index of created node in "noeuds"
	 */
	public int addNoeud(Noeud noeud) {
		this.noeuds.add(noeud);
		return this.noeuds.size() - 1; 
	}

	/**
	 * Add noeud in the Plan (to be used from the xmlbuilder)
	 * @param coordX	coord X of new node
	 * @param coordY	coord Y of new node
	 * @param id		id of new node
	 * @return			index of created node in "noeuds"
	 */
	public int addNoeud(int coordX, int coordY, int id) {
		Noeud n = new Noeud(coordX, coordY, id, new ArrayList<Troncon>());
		return addNoeud(n);
	}
	
	/**
	 * Add a troncon to a node previously created with addNode()
	 * (to be used from xmlbuilder
	 * @param idNoeud				Node Id on which the troncon is added
	 * @param nomRue				Street name 
	 * @param vitesse				Speed on the troncon
	 * @param longueur				Length of troncon
	 * @param idnoeudDestination	Node toward which goes the troncon
	 * @return		True or false dependig on the success of method.
	 */
	public boolean addTronconToNoeud(int idNoeud, String nomRue, float vitesse, float longueur, int idnoeudDestination) {
		Noeud in = getNoeudById(idNoeud);
		Noeud out = getNoeudById(idnoeudDestination);
		if (in == null || out == null) {
			return false;
		}
		in.addTroncon(new Troncon(nomRue,vitesse,longueur,out));
		return true;
	}
	
	/**
	 * Remove a node from the Plan
	 * @param indexNoeud 	index of node you want to delete (in noeuds)
	 */
	public void removeNoeud(int indexNoeud) {
		this.noeuds.remove(indexNoeud);
	}
	
	/**
	 * Find a node in "noeuds" knowing its id
	 * @param id 		id of node
	 * @return	noeud   The node
	 */
	public Noeud getNoeudById(int id) {
		Iterator<Noeud> it = noeuds.iterator();
		while(it.hasNext()) {
			Noeud noeud = it.next();
			if (noeud.getId() == id) {
				return noeud;
			}
		}
		return null;
	}
	
	public float[][] GetMatrix() {
		float[][] matriceAdj = new float[noeuds.size()][noeuds.size()];
		Iterator<Noeud> it = noeuds.iterator();
		while(it.hasNext()){
			Noeud noeud = it.next();
			matriceAdj[noeud.getId()] = noeud.GetCosts(noeuds.size());
		}
		return matriceAdj;
	}

	
	// GETTERS - SETTERS 
	public Noeud getEntrepot() {
		return entrepot;
	}

	public void setEntrepot(Noeud entrepot) {
		this.entrepot = entrepot;
	}

	public ArrayList<Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(ArrayList<Noeud> noeuds) {
		this.noeuds = noeuds;
	}

	
	@Override
	public String toString() {
		String retour = "Plan [entrepotID=" + entrepot.getId() + "]\n";
		retour += entrepot.toString();
		Iterator<Noeud> it = noeuds.iterator();
		while(it.hasNext()) {
			retour += it.next().toString();
		}
		return retour;
	}
	
	
	/**
	 * To be commented later
	 * @return
	 */
	public ShippmentGraph computeShippmentGraph(){
		ShippmentGraph shGraph = new ShippmentGraph(getNoeuds().size());
		for(Noeud n : getNoeuds()){
			shGraph.addNode(n);
		}
		//shGraph.fillBlankCosts();
		return shGraph;
	}

}
