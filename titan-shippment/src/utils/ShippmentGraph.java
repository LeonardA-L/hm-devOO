package utils;
import java.util.ArrayList;

import model.agglomeration.Noeud;
import model.agglomeration.Troncon;

/**
 * @author Christine Solnon
 *
 */

public class ShippmentGraph implements Graph {
	
	private final int NO_START_COST = 0;
	
	private int nbVertices;
	private int maxArcCost;
	private int minArcCost;
	private int[][] cost; // cost[from][to]
	private ArrayList<ArrayList<Integer>> succ; 

	/**
	 * Creates a graph such that each vertex is connected to the next <code>d</code> vertices (modulo <code>n</code>) and
	 * such that the cost of each arc is a randomly chosen integer ranging between <code>min</code> and <code>max</code>
	 * @param n a number of vertices such that <code>n > 0</code>
	 */
	public ShippmentGraph(int n){
		nbVertices = n;
		maxArcCost = -1;
		minArcCost = Integer.MAX_VALUE;
		cost = new int[nbVertices][nbVertices];
		succ = new ArrayList<ArrayList<Integer>>(n);
		while (succ.size() < n) succ.add(null); 
	}

	public int getMaxArcCost() {
		return maxArcCost;
	}

	public int getMinArcCost() {
		return minArcCost;
	}

	public int getNbVertices() {
		return nbVertices;
	}

	public int[][] getCost(){
		return cost;
	}

	public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException{
		if ((i<0) || (i>=nbVertices))
			throw new ArrayIndexOutOfBoundsException();
		int[] tab = new int[succ.get(i).size()];
		for(int j=0;j<tab.length;j++){
			tab[j] = succ.get(i).get(j);
		}
		return tab;
	}


	public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
		if ((i<0) || (i>=nbVertices))
			throw new ArrayIndexOutOfBoundsException();
		return succ.get(i).size();
	}

	public void addNode(Noeud n){
		ArrayList<Integer> exitNodesIds = new ArrayList<Integer>();
		for(Troncon t : n.getTroncons()){
			exitNodesIds.add(t.getNoeudDestination().getId());
			int arcCost = (int)(((double)t.getLongueur()) / t.getVitesse());
			cost[n.getId()][t.getNoeudDestination().getId()] = arcCost;
			updateMinMax(arcCost);
		}
		succ.set(n.getId(), exitNodesIds);
	}

	private void updateMinMax(int arcCost) {
		if(arcCost > maxArcCost)
			maxArcCost = arcCost;
		if(arcCost < minArcCost)
			minArcCost = arcCost;
	}
	
	public void fillBlankCosts(){
		for (int i = 0; i < cost.length; i++) {
			for (int j = 0; j < cost.length; j++) {
				if(cost[i][j] == NO_START_COST)
					cost[i][j] = maxArcCost+1;
			}
		}
	}
	
	public int getCost(int from, int to) {
		return cost[from][to];
	}
	
	public ArrayList<Integer> getNeighbourIDs(int nodeID) {
		return this.succ.get(nodeID);
	}
}
