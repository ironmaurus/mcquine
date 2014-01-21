package ph.edu.upm.dpsm.cmsc130.mcquine.model;

public class Minterm {
	int decimalRep;
	String binaryRep;
	
	public Minterm(int decimalRep) {
		this.decimalRep = decimalRep;
		this.binaryRep = Integer.toBinaryString(decimalRep);		
	}
	
	
}
