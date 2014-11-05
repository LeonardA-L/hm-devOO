package model.agglomeration;

import java.util.ArrayList;
import java.util.Iterator;

public class Noeud {
	
	private int coordX;
	private int coordY;
	private int id;
	private ArrayList<Troncon> troncons;
	
	public Noeud () {
		this.coordX = 0;
		this.coordY = 0;
		this.id = 0;
		troncons = new ArrayList<Troncon>();
	}

	public Noeud(int coordX, int coordY, int id, ArrayList<Troncon> troncons) {
		this.coordX = coordX;
		this.coordY = coordY;
		this.id = id;
		this.troncons = troncons;
	}
	
	public int addTroncon(Troncon troncon) {
		this.troncons.add(troncon);
		return this.troncons.size(); // return index of troncon
	}
	
	public void removeTroncon(int indexTroncon) {
		this.troncons.remove(indexTroncon);
	}
	
	/**
	 * 
	 */
	public int[] GetCosts(int nbNoeuds){
		int[] line = new int[nbNoeuds];
		Iterator<Troncon> it = troncons.iterator();
		while(it.hasNext()){
			Troncon troncon = it.next();
			line[troncon.getNoeudDestination().getId()] = troncon.GetTime();
		}
		return line;
	}

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
		String retour = "\tNoeud " + id + " [" + coordX + "," + coordY + "] :\n";
		int size = troncons.size();
		
		for (int i = 0; i < size; ++i) {
			retour += "\t\t" + troncons.get(i).toString() + "\n";
		}
		return retour;
	}

}
