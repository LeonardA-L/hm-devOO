package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import model.agglomeration.Noeud;
import model.planning.Livraison;
import model.planning.PlageHoraire;
import solver.ResolutionPolicy;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.loop.monitors.SearchMonitorFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

public class DijkstraFinder implements PathFinder {
	Graph graph;
	

	public static final int UNDEFINED_NODE = -1;
	
	public DijkstraFinder(Graph g) {
		this.graph = g;
	}
	
	@Override
	/**
	 * Careful : the last value of this arrayList is the total distance of the path. Pop it to have the real path
	 */
	public ArrayList<Integer> findShortestPath(int start, int end) {
		int n = graph.getNbVertices();
		int[][] cost = graph.getCost();
		
		int[] prev = new int[n];
		int[] dist = new int[n];
		
		dist[start] = 0;
		ArrayList<Integer> Q = new ArrayList<Integer>();
		
		for (int v=0 ; v<n ; v++) {
			if(v != start){
				dist[v] = Integer.MAX_VALUE;
				prev[v] = UNDEFINED_NODE;
			}
			Q.add(v);
		}
		
		while(!Q.isEmpty()){
			int bestIndex = findShortestDistance(Q,dist);
			if(bestIndex == -1){
				return null;
			}
			int u = Q.get(bestIndex);
			Q.remove(bestIndex);
			
			int[] neighbours = graph.getSucc(u);
			for(int v : neighbours){
				int alt = dist[u] + cost[u][v];
				if(alt < dist[v]){
					dist[v] = alt;
					prev[v] = u;
				}
			}
		}
		
		ArrayList<Integer> reversedPath = backtrack(prev, start, end);
		Collections.reverse(reversedPath);
		
		// Adding the total distance of the path to the list
		// Careful : pop it
		reversedPath.add(dist[end]);
		
		return reversedPath;
	}
	
	/**
	 * 
	 * @param q
	 * @return the index in Q of the node with the shortest distance 
	 */
	private int findShortestDistance(ArrayList<Integer> q, int[] dist) {
		int minDist = Integer.MAX_VALUE;
		int selectedNode = -1;
		for(int i : q){
			if(dist[i] < minDist){
				minDist = dist[i];
				selectedNode = i;
			}
		}
		
		return q.indexOf(selectedNode);
	}

	/**
	 * It is really important not to mess up with the indexes in the following lists, as the Object concept is blown up by choco in this method
	 */
	public ArrayList<Livraison> findCycle(int upperCostBound, ArrayList<Livraison> livraisons, Noeud storehouse) {
		
		//TODO : what is that upperCostBound thing ?
		ArrayList<Integer> nodes = new ArrayList<Integer>();
		ArrayList<PlageHoraire> plages = new ArrayList<PlageHoraire>();
		Livraison storeHousePoint = new Livraison(null, storehouse,-1,-1);
		
		livraisons.add(0, storeHousePoint);
		// ---------------------- Storehouse
		/*
		// Check if the storehouse is already in the delivery list
		boolean storehouseInList = false;
		for(Livraison l : livraisons){
			if(l.getAdresse().getId() == storehouse.getId()){
				storehouseInList = true;
				storeHousePoint = l;
				break;
			}
		}
		// If not, add it
		if(!storehouseInList){
			storeHousePoint = new Livraison(null, storehouse,-1,-1);
			livraisons.add(storeHousePoint);
		}
		*/
		// --------------------- Initializing variables
		Map< PlageHoraire,ArrayList<Livraison>> livraisonByTimeWindow = new HashMap<PlageHoraire, ArrayList<Livraison>>(); 
		
		
		for(int i=1; i<livraisons.size();i++){	// do NOT use for in...
			Livraison l = livraisons.get(i);
			nodes.add(l.getAdresse().getId());
			
			PlageHoraire pl = l.getPlageHoraire();
			if(livraisonByTimeWindow.get(pl) == null){
				livraisonByTimeWindow.put(pl, new ArrayList<Livraison>());
			}
			livraisonByTimeWindow.get(pl).add(l);
			
			if(!plages.contains(pl)) plages.add(pl);
		}
		
		Collections.sort(plages,new Comparator<PlageHoraire>(){
            public int compare(PlageHoraire p1,PlageHoraire p2){
            	int[] boundsp1 = p1.getBounds();
            	int[] boundsp2 = p2.getBounds();
                  return ((Integer)(boundsp1[1])).compareTo((Integer)boundsp2[0]);
            }});
		//System.out.println(plages);
		nodes.clear();
		
		nodes.add(storehouse.getId());
		for(PlageHoraire pl : plages){
			ArrayList<Livraison> liv = livraisonByTimeWindow.get(pl);
			for(int i=0;i<liv.size();i++){
				nodes.add(liv.get(i).getAdresse().getId());
			}
		}
		int n = nodes.size();
		
		ShippmentGraph subG = graph.createTSPGraph(nodes, livraisonByTimeWindow, plages, storeHousePoint);
		
		// creating a sub graph with only the wanted nodes
		//ShippmentGraph subG = graph.createTSPGraph(nodes);
		
		// Variables
		
		int minCost = subG.getMinArcCost();
		int maxCost = subG.getMaxArcCost();
		int[][] cost = subG.getCost();
		ArrayList<Integer> next = new ArrayList<Integer>();
		ArrayList<Livraison> sortedList = new ArrayList<Livraison>();
		sortedList.ensureCapacity(n);
		Solver solver = new Solver();
		
		
		// ---------------------- Choco initialization
		
		// Create CP variables
		// xNext[i] = vertex visited after i
		IntVar[] xNext = new IntVar[n];
		IntVar[] xTime = new IntVar[n];
		for (int i = 0; i < n; i++) {
			xNext[i] = VariableFactory.enumerated("Next " + i, subG.getSucc(i), solver);
		}
		// xCost[i] = cost of arc (i,xNext[i])
		IntVar[] xCost = VariableFactory.boundedArray("Cost ", n, minCost, maxCost, solver);
		// xTotalCost = total cost of the solution
		IntVar xTotalCost = VariableFactory.bounded("Total cost ", n*minCost, upperCostBound - 1, solver);
		
		// Add constraints
		for (int i = 0; i < n; i++){
			//System.out.println("node: " + livraisons.get(i).getAdresse().getId() +" i: "+i+" cost[i]: "+Arrays.toString(cost[i]));
			solver.post(IntConstraintFactory.element(xCost[i], cost[i], xNext[i], 0, "none"));
		}
		solver.post(IntConstraintFactory.circuit(xNext,0));
		solver.post(IntConstraintFactory.sum(xCost, xTotalCost));
		
		
		
		// --------------------------- Choco Solve
		// limit CPU time
		SearchMonitorFactory.limitTime(solver,30000);
		// set the branching heuristic (branch on xNext only by selecting smallest domains first)
		solver.set(IntStrategyFactory.firstFail_InDomainMin(xNext));
		// try to find and prove the optimal solution
		solver.findOptimalSolution(ResolutionPolicy.MINIMIZE,xTotalCost);
		// record solution and state
		
		// ------------------------- Retrieve result
		
		if (solver.getMeasures().getSolutionCount() > 0) {
			int current = xNext[0].getValue();
			for(int i=0;i<n;i++) {
				sortedList.add(livraisons.get(xNext[current].getValue()));
				current = xNext[current].getValue();
				//int totalCost = xTotalCost.getValue();
				//System.out.println("r: " +xNext[i].getValue() + "ex: " + solver.getExplainer().retrieve(xNext[i], i));
			}
		}
		
		// ------------------------- Re-center the list
		
		// Re-center the list (put the storehouse at the beginning of the tournee
		ArrayList<Livraison> centeredList = new ArrayList<Livraison>();
		// find storehouse's index in cycle
		int indexStorehouse = sortedList.indexOf(storeHousePoint);
		// Re center list
		for(int i=indexStorehouse; i<indexStorehouse+n;i++){
			centeredList.add(sortedList.get(i%sortedList.size()));
		}
		
		// -------------------------
		
		return centeredList;
	}
	
	/**
	* It is really important not to mess up with the indexes in the following lists, as the Object concept is blown up in this method
	*/
	public ArrayList<Livraison> findCycleWithoutTimeWindows(int upperCostBound, ArrayList<Livraison> livraisons, Noeud storehouse) {
		// TODO : what is that upperCostBound thing ?
		ArrayList<Integer> nodes = new ArrayList<Integer>();
		ArrayList<PlageHoraire> plages = new ArrayList<PlageHoraire>();
		Livraison storeHousePoint = null;
		// Check if the storehouse is already in the delivery list
		boolean storehouseInList = false;
		for (Livraison l : livraisons) {
			if (l.getAdresse().getId() == storehouse.getId()) {
				storehouseInList = true;
				storeHousePoint = l;
				break;
			}
		}
		// If not, add it
		if (!storehouseInList) {
			storeHousePoint = new Livraison(null, storehouse, -1, -1);
			livraisons.add(storeHousePoint);
		}
		for (int i = 0; i < livraisons.size(); i++) { // do NOT use for in...
			Livraison l = livraisons.get(i);
			nodes.add(l.getAdresse().getId());
			plages.add(l.getPlageHoraire());
		}
		ShippmentGraph subG = graph.createTSPGrapWithoutTimeWindows(nodes);
		int n = nodes.size();
		int minCost = subG.getMinArcCost();
		int maxCost = subG.getMaxArcCost();
		int[][] cost = subG.getCost();
		ArrayList<Integer> next = new ArrayList<Integer>();
		ArrayList<Livraison> sortedList = new ArrayList<Livraison>();
		sortedList.ensureCapacity(n);
		Solver solver = new Solver();
		// Create variables
		// xNext[i] = vertex visited after i
		IntVar[] xNext = new IntVar[n];
		IntVar[] xTime = new IntVar[n];
		for (int i = 0; i < n; i++) {
			xNext[i] = VariableFactory.enumerated("Next " + i, subG.getSucc(i),
					solver);
			if (livraisons.get(i).getPlageHoraire() != null) { // storehouse
				int[] timeBounds = livraisons.get(i).getPlageHoraire()
						.getBounds();
				// add time variables and their boundaries
				xTime[i] = VariableFactory.bounded("Arriving time at " + i,
						timeBounds[0], timeBounds[1], solver);
			}
		}
		// xCost[i] = cost of arc (i,xNext[i])
		IntVar[] xCost = VariableFactory.boundedArray("Cost ", n, minCost,
				maxCost, solver);
		// xTotalCost = total cost of the solution
		IntVar xTotalCost = VariableFactory.bounded("Total cost ", n * minCost,
				upperCostBound - 1, solver);
		// Add constraints
		for (int i = 0; i < n; i++) {
			System.out.println("node: "
					+ livraisons.get(i).getAdresse().getId() + " i: " + i
					+ " cost[i]: " + Arrays.toString(cost[i]));
			solver.post(IntConstraintFactory.element(xCost[i], cost[i],
					xNext[i], 0, "none"));
			/*
			 * if(i != n-1){
			 * solver.post(IntConstraintFactory.distance(xTime[xNext
			 * [i].getValue()], xTime[xNext[i+1].getValue()],"=",xCost[i])); }
			 */
		}
		solver.post(IntConstraintFactory.circuit(xNext, 0));
		solver.post(IntConstraintFactory.sum(xCost, xTotalCost));
		// TODO : add time constraints
		// limit CPU time
		SearchMonitorFactory.limitTime(solver, 30000);
		// set the branching heuristic (branch on xNext only by selecting
		// smallest domains first)
		solver.set(IntStrategyFactory.firstFail_InDomainMin(xNext));
		// try to find and prove the optimal solution
		solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, xTotalCost);
		// record solution and state
		if (solver.getMeasures().getSolutionCount() > 0) {
			int current = xNext[0].getValue();
			for (int i = 0; i < n; i++) {
				sortedList.add(livraisons.get(xNext[current].getValue()));
				current = xNext[current].getValue();
				// int totalCost = xTotalCost.getValue();
				// System.out.println("r: " +xNext[i].getValue() + "ex: " +
				// solver.getExplainer().retrieve(xNext[i], i));
			}
		} else {
		}
		// Re-center the list (put the storehouse at the beginning of the
		// tournee
		ArrayList<Livraison> centeredList = new ArrayList<Livraison>();
		// find storehouse's index in cycle
		int indexStorehouse = sortedList.indexOf(storeHousePoint);
		// Re center list
		for (int i = indexStorehouse; i < indexStorehouse + n; i++) {
			centeredList.add(sortedList.get(i % sortedList.size()));
		}
		return centeredList;
	}
	
	private ArrayList<Integer> backtrack(int[] prev, int start, int end){
		ArrayList<Integer> reversedPath = new ArrayList<Integer>();
		while(end != start){
			reversedPath.add(end);
			end = prev[end];
		}
		reversedPath.add(start);
		return reversedPath;
	}
	
	
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
}
