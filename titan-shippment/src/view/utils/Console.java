package view.utils;

import java.awt.Dimension;

import javax.swing.JLabel;

public class Console extends JLabel {
	
	private String text;
	
	public Console(Dimension sizeBottomPanel) {
		Dimension consoleSize = new Dimension((int)sizeBottomPanel.getWidth()-20, (int)sizeBottomPanel.getHeight()-20);
		this.setSize(consoleSize);
		this.setPreferredSize(consoleSize);
		this.text = "Ci-gît la console";
		this.setText("<html>" + this.text + "</html>");
	}
	
	public void log(String text) {
		this.text += "<br />" + text;
		this.setText("<html>" + this.text + "</html>");
	}
}
