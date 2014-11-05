package view.agglomeration;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import view.utils.Vue;
import model.agglomeration.Noeud;
import model.agglomeration.Troncon;

public class VueNoeud extends Vue {
	
	// pixel
	private final int TOLERANCE_CLICK = 10;
	
	private Noeud noeud;
	private ArrayList<VueTroncon> vues_troncons;
	
	private Color color;
	private Graphics graphics_plan;
	
	public VueNoeud() {
		this.noeud = null;
		this.vues_troncons = new ArrayList<VueTroncon>();
		this.color = Color.BLACK;
	}
	
	public VueNoeud(Noeud noeud) {
		this.noeud = noeud;
		this.vues_troncons = new ArrayList<VueTroncon>();
		this.color = Color.BLACK;
		
		Iterator<Troncon> it = noeud.getTroncons().iterator();
		while (it.hasNext()) {
			this.vues_troncons.add(new VueTroncon(it.next()));
		}
	}
	
	public void dessine(Graphics g) {
		int x = noeud.getCoordX();
		int y = noeud.getCoordY();
		g.setColor(this.color);
		g.drawLine(x-5, y, x+5, y);
		g.drawLine(x, y-5, x, y+5);
		g.setColor(Color.BLACK);
		
		Iterator<VueTroncon> it = vues_troncons.iterator();
		
		while (it.hasNext()) {
			VueTroncon vue_troncon = it.next();
			vue_troncon.dessine(g, noeud);
		}
	}

	public boolean estCliquee(int x, int y) {
		int noeudX = this.noeud.getCoordX();
		int noeudY = this.noeud.getCoordY();
		if (x >= noeudX - TOLERANCE_CLICK && x <= noeudX + TOLERANCE_CLICK && y >= noeudY - TOLERANCE_CLICK && y <= noeudY + TOLERANCE_CLICK) {
			return true;
		}
		return false;
	}

	public Noeud getNoeud() {
		return this.noeud;
	}

	public void highlight() {
		this.setColor(Color.RED);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
