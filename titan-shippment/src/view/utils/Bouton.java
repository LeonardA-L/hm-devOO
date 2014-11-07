package view.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import controller.Controller;

public class Bouton extends JButton {
	
	private String name;
	private boolean loadFile;
	
	public Bouton(final String name, String text, final boolean loadFile) {
		this.setText(text);
		this.name = name;
		this.loadFile = loadFile;
		
		if (loadFile) {
			this.setIcon(new ImageIcon("images/upload.png"));
		}
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if (loadFile) {
		            Controller.getInstance().trigger("loadFile", name);
				}
				else {
					Controller.getInstance().trigger("click_button", name);
				}
			}
			
		});
	}

}
