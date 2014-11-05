/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aerholt
 */
public class BreadthFirstFinder implements PathFinder {

    Graph g;
    public final int NOT_VISITED = -1;

    public BreadthFirstFinder(Graph g) {
        this.g = g;
    }

    @Override
    public int[] findShortestPath(int start, int end) {

        if (start == end) {
            return new int[]{start, end};
        }

        //int[][] cost = g.getCost();
        int[] visited = new int[g.getNbVertices()];

        for (int i = 0; i < g.getNbVertices(); i++) {
            visited[i] = NOT_VISITED;
        }

        ArrayDeque<Integer> q = new ArrayDeque<Integer>();

        q.add(start);
        while (!q.isEmpty()) {
            int vertex = q.poll();
            int[] neighbours = g.getSucc(vertex);
            for (int n : neighbours) {
            	if (visited[n] == NOT_VISITED) {
                    q.add(n);
                    
                    visited[n] = vertex;
                    if (n == end) {
                        return backtrack(visited, end);
                    }
                }
            }
        }

        return null;
    }

    private int[] backtrack(int[] prev, int start) {
        ArrayList<Integer> l = new ArrayList<Integer>();
        int current = start;

        while (current != NOT_VISITED) {
            l.add(current);
            current = prev[current];
        }


        return reverse(toIntArray(l));
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
