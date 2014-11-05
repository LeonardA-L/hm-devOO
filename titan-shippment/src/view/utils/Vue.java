package view.utils;

import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class Vue extends JPanel {
	protected abstract void dessine(Graphics g);
}
