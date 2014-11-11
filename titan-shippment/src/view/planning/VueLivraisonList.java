package view.planning;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.planning.Livraison;

public class VueLivraisonList extends JTable {
	
	public VueLivraisonList(DefaultTableModel model){
		super(model);
		this.setPreferredScrollableViewportSize(this.getPreferredSize());
	}
	
	public void addLivraison(Livraison livraison){
		DefaultTableModel  model = (DefaultTableModel )this.getModel();
		model.addRow(new Object[]{String.valueOf(livraison.getIdClient()),livraison.getPlageHoraire().getHeureDebut(),livraison.getPlageHoraire().getHeureFin()});
	}
	
	public void reset(){
		DefaultTableModel model=(DefaultTableModel)this.getModel();
        int rc= model.getRowCount();
        for(int i = 0;i<rc;i++){
            model.removeRow(0);
        }   
	}
}
