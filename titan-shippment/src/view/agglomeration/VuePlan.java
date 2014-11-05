package view.agglomeration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import view.utils.Vue;
import controller.Controller;
import model.agglomeration.Noeud;
import model.agglomeration.Plan;

public class VuePlan extends Vue {
	
	private Plan plan;
	
	private double ratioVueModele;
	private VueNoeud vueEntrepot;
	private ArrayList<VueNoeud> vueNoeuds;
	
	public VuePlan() {
		this.setSize(500,200);
		this.setPreferredSize(new Dimension(500,200));
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		this.plan = null;
		this.vueEntrepot = null;
		this.vueNoeuds = new ArrayList<VueNoeud>();
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int x = arg0.getX();
				int y = arg0.getY();
				Controller.getInstance().trigger("click_map", x, y);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public Noeud getWhoIsClicked(int x, int y) {
		if (vueEntrepot.estCliquee(x, y)) {
			return vueEntrepot.getNoeud();
		}
		
		Iterator<VueNoeud> it = vueNoeuds.iterator();
		
		while(it.hasNext()) {
			VueNoeud vue_noeud = it.next();
			if (vue_noeud.estCliquee(x, y)) {
				return vue_noeud.getNoeud();
			}
		}
		
		return null;
	}
	
	protected void dessine(Graphics g) {
		// si le plan est chargé
		if (plan != null) {
			Iterator<VueNoeud> it = vueNoeuds.iterator();
			while(it.hasNext()) {
				VueNoeud vue_noeud = it.next();
				vue_noeud.dessine(g);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		dessine(g);
		
		//Graphics2D g2 = (Graphics2D)g;
		
		
		
		/*g2.setColor(Color.BLUE);
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
		}*/
		
		
	}
	
	private void drawPoint(Graphics2D g, int x, int y) {
		g.drawLine(x-5, y, x+5, y);
		g.drawLine(x, y-5, x, y+5);
	}

}
