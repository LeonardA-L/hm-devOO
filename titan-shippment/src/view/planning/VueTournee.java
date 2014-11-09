package view.planning;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import controller.Controller;
import model.agglomeration.Troncon;
import model.planning.Itineraire;
import model.planning.Tournee;
import view.agglomeration.VueNoeud;
import view.agglomeration.VuePlan;
import view.agglomeration.VueTroncon;
import view.utils.Vue;

public class VueTournee implements Vue {
	
	private Tournee tournee;
	private ArrayList<VueLivraison> livraisons;
	private ArrayList<VueItineraire> itineraires;
	
	public VueTournee() {
		tournee = null;
		livraisons = new ArrayList<VueLivraison>();
		itineraires = new ArrayList<VueItineraire>();
	}

	@Override
	public void dessine(Graphics g) {		
		Iterator<VueItineraire> it = itineraires.iterator();
		while (it.hasNext()) {
			it.next().dessine(g);
		}
	}

	public ArrayList<VueLivraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(ArrayList<VueLivraison> livraisons) {
		this.livraisons = livraisons;
	}

	public void reset() {
		livraisons.clear();
		itineraires.clear();
	}

	public Tournee getTournee() {
		return tournee;
	}

	public boolean setTournee(Tournee tournee) {
		this.tournee = tournee;
		
		// create all views
		Iterator<Itineraire> it = tournee.getItineraires().iterator();
		while(it.hasNext()) {
			Itineraire itineraire = it.next();
			VuePlan vue_plan = Controller.getInstance().getInterfaceView().getVuePanel().getVue_plan();
			VueNoeud depart = vue_plan.getVueNoeudById(itineraire.getDepart().getId());
			VueNoeud arrivee = vue_plan.getVueNoeudById(itineraire.getArrivee().getId());
			
			if (depart == null || arrivee == null) {
				this.reset();
				return false;
			}
			
			VueItineraire vue_itineraire = new VueItineraire(depart, arrivee);
			
			Iterator<Troncon> it_troncon = itineraire.getTroncons().iterator();
			while (it_troncon.hasNext()) {
				VueTroncon vue_troncon = new VueTroncon(it_troncon.next());
				vue_troncon.highlight();
				vue_itineraire.addVueTroncon(vue_troncon);
			}
			
			itineraires.add(vue_itineraire);
		}
		return true;
	}

}
