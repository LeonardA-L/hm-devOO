package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import solver.ResolutionPolicy;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.loop.monitors.SearchMonitorFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

public class DijkstraFinder implements PathFinder {
	Graph g;
	
	public static final int UNDEFINED_NODE = -1;
	
	public DijkstraFinder(Graph g) {
		this.g = g;
	}
	
	@Override
	public int[] findShortestPath(int start, int end) {
		int n = g.getNbVertices();
		int minCost = g.getMinArcCost();
		int maxCost = g.getMaxArcCost();
		int[][] cost = g.getCost();
		int[] next = new int[n];
		
		boolean[] visited = new boolean[n];
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
			int u = Q.get(bestIndex);
			Q.remove(bestIndex);
			
			int[] neighbours = g.getSucc(u);
			for(int v : neighbours){
				int alt = dist[u] + cost[u][v];
				if(alt < dist[v]){
					dist[v] = alt;
					prev[v] = u;
				}
			}
		}
		
		List<Integer> reversedPath = backtrack(prev, start, end);
		Collections.reverse(reversedPath);
		
		return toIntArray(reversedPath);
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

	public int[] findCycle(int start, int end, int upperCostBound) {
		//TODO : not functional
		int n = g.getNbVertices();
		int minCost = g.getMinArcCost();
		int maxCost = g.getMaxArcCost();
		int[][] cost = g.getCost();
		int[] next = new int[n];
		Solver solver = new Solver();
		
		// Create variables
		// xNext[i] = vertex visited after i
		IntVar[] xNext = new IntVar[n];
		for (int i = 0; i < n; i++)
			xNext[i] = VariableFactory.enumerated("Next " + i, g.getSucc(i), solver);
		// xCost[i] = cost of arc (i,xNext[i])
		IntVar[] xCost = VariableFactory.boundedArray("Cost ", n, minCost, maxCost, solver);
		// xTotalCost = total cost of the solution
		IntVar xTotalCost = VariableFactory.bounded("Total cost ", n*minCost, upperCostBound - 1, solver);
		
		// Add constraints
		for (int i = 0; i < n; i++) 
			solver.post(IntConstraintFactory.element(xCost[i], cost[i], xNext[i], 0, "none"));
		//solver.post(IntConstraintFactory.circuit(xNext,0));
		solver.post(IntConstraintFactory.sum(xCost, xTotalCost));
		
		// limit CPU time
		SearchMonitorFactory.limitTime(solver,10000);
		// set the branching heuristic (branch on xNext only by selecting smallest domains first)
		solver.set(IntStrategyFactory.firstFail_InDomainMin(xNext));
		// try to find and prove the optimal solution
		solver.findOptimalSolution(ResolutionPolicy.MINIMIZE,xTotalCost);
		// record solution and state
		if(solver.getMeasures().getSolutionCount()>0){
			for(int i=0;i<n;i++) next[i] = xNext[i].getValue();
			int totalCost = xTotalCost.getValue();
		}
		else {
			
		}
		
		return next;
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
	
	private int[] toIntArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }
	
	private int[] reverse(int[] a) {
        for (int i = 0; i < a.length/2; i++) {
            // Swap
            int tmp = a[i];
            a[i] = a[a.length-1];
            a[a.length-1] = tmp;
        }
        return a;
    }
}
