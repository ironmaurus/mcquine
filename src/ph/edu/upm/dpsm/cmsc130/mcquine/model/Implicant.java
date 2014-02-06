package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.*;

public class Implicant{
	private int bitCount;
	private int[] minterms;
	private String binaryValue;
	private boolean isPaired; 
	
	public Implicant(int[] minterms, String binaryValue) {
		this.binaryValue = new String(binaryValue);
		this.minterms = minterms;
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
	
	public int getMinterm(){
		return minterms[0];
	}
	
	public int[] getMinterms(){
		return minterms;
	}
	
	public int getMintermSize() {
		return minterms.length;
	}

	public boolean isPaired() {
		return isPaired;
	}
	
	public void setPaired(boolean isPaired) {
		this.isPaired = isPaired;
	}
	
	public String getBinaryValue(){
		return binaryValue;
	}
	
	public String toString(){
		return String.format("| %8d | %11s | %5s | %s ", bitCount, binaryValue, isPaired, Arrays.toString(minterms));
	}	
}
