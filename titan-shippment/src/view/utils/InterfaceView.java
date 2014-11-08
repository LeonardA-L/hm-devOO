package view.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.planning.Livraison;
import view.agglomeration.VueNoeud;
import view.planning.VueLivraison;

public class InterfaceView {
	
	private VuePanel vuePanel;

	public InterfaceView() {
		Fenetre fenetre = new Fenetre();
		fenetre.setVisible(true);
		vuePanel = fenetre.getVue();
	}

	public void displayAlert(String titre, String message, String type) {
		
		int type_alert = JOptionPane.INFORMATION_MESSAGE;
		if (type.equals("warning")) {
			type_alert = JOptionPane.WARNING_MESSAGE;
		}
		else if (type.equals("error")) {
			type_alert = JOptionPane.ERROR_MESSAGE;
		}
		JOptionPane.showMessageDialog(null, message, titre, type_alert);
	}
	
	public String[] askPlageHoraire() {
		String format = "\\d{1,2}:\\d{1,2}:\\d{1,2}";
		String[] retour = new String[3];
		
		String heureDebut = JOptionPane.showInputDialog(null, "Veuillez entrer une heure de début (Format : H:M:S) : ");
		if (heureDebut == null || !heureDebut.matches(format)) {
			displayAlert("Erreur", "Mauvais format d'heures (H:M:S)", "error");
			retour[0] = null;
			retour[1] = null;
			retour[2] = null;
			return retour;
		}
		
		String heureFin = JOptionPane.showInputDialog(null, "Veuillez entrer une heure de fin (Format : H:M:S) : ");
		if (heureFin == null || !heureFin.matches(format)) {
			displayAlert("Erreur", "Mauvais format d'heures (H:M:S)", "error");
			retour[0] = null;
			retour[1] = null;
			retour[2] = null;
			return retour;
		}
		
		String idClient = JOptionPane.showInputDialog(null, "Veuillez entrer l'identifiant du client :");
		if (idClient == null || !idClient.matches("[0-9]+")) {
			displayAlert("Erreur", "L'identifiant doit être un nombre", "error");
			retour[0] = null;
			retour[1] = null;
			retour[3] = null;
			return retour;
		}
		retour[0] = heureDebut;
		retour[1] = heureFin;
		retour[2] = idClient;
		return retour;
	}

	public String loadFile() {
		JFileChooser dialogue = new JFileChooser();
		dialogue.setCurrentDirectory(new File("../"));
		dialogue.setFileFilter(new FileNameExtensionFilter("Fichier XML", "xml"));
		dialogue.setAcceptAllFileFilterUsed(false);
        dialogue.showOpenDialog(null);
        File file = dialogue.getSelectedFile();
        if (file != null) {
        	return file.getPath();
        }
        return null;
	}

	public void repaint() {
		vuePanel.repaint();
	}
	
	
	public void highlight(VueNoeud noeud) {
		this.getVuePanel().getNoeud_highlighted().add(noeud);
		noeud.highlight();
		this.repaint();
	}
	
	public void clearHighlightedNodes() {
		Iterator<VueNoeud> it = this.getVuePanel().getNoeud_highlighted().iterator();
		while (it.hasNext()) {
			it.next().unhighlight();
		}
		this.getVuePanel().getNoeud_highlighted().clear();
		this.repaint();
	}

	public boolean genererVueLivraisons(ArrayList<Livraison> listeLivraisons) {
		Iterator<Livraison> it = listeLivraisons.iterator();
		while (it.hasNext()) {
			Livraison livraison = it.next();
			VueNoeud vue_noeud = vuePanel.getVue_plan().getVueNoeudById(livraison.getAdresse().getId());
			
			if (vue_noeud == null) {
				vuePanel.getVues_livraisons().clear();
				return false;
			}
			
			vuePanel.getVues_livraisons().add(new VueLivraison(livraison, vue_noeud));
		}
		return true;
	}
	
	public VuePanel getVuePanel() {
		return vuePanel;
	}

	public void setVuePanel(VuePanel vuePanel) {
		this.vuePanel = vuePanel;
	}
}
