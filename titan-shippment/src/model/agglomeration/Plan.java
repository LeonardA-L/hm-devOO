package model.agglomeration;

import java.util.ArrayList;
import java.util.Iterator;

import utils.ShippmentGraph;

public class Plan {

	private ArrayList<Noeud> noeuds;

	/**
	 * Constructor - no parameter
	 */
	Plan() {
		this.noeuds = new ArrayList<Noeud>();
	}

	/**
	 * Constructor - w/params
	 * 
	 * @param entrepot
	 *            special node found in "livraison.xml"
	 * @param noeuds
	 *            all the nodes in the graph
	 * @see Noeud.java Troncon.java
	 */
	public Plan(ArrayList<Noeud> noeuds) {
		this.noeuds = noeuds;
	}

	/**
	 * Add a node in the Plan object
	 * 
	 * @param noeud
	 * @return index of created node in "noeuds"
	 */
	public int addNoeud(Noeud noeud) {
		this.noeuds.add(noeud);
		return this.noeuds.size() - 1;
	}

	/**
	 * Add noeud in the Plan (to be used from the xmlbuilder)
	 * 
	 * @param coordX
	 *            coord X of new node
	 * @param coordY
	 *            coord Y of new node
	 * @param id
	 *            id of new node
	 * @return boolean if add worked
	 */
	public boolean addNoeud(int coordX, int coordY, int id) {
		if (coordX < 0 || coordY < 0 || id < 0) {
			return false;
		}
		Noeud n = new Noeud(coordX, coordY, id, new ArrayList<Troncon>());
		this.addNoeud(n);
		return true;
	}

	/**
	 * Add a troncon to a node previously created with addNode() (to be used from xmlbuilder
	 * 
	 * @param idNoeud
	 *            Node Id on which the troncon is added
	 * @param nomRue
	 *            Street name
	 * @param vitesse
	 *            Speed on the troncon
	 * @param longueur
	 *            Length of troncon
	 * @param idnoeudDestination
	 *            Node toward which goes the troncon
	 * @return True or false dependig on the success of method.
	 */
	public boolean addTronconToNoeud(int idNoeud, String nomRue, float vitesse, float longueur, int idnoeudDestination) {
		Noeud in = getNoeudById(idNoeud);
		Noeud out = getNoeudById(idnoeudDestination);
		if (in == null || out == null) {
			return false;
		}
		in.addTroncon(new Troncon(nomRue, vitesse, longueur, out));
		return true;
	}

	/**
	 * Remove a node from the Plan
	 * 
	 * @param indexNoeud
	 *            index of node you want to delete (in noeuds)
	 */
	public void removeNoeud(int indexNoeud) {
		this.noeuds.remove(indexNoeud);
	}

	/**
	 * Find a node in "noeuds" knowing its id
	 * 
	 * @param id
	 *            id of node
	 * @return noeud The node
	 */
	public Noeud getNoeudById(int id) {
		Iterator<Noeud> it = noeuds.iterator();
		while (it.hasNext()) {
			Noeud noeud = it.next();
			if (noeud.getId() == id) {
				return noeud;
			}
		}
		return null;
	}

	/**
	 * Deprecated
	 */
	public float[][] getMatrix() {
		float[][] matriceAdj = new float[noeuds.size()][noeuds.size()];
		Iterator<Noeud> it = noeuds.iterator();
		while (it.hasNext()) {
			Noeud noeud = it.next();
			matriceAdj[noeud.getId()] = noeud.getCosts(noeuds.size());
		}
		return matriceAdj;
	}

	/**
	 *  Remove all nodes in plan
	 */
	public void reset() {
		noeuds.clear();
	}

	/**
	 * This methods recalculate the coordinates of each node so that any map 
	 * fits in our 500*500px JPanel
	 * @param xSize
	 * @param ySize
	 */
	public void fitJPanel(int xSize, int ySize) {
		int xMax = 0;
		int yMax = 0;
		
		for (Noeud n : noeuds) {
			xMax = n.getCoordX() > xMax ? n.getCoordX() : xMax;
			yMax = n.getCoordY() > yMax ? n.getCoordY() : yMax;
		}

		float xRatio = (float) xSize / (float) xMax;
		float yRatio = (float) ySize / (float) yMax;
		for (Noeud n : noeuds) {
			n.setCoordX((int) (n.getCoordX() * xRatio));
			n.setCoordY((int) (n.getCoordY() * yRatio * 0.95));
		}
	}

	/**
	 * Computes a ShippmentGraph object from this one, meeting choco's requirements
	 */
	public ShippmentGraph computeShippmentGraph() {
		ShippmentGraph shGraph = new ShippmentGraph(getNoeuds().size());
		for (Noeud n : getNoeuds()) {
			shGraph.addNode(n);
		}
		// Computes every path between every node couple
		shGraph.makeGraphComplete();
		return shGraph;
	}

	
	// ------------------------------------------------------------------
	// GETTERS - SETTERS
	public ArrayList<Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(ArrayList<Noeud> noeuds) {
		this.noeuds = noeuds;
	}

	@Override
	public String toString() {
		String retour = "Plan" + "\n";

		Iterator<Noeud> it = noeuds.iterator();
		while (it.hasNext()) {
			Noeud n = it.next();
			retour += n.toString();
		}
		return retour;
	}
}
