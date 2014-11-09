package view.agglomeration;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import view.utils.Type_i;
import view.utils.Vue;
import model.agglomeration.Noeud;
import model.agglomeration.Troncon;

public class VueNoeud implements Vue, Type_i {
	
	// pixel
	private final int TOLERANCE_CLICK = 10;
	
	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	private Noeud noeud;
	private ArrayList<VueTroncon> vues_troncons;
	
	private Color color;
	private Type type;
	private int taille;

	public VueNoeud() {
		this.noeud = null;
		this.vues_troncons = new ArrayList<VueTroncon>();
		this.color = Color.BLACK;
		this.type = Type.CERCLE;
		this.taille = 5;
	}
	
	public VueNoeud(Noeud noeud) {
		this.noeud = noeud;
		this.vues_troncons = new ArrayList<VueTroncon>();
		this.color = Color.BLACK;
		this.type = Type.CERCLE;
		this.taille = 5;
		
		Iterator<Troncon> it = noeud.getTroncons().iterator();
		while (it.hasNext()) {
			this.vues_troncons.add(new VueTroncon(it.next()));
		}
	}
	
	public void dessine(Graphics g) {
		if (noeud != null) {
			int x = noeud.getCoordX();
			int y = noeud.getCoordY();

			g.setColor(this.color);
			if (this.type == Type.CERCLE) {
				g.drawOval(x-taille/2, y-taille/2, taille, taille);
			}
			else if (this.type == Type.CERCLE_PLEIN) {
				g.fillOval(x-taille/2, y-taille/2, taille, taille);
			}
			else if (this.type == Type.CARRE) {
				g.drawRect(x-taille/2, y-taille/2, taille, taille);
				/*g.drawLine(x-taille/2, y-taille/2, x+taille/2, y-taille/2); // haut
				g.drawLine(x-taille/2, y+taille/2, x+taille/2, y+taille/2); // bas
				g.drawLine(x-taille/2, y-taille/2, x-taille/2, y+taille/2); // gauche
				g.drawLine(x+taille/2, y-taille/2, x+taille/2, y+taille/2);*/ // droite
			}
			else if (this.type == Type.CARRE_PLEIN) {
				g.fillRect(x-taille/2, y-taille/2, taille, taille);
			}
			else {
				// croix
				g.drawLine(x-taille/2, y, x+taille/2, y);
				g.drawLine(x, y-taille/2, x, y+taille/2);
			}
			g.setColor(Color.BLACK);
			
			Iterator<VueTroncon> it = vues_troncons.iterator();
			while (it.hasNext()) {
				VueTroncon vue_troncon = it.next();
				vue_troncon.dessine(g, noeud);
			}
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
		this.setType(Type.CARRE_PLEIN);
		this.setTaille(10);
	}
	
	public void highlight(Color color) {
		this.setColor(color);
		this.setType(Type.CARRE_PLEIN);
		this.setTaille(10);
	}
	
	public void highlight(Color color, Type type) {
		this.setColor(color);
		this.setType(type);
		this.setTaille(10);
	}
	
	public void highlight(Color color, Type type, int taille) {
		this.setColor(color);
		this.setType(type);
		this.setTaille(taille);
	}
	
	public void unhighlight() {
		this.setColor(Color.BLACK);
		this.setType(Type.CERCLE);
		this.setTaille(5);
	}
	
	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
