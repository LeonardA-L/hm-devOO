package tests;

import java.util.ArrayList;

import utils.DijkstraFinder;
import utils.PathFinder;
import utils.ShippmentGraph;
import controller.Controller;
import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import model.agglomeration.Troncon;
import model.planning.Itineraire;
import model.planning.Livraison;
import model.planning.PlageHoraire;
import model.planning.Tournee;

public class UtilsTest {

	public static Tournee DummyTourneeCreate(Plan plan,
			ArrayList<Livraison> livraisons, Noeud entrepot) {
		// Instanciate pathfinder
		PathFinder pf = new DijkstraFinder(plan.computeShippmentGraph());
		ShippmentGraph shGraph = (ShippmentGraph) ((DijkstraFinder) pf)
				.getGraph();
		// Compute cycle (sorted list of livraison)
		ArrayList<Livraison> cycle = pf.findCycle(100000, livraisons, entrepot);

		// Finding itineraires
		ArrayList<Itineraire> itineraires = new ArrayList<Itineraire>();
		for (int i = 0; i < cycle.size() - 1; i++) { // No for in !
			Livraison l1 = cycle.get(i);
			Livraison l2 = cycle.get(i + 1);
			Itineraire it = findItineraire(l1, l2, plan, shGraph);
			itineraires.add(it);
		}
		// Loop the cycle
		Livraison l1 = cycle.get(cycle.size() - 1);
		Livraison l2 = cycle.get(0);
		Itineraire it = findItineraire(l1, l2, plan, shGraph);
		itineraires.add(it);

		// Prepare the tournee
		Tournee tournee = new Tournee();
		tournee.setLivraisons(cycle);
		tournee.setItineraires(itineraires);

		return tournee;
	}

	public static Livraison DummyDeliveryCreate(Plan plan) {
		int nodesNumber = plan.getNoeuds().size();
		return new Livraison(new PlageHoraire("12:00:00", "14:00:00"), plan
				.getNoeuds().get((int) (Math.random() * nodesNumber)),
				(int) (Math.random() * 1000),
				(int) (Math.random() * nodesNumber));
	}

	public static ArrayList<Livraison> DummyDeliveriesListCreate(Plan plan,
			int number) {
		ArrayList<Livraison> list = new ArrayList<Livraison>();
		for (int i = 0; i < number; ++i) {
			list.add(UtilsTest.DummyDeliveryCreate(plan));
		}
		return list;
	}

	public static Plan DummyPlanCreate(int nbNodes, int maxX, int maxY) {
		// creating map
		String[] rues = { "Avenue des titans", "Rue de L�onard", "Super rue",
				"Anton Long Avenue", "GIGA AVENUE", "Persistence street" };

		// Noeud entrepot = new Noeud(20,30,-1, new ArrayList<Troncon>());
		Plan plan = new Plan(new ArrayList<Noeud>());

		// generate random nodes
		for (int i = 0; i < nbNodes; ++i) {
			int x = (int) Math.floor(Math.random() * maxX);
			int y = (int) Math.floor(Math.random() * maxY);
			int id = i;
			Noeud noeud = new Noeud(x, y, id, new ArrayList<Troncon>());
			plan.addNoeud(noeud);
		}

		// generate random troncons
		int nbTroncons = 10;
		for (int i = 0; i < nbTroncons; ++i) {
			int nodeInIndex = (int) Math.floor(Math.random() * nbNodes);
			int nodeOutIndex = (int) Math.floor(Math.random() * nbNodes);

			// no boucle
			if (nodeInIndex == nodeOutIndex) {
				continue;
			}

			Noeud nodeIn = plan.getNoeudById(nodeInIndex);
			Noeud nodeOut = plan.getNoeudById(nodeOutIndex);

			int vitesse = (int) Math.floor(Math.random() * 50);
			int longueur = (int) Math.floor(Math.random() * 30) + 1;
			String rue = rues[(int) Math.floor(Math.random() * rues.length)];

			double doubleSens = Math.random();

			Troncon troncon1 = new Troncon(rue, vitesse, longueur, nodeOut);
			nodeIn.addTroncon(troncon1);

			if (doubleSens < 0.5) {
				Troncon troncon2 = new Troncon(rue, vitesse, longueur, nodeIn);
				nodeOut.addTroncon(troncon2);
			}
		}
		return plan;
	}

	private static Itineraire findItineraire(Livraison l1, Livraison l2,
			Plan plan, ShippmentGraph shGraph) {
		Itineraire it = new Itineraire(l1.getAdresse(), l2.getAdresse(),
				new ArrayList<Troncon>());
		// Compute list of troncons
		String pathHash = "" + l1.getAdresse().getId() + "-"
				+ l2.getAdresse().getId();
		it.computeTronconsFromNodes(plan, shGraph.getPaths().get(pathHash));
		return it;
	}
}
