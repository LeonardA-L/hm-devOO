package main;

import java.util.ArrayList;

import tests.PlanTest;
import utils.ShippmentGraph;
import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import model.agglomeration.Troncon;

public class Main {


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main m = new Main();
		m.go();
	}
	
	public void go() {
		int nbNodes = 20;
		int maxX = 100;
		int maxY = 100;
		Plan plan = PlanTest.DummyPlanCreate(nbNodes, maxX, maxY);
		
		ShippmentGraph shGraph = plan.computeShippmentGraph();
		
		// display MAP
		System.out.println(plan.toString());
		
	}
}
