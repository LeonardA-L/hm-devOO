package view.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.planning.PlageHoraire;
import controller.Controller;
import view.agglomeration.VueNoeud;
import view.agglomeration.VuePlan;
import view.planning.VueLivraison;
import view.planning.VueTournee;


public class VuePanel extends JPanel {
	
	private VuePlan vue_plan;
	private VueTournee vue_tournee;
	private VueNoeud vue_entrepot;
	private ArrayList<VueLivraison> vues_livraisons;
	
	private ArrayList<VueNoeud> noeud_highlighted;
	
	public VuePanel() {
		// init views
		vue_plan = new VuePlan();
		vue_tournee = new VueTournee();
		vues_livraisons = new ArrayList<VueLivraison>();
		noeud_highlighted = new ArrayList<VueNoeud>();
		vue_entrepot = new VueNoeud();
		
		// init panel
		this.setSize(500,200);
		this.setPreferredSize(new Dimension(500,200));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				int x = arg0.getX();
				int y = arg0.getY();
				Controller.getInstance().trigger("mouse_moved_on_map", x, y);;				
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
			VueLivraison vueLivraison = it.next();
			PlageHoraire ph = vueLivraison.getLivraison().getPlageHoraire();
			
			Color color = null;
			try {
				int heureDebut = Integer.parseInt(ph.getHeureDebut().replaceAll(":", ""));
				int heureFin = Integer.parseInt(ph.getHeureFin().replaceAll(":", ""));
				
				final int LIMITE_MIN = 50 %255;
				final int LIMITE_MAX = 200 %255;
				final int MODULO = LIMITE_MAX-LIMITE_MIN+1;
				
				color = new Color(LIMITE_MIN + heureDebut%MODULO, LIMITE_MIN + heureFin%MODULO , LIMITE_MIN + (heureDebut+heureFin)%MODULO);
			} catch (Exception e) {
				// unparsable plage horaire
				 color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			}
			
			vueLivraison.dessine(g, color);
		}
		
		if (vue_entrepot != null) {
			vue_entrepot.dessine(g);
		}
		
		if (vue_tournee.getTournee() != null) {
			vue_tournee.dessine(g);
		}
		
	}
	
	public void resetLivraisons() {
		vue_entrepot.unhighlight();
		vue_entrepot.setNoeud(null);
		
		Iterator<VueLivraison> it = vues_livraisons.iterator();
		while (it.hasNext()) {
			it.next().reset();
		}
		vues_livraisons.clear();
	}

	public void resetPlan() {
		vue_plan.reset();
	}
	
	public void resetTournee() {
		vue_tournee.reset();
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
	
	public VueNoeud getVue_entrepot(){
		return vue_entrepot;
	}
	
	public void setVue_entrepot(VueNoeud vue_entrepot){
		this.vue_entrepot = vue_entrepot;
	}

	public ArrayList<VueNoeud> getNoeud_highlighted() {
		return noeud_highlighted;
	}

	public void setNoeud_highlighted(ArrayList<VueNoeud> noeud_highlighted) {
		this.noeud_highlighted = noeud_highlighted;
	}

}
