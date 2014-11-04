package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import model.agglomeration.Plan;

public class Fenetre extends JFrame {
	
	private VuePlan view_plan;
	private JPanel container;
	private JPanel top;
	private JPanel bottom;
	private JPanel top_right;
	
	private final int tailleX = 1000;
	private final int tailleY = 600;

	public Fenetre(Plan plan) {
		
		// init window
		this.setTitle("DevOO");
		this.setSize(this.tailleX, this.tailleY);
	    this.setLocationRelativeTo(null);               
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initContainer(plan);
		
	}
	
	private void initContainer(Plan plan) {
		this.container = new JPanel();
		this.container.setLayout(new BorderLayout());
		
		initTop(plan);
		initBottom();
		
		this.setContentPane(container);
	}
	
	private void initTop(Plan plan) {
		this.top = new JPanel();
		Dimension sizeTop = new Dimension(this.tailleX, (int)(this.tailleY*7/8));
		this.top.setSize(sizeTop);
		this.top.setPreferredSize(sizeTop);
		this.top.setLayout(new GridLayout(1,2));
		
		this.view_plan = new VuePlan(plan);
		this.top.add(this.view_plan, 0);
		
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
		livraisons_panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		livraisons_panel.add(new JLabel("Les livraisons"));
		this.top_right.add(livraisons_panel, 0);
	}
	
	private void initBtnPanel() {
		JPanel btn_panel = new JPanel();
		btn_panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		btn_panel.setLayout(new BoxLayout(btn_panel, BoxLayout.Y_AXIS));
		
		Bouton loadMap = new Bouton("Chargement de la carte");
		Bouton loadLivraisons = new Bouton("Chargement des livraisons");
		Bouton calculTournee = new Bouton("Calcul de la tournée");
		
		btn_panel.add(Box.createVerticalGlue());
		btn_panel.add(loadMap);
		btn_panel.add(loadLivraisons);
		btn_panel.add(calculTournee);
		btn_panel.add(Box.createVerticalGlue());
		
		this.top_right.add(btn_panel, 1);
	}

	public VuePlan getVuePlan() {
		return view_plan;
	}

	public void setVuePlan(VuePlan plan) {
		this.view_plan = plan;
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
	
	
}
