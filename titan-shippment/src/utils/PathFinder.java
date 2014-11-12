package utils;

import java.util.ArrayList;

import model.agglomeration.Noeud;
import model.planning.Livraison;

public interface PathFinder {
	/**
	 * Finds the shortest path between two nodes, if any.
	 * <p>Note : Careful ! The last element of the array is the total cost of the path. Be sure to pop it</p>
	 * @param start	the ID of the start node
	 * @param end the ID of the end node
	 * @return	an array of nodes IDs representing the shortest path from "start" to "end", or null if no path available
	 */
	public ArrayList<Integer> findShortestPath(int start, int end);
	
	/**
	 * Will find the shortest cycle respecting time windows around the nodes given as input, if any. Uses the choco lib and the TSPTW algorithm
	 * @param upperCostBound unknow TODO (set to 100000 for now)
	 * @param livraisons a list of livraisons the cycle must fulfill
	 * @param storehouse the node of the storehouse
	 * @return a list of nodes IDs for the shortest cycle
	 */
	public ArrayList<Livraison> findCycle(int upperCostBound, ArrayList<Livraison> livraisons, Noeud storehouse);
	
	/**
	 * Will find the shortest cycle around the nodes given as input, if any. Uses the choco lib and the TSP algorithm
	 * @param upperCostBound unknow TODO (set to 100000 for now)
	 * @param livraisons a list of livraisons the cycle must fulfill
	 * @param storehouse the node of the storehouse
	 * @return a list of nodes IDs for the shortest cycle
	 */
	public ArrayList<Livraison> findCycleWithoutTimeWindows(int upperCostBound, ArrayList<Livraison> livraisons, Noeud storehouse);
}
