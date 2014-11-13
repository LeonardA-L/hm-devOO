package utils;

import java.util.ArrayList;
import java.util.Map;

import model.planning.Livraison;
import model.planning.PlageHoraire;

/**
 * @author Christine Solnon
 *
 */
public interface Graph {

	/**
	 * @return the maximal cost of an arc of <code>this</code>
	 */
	public abstract int getMaxArcCost();

	/**
	 * @return the minimal cost of an arc of <code>this</code>
	 */
	public abstract int getMinArcCost();

	/**
	 * @return the number of vertices of <code>this</code>
	 */
	public abstract int getNbVertices();

	/**
	 * @return the <code>cost</code> matrix of <code>this</code>: for all
	 *         vertices <code>i</code> and <code>j</code>, if <code>(i,j)</code>
	 *         is an arc of <code>this</code>, then <code>cost[i][j]</code> =
	 *         cost of <code>(i,j)</code>, otherwise
	 *         <code>cost[i][j] = this.getMaxArcCost()+1</code>
	 */
	public abstract int[][] getCost();

	/**
	 * @param i
	 *            a vertex such that <code>0 <= i < this.getNbVertices()</code>
	 * @return an array containing all successor vertices of <code>i</code> in
	 *         <code>this</code>
	 * @throws ArrayIndexOutOfBoundsException
	 *             If <code>i<0</code> or <code>i>=this.getNbVertices()</code>
	 */
	public abstract int[] getSucc(int i) throws ArrayIndexOutOfBoundsException;

	/**
	 * @param i
	 *            a vertex such that <code>0 <= i < this.getNbVertices()</code>
	 * @return the number of successor vertices of <code>i</code> in
	 *         <code>this</code>
	 * @throws ArrayIndexOutOfBoundsException
	 *             If <code>i<0</code> or <code>i>=this.getNbVertices()</code>
	 */
	public abstract int getNbSucc(int i) throws ArrayIndexOutOfBoundsException;

	/**
	 * Creates a sub graph meeting TSPTW's requirements
	 * 
	 * @param nodes
	 *            A list of node ids for the desired cycle
	 * @param livraisonByTimeWindow
	 *            the list of Livraison objects ordered by PlageHoraire
	 * @param sortedPlages
	 *            a time sorted list of PlageHoraire objects
	 * @param storeHousePoint
	 *            the storehouse
	 * @return a sub graph that can be piped into choco
	 */
	public ShippmentGraph createTSPGraph(ArrayList<Integer> nodes,
			Map<PlageHoraire, ArrayList<Livraison>> livraisonByTimeWindow,
			ArrayList<PlageHoraire> sortedPlages, Livraison storeHousePoint);

	/**
	 * Creates a sub graph meeting TSP's requirements
	 * 
	 * @param nodes
	 *            A list of node ids for the desired cycle
	 * @return a sub graph that can be piped into choco
	 */
	public ShippmentGraph createTSPGrapWithoutTimeWindows(
			ArrayList<Integer> nodes);
}