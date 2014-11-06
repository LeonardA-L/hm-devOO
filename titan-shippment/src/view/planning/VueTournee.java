package view.planning;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import view.utils.Vue;

public class VueTournee extends Vue {
	
	private ArrayList<VueLivraison> livraisons;
	private ArrayList<VueItineraire> itineraires;
	
	public VueTournee() {
		livraisons = new ArrayList<VueLivraison>();
		itineraires = new ArrayList<VueItineraire>();
	}

	@Override
	protected void dessine(Graphics g) {
		// reset
		super.paintComponent(g);
		
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

}
