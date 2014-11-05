package model.agglomeration;

import java.util.ArrayList;

import utils.ShippmentGraph;

public class Plan {
	
	private Noeud entrepot;
	private ArrayList<Noeud> noeuds;
	
	Plan() {
		this.entrepot = null;
		this.noeuds = new ArrayList<Noeud>();
	}

	public Plan(Noeud entrepot, ArrayList<Noeud> noeuds) {
		this.entrepot = entrepot;
		this.noeuds = noeuds;
	}
	
	public int addNoeud(Noeud noeud) {
		this.noeuds.add(noeud);
		return this.noeuds.size() - 1; // return index of node
	}
	
	public void removeNoeud(int indexNoeud) {
		this.noeuds.remove(indexNoeud);
	}
	
	public Noeud getNoeudById(int id) {
		int size = this.noeuds.size();
		for (int i = 0; i < size; ++i) {
			Noeud noeud = this.noeuds.get(i);
			if (noeud.getId() == id) {
				return noeud;
			}
		}
		return null;
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
		for (int i = 0; i < size; ++i) {
			retour += this.noeuds.get(i).toString();
		}
		return retour;
	}
	
	public ShippmentGraph computeShippmentGraph(){
		ShippmentGraph shGraph = new ShippmentGraph(getNoeuds().size());
		for(Noeud n : getNoeuds()){
			shGraph.addNode(n);
		}
		shGraph.fillBlankCosts();
		return shGraph;
	}

}
