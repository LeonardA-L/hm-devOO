package utils;

import solver.ResolutionPolicy;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.loop.monitors.SearchMonitorFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

public class DijkstraFinder implements PathFinder {
	Graph g;
	
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
		/*TODO
		for (Vertex v : g.getVertices()) {
			...
		}*/
		
		
		
		return next;
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
}
