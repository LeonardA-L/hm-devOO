package view.agglomeration;

import java.awt.Color;
import java.awt.Graphics;

import view.utils.Vue;
import model.agglomeration.Noeud;
import model.agglomeration.Troncon;

public class VueTroncon extends Vue {
	
	private Troncon troncon;
	private Color color;
	
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
		super.paintComponent(g);
		
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
		g.setColor(color);
		g.drawLine(x, y, xPrime, yPrime);
		g.setColor(Color.BLACK);
	}

	public void highlight() {
		color = Color.GREEN;
	}
	
	

}
