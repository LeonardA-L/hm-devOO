package main;
import controller.Controller;
import tests.PlanTest;
import utils.ShippmentGraph;
import view.utils.Fenetre;
import view.utils.InterfaceView;
import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Plan;
import model.planning.InterfacePlanning;

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
		Plan plan = PlanTest.DummyPlanCreate();
		
		ShippmentGraph shGraph = plan.computeShippmentGraph();

		/*System.out.println(shGraph.getPaths().get("0-5"));
		System.out.println(shGraph.getPaths().get("3-1"));
		System.out.println(shGraph.getPaths().get("0-4"));
		System.out.println(shGraph.getPaths().get("0-0"));
		// display MAP
		System.out.println(plan.toString());*/
		
		Controller controller = Controller.getInstance();
		controller.setInterfaceAgglo(new InterfaceAgglo());
		controller.setInterfacePlanning(new InterfacePlanning());
		controller.setInterfaceView(new InterfaceView());
		
	}
}