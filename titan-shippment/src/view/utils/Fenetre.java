package view.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.agglomeration.VuePlan;

public class Fenetre extends JFrame {
	
	private VuePanel vue;
	private JPanel container;
	private JPanel top;
	private JPanel bottom;
	private JPanel top_right;

	private int tailleX = 1200;
	private int tailleY = 800;

	public Fenetre() {
		
		// init window
		this.setTitle("DevOO");
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setSize(this.tailleX, this.tailleY);
	    this.setLocationRelativeTo(null);               
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initContainer();
		
	}
	
	private void initContainer() {
		this.container = new JPanel();
		this.container.setLayout(new BorderLayout());
		
		initTop();
		initBottom();
		
		this.setContentPane(container);
	}
	
	private void initTop() {
		this.top = new JPanel();
		Dimension sizeTop = new Dimension(this.tailleX, (int)(this.tailleY*7/8));
		this.top.setSize(sizeTop);
		this.top.setPreferredSize(sizeTop);
		this.top.setLayout(new GridLayout(1,2));
		
		// VuePlan is a JPanel AND a VueObject => we should have cut this into 2 objects
		this.vue = new VuePanel();
		this.top.add(this.vue, 0);
		
		// list livraisons & btn
		this.top_right = new JPanel();
		this.top_right.setLayout(new GridLayout(1,2));
		this.top_right.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		initLivraisonPanel();
		initBtnPanel();		
		
		this.top.setBorder(BorderFactory.createLineBorder(Color.PINK));
		this.top.add(this.top_right, 1);
		
		this.container.add(this.top, BorderLayout.CENTER);
	}
	
	private void initBottom() {
		this.bottom = new JPanel();
		Dimension sizeBottom = new Dimension(this.tailleX, (int)(this.tailleY/8));
		this.bottom.setSize(sizeBottom);
		this.bottom.setPreferredSize(sizeBottom);
		
		Console console = new Console(sizeBottom);
		console.log("Super Léonard");
		console.log("GIGA ANTON");
		
		this.bottom.add(console);
		
		this.bottom.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.container.add(this.bottom, BorderLayout.SOUTH);
	}
	
	private void initLivraisonPanel() {
		JPanel livraisons_panel = new JPanel();
		livraisons_panel.setLayout(new BorderLayout());
		livraisons_panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		livraisons_panel.add(new JLabel("Les livraisons"), BorderLayout.CENTER);
		
		Bouton undo = new Bouton("undo", "", false);
		Bouton redo = new Bouton("redo", "", false);
		undo.setIcon(new ImageIcon("images/undo.png"));
		redo.setIcon(new ImageIcon("images/redo.png"));
		
		JPanel undoRedoPanel = new JPanel();
		undoRedoPanel.setLayout(new GridLayout(1,2));
		undoRedoPanel.add(undo, 0);
		undoRedoPanel.add(redo, 1);
		
		livraisons_panel.add(undoRedoPanel, BorderLayout.NORTH);
		this.top_right.add(livraisons_panel, 0);
	}
	
	private void initBtnPanel() {
		JPanel btn_panel = new JPanel();
		btn_panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		btn_panel.setLayout(new BoxLayout(btn_panel, BoxLayout.Y_AXIS));
		
		Bouton loadMap = new Bouton("loadMap", "Chargement de la carte", true);
		Bouton loadLivraisons = new Bouton("loadLivraisons", "Chargement des livraisons", true);
		Bouton calculTournee = new Bouton("calculTournee", "Calcul de la tournée", false);
		
		btn_panel.add(Box.createVerticalGlue());
		btn_panel.add(loadMap);
		btn_panel.add(loadLivraisons);
		btn_panel.add(calculTournee);
		btn_panel.add(Box.createVerticalGlue());
		
		this.top_right.add(btn_panel, 1);
	}

	public JPanel getFenetreContainer() {
		return container;
	}

	public void setContainer(JPanel container) {
		this.container = container;
	}

	public int getTaillex() {
		return tailleX;
	}

	public int getTailley() {
		return tailleY;
	}
	
	public VuePanel getVue() {
		return vue;
	}

	public void setVue(VuePanel vue) {
		this.vue = vue;
	}
	
	
}
