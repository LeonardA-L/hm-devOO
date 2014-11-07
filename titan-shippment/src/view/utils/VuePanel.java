package view.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import controller.Controller;
import view.agglomeration.VuePlan;
import view.planning.VueLivraison;
import view.planning.VueTournee;

public class VuePanel extends JPanel {
	
	private VuePlan vue_plan;
	private VueTournee vue_tournee;
	private ArrayList<VueLivraison> vues_livraisons;
	
	public VuePanel() {
		// init views
		vue_plan = new VuePlan();
		vue_tournee = new VueTournee();
		vues_livraisons = new ArrayList<VueLivraison>();
		
		// init panel
		this.setSize(500,200);
		this.setPreferredSize(new Dimension(500,200));
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
		
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
	
	@Override
	protected void paintComponent(Graphics g) {
		// reset
		super.paintComponent(g);
		
		// on dessine le plan
		if (vue_plan != null) {
			vue_plan.dessine(g);
		}
		
		Iterator<VueLivraison> it = vues_livraisons.iterator();
		while (it.hasNext()) {
			it.next().dessine(g);
		}
		
	}

	public VuePlan getVue_plan() {
		return vue_plan;
	}

	public void setVue_plan(VuePlan vue_plan) {
		this.vue_plan = vue_plan;
	}

	public ArrayList<VueLivraison> getVues_livraisons() {
		return vues_livraisons;
	}

	public void setVues_livraisons(ArrayList<VueLivraison> vues_livraisons) {
		this.vues_livraisons = vues_livraisons;
	}

	public VueTournee getVue_tournee() {
		return vue_tournee;
	}

	public void setVue_tournee(VueTournee vue_tournee) {
		this.vue_tournee = vue_tournee;
	}
}
