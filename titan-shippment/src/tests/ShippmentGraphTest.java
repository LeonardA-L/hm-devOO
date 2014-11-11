package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import utils.Graph;

public class ShippmentGraphTest {
	
	@Test
	public void testGetSucc() {
		Graph g = PlanTest.DummyPlanCreate().computeShippmentGraph();
		int[] expected = new int[]{1, 2};
		
		int[] actual = g.getSucc(0);
		Arrays.sort(actual);
		
		for (int i = 0; i < actual.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
		
		actual = g.getSucc(4);
		assertEquals(5, actual[0]);
	}
	
	/**
	 * Checks that each arc of <code>RegularGraph(nbVertices,degree,minArcCost,maxArcCost)</code> 
	 * has a cost ranging between<code>minArcCost</code> and <code>maxArcCost</code>
	 */
	@Test
	public void testArcCost(){
		int nbVertices = 20;
		int minArcCost = 1;
		int maxArcCost = 10;
		Graph g = UtilsTest.DummyPlanCreate(nbVertices, 100, 100).computeShippmentGraph();
		for (int i=0; i<g.getNbVertices(); i++){
			int[] succ = g.getSucc(i);
			for (int j=0; j<g.getNbSucc(i); j++){
				assertTrue(g.getCost()[i][succ[j]] >= minArcCost);
				assertTrue(g.getCost()[i][succ[j]] <= maxArcCost);
			}
		}
	}
	
	/**
	 * Checks that for each couple of vertices <code>(i,j)</code> of <code>RegularGraph(nbVertices,degree,minArcCost,maxArcCost)</code> 
	 * such that <code>(i,j)</code> is not an arc, <code>getCost()[i][j]</code> returns <code>getMaxArcCost()+1</code>
	 */
	@Test
	public void testNonArcCost(){
		int nbVertices = 20;
		Graph g = UtilsTest.DummyPlanCreate(nbVertices, 100, 100).computeShippmentGraph();
		for (int i=0; i<g.getNbVertices(); i++){
			for (int j=0; j<g.getNbVertices(); j++){
				int[] succ = g.getSucc(i);
				int k = 0;
				while (k<g.getNbSucc(i) && succ[k] != j)
					k++;
				if (k == g.getNbSucc(i))
					assertEquals(g.getCost()[i][j],g.getMaxArcCost()+1);
			}
		}
	}
}
