package view.planning;

import java.awt.Color;
import java.awt.Graphics;

import model.planning.Livraison;
import view.agglomeration.VueNoeud;
import view.utils.Vue;

public class VueLivraison implements Vue {
	
	private Livraison livraison;
	private VueNoeud noeud;
	
	public VueLivraison() {
		setLivraison(null);
		setNoeud(null);
	}
	
	public VueLivraison(Livraison livraison, VueNoeud noeud) {
		this.setLivraison(livraison);
		this.noeud = noeud;
	}

	@Override
	public void dessine(Graphics g) {
		// TODO Auto-generated method stub
		noeud.highlight();
		noeud.dessine(g);
	}
	
	public void dessine(Graphics g, Color color) {
		// TODO Auto-generated method stub
		color = (livraison.isDelayed()) ? Color.RED : color;
		noeud.highlight(color);
		noeud.dessine(g);
	}
	
	public void reset() {
		noeud.unhighlight();
	}

	public VueNoeud getNoeud() {
		return noeud;
	}

	public void setNoeud(VueNoeud noeud) {
		this.noeud = noeud;
	}

	public Livraison getLivraison() {
		return livraison;
	}

	public void setLivraison(Livraison livraison) {
		this.livraison = livraison;
	}

}
