package view.agglomeration;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import view.utils.Vue;

public class VuePlan implements Vue {

	private Plan plan;

	private double ratioVueModele;
	private VueNoeud vueEntrepot;
	private ArrayList<VueNoeud> vueNoeuds;

	/**
	 * Constructor w/o parameter
	 */
	public VuePlan() {
		this.plan = null;
		this.vueEntrepot = null;
		this.vueNoeuds = new ArrayList<VueNoeud>();
	}

	/**
	 * Find which node was clicked (if any)
	 * @param x		x coordinate of click
	 * @param y		y coordinate of click
	 * @return		VueNoeud or null if no node fits the coordinates
	 */
	public VueNoeud getWhoIsClicked(int x, int y) {
		if (vueEntrepot != null && vueEntrepot.estCliquee(x, y)) {
			return vueEntrepot;
		}
		Iterator<VueNoeud> it = vueNoeuds.iterator();
		while (it.hasNext()) {
			VueNoeud vue_noeud = it.next();
			if (vue_noeud.estCliquee(x, y)) {
				return vue_noeud;
			}
		}
		return null;
	}

	/**
	 * Draw all node of map
	 */
	public void dessine(Graphics g) {
		// si le plan est charg�
		if (plan != null) {
			Iterator<VueNoeud> it = vueNoeuds.iterator();
			while (it.hasNext()) {
				VueNoeud vue_noeud = it.next();
				vue_noeud.dessine(g);
			}
		}
	}

	/**
	 * Reset the view of map
	 */
	public void reset() {
		vueEntrepot = null;
		vueNoeuds.clear();
	}

	/**
	 * Get a VueTroncon knowing 2 VueNoeud 
	 * @param n1		VueNoeud 1
	 * @param n2		VueNoeud2
	 * @return			VueTroncon
	 */
	public VueTroncon getVueTronconByVueNodes(VueNoeud n1, VueNoeud n2) {
		Iterator<VueNoeud> it = vueNoeuds.iterator();
		while (it.hasNext()) {

			if (it.next().getNoeud().getId() == n1.getNoeud().getId()) {
				Iterator<VueTroncon> it_vues_troncons = it.next().getVues_troncons().iterator();
				while (it_vues_troncons.hasNext()) {
					VueTroncon vue_troncon = it_vues_troncons.next();
					if (vue_troncon.getTroncon().getNoeudDestination().getId() == n2.getNoeud().getId()) {
						return vue_troncon;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get a VueNoeud knowing the id of the node
	 * @param id   Id of node 
	 * @return	VueNoeud or null if id is incorrect
	 */
	public VueNoeud getVueNoeudById(int id) {
		Iterator<VueNoeud> it = vueNoeuds.iterator();
		while (it.hasNext()) {
			VueNoeud noeud = it.next();
			if (noeud.getNoeud().getId() == id) {
				return noeud;
			}
		}
		return null;
	}

	// ------------------------------------------------------------------
	// GETTERS - SETTERS
	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;

		Iterator<Noeud> it = plan.getNoeuds().iterator();
		while (it.hasNext()) {
			this.vueNoeuds.add(new VueNoeud(it.next()));
		}
	}

	public double getRatioVueModele() {
		return ratioVueModele;
	}

	public void setRatioVueModele(double ratioVueModele) {
		this.ratioVueModele = ratioVueModele;
	}

	public VueNoeud getVueEntrepot() {
		return vueEntrepot;
	}

	public void setVueEntrepot(VueNoeud vueEntrepot) {
		this.vueEntrepot = vueEntrepot;
	}

	public ArrayList<VueNoeud> getVueNoeuds() {
		return vueNoeuds;
	}

	public void setVueNoeuds(ArrayList<VueNoeud> vueNoeuds) {
		this.vueNoeuds = vueNoeuds;
	}

}
