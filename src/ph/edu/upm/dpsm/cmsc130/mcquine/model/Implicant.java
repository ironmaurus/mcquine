package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.*;

public class Implicant{
	private int bitCount;
	private int[] minterms, weightPositions;
	private String binaryValue;
	private boolean isPaired; 
	
	public Implicant(int[] minterms, String binaryValue) {
		this.binaryValue = new String(binaryValue);
		this.minterms = minterms;
		setWeightPositions(binaryValue);
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
	
	public void setWeightPositions(String binaryString){
		ArrayList<Integer> weightList = new ArrayList<Integer>();
		String s = new StringBuilder(binaryString).reverse().toString();
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == '-'){
				weightList.add(new Double(Math.pow(2, i)).intValue());
			}
		}
		
		weightPositions = new int[weightList.size()];
		for(int i = 0; i < weightPositions.length; i++){
			weightPositions[i] = weightList.get(i);
		}
	}
	
	public String getWeightPositions(){
		return Arrays.toString(weightPositions);
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
		return bitCount + " " + Arrays.toString(minterms) + " " + binaryValue + " " + isPaired + " " + Arrays.toString(weightPositions);
	}	
}
