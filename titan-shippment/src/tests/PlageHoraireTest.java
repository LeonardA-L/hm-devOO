package tests;

import static org.junit.Assert.*;
import model.planning.PlageHoraire;

import org.junit.Before;
import org.junit.Test;

public class PlageHoraireTest {

	private PlageHoraire ph;
	
	@Before
	public void setUp() {
		String heureDebut = "12:00:00";
		String heureFin = "13:00:00";
		ph = new PlageHoraire(heureDebut, heureFin);
	}
	
	@Test
	public void bounds() {
		int[] bounds = ph.getBounds();
		assertTrue(bounds[0] == 12*60*60);
		assertTrue(bounds[1] == 13*60*60);
	}

}
