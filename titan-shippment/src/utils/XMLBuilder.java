package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Plan;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

//verifications de la coherence des fichiers XML
//fournir une API qui permet de recuperer les donnees utiles pour ensuite instancier les objets livraison, plan 
//dans une autre classe/interface
public class XMLBuilder {

	/* this var is declared here because it will be found when 
	/ calling to getLivraisons, but transmitted to interface agglo while
	/ the getLivraison method is called by interface planning
	*/
	private static int t_entrepot = -1;
	private static boolean entrepotReady = false;
	
	//vérifie si le fichier est lisible et bien formé, et que l'uppermost balise est bien <Reseau> (pour un plan)
	//ou <JourneeType> (pour une liste de livraisons)
	//modifier pour intégrer une validation against DTD
	public static boolean checkWellformedness(String file) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		try{
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setErrorHandler(new SimpleErrorHandler());
			reader.parse(new InputSource(file));
		} catch (SAXException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (ParserConfigurationException e) {
			return false;
		}
		return true;
	}
	
	
	//détermine s'il s'agit d'un plan ou d'une liste ed livraisons. A appeler a chaque chargement d'un xml.
	//Note : public pour les tests. Eventuellement à mettre privé plus tard.
	private static int getType(BufferedReader reader) throws IOException{
			    String line = null;
			    for(int i = 0; i<2; i++){
			    	line = reader.readLine();
			    }
			    if(line.contains("Reseau")){
			    	return 0;
			    } else if(line.contains("JourneeType")){
			    	return 1;
				} else
			    	return 2;
	}

	
	//Erreurs prises en charge : longueur négative, vitesse négative, tronçon : arrivée = destination
	//rapports d'erreur envoyés sur System.err
	//Renvoie un plan vide en cas d'erreur
	public static Plan getPlan(String filename, InterfaceAgglo intf)
	{
		Plan plan = intf.getPlan();
		Path path = (new File(filename)).toPath();
		try (InputStream in = Files.newInputStream(path);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			
			if(!checkWellformedness(filename)){
				intf.resetPlan();
				return null;
				//TODO
			}
			
			if(getType(reader) != 0){
				throw new IOException("The input file does not match the expected structure.");
			}
			    String line = null;

			    while ((line = reader.readLine()) != null) {
			    	int idNoeud=  0;
			    	
			    	if(line.contains("<Noeud")){
			    		int index = line.indexOf("id=")+4;
			    		idNoeud = Integer.parseInt(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("x=", index)+3;
			    		int x = Integer.parseInt(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("y=", index)+3;
			    		int y = Integer.parseInt((line.substring(index, line.indexOf("\"", index))));
			    		plan.addNoeud(x, y, idNoeud);
			    	}			    	
			    }
			    
			    if(in != null) {
			    	in.close();
			    }
			    InputStream in1 = Files.newInputStream(path);
			    BufferedReader reader1 = new BufferedReader(new InputStreamReader(in1));
			    line = null;
			    int idNoeud = 0;
			    while ((line = reader1.readLine()) != null) {
			    	if(line.contains("<Noeud")){
			    		int index = line.indexOf("id=")+4;
			    		idNoeud= Integer.parseInt(line.substring(index, line.indexOf("\"", index)));
			    	}
			    	
			    	while(line.contains("<LeTronconSortant"))
			    	{
			    		int index = line.indexOf("nomRue=")+8;
			    		String rue = line.substring(index, line.indexOf("\"", index));
			    		index = line.indexOf("vitesse=", index)+9;
	
			    		float vitesse = Float.parseFloat(line.substring(index, line.indexOf("\"", index)).replace(',', '.'));
			    		if(vitesse < 0){
			    			throw new NumberFormatException("Negative speed");
			    		}
			    		index = line.indexOf("longueur=", index)+10;
			    		float longueur = Float.parseFloat( line.substring(index, line.indexOf("\"", index)).replace(',', '.'));
			    		if(longueur < 0){
			    			throw new NumberFormatException("Negative length");
			    		}
			    		index = line.indexOf("idNoeudDestination=", index)+20;
			    		int idDestination = Integer.parseInt(line.substring(index, line.indexOf("\"", index)));
			    		if(idNoeud == idDestination){
			    			throw new Exception("Id is identical to destination for node" + idNoeud);
			    		}
			    		plan.addTronconToNoeud(idNoeud, rue, vitesse, longueur, idDestination);
			    		line = reader1.readLine();
			    	}
			    }
			    
			    if(in1 != null) {
			    	in1.close();
			    }
		} catch (NoSuchFileException e) {
			//System.out.println("Fichier introuvable :" + e.getFile().toString());
			intf.resetPlan();
			return null;
		} catch (NumberFormatException e) {
			intf.resetPlan();
			return null;
		} catch (Exception e){
			intf.resetPlan();
			return null;
		}
		return plan ;
		
	}


/**
 * Xml parser and livraisons builder.
 * Lines commmented with "// **** " are deprecated in my opinion.
 * The methods parses the xml file containing livraisons and add them 
 * "on the go" using the addLivraison method from InterfacePlanning.
 * #####################################################################"
 * # Not sure about error checks yet, a valid xml must be used for now #
 * #####################################################################"
 * 
 * @param filename		xml file name/path
 * @param intf			reference to the InterfacePlanning object
 * @return	boolean		true or false depending on the success
 */
public static boolean getLivraisons(String filename, model.planning.InterfacePlanning intf) {
		// **** ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		Path path = (new File(filename)).toPath();
		
		// temporary var
		String t_heureDebut = "";
		String t_heureFin = "";
		int t_idClient;
		int t_idLivraison;
		int t_adresseLivraison;
		
		try (InputStream in = Files.newInputStream(path);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			
			if(getType(reader) != 1) {
				throw new IOException("The input file does not match the expected structure.");
			}
			
		    String line = null;
		    // main loop through file
		    while ((line = reader.readLine()) != null) {
		    	if(line.contains("<Plage h")) {
		    		// **** ArrayList<String> node = new ArrayList<String>();
		    		int index = line.indexOf("heureDebut=")+12;	//12 = heureDebut.size()...
		    		// **** node.add(line.substring(index, line.indexOf("\"", index)));
		    		t_heureDebut = line.substring(index, line.indexOf("\"", index));	// *
		    		index = line.indexOf("heureFin=", index)+10;	
		    		// **** node.add(line.substring(index, line.indexOf("\"", index)));
		    		t_heureFin = line.substring(index, line.indexOf("\"", index));
		    		
		    		// **** data.add(node);
		    	}
		    	else if(line.contains("<Livraison id")){	// if only "<Livraison", it takes the line with <Livraisons> as well
		    		// **** ArrayList<String> node = new ArrayList<String>();
		    		int index = line.indexOf("id=")+4;
		    		t_idLivraison = Integer.parseInt(line.substring(index, line.indexOf('"', index)));
		    		// **** node.add(line.substring(index, line.indexOf("\"", index)));
		    		index = line.indexOf("client=", index)+8;
		    		t_idClient = Integer.parseInt(line.substring(index, line.indexOf('"', index)));
		    		// **** node.add(line.substring(index, line.indexOf("\"", index)));
		    		index = line.indexOf("adresse=", index)+9;
		    		t_adresseLivraison = Integer.parseInt(line.substring(index, line.indexOf('"', index)));
		    		
		    		boolean success = intf.AddLivraison(t_idClient, t_idLivraison, t_heureDebut, t_heureFin, t_adresseLivraison);
		    		System.out.println("Adding livraison (XmlBuilder) : \n--Client : "+t_idClient+"\n--Livraison : "+t_idLivraison+"\n--HeureDebut : "+t_heureDebut+"\n--HeureFin : "+t_heureFin+"\n--Address"+t_adresseLivraison);
		    		if(!success) {	// problem on interface side, stop parsing. 
		    			return false; 
		    		}
		    		// **** node.add(line.substring(index, line.indexOf("\"", index)));
		    		// **** data.add(node);
		    	}
		      	// get entrepot 
		    	else if(line.contains("<Entrepot")){
		    		//ArrayList<String> node = new ArrayList<String>();
		    		int index = line.indexOf("adresse=")+9;
		    		// **** node.add(line.substring(index, line.indexOf("\"", index)));
		    		// **** data.add(node);
		    		// **** verifier que l'on substring bien l'adresse uniquement et pas les guillemets autours.
		    		t_entrepot = Integer.parseInt(line.substring(index, line.indexOf("\"",index)));
		    	}
		    }
		    if(in != null) {
		    	in.close();
		    }
		    
		    return true;
		    
		} catch (IOException e) {
			return false;
		}
	}

	// allows the interfaces to get the entrepot number when it has been read
	public static int getEntrepot() {
		if (entrepotReady) {
			return t_entrepot;
		}
		else{
			return -1;
		}
	}
	
}
