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
		this.entrepot = new Noeud();
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
	
	public int addNoeud(int coordX, int coordY, int id) {
		Noeud n = new Noeud(coordX, coordY, id, new ArrayList<Troncon>());
		return addNoeud(n);
	}
	
	public boolean addTronconToNoeud(int idNoeud, String nomRue, int vitesse, int longueur, int idnoeudDestination) {
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
	
	public int[][] GetMatrix() {
		int[][] matriceAdj = new int[noeuds.size()][noeuds.size()];
		Iterator<Noeud> it = noeuds.iterator();
		while(it.hasNext()){
			Noeud noeud = it.next();
			matriceAdj[noeud.getId()] = noeud.GetCosts(noeuds.size());
		}
		return matriceAdj;
	}

	
	
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
		
		int size = this.noeuds.size();
		Iterator<Noeud> it = noeuds.iterator();
		while(it.hasNext()) {
			retour += it.next().toString();
		}
		return retour;
	}
	
	
	/**
	 * later
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
