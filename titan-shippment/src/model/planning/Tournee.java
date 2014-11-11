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
		Livraison deliveryAfter = null; 
		for(Livraison l : livraisons) {
			if (l == deliveryBefore) {
				int index = livraisons.lastIndexOf(l);
				System.out.println("Livraison avant la nouvelle livraison à l'index "+index+" dans livraisons.");
				livraisons.add(index+1, newDelivery);		// new delivery added at the right place
				deliveryAfter = livraisons.get(index+2);
			}
		}
		return deliveryAfter;
	}	
	
	public boolean addItineraire(Livraison startPoint, Livraison endPoint) {
		
		return false;
	}
	
	public boolean removeItineraire(Livraison startPoint, Livraison endPoint)
	{
		Itineraire toBeRemoved = null;
		for(Itineraire it : itineraires) {
			if(it.getDepart()== startPoint.getAdresse() && it.getArrivee() == endPoint.getAdresse())
			{
				System.out.println("### Itinéraire à supprimer trouvé ###");
				toBeRemoved = it;
				break;
			}
		}
		if( toBeRemoved == null) {
			return false;
		}
		itineraires.remove(toBeRemoved);
		System.out.println("Itinéraire supprimé de la liste des itinéraires.");
		return true;
	}
	
	public boolean removeLivraison(int adresse){
		Livraison toBeRemoved = null;
		for(Livraison l : livraisons) {
			if(l.getAdresse().getId() == adresse) {
				System.out.println("### Livraison à supprimer trouvée ###");
				toBeRemoved = l;
				break;
			}
		}
		if (toBeRemoved == null) {
			return false;
		}
		livraisons.remove(toBeRemoved);
		System.out.println("Livraison supprimée de la liste des livraisons.");
		return false;
	}
	
	@Override
	public String toString() {
		String texte = "Tournée :\n";
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
