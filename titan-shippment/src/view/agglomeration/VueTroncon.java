package view.agglomeration;

import java.awt.Graphics;

import view.utils.Vue;
import model.agglomeration.Noeud;
import model.agglomeration.Troncon;

public class VueTroncon extends Vue {
	
	private Troncon troncon;
	
	public VueTroncon() {
		this.troncon = null;
	}
	
	public VueTroncon(Troncon troncon) {
		this.troncon = troncon;
	}
	
	public void dessine(Graphics g) {
		// cannot draw without node in
	}
	
	public void dessine(Graphics g, Noeud noeud) {
		super.paintComponent(g);
		g.drawLine(noeud.getCoordX(), noeud.getCoordY(), troncon.getNoeudDestination().getCoordX(), troncon.getNoeudDestination().getCoordY());
	}
	
	

}
