package view.planning;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import view.utils.ButtonEditor;
import view.utils.ButtonRenderer;
import model.planning.Livraison;

public class VueLivraisonList extends JTable {
	
	public VueLivraisonList(DefaultTableModel model){
		super(model);
		this.setPreferredScrollableViewportSize(this.getPreferredSize());
		this.getColumn("Delete").setCellRenderer(new ButtonRenderer());
		this.getColumn("Delete").setCellEditor(
		        new ButtonEditor(new JCheckBox()));
	}
	
	public void addLivraison(Livraison livraison){
		DefaultTableModel  model = (DefaultTableModel )this.getModel();
		model.addRow(new Object[]{String.valueOf(livraison.getIdClient()),livraison.getPlageHoraire().getHeureDebut(),livraison.getPlageHoraire().getHeureFin(),"Delete"});
	}
	
	public boolean removeLivraison(Livraison livraison){
		DefaultTableModel  model = (DefaultTableModel )this.getModel();
		int rc= model.getRowCount();
		int rowToDelete=-1;
		int i = 0;
        while(i<rc){
            if(Integer.parseInt(model.getValueAt(i, 0).toString())==livraison.getIdClient()){
            	rowToDelete = i;
            	break;
            }
            i++;
        }
        if(rowToDelete==-1){
        	return false;
        }
        model.removeRow(i);
        return true;
	}
	
	public void reset(){
		DefaultTableModel model=(DefaultTableModel)this.getModel();
        int rc= model.getRowCount();
        for(int i = 0;i<rc;i++){
            model.removeRow(0);
        }   
	}
}
