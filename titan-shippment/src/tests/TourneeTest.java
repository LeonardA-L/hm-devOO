package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.agglomeration.Plan;
import model.planning.Livraison;
import model.planning.Tournee;

import org.junit.Before;
import org.junit.Test;

public class TourneeTest {
	
	private Plan plan;
	private Tournee tournee;
	private ArrayList<Livraison> livraisons;
	
	@Before
	public void create() {
		int nbNodes = 20;
		int maxX = 100;
		int maxY = 100;
		plan = UtilsTest.DummyPlanCreate(nbNodes, maxX, maxY);
		livraisons = UtilsTest.DummyDeliveriesListCreate(plan, 5);
		tournee = new Tournee();
	}
	
	@Test
	public void test() {
		
	}

}
