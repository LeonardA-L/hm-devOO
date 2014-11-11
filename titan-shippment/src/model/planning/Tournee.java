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
			System.out.println("Livraison à l'index "+livraisons.lastIndexOf(l)+" = "+l.getAdresse().getId());
			if (l == deliveryBefore) {
				index = livraisons.lastIndexOf(l);
				System.out.println("La livraisons précédent la nouvelle livraison est à l'index : "+index+" dans livraisons.");		
				//break;
			}
		}
		if (index != -1) {
			deliveryAfter = livraisons.get(index+1);
			livraisons.add(index+1, newDelivery);		// new delivery added at the right place
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
				System.out.println("### Itinéraire à supprimer trouvé ###");
				toBeRemoved = it;
				break;
			}
		}
		if( toBeRemoved == null) {
			return adresseBefore;
		}
		adresseBefore = toBeRemoved.getDepart().getId();
		itineraires.remove(toBeRemoved);
		System.out.println("Itinéraire supprimé de la liste des itinéraires.");
		return adresseBefore;
	}
	
	public int removeItineraireAfter(Livraison startPoint)
	{
		int adresseAfter = -1;
		Itineraire toBeRemoved = null;
		for(Itineraire it : itineraires) {
			if(it.getDepart()== startPoint.getAdresse())
			{
				System.out.println("### Itinéraire à supprimer trouvé ###");
				toBeRemoved = it;
				break;
			}
		}
		if( toBeRemoved == null) {
			return adresseAfter;
		}
		adresseAfter = toBeRemoved.getArrivee().getId();
		boolean deleteOk = itineraires.remove(toBeRemoved);
		if (deleteOk) {
			System.out.println("Itinéraire supprimé de la liste des itinéraires.");
		}
		else
		{
			System.out.println("Erreur à la suppression");
		}
		return adresseAfter;
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
		String texte = "#### INSTRUCTIONS DE LIVRAISON ####\n\n";
		Iterator<Itineraire> it = itineraires.iterator();
		while (it.hasNext()) {
			Itineraire itineraire = it.next();
			int adresseLivraison = itineraire.getArrivee().getId();
			for(Livraison l : livraisons) {
				if(l.getAdresse().getId()==adresseLivraison) {
					texte+="Livraison n°"+l.getIdLivraison()+" pour le client : "+l.getIdClient()+"\n";
					//texte+="Heure de livraison entre "+l.getPlageHoraire().getHeureDebut()+" et "+l.getPlageHoraire().getHeureFin()+".\n\n";
					break;
				}
			}
			texte += "Itinéraire à suivre : \n";
			texte += itineraire.toString();
			texte += "\n----------------------------------------------------------------------------------------------\n\n";
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
