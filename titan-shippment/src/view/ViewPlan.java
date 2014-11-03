package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.agglomeration.Noeud;
import model.agglomeration.Plan;
import model.agglomeration.Troncon;

public class ViewPlan extends JPanel {
	
	private Plan plan;
	
	public ViewPlan(Plan plan) {
		this.setSize(500,200);
		this.setPreferredSize(new Dimension(500,200));
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
		this.plan = plan;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.BLUE);
		int nbNoeuds = plan.getNoeuds().size();
		for (int i = 0; i < nbNoeuds; ++i) {
			
			Noeud node = plan.getNoeuds().get(i);
			drawPoint(g2, node.getCoordX(), node.getCoordY());
			
			int size = node.getTroncons().size();
			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(3));
			for (int j = 0; j < size; ++j) {
				Troncon t = node.getTroncons().get(j);
				g2.drawLine(node.getCoordX(), node.getCoordY(), t.getNoeudDestination().getCoordX(), t.getNoeudDestination().getCoordY());
			}
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.BLUE);
		}
		
		
	}
	
	private void drawPoint(Graphics2D g, int x, int y) {
		g.drawLine(x-5, y, x+5, y);
		g.drawLine(x, y-5, x, y+5);
	}

}
