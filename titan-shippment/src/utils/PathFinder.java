package utils;

import java.util.ArrayList;

public interface PathFinder {
	/**
	 * Finds the shortest path between two nodes, if any.
	 * @param start	the ID of the start node
	 * @param end the ID of the end node
	 * @return	an array of nodes IDs representing the shortest path from "start" to "end" 
	 */
	public ArrayList<Integer> findShortestPath(int start, int end);
	
	/**
	 * Will find the shortest cycle around the nodes given as input, if any. Uses the choco lib and the TSP algorithm
	 * @param upperCostBound unknow TODO (set to 10000 for now)
	 * @param nodes a list of the nodes IDs the cycle as to go through
	 * @return a list of nodes IDs for the shortest cycle
	 */
	public ArrayList<Integer> findCycle(int upperCostBound, ArrayList<Integer> nodes);
}
