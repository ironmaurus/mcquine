package ph.edu.upm.dpsm.cmsc130.mcquine.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ph.edu.upm.dpsm.cmsc130.mcquine.McQuineController;

public class McQuineControllerTest {
	McQuineController engine;
	
	@Test
	public void test1() {
		String expression = "b'c'd+a'bd'+ab'+bcd";
		engine = new McQuineController("1 4 6 7 8 9 10 11 15");
		engine.runQuineMcCluskey();
		assertTrue("", expression.equals(engine.getOutput()));
	}
	
	@Test
	public void test2() {
		String expression = "b'd'+bd+cd'+ad'";
		engine = new McQuineController("0 2 5 6 7 8 10 12 13 14 15");
		engine.runQuineMcCluskey();
		assertTrue("", expression.equals(engine.getOutput()));
	}
	
	@Test
	public void test3() {
		String expression = "1";
		engine = new McQuineController("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
		engine.runQuineMcCluskey();
		assertTrue("", expression.equals(engine.getOutput()));
	}
	
	@Test
	public void test4() {
		String expression = "a'd'+ab'+bc'+a'c";
		engine = new McQuineController("0 2 3 4 5 6 7 8 9 10 11 12 13");
		engine.runQuineMcCluskey();
		assertTrue("", expression.equals(engine.getOutput()));
	}

}
