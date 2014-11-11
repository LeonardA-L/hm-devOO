package utils;

import java.util.ArrayList;
import java.util.Collections;

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
	 * It is really important not to mess up with the indexes in the following lists, as the Object concept is blown up in this method
	 */
	public ArrayList<Livraison> findCycle(int upperCostBound, ArrayList<Livraison> livraisons, Noeud storehouse) {
		//TODO : what is that upperCostBound thing ?
		ArrayList<Integer> nodes = new ArrayList<Integer>();
		ArrayList<PlageHoraire> plages = new ArrayList<PlageHoraire>();
		
		// Check if the storehouse is already in the delivery list
		boolean storehouseInList = false;
		for(Livraison l : livraisons){
			if(l.getAdresse() == storehouse){
				storehouseInList = true;
				break;
			}
		}
		// If not, add it
		if(!storehouseInList){
			Livraison storeHousePoint = new Livraison(null, storehouse,-1,-1);
			livraisons.add(storeHousePoint);
		}
		
		for(int i=0; i<livraisons.size();i++){	// do NOT use for in...
			Livraison l = livraisons.get(i);
			nodes.add(l.getAdresse().getId());
			plages.add(l.getPlageHoraire());
		}
		ShippmentGraph subG = graph.createTSPGraph(nodes);
		
		int n = nodes.size();
		int minCost = subG.getMinArcCost();
		int maxCost = subG.getMaxArcCost();
		int[][] cost = subG.getCost();
		ArrayList<Integer> next = new ArrayList<Integer>();
		ArrayList<Livraison> sortedList = new ArrayList<Livraison>();
		Solver solver = new Solver();
		
		
		
		// Create variables
		// xNext[i] = vertex visited after i
		IntVar[] xNext = new IntVar[n];
		IntVar[] xTime = new IntVar[n];
		for (int i = 0; i < n; i++){
			xNext[i] = VariableFactory.enumerated("Next " + i, subG.getSucc(i), solver);
			if(livraisons.get(i).getPlageHoraire() != null){	// storehouse
				int[] timeBounds = livraisons.get(i).getPlageHoraire().getBounds();
				System.out.println(timeBounds[1]);
				// add time variables and their boundaries
				xTime[i] = VariableFactory.bounded("Arriving time at "+i, timeBounds[0], timeBounds[1], solver);
			}
		}
		// xCost[i] = cost of arc (i,xNext[i])
		IntVar[] xCost = VariableFactory.boundedArray("Cost ", n, minCost, maxCost, solver);
		// xTotalCost = total cost of the solution
		IntVar xTotalCost = VariableFactory.bounded("Total cost ", n*minCost, upperCostBound - 1, solver);
		
		// Add constraints
		for (int i = 0; i < n; i++){
			solver.post(IntConstraintFactory.element(xCost[i], cost[i], xNext[i], 0, "none"));
			/*
			if(i != n-1){
			solver.post(IntConstraintFactory.distance(xTime[xNext[i].getValue()], xTime[xNext[i+1].getValue()],"=",xCost[i]));
			}
			*/
		}
		solver.post(IntConstraintFactory.circuit(xNext,0));
		solver.post(IntConstraintFactory.sum(xCost, xTotalCost));
		
		//TODO : add time constraints
		
		// limit CPU time
		SearchMonitorFactory.limitTime(solver,30000);
		// set the branching heuristic (branch on xNext only by selecting smallest domains first)
		solver.set(IntStrategyFactory.firstFail_InDomainMin(xNext));
		// try to find and prove the optimal solution
		solver.findOptimalSolution(ResolutionPolicy.MINIMIZE,xTotalCost);
		// record solution and state
		if(solver.getMeasures().getSolutionCount()>0){
			for(int i=0;i<n;i++) sortedList.add(livraisons.get(xNext[i].getValue()));
			//int totalCost = xTotalCost.getValue();
			
		}
		else {
			
		}
		
		return sortedList;
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
