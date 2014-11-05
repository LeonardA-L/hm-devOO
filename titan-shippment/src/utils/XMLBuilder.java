package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import model.agglomeration.Noeud;

//verifications de la coherence des fichiers XML
//fournir une API qui permet de recuperer les donnees utiles pour ensuite instancier les objets livraison, plan 
//dans une autre classe/interface
public class XMLBuilder {
	
	//----attributs
	private String file;
	private int type;
	
	private class SimpleErrorHandler implements ErrorHandler {
	    public void warning(SAXParseException e) throws SAXException {
	        System.out.println(e.getMessage());
	    }

	    public void error(SAXParseException e) throws SAXException {
	        System.out.println(e.getMessage());
	    }

	    public void fatalError(SAXParseException e) throws SAXException {
	        System.out.println(e.getMessage());
	    }
	}
	
	
	
	//private enum Type{
		//LIVRAISON, PLAN, NONE
	//}
	//deux "types" de xml possibles : livraison et plan
	//plan : <Reseau> <Noeud> <LeTronconSortant/> </Noeud> </Reseau>
	//livraison : <JourneeType> <Entrepot/>	<PlagesHoraires> <Plage> <Livraisons> <Livraison/> </Livraisons> </Plage> </PlagesHoraires> </JourneeType>
	//identifier s'il s'agit de l'un ou de l'autre : 
	//passer par-dessus la balise <?xml ?>
	//la deuxième balise contient logiquement <Reseau> ou <JourneeType>
	


	
	
	
	
	
	
	
	
	//----constructeurs
	public XMLBuilder(String path){
		file = path;
	}
	
	
	//----methodes

	
	//vérifie si le fichier est lisible et bien formé, et que l'uppermost balise est bien <Reseau> (pour un plan)
	//ou <JourneeType> (pour une liste de livraisons)
	public boolean checkWellformedness(){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		try{
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setErrorHandler(new SimpleErrorHandler());
			reader.parse(new InputSource(file));
		} catch (SAXException e) {
			System.out.println(e.toString());
			return false;
		} catch (IOException e) {
			System.out.println(e.toString());
			return false;
		} catch (ParserConfigurationException e) {
			System.out.println(e.toString());
			return false;
		}
		
		setType();
		if(getType() == 0 || getType() == 1)
			return true;
		else 
			return false;
	}
	
	//renvoie le type. Utilisé pour les tests.
	public int getType(){
		return type;
	}
	
	//détermine s'il s'agit d'un plan ou d'une liste ed livraisons. A appeler a chaque chargement d'un xml.
	//Note : public pour les tests. Eventuellement à mettre privé plus tard.
	private void setType(){
		Path path = (new File(file)).toPath();
		try (InputStream in = Files.newInputStream(path);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			    String line = null;
			
			    for(int i = 0; i<2; i++){
			    	line = reader.readLine();
			    }
			    //while ((line = reader.readLine()) != null) {
			        //System.out.println(line);  }
			    if(line.contains("Reseau"))
			    	type = 0;
			    else if(line.contains("JourneeType"))
			    	type = 1;
			    else
			    	type = 2;
			    if(in != null)
			    	in.close();
			} catch (IOException x) {
			    System.err.println(x);
			}
	}

	//lecture ligne par ligne de tout le fichier.
	//à chaque ligne : dépecer le markup, récupérer les données
	//quel format pour les données en sortie ?
	public ArrayList getData(){
		
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		Path path = (new File(file)).toPath();
		try (InputStream in = Files.newInputStream(path);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			    String line = null;
			    
			if(getType()==0){//plan
				//format : un ArrayListString correspond à une ligne du XML
				//un ArrayList<String> de taille 4 décrit un "LeTronconSortant"
				//un ArrayList<String> de taille 3 décrit un Noeud
				//un Noeud contient donc toute sles lignes de taille 4 dans "l'arborescence"
	
			    while ((line = reader.readLine()) != null) {
			    	if(line.contains("<Noeud")){
			    		ArrayList<String> node = new ArrayList<String>();
			    		int index = line.indexOf("id=")+4;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("x=", index)+3;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("y=", index)+3;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		data.add(node);
			    	}
			    	//nomRue="v0" vitesse="4,400000" longueur="300,200000" idNoeudDestination="1"
			    	else if(line.contains("<LeTronconSortant")){
			    		ArrayList<String> node = new ArrayList<String>();
			    		int index = line.indexOf("nomRue=")+8;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("vitesse=", index)+9;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("longueur=", index)+10;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		index = line.indexOf("idNoeudDestination=", index)+20;
			    		node.add(line.substring(index, line.indexOf("\"", index)));
			    		data.add(node);
			    	}
			    }
			    if(in != null)
			    	in.close();
			    return data;
			
			}
			else if(getType() == 1){
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
