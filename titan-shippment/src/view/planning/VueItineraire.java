package view.planning;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import view.agglomeration.VueNoeud;
import view.agglomeration.VueTroncon;
import view.utils.Vue;

public class VueItineraire extends Vue {

	private VueNoeud depart;
	private VueNoeud arrivee;
	private ArrayList<VueTroncon> troncons;
	
	public VueItineraire() {
		depart = null;
		arrivee = null;
		troncons = new ArrayList<VueTroncon>();
	}
	
	public VueItineraire(VueNoeud depart, VueNoeud arrivee) {
		this.depart = depart;
		this.arrivee = arrivee;
		troncons = new ArrayList<VueTroncon>();
	}
	
	@Override
	protected void dessine(Graphics g) {
		
		// paint first node
		depart.dessine(g);
		
		Iterator<VueTroncon> it = troncons.iterator();
		while (it.hasNext()) {
			VueTroncon vue_troncon = it.next();
			vue_troncon.highlight();
			vue_troncon.dessine(g);
		}
		
		// paint last node
		arrivee.dessine(g);
	}
	
	public int addVueTroncon(VueTroncon troncon) {
		troncons.add(troncon);
		return troncons.size()-1;
	}
	
	public void removeVueTroncon(int index) {
		troncons.remove(index);
	}

	public VueNoeud getDepart() {
		return depart;
	}

	public void setDepart(VueNoeud depart) {
		this.depart = depart;
	}

	public ArrayList<VueTroncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(ArrayList<VueTroncon> troncons) {
		this.troncons = troncons;
	}

	public VueNoeud getArrivee() {
		return arrivee;
	}

	public void setArrivee(VueNoeud arrivee) {
		this.arrivee = arrivee;
	}

}
