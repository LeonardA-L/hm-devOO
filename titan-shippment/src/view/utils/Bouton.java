package view.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controller.Controller;

public class Bouton extends JButton {
	
	private String name;
	
	public Bouton(final String name, String text) {
		this.setText(text);
		this.name = name;
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				Controller.getInstance().trigger("click_button", name);
				// controller.trigger("nomBouton");
				JOptionPane.showMessageDialog(null, "Pop-up", "Pop-up du titan", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
	}

}
