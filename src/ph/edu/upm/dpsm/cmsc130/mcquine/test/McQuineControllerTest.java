package ph.edu.upm.dpsm.cmsc130.mcquine.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ph.edu.upm.dpsm.cmsc130.mcquine.McQuineController;

public class McQuineControllerTest {
	McQuineController engine;

//	@Test
//	public void testa() {
//		String expression = "b'c'd+a'bd'+ab'+bcd";
//		engine = new McQuineController("1 4 6 7 8 9 10 11 15");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
	
//	@Test
//	public void testb() {
//		String expression = "b'd'+bd+cd'+ad'";
//		engine = new McQuineController("0 2 5 6 7 8 10 12 13 14 15");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
	
//	@Test
//	public void testc() {
//		String expression = "1";
//		engine = new McQuineController("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
	
//	@Test
//	public void testd() {
//		String expression = "a'd'+ab'+bc'+a'c";
//		engine = new McQuineController("0 2 3 4 5 6 7 8 9 10 11 12 13");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void teste() {
//		String expression = "b'";
//		engine = new McQuineController("0 2");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testf(){
//		String expression = "a'b'c'+b'd'+ac";
//		engine = new McQuineController("0 1 2 8 10 11 14 15");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testg() {
//		String expression = "b'";
//		engine = new McQuineController("0 2");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testh() {
//		String expression = "a'b'+ab";
//		engine = new McQuineController("0 3");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testi() {
//		String expression = "b+a";
//		engine = new McQuineController("1 2 3");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testj() {
//		String expression = "bc+ac'";
//		engine = new McQuineController("3 4 6 7");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testk() {
//		String expression = "c'+ab'";
//		engine = new McQuineController("0 2 4 5 6");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testl() {
//		String expression = "a'b+ab'";
//		engine = new McQuineController("2 3 4 5");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testm() {
//		String expression = "c'+a'd'+bd'";
//		engine = new McQuineController("0 1 2 4 5 6 8 9 12 13 14");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testn() {
//		String expression = "b'd'+bd+b'c+ab'";
//		engine = new McQuineController("0 2 3 5 7 8 9 10 11 13 15");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
//	
//	@Test
//	public void testo() {
//		String expression = "a'b'e'+bd'e+ace";
//		engine = new McQuineController("0 2 4 6 9 13 21 23 25 29 31");
//		engine.runQuineMcCluskey();
//		assertTrue("", expression.equals(engine.getOutput()));
//	}
	
	@Test
	public void testq() {
		String expression = "";
		engine = new McQuineController("0 2 4 6 9 13 21 23 25 29 31");
		engine.runQuineMcCluskey();
		assertTrue("", expression.equals(engine.getOutput()));
	}

}