package view.agglomeration;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import javax.swing.border.StrokeBorder;

import view.utils.Vue;
import model.agglomeration.Noeud;
import model.agglomeration.Troncon;

public class VueTroncon implements Vue {
	
	private Troncon troncon;
	private Color color;
	private int stroke;

	private final int RAYON_NOEUD = 5;
	
	public VueTroncon() {
		this.troncon = null;
		this.color = Color.BLACK;
	}
	
	public VueTroncon(Troncon troncon) {
		this.troncon = troncon;
		this.color = Color.BLACK;
	}
	
	public void dessine(Graphics g) {
		// cannot draw without node in
	}
	
	public void dessine(Graphics g, Noeud noeud) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		int x = noeud.getCoordX();
		int y = noeud.getCoordY();
		
		int xPrime = troncon.getNoeudDestination().getCoordX();
		int yPrime = troncon.getNoeudDestination().getCoordY();
		
		/*double distanceY = Math.abs(y-yPrime);
		double distanceX = Math.abs(x-xPrime);
		
		double alpha = Math.atan(distanceY/distanceX);
		
		int coeffX = (int)Math.floor(RAYON_NOEUD*Math.cos(alpha));
		int coeffY = (int)Math.floor(RAYON_NOEUD*Math.sin(alpha));
		
		x += coeffX;
		y -= coeffY;
		
		xPrime -= coeffX;
		yPrime += coeffY;*/
		g2.setColor(color);
		g2.setStroke(new BasicStroke(stroke));
		g2.drawLine(x, y, xPrime, yPrime);
		
		if (stroke > 1) {
			g2.setStroke(new BasicStroke(1));
			// draw arrow to indicate direction
			g2.setStroke(new BasicStroke(stroke));
		}
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
	}

	public void highlight() {
		color = Color.GREEN;
		stroke = 3;
	}
	
	public void highlight(Color color) {
		this.color = color;
		stroke = 3;
	}
	
	public Troncon getTroncon() {
		return troncon;
	}

	public void setTroncon(Troncon troncon) {
		this.troncon = troncon;
	}

}
