package view.utils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import controller.Controller;
import model.agglomeration.Plan;
import view.agglomeration.VuePlan;

public class InterfaceView {
	
	private VuePlan vue_plan;
	
	public InterfaceView() {
		Fenetre fenetre = new Fenetre();
		fenetre.setVisible(true);
		vue_plan = fenetre.getVuePlan();
	}
	
	public InterfaceView(VuePlan vue_plan) {
		this.vue_plan = vue_plan;
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
}
