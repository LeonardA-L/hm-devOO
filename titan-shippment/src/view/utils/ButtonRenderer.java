package view.utils;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean isFocus, int row, int col) {
		// On �crit dans le bouton ce que contient la cellule
		setText((value != null) ? "x" : "");
		// On retourne notre bouton
		return this;
	}

}
