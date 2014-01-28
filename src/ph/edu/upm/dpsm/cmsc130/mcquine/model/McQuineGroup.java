package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.ArrayList;

public class McQuineGroup {
	private int bitCount;
	private ArrayList<Implicant> implicants;
	
	public McQuineGroup(int bitCount) {
		this.bitCount = bitCount;
	}
	
	public void addImplicant(Implicant imp){
		implicants.add(imp);
	}
}
