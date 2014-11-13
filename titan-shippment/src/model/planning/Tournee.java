package model.planning;

import java.util.ArrayList;
import java.util.Iterator;

public class Tournee {

	private ArrayList<Livraison> livraisons;
	private ArrayList<Itineraire> itineraires;

	public Tournee() {
		this.livraisons = new ArrayList<Livraison>();
		this.itineraires = new ArrayList<Itineraire>();
	}

	public Tournee(ArrayList<Livraison> livraisons,
			ArrayList<Itineraire> itineraires) {
		this.livraisons = livraisons;
		this.itineraires = itineraires;
	}

	public int addLivraisonAfter(Livraison newDelivery, int adresseBefore) {
		int index = -1; // index of livraisons
		int adresseAfter = -1; // adresse of node before the new delivery in the
								// tournee
		for (Livraison l : livraisons) {
			if (l.getAdresse().getId() == adresseBefore) {
				index = livraisons.indexOf(l);
				System.out
						.println("FOUND = La livraisons precedent la nouvelle livraison est a l'index : "
								+ index + " dans livraisons.");
				break;
			}
		}

		if (index + 1 < livraisons.size()) { // if the delivery the user want to
												// add is not at the end of
												// tournee
			adresseAfter = livraisons.get(index + 1).getAdresse().getId();
			livraisons.add(index + 1, newDelivery);
			System.out
					.println("addLivraisonAfter -- Adresse after is a delivery");
		} else { // if the new delivery is at the end of tournee
			livraisons.add(newDelivery);
			System.out
					.println("addLivraisonAfter -- Adresse after is a NOT delivery");
		}
		return adresseAfter; // -1 if adresseAfter is the warehouse or the
								// address if it is a delivery
	}

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

	public int removeItineraireBefore(int endPoint) {
		int adresseBefore = -1;
		Itineraire toBeRemoved = null;
		for (Itineraire it : itineraires) {
			if (it.getArrivee().getId() == endPoint) {
				System.out.println("### Itineraire a supprimer trouve ###");
				toBeRemoved = it;
				break;
			}
		}
		if (toBeRemoved == null) {
			return adresseBefore;
		}
		adresseBefore = toBeRemoved.getDepart().getId();
		itineraires.remove(toBeRemoved);
		System.out.println("Itineraire supprime de la liste des itineraires.");
		return adresseBefore;
	}

	public int removeItineraireAfter(int startPoint) {
		int adresseAfter = -1;
		Itineraire toBeRemoved = null;
		for (Itineraire it : itineraires) {
			if (it.getDepart().getId() == startPoint) {
				System.out.println("### Itineraire a supprimer trouve ###");
				toBeRemoved = it;
				break;
			}
		}
		if (toBeRemoved == null) {
			return adresseAfter;
		}
		adresseAfter = toBeRemoved.getArrivee().getId();
		boolean deleteOk = itineraires.remove(toBeRemoved);
		if (deleteOk) {
			System.out
					.println("Itineraire supprime de la liste des itineraires.");
		} else {
			System.out.println("Erreur a la suppression");
		}
		return adresseAfter;
	}

	public boolean removeLivraison(int adresse) {
		Livraison toBeRemoved = null;
		for (Livraison l : livraisons) {
			if (l.getAdresse().getId() == adresse) {
				System.out.println("### Livraison a supprimer trouvee ###");
				toBeRemoved = l;
				break;
			}
		}
		if (toBeRemoved == null) {
			return false;
		}
		livraisons.remove(toBeRemoved);
		System.out.println("Livraison supprimee de la liste des livraisons.");
		return true;
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
					texte += "Livraison no" + l.getIdLivraison()
							+ " pour le client : " + l.getIdClient() + "\n";
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

}
