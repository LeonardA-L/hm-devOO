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
	
	// no sure this is still useful ... ?
	public Tournee calcul() {
		// do math stuff
		return this;
	}
	
	public Livraison addLivraisonAfter(Livraison newDelivery, Livraison deliveryBefore)
	{
		int index = -1;
		Livraison deliveryAfter = null;
		for (Livraison l : livraisons) {
			System.out.println("Livraison � l'index "+livraisons.lastIndexOf(l)+" = "+l.getAdresse().getId());
			if (l == deliveryBefore) {
				index = livraisons.lastIndexOf(l);
				System.out.println("La livraisons pr�c�dent la nouvelle livraison est � l'index : "+index+" dans livraisons.");		
				//break;
			}
		}
		if (index != -1) {
			livraisons.add(index+1, newDelivery);		// new delivery added at the right place
			deliveryAfter = livraisons.get(index+2);
		}
		return deliveryAfter;
	}	
	
	public boolean addItineraireAfter(Itineraire newIte) {
		int index = -1;
		for(Itineraire it : itineraires) {
			if (it.getArrivee() == newIte.getDepart()) {
				index = itineraires.lastIndexOf(it);
				break;
			}
		}
		if (index != -1) {
			itineraires.add(index+1, newIte);
			return true;
		}
		return false;
	}
	
	public int removeItineraireBefore(Livraison endPoint)
	{
		int adresseBefore = -1;
		Itineraire toBeRemoved = null;
		for(Itineraire it : itineraires) {
			if(it.getArrivee() == endPoint.getAdresse())
			{
				System.out.println("### Itin�raire � supprimer trouv� ###");
				toBeRemoved = it;
				break;
			}
		}
		if( toBeRemoved == null) {
			return adresseBefore;
		}
		adresseBefore = toBeRemoved.getDepart().getId();
		itineraires.remove(toBeRemoved);
		System.out.println("Itin�raire supprim� de la liste des itin�raires.");
		return adresseBefore;
	}
	
	public int removeItineraireAfter(Livraison startPoint)
	{
		int adresseAfter = -1;
		Itineraire toBeRemoved = null;
		for(Itineraire it : itineraires) {
			if(it.getDepart()== startPoint.getAdresse())
			{
				System.out.println("### Itin�raire � supprimer trouv� ###");
				toBeRemoved = it;
				break;
			}
		}
		if( toBeRemoved == null) {
			return adresseAfter;
		}
		adresseAfter = toBeRemoved.getArrivee().getId();
		itineraires.remove(toBeRemoved);
		System.out.println("Itin�raire supprim� de la liste des itin�raires.");
		return adresseAfter;
	}
	public boolean removeLivraison(int adresse){
		Livraison toBeRemoved = null;
		for(Livraison l : livraisons) {
			if(l.getAdresse().getId() == adresse) {
				System.out.println("### Livraison � supprimer trouv�e ###");
				toBeRemoved = l;
				break;
			}
		}
		if (toBeRemoved == null) {
			return false;
		}
		livraisons.remove(toBeRemoved);
		System.out.println("Livraison supprim�e de la liste des livraisons.");
		return false;
	}
	
	@Override
	public String toString() {
		String texte = "Tourn�e :\n";
		Iterator<Itineraire> it = itineraires.iterator();
		while (it.hasNext()) {
			texte += it.next().toString();
		}
		return texte;
	}
	
	
	public int addItineraire(Itineraire itineraire) {
		itineraires.add(itineraire);
		return itineraires.size()-1;
	}
	
	public int addLivraison(Livraison livraison) {
		livraisons.add(livraison);
		return livraisons.size()-1;
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
