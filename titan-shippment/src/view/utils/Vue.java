package view.utils;

import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class Vue extends JPanel {
	protected abstract void dessine(Graphics g);
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}
}
