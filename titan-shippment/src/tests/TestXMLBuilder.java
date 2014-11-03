package tests;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import utils.XMLBuilder;

public class TestXMLBuilder {
	
	@Test
	public void testWellformedness(){
	XMLBuilder builder = new XMLBuilder("./XML/livraison10x10-1.xml");
		Assert.assertTrue(builder.checkWellformedness());
	}
	
	/*
	Utilisé pour tester séparément la méthode setType()
	Requiert de mettre cette méthode publique au préalable
	Cette méthode est maintenant nested dans checkWellformedness (test above),
	ce test est donc obsolète
	
	@Test
	public void testType(){
		XMLBuilder livraison = new XMLBuilder("./XML/livraison10x10-1.xml");
		livraison.setType();
		XMLBuilder plan = new XMLBuilder("./XML/plan20x20.xml");
		plan.setType();
		Assert.assertEquals(1, livraison.getType());
		Assert.assertEquals(0, plan.getType());
		XMLBuilder fake = new XMLBuilder("./XML/livraison10x10-copy.xml");
		fake.setType();
		Assert.assertEquals(2, fake.getType());
	}
	*/
	
	@Test
	public void testDataPlan(){
		XMLBuilder plan = new XMLBuilder("./XML/plan20x20.xml");
		Assert.assertTrue(plan.checkWellformedness());
		ArrayList<ArrayList> testData = plan.getData();
		for(int i = 0; i< testData.size();i++){
			for(int j = 0; j<testData.get(i).size();j++){
				System.out.print(testData.get(i).get(j) +"\t");
			}
			System.out.print("\n");
		}
	}
}
