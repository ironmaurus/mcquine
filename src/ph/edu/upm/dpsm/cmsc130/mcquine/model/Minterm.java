package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.ArrayList;

public class Minterm extends ArrayList{
	int decimalRep;
	String binaryRep;
	
	public Minterm(int decimalRep) {
		this.decimalRep = decimalRep;
		this.binaryRep = Integer.toBinaryString(decimalRep);		
	}
}
