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
import javax.swing.JScrollPane;

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
		this.setResizable(false);
		
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
		this.top.setLayout(new BorderLayout());
		
		// VuePlan is a JPanel AND a VueObject => we should have cut this into 2 objects
		this.vue = new VuePanel();
		this.top.add(this.vue, BorderLayout.CENTER);
		
		// list livraisons & btn
		this.top_right = new JPanel();
		this.top_right.setLayout(new GridLayout(1,2));
		this.top_right.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		initLivraisonPanel();
		initBtnPanel();		
		
		this.top.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.top.add(this.top_right, BorderLayout.EAST);
		
		this.container.add(this.top, BorderLayout.CENTER);
	}
	
	private void initBottom() {
		this.bottom = new JPanel();
		Dimension sizeBottom = new Dimension(this.tailleX, (int)(this.tailleY/8));
		this.bottom.setSize(sizeBottom);
		this.bottom.setPreferredSize(sizeBottom);
		this.bottom.setLayout( new BorderLayout() );
		
		Console console = new Console(sizeBottom);
		console.log("Super Léonard");
		console.log("GIGA ANTON");
		console.log("GIGA ANTON1");
		console.log("GIGA ANTON2");
		console.log("GIGA ANTON3");
		console.log("GIGA ANTON4");
		console.log("GIGA ANTON5");
		
		//JScrollPane scrollPane = new JScrollPane (new ScrolledConsole(console));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView( console );
		this.bottom.add( scrollPane, BorderLayout.NORTH );
		
		//this.bottom.add(scrollPane);
		
		this.bottom.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.container.add(this.bottom, BorderLayout.SOUTH);
	}
	
	private void initLivraisonPanel() {
		JPanel livraisons_panel = new JPanel();
		livraisons_panel.setLayout(new BorderLayout());
		livraisons_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		livraisons_panel.add(new JLabel("Les livraisons"), BorderLayout.CENTER);
		
		Bouton undo = new Bouton("undo", "", false);
		Bouton redo = new Bouton("redo", "", false);
		undo.setIcon(new ImageIcon("images/undo.png"));
		//undo.setPreferredSize(new Dimension(80,40));
		redo.setIcon(new ImageIcon("images/redo.png"));
		//redo.setPreferredSize(new Dimension(80,40));
		
		JPanel undoRedoPanel = new JPanel();
		undoRedoPanel.setLayout(new BoxLayout(undoRedoPanel, BoxLayout.X_AXIS));
		undoRedoPanel.add(Box.createHorizontalGlue());
		undoRedoPanel.add(undo);
		undoRedoPanel.add(redo);
		undoRedoPanel.add(Box.createHorizontalGlue());
		
		livraisons_panel.add(undoRedoPanel, BorderLayout.NORTH);
		this.top_right.add(livraisons_panel, 0);
	}
	
	private void initBtnPanel() {
		JPanel btn_panel = new JPanel();
		//btn_panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		btn_panel.setLayout(new BoxLayout(btn_panel, BoxLayout.Y_AXIS));
		
		Bouton loadMap = new Bouton("loadMap", "Chargement de la carte", true);
		Bouton loadLivraisons = new Bouton("loadLivraisons", "Chargement des livraisons", true);
		Bouton calculTournee = new Bouton("calculTournee", "Calcul de la tournée", false);
		
		btn_panel.add(Box.createGlue());
		btn_panel.add(loadMap);
		btn_panel.add(loadLivraisons);
		btn_panel.add(calculTournee);
		btn_panel.add(Box.createGlue());
		
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
