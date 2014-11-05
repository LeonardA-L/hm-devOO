package main;
import tests.PlanTest;
import utils.ShippmentGraph;
import view.utils.Fenetre;
import model.agglomeration.Plan;

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
		int maxX = 500;
		int maxY = 400;
		Plan plan = PlanTest.DummyPlanCreate(nbNodes, maxX, maxY);
		
		ShippmentGraph shGraph = plan.computeShippmentGraph();
		
		// display MAP
		System.out.println(plan.toString());
		
		Fenetre fenetre = new Fenetre(plan);
		fenetre.setVisible(true);
		
	}
}