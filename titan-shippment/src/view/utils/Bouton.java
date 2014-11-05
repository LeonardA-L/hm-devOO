package view.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import controller.Controller;

public class Bouton extends JButton {
	
	private String name;
	private boolean loadFile;
	
	public Bouton(final String name, String text, final boolean loadFile) {
		this.setText(text);
		this.name = name;
		this.loadFile = loadFile;
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if (loadFile) {
		            JFileChooser dialogue = new JFileChooser();
		            dialogue.showOpenDialog(null);
		            File file = dialogue.getSelectedFile();
		            if (file != null) {
		            	Controller.getInstance().trigger("loadFile", name, file.getPath());
		            }
				}
				else {
					Controller.getInstance().trigger("click_button", name);
				}
			
				// controller.trigger("nomBouton");
				//JOptionPane.showMessageDialog(null, "Pop-up", "Pop-up du titan", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
	}

}
