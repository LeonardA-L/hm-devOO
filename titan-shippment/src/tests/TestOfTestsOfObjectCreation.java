package tests;

import static org.junit.Assert.assertSame;
import main.Main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestOfTestsOfObjectCreation {

	private Main m;

	@Before
	public void setUp() throws Exception {
		m = new Main();
	}

	@After
	public void tearDown() throws Exception {
		m = null;
	}

	/**
	 * Make sure that m has the correct class.
	 */
	@Test
	public void test() {
		Class classOfM = m.getClass();
		Class main = Main.class;
		assertSame(main, classOfM);
	}

}
