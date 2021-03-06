package view.planning;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.planning.Livraison;
import view.utils.ButtonEditor;
import view.utils.ButtonRenderer;

public class VueLivraisonList extends JTable {

	public VueLivraisonList(DefaultTableModel model) {
		super(model);
		this.setPreferredScrollableViewportSize(new Dimension(250, 200));
		this.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
		this.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));
		this.getColumnModel().getColumn(4).setMinWidth(0);
		this.getColumnModel().getColumn(4).setMaxWidth(0);

		this.getColumnModel().getColumn(3).setMinWidth(50);
		this.getColumnModel().getColumn(3).setMaxWidth(50);
	}

	public void addLivraison(Livraison livraison) {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		model.addRow(new Object[] { String.valueOf(livraison.getIdClient()), livraison.getPlageHoraire().getHeureDebut(), livraison.getPlageHoraire().getHeureFin(), "x", livraison.getAdresse().getId() });
	}

	public boolean removeLivraison(int idNoeud) {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		int rc = model.getRowCount();
		int rowToDelete = -1;
		int i = 0;
		while (i < rc) {
			if (Integer.parseInt(model.getValueAt(i, 4).toString()) == idNoeud) {
				rowToDelete = i;
				break;
			}
			i++;
		}
		if (rowToDelete == -1) {
			return false;
		}
		model.removeRow(i);
		return true;
	}

	public void reset() {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		int rc = model.getRowCount();
		for (int i = 0; i < rc; i++) {
			model.removeRow(0);
		}
	}
}
