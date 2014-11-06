package view.planning;

import java.awt.Graphics;

import model.planning.Livraison;
import view.agglomeration.VueNoeud;
import view.utils.Vue;

public class VueLivraison extends Vue {
	
	private Livraison livraison;
	private VueNoeud noeud;
	
	public VueLivraison() {
		livraison = null;
		setNoeud(null);
	}
	
	public VueLivraison(Livraison livraison, VueNoeud noeud) {
		this.livraison = livraison;
		this.noeud = noeud;
	}

	@Override
	protected void dessine(Graphics g) {
		// TODO Auto-generated method stub
		noeud.highlight();
		noeud.dessine(g);
	}

	public VueNoeud getNoeud() {
		return noeud;
	}

	public void setNoeud(VueNoeud noeud) {
		this.noeud = noeud;
	}

}
