package utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	private Map<String, ArrayList<Integer>> paths;
	private PathFinder pathFinder;

	

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
		paths = new HashMap<String, ArrayList<Integer>>();
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

	/**
	 * Will create a subgraph with a sub cost matrix only containing the desired nodes for a cycle
	 */
	public ShippmentGraph createTSPGraph(ArrayList<Integer> nodes){
		int n = nodes.size();
		ShippmentGraph shGraph = new ShippmentGraph(n);
		int[][] totalCosts = this.getCost();
		int[][] costs = new int[n][n];
		
		int min = Integer.MAX_VALUE;
		int max = -1;
		for(int i : nodes){
			for(int j : nodes){
				costs[nodes.indexOf(i)][nodes.indexOf(j)] = totalCosts[i][j];
				if(totalCosts[i][j] > max){
					max = totalCosts[i][j];
				}
				if(totalCosts[i][j] < min){
					min = totalCosts[i][j];
				}
			}
		}
		shGraph.setMaxArcCost(max);
		shGraph.setMinArcCost(min);
		shGraph.setNbVertices(n);
		shGraph.setCost(costs);
		
		ArrayList<ArrayList<Integer>> succs = new ArrayList<ArrayList<Integer>>(n);
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for (int i=0; i< n;i++) {
			indexList.add(i);
		}
		for (int i=0; i< n;i++) {
			ArrayList<Integer> copyNodes = new ArrayList<Integer>(indexList);
			//copyNodes.remove(i);
			succs.add(copyNodes);
		}
		
		shGraph.setSucc(succs);
		return shGraph;
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
	
	/**
	 * In order to get the PF algorithm working, we have to make sure that impossible routes won't be taken,
	 * by giving them a higher than all weight, thus forcing Dijkstra to find for better solutions.
	 */
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
	
	/**
	 * Will complete the cost matrix, by calculating every paths from node to node.
	 * This way the cost matrix will be more accurate.
	 * Also stores the computed paths into a map, for further use
	 * <p>Note : Not available paths will be set to null</p>
	 */
	public void makeGraphComplete(){
		pathFinder = new DijkstraFinder(this);
		for (int i = 0; i < cost.length; i++) {
			for (int j = 0; j < cost.length; j++) {
				ArrayList<Integer> pathFromIToJ = pathFinder.findShortestPath(i, j);
				// popping the total distance of the path
				if(pathFromIToJ != null){
					int totalCost = pathFromIToJ.remove(pathFromIToJ.size()-1);
					//pathFromIToJ.add(totalCost);
					cost[i][j] = totalCost;
					updateMinMax(totalCost);
				}
				paths.put(i+"-"+j, pathFromIToJ);
			}
		}
		fillBlankCosts();
	}

	public Map<String, ArrayList<Integer>> getPaths() {
		return paths;
	}

	public void setPaths(Map<String, ArrayList<Integer>> paths) {
		this.paths = paths;
	}

	public void setCost(int[][] cost) {
		this.cost = cost;
	}
	public ArrayList<ArrayList<Integer>> getSucc() {
		return succ;
	}

	public void setSucc(ArrayList<ArrayList<Integer>> succ) {
		this.succ = succ;
	}

	public void setNbVertices(int nbVertices) {
		this.nbVertices = nbVertices;
	}

	public void setMaxArcCost(int maxArcCost) {
		this.maxArcCost = maxArcCost;
	}

	public void setMinArcCost(int minArcCost) {
		this.minArcCost = minArcCost;
	}
	
	public PathFinder getPathFinder() {
		return pathFinder;
	}

	public void setPathFinder(PathFinder f) {
		this.pathFinder = f;
	}
}
