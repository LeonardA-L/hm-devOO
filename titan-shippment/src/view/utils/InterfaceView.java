package view.utils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import view.agglomeration.VuePlan;
import view.planning.VueTournee;

public class InterfaceView {
	
	private VuePlan vue_plan;
	private VueTournee vue_tournee;
	
	public InterfaceView() {
		Fenetre fenetre = new Fenetre();
		fenetre.setVisible(true);
		vue_plan = fenetre.getVuePlan();
	}
	
	public InterfaceView(VuePlan vue_plan) {
		this.vue_plan = vue_plan;
		this.setVue_tournee(null);
	}
	
	public VueTournee getVue_tournee() {
		return vue_tournee;
	}

	public void setVue_tournee(VueTournee vue_tournee) {
		this.vue_tournee = vue_tournee;
	}

	public VuePlan getVue_plan() {
		return vue_plan;
	}

	public void setVue_plan(VuePlan vue_plan) {
		this.vue_plan = vue_plan;
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

	public String loadFile() {
		JFileChooser dialogue = new JFileChooser();
        dialogue.showOpenDialog(null);
        File file = dialogue.getSelectedFile();
        if (file != null) {
        	return file.getPath();
        }
        return null;
	}

	public void repaint() {
		vue_plan.repaint();
	}
}
