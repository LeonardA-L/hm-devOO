package utils;

import java.util.ArrayList;

public interface PathFinder {
	public ArrayList<Integer> findShortestPath(int start, int end);
	public ArrayList<Integer> findCycle(int upperCostBound, ArrayList<Integer> nodes);
}
