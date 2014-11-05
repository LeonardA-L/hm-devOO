package view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import model.agglomeration.Noeud;
import model.agglomeration.Troncon;

public class VueNoeud extends JPanel {
	
	// pixel
	private final int TOLERANCE_CLICK = 10;
	
	private Noeud noeud;
	private ArrayList<VueTroncon> vues_troncons;
	
	public VueNoeud(Noeud noeud) {
		this.noeud = noeud;
		this.vues_troncons = new ArrayList<VueTroncon>();
		
		Iterator<Troncon> it = noeud.getTroncons().iterator();
		while (it.hasNext()) {
			this.vues_troncons.add(new VueTroncon(it.next()));
		}
	}
	
	public void dessine(Graphics g) {
		paintComponent(g);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		int x = noeud.getCoordX();
		int y = noeud.getCoordY();
		g.drawLine(x-5, y, x+5, y);
		g.drawLine(x, y-5, x, y+5);
		
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

}
