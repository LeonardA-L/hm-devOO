package view.agglomeration;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import model.agglomeration.Noeud;
import model.agglomeration.Troncon;
import view.utils.Vue;

public class VueTroncon implements Vue {

	private Troncon troncon;
	private Color color;
	private int stroke;

	/**
	 * Constructor w/o parameters
	 */
	public VueTroncon() {
		this.troncon = null;
		this.color = Color.BLACK;
	}

	/**
	 * Constructor w/parameter
	 * @param troncon	Tronçon associated with view
	 */
	public VueTroncon(Troncon troncon) {
		this.troncon = troncon;
		this.color = Color.BLACK;
	}

	public void dessine(Graphics g) {
		// cannot draw without node in
	}

	/**
	 * Draw the Troncon
	 * @param g			Graphics from Vue
	 * @param noeud		Node
	 */
	public void dessine(Graphics g, Noeud noeud) {

		Graphics2D g2 = (Graphics2D) g;

		int x = noeud.getCoordX();
		int y = noeud.getCoordY();

		int xPrime = troncon.getNoeudDestination().getCoordX();
		int yPrime = troncon.getNoeudDestination().getCoordY();

		g2.setColor(color);
		g2.setStroke(new BasicStroke(stroke));
		g2.drawLine(x, y, xPrime, yPrime);

		if (stroke > 1) {
			g2.setStroke(new BasicStroke(1));
			drawArrow(g2, x, y, xPrime, yPrime);
			g2.setStroke(new BasicStroke(stroke));
		}

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
	}

	public void highlight() {
		color = Color.GREEN;
		stroke = 2;
	}

	public void highlight(Color color) {
		this.color = color;
		stroke = 2;
	}

	public Troncon getTroncon() {
		return troncon;
	}

	public void setTroncon(Troncon troncon) {
		this.troncon = troncon;
	}

	private final int ARR_SIZE = 6;

	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len }, new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 }, 4);
	}
}
