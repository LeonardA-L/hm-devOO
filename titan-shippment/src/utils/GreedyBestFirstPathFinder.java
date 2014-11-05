package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

import model.agglomeration.Noeud;

public class GreedyBestFirstPathFinder implements PathFinder {

	Graph g;
	
	class Vertex {
		private final int id;
		
		Vertex(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public ArrayList<Vertex> getNeighbours() {
			return null;
		}
	}
	
	public GreedyBestFirstPathFinder(Graph g) {
		this.g = g;
	}
	
	
	private double heuristicEval(int from, int to) {
		return Math.sqrt(Math.pow(from, 2) + Math.pow(to, 2));
	}
	
	/**
	 *	@return the path 
	 **/
	@Override
	public ArrayList<Integer> findShortestPath(int start, int end) {
		
		boolean[] visited = new boolean[g.getNbVertices()];
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(g.getNbVertices());
		
		visited[start] = true;
		q.add(new Vertex(start));
		
		while (q.size() > 1) {
			Vertex v = q.poll();
			
			Collection<Vertex> neighbours =  v.getNeighbours();
			for (Vertex neighbour : neighbours) {
				if (!visited[neighbour.getId()]) {
					
				}
			}
		}
		
		
		return null;
		
	}

}
