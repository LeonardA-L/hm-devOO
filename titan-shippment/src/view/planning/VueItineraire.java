package view.planning;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import model.agglomeration.Noeud;
import view.agglomeration.VueNoeud;
import view.agglomeration.VueTroncon;
import view.utils.Vue;

public class VueItineraire implements Vue {

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
	public void dessine(Graphics g) {

		Noeud current_node = depart.getNoeud();
		depart.dessine(g);

		int x = this.depart.getNoeud().getCoordX();
		int y = this.depart.getNoeud().getCoordY();

		final int LIMITE_MIN = 50 % 255;
		final int LIMITE_MAX = 200 % 255;
		final int MODULO = LIMITE_MAX - LIMITE_MIN + 1;

		Color color = new Color(LIMITE_MIN + x % MODULO, LIMITE_MIN + y % MODULO, LIMITE_MIN + (x + y) % MODULO);

		Iterator<VueTroncon> it = troncons.iterator();
		while (it.hasNext()) {
			VueTroncon vue_troncon = it.next();
			vue_troncon.highlight(color);
			vue_troncon.dessine(g, current_node);
			current_node = vue_troncon.getTroncon().getNoeudDestination();
		}

		// paint last node
		arrivee.dessine(g);
	}

	public int addVueTroncon(VueTroncon troncon) {
		troncons.add(troncon);
		return troncons.size() - 1;
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
