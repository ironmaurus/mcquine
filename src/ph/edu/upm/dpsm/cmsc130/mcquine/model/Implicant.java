package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.*;

public class Implicant{
	private int[] minterms, dashPositions;
	private int bitCount;
	private String binaryValue;
	private boolean isPaired;
	
	
	public Implicant(int[] minterms, String binaryValue, int[] dashPositions) {
		this.minterms = minterms;
		this.binaryValue = new String(binaryValue);
		this.dashPositions = dashPositions;
		setPaired(false);
		setBitCount();
	}
	
	private void setBitCount(){
		bitCount = 0;
		for(char digit : binaryValue.toCharArray()){
			if(digit == '1'){
				bitCount++;
			}
		}
	}
	
	public int getBitCount(){
		return bitCount;
	}
	
	public String getDashPositions(){
		return Arrays.toString(dashPositions);
	}

	public boolean isPaired() {
		return isPaired;
	}
	
	public void setPaired(boolean isPaired) {
		this.isPaired = isPaired;
	}
	
	public String toString(){
		return bitCount + "\t\t" + Arrays.toString(minterms) + "\t\t" + binaryValue + "\t\t" + Arrays.toString(dashPositions) + "\t" + isPaired;
	}
	
	public String getBinaryValue(){
		return binaryValue;
	}
}
