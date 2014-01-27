package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.Arrays;

public class Implicant{
	private int[] minterms;
	private String binaryValue;
	private boolean isPaired;
	
	public Implicant(int[] minterms, String binaryValue) {
		this.minterms = minterms;
		this.binaryValue = new String(binaryValue);
		setPaired(false);
	}

	public boolean isPaired() {
		return isPaired;
	}

	public void setPaired(boolean isPaired) {
		this.isPaired = isPaired;
	}
	
	public String toString(){
		return "Minterm: " + Arrays.toString(minterms) + "\tBinary Value: " + binaryValue + "\tisPaired: " + isPaired;
	}

	public String getBinaryValue() {
		return binaryValue;
	}
	
	public String getMinterms(){
		return Arrays.toString(minterms);
	}
}
