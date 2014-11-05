package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Bouton extends JButton {
	
	public Bouton(String text) {
		this.setText(text);
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				// controller.trigger("nomBouton");
				JOptionPane.showMessageDialog(null, "Pop-up", "Pop-up du titan", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
	}

}
