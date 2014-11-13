package model.planning;

import java.util.ArrayList;
import java.util.Iterator;

public class Tournee {

	private ArrayList<Livraison> livraisons;
	private ArrayList<Itineraire> itineraires;

	/**
	 * Constructor w/o parameter
	 */
	public Tournee() {
		this.livraisons = new ArrayList<Livraison>();
		this.itineraires = new ArrayList<Itineraire>();
	}

	/**
	 * Constructor w/parameters
	 * @param livraisons		Livraisons to be included in Tournee
	 * @param itineraires		Itineraires between Lirvraisons
	 */
	public Tournee(ArrayList<Livraison> livraisons, ArrayList<Itineraire> itineraires) {
		this.livraisons = livraisons;
		this.itineraires = itineraires;
	}

	/**
	 * Adds a delivery in the 'tournée' after another delivery
	 * @param newDelivery		The new delivery to be added to the 'tournée'
	 * @param adresseBefore		Address of the delivery that takes place before
	 * @return	Address of delivery before newly added 'tournée' or -1 it is the warehouse
	 */
	public int addLivraisonAfter(Livraison newDelivery, int adresseBefore) {
		int index = -1; // index of livraisons
		int adresseAfter = -1; // adresse of node before the new delivery in the tournee
		for (Livraison l : livraisons) {
			if (l.getAdresse().getId() == adresseBefore) {
				index = livraisons.indexOf(l);
				break;
			}
		}

		if (index + 1 < livraisons.size()) { // if the delivery the user want to add is not at the end of tournee
			adresseAfter = livraisons.get(index + 1).getAdresse().getId();
			livraisons.add(index + 1, newDelivery);
		} else { // if the new delivery is at the end of tournee
			livraisons.add(newDelivery);
		}
		return adresseAfter;
	}

	/**
	 * Adds a new 'itineraire' after a certain delivery 
	 * (whose adress is the starting point of the 'itineraire')
	 * @param newIte
	 * @return
	 */
	public boolean addItineraireAfter(Itineraire newIte) {
		int index = -1;
		for (Itineraire it : itineraires) {
			if (it.getArrivee() == newIte.getDepart()) {
				index = itineraires.lastIndexOf(it);
				break;
			}
		}
		if (index != -1) {
			itineraires.add(index + 1, newIte);
			return true;
		}
		return false;
	}

	/**
	 * Remove 'itineraire' whose ending point is at address endPoint
	 * @param endPoint		Adress where the itineraire ends
	 * @return
	 */
	public int removeItineraireBefore(int endPoint) {
		int adresseBefore = -1;
		Itineraire toBeRemoved = null;
		for (Itineraire it : itineraires) {
			if (it.getArrivee().getId() == endPoint) {
				toBeRemoved = it;
				break;
			}
		}
		if (toBeRemoved == null) {
			return adresseBefore;
		}
		adresseBefore = toBeRemoved.getDepart().getId();
		itineraires.remove(toBeRemoved);
		return adresseBefore;
	}

	/**
	 * Remove 'itineraire' whose start point is at address startPoint
	 * @param startPoint		Adress frpom which the 'itineraire' starts
	 * @return
	 */
	public int removeItineraireAfter(int startPoint) {
		int adresseAfter = -1;
		Itineraire toBeRemoved = null;
		for (Itineraire it : itineraires) {
			if (it.getDepart().getId() == startPoint) {
				toBeRemoved = it;
				break;
			}
		}
		if (toBeRemoved == null) {
			return adresseAfter;
		}
		adresseAfter = toBeRemoved.getArrivee().getId();
		itineraires.remove(toBeRemoved);
		return adresseAfter;
	}

	/**
	 * Remove 'livraison' at address 'adresse' from tournee
	 * @param adresse
	 * @return	
	 */
	public boolean removeLivraison(int adresse) {
		Livraison toBeRemoved = null;
		for (Livraison l : livraisons) {
			if (l.getAdresse().getId() == adresse) {
				toBeRemoved = l;
				break;
			}
		}
		if (toBeRemoved == null) {
			return false;
		}
		livraisons.remove(toBeRemoved);
		return true;
	}

	// ------------------------------------------------------------------
	// GETTERS - SETTERS
	public int addItineraire(Itineraire itineraire) {
		itineraires.add(itineraire);
		return itineraires.size() - 1;
	}

	public int addLivraison(Livraison livraison) {
		livraisons.add(livraison);
		return livraisons.size() - 1;
	}

	public ArrayList<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(ArrayList<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public ArrayList<Itineraire> getItineraires() {
		return itineraires;
	}

	public void setItineraires(ArrayList<Itineraire> itineraires) {
		this.itineraires = itineraires;
	}

	public void reset() {
		livraisons.clear();
		itineraires.clear();
	}
	
	@Override
	public String toString() {
		String texte = "#### INSTRUCTIONS DE LIVRAISON ####\n\n";
		Iterator<Itineraire> it = itineraires.iterator();
		while (it.hasNext()) {
			Itineraire itineraire = it.next();
			int adresseLivraison = itineraire.getArrivee().getId();
			for (Livraison l : livraisons) {
				if (l.getAdresse().getId() == adresseLivraison) {
					texte += "Livraison no" + l.getIdLivraison() + " pour le client : " + l.getIdClient() + "\n";
					// texte+="Heure de livraison entre "+l.getPlageHoraire().getHeureDebut()+" et "+l.getPlageHoraire().getHeureFin()+".\n\n";
					break;
				}
			}
			texte += "Itineraire a suivre : \n";
			texte += itineraire.toString();
			texte += "\n----------------------------------------------------------------------------------------------\n\n";
		}
		return texte;
	}

}
