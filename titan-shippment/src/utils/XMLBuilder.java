package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.agglomeration.InterfaceAgglo;
import model.agglomeration.Noeud;
import model.agglomeration.Plan;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

//verifications de la coherence des fichiers XML
//fournir une API qui permet de recuperer les donnees utiles pour ensuite instancier les objets livraison, plan 
//dans une autre classe/interface
public class XMLBuilder {


	
	//v�rifie si le fichier est lisible et bien form�, et que l'uppermost balise est bien <Reseau> (pour un plan)
	//ou <JourneeType> (pour une liste de livraisons)
	//modifier pour int�grer une validation against DTD
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
	
	
	//d�termine s'il s'agit d'un plan ou d'une liste ed livraisons. A appeler a chaque chargement d'un xml.
	//Note : public pour les tests. Eventuellement � mettre priv� plus tard.
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

	//lecture ligne par ligne de tout le fichier.
	//� chaque ligne : d�pecer le markup, r�cup�rer les donn�es
	//quel format pour les donn�es en sortie ?
	//! cas d'erreur nested dans le code, � sortir dans une m�thode
	
	//Erreurs prises en charge : longueur n�gative, vitesse n�gative, tron�on : arriv�e = destination
	//rapports d'erreur envoy�s sur System.err
	public static Plan getPlan(String filename, InterfaceAgglo intf)
	{
		if(!checkWellformedness(filename)){
			//TODO
			//Les rapports d'erreur du v�rificateur de syntaxe sont envoy�s sur System.err par le SimpleErrorHandler
		}
		Plan plan = intf.getPlan();
		Path path = (new File(filename)).toPath();
		try (InputStream in = Files.newInputStream(path);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
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
			    
			    if(in != null)
			    	in.close();
			    InputStream in1 = Files.newInputStream(path);
			    BufferedReader reader1 = new BufferedReader(new InputStreamReader(in1));
			    line = null;
			    while ((line = reader1.readLine()) != null) {
			    	int idNoeud = 0; 
			    	if(line.contains("<Noeud")){
			    		int index = line.indexOf("id=")+4;
			    		idNoeud= Integer.parseInt(line.substring(index, line.indexOf("\"", index)));
			    		reader1.readLine();
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
			    		
			    		Noeud destinationNode = plan.getNoeudById(idDestination);
			    		plan.addTronconToNoeud(idNoeud, rue, vitesse, longueur, idDestination);
			    		line = reader1.readLine();
			    	}
			    	
			    	
			    	
			    }
			    if(in1 != null)
			    	in1.close();
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			//TODO
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return plan ;
		
	}

/*	
 * Waiting
public Livraison getLivraison(String filename, model.agglomeration.InterfaceAgglo intf){
		
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		Path path = (new File(filename)).toPath();
		try (InputStream in = Files.newInputStream(path);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			
			if(getType(reader) != 1){
				throw new IOException("The input file does not match the expected structure.");
			}
			    String line = null;
			    
	
			    while ((line = reader.readLine()) != null) {
			    	if(line.contains("<Entrepot")){
			    		ArrayList<String> node = new ArrayList<String>();
			    		int index = line.indexOf("adresse=")+9;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		data.add(node);
			    	}
			    	//nomRue="v0" vitesse="4,400000" longueur="300,200000" idNoeudDestination="1"
			    	else if(line.contains("<Plage h")){
			    		ArrayList<String> node = new ArrayList<String>();
			    		int index = line.indexOf("heureDebut=")+12;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("heureFin=", index)+10;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		data.add(node);
			    	}
			    	else if(line.contains("<Livraison")){
			    		ArrayList<String> node = new ArrayList<String>();
			    		int index = line.indexOf("id=")+4;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("client=", index)+8;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("adresse=", index)+9;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		data.add(node);
			    	}
			    }
			    if(in != null)
			    	in.close();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}*/

}
