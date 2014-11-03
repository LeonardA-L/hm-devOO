package model.planning;

import java.util.ArrayList;

public class Manager {

	private ArrayList<Livraison> livraisons;
	private ArrayList<Livraison> livraisonsEffectuees;
	private Tournee tournee;
	
	public Manager() {
		this.livraisons = new ArrayList<Livraison>();
		this.livraisonsEffectuees = new ArrayList<Livraison>();
		this.tournee = new Tournee();
	}

	public Manager(ArrayList<Livraison> livraisons,
			ArrayList<Livraison> livraisonsEffectuees, Tournee tournee) {
		this.livraisons = livraisons;
		this.livraisonsEffectuees = livraisonsEffectuees;
		this.tournee = tournee;
	}

	/* Method used by CommandAddOne
	 * @param x 	X coordinate where the user clicked on map to create the new livraison
	 * @param y 	Y coordinate where the user clicked on map to create the new livraison
	 * @param heureDebut 	Start of PlageHoraire as written by user
	 * @param heureFin		End of PlageHoraire as written by user
	 * @param client		id of client
	 * @param xBefore 		X coordinate of the livraison after which the new livraison is added
	 * @param yBefore		Y coordinate of the livraison after which the new livraison is added
	 * @return True or false depending on the success of the command
	 * @see CommandAddOne
	 */
	public boolean AddOneLivraison(int x, int y, String heureDebut, String heureFin, int client, int xBefore, int yBefore)
	{
		// See sequence diagram.
		return false;
	}
	
	/* Method used by CommandAddOne
	 * @param x 	X coordinate where the user clicked on map to remove the new livraison
	 * @param y 	Y coordinate where the user clicked on map to remove the new livraison
	 * @return  True or false depending on the success of the command
	 * @see 	CommandRemoveOne
	 */
	public boolean RemoveLivraison(int x, int y)
	{
		// See sequence diagram. 
		return false;
	}
	

	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public ArrayList<Livraison> getLivraisonsEffectuees() {
		return livraisonsEffectuees;
	}

	public void setLivraisonsEffectuees(ArrayList<Livraison> livraisonsEffectuees) {
		this.livraisonsEffectuees = livraisonsEffectuees;
	}

	public Tournee getTournee() {
		return tournee;
	}

	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
	}	
	
}
