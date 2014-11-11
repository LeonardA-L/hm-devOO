package model.agglomeration;

import java.util.ArrayList;
import java.util.Iterator;

public class Noeud {
	
	private int coordX;
	private int coordY;
	private int id;
	private ArrayList<Troncon> troncons;
	
	
	/**
	 * 	Constructor wo/parameter
	 */
	public Noeud () {
		this.coordX = 0;
		this.coordY = 0;
		this.id = 0;
		troncons = new ArrayList<Troncon>();
	}

	/**
	 * Constructor w/params
	 * @param coordX	x coordinate of node
	 * @param coordY	y coordinate of node
	 * @param id		id of node
	 * @param troncons	list of troncons (empty at first in our case)
	 */
	public Noeud(int coordX, int coordY, int id, ArrayList<Troncon> troncons) {
		this.coordX = coordX;
		this.coordY = coordY;
		this.id = id;
		this.troncons = troncons;
	}
	
	/**
	 * Add a troncon to a node
	 * @param troncon
	 * @return index of troncon in 'troncons'
	 */
	public int addTroncon(Troncon troncon) {
		this.troncons.add(troncon);
		return this.troncons.size(); // return index of troncon
	}
	
	/**
	 * Remove troncon from node
	 * @param indexTroncon  index of the troncon to be removed
	 */
	public void removeTroncon(int indexTroncon) {
		this.troncons.remove(indexTroncon);
	}
	
	/**
	 * 
	 */
	public float[] GetCosts(int nbNoeuds){
		float[] line = new float[nbNoeuds];
		Iterator<Troncon> it = troncons.iterator();
		while(it.hasNext()){
			Troncon troncon = it.next();
			line[troncon.getNoeudDestination().getId()] = troncon.GetTime();
		}
		return line;
	}

	
	// GETTERS - SETTERS
	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(ArrayList<Troncon> troncons) {
		this.troncons = troncons;
	}
	
	
	@Override
	public String toString() {
		String retour = "Noeud n°" + id + "(coord x : " + coordX + ", y : " + coordY + ")";
		
//		Iterator<Troncon> it = troncons.iterator();
//		while (it.hasNext()) {
//			retour += "" + it.next().toString() + "\n";
//		}
		
		return retour;
	}

}
