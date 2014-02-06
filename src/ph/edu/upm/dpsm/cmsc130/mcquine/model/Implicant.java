package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.*;

/**
 * Represents an Implicant class.
 * @author ironmaurus
 *
 */
public class Implicant{
	/**
	 * Number of 1-bits
	 */
	private int bitCount;
	/**
	 * Array of minterms in decimal representation.
	 */
	private int[] minterms;
	/**
	 * The binary representation of the Implicant.
	 */
	private String binaryValue;
	/**
	 * Boolean to check if the implicant is already compared. 
	 */
	private boolean isPaired; 
	
	/**
	 * Implicant constructor.
	 * @param minterms
	 * @param binaryValue
	 */
	public Implicant(int[] minterms, String binaryValue) {
		this.binaryValue = new String(binaryValue);
		this.minterms = minterms;
		setPaired(false);
		setBitCount();
	}
	
	/**
	 * Sets the number of bits based on the binary representation.
	 */
	private void setBitCount(){
		bitCount = 0;
		for(char digit : binaryValue.toCharArray()){
			if(digit == '1'){
				bitCount++;
			}
		}
	}
	
	/**
	 * Gets the bit count of the implicant.
	 * @return number of bits.
	 */
	public int getBitCount(){
		return bitCount;
	}
	
	/**
	 * Gets the first minterm.
	 * @return int
	 */
	public int getMinterm(){
		return minterms[0];
	}
	
	/**
	 * Gets the array of minterms.
	 * @return array of int
	 */
	public int[] getMinterms(){
		return minterms;
	}
	
	/**
	 * Gets the number of minterms.
	 * @return integer
	 */
	public int getMintermSize() {
		return minterms.length;
	}

	/**
	 * Gets the boolean that tells if the implicant is already compared or not.
	 * @return boolean
	 */
	public boolean isPaired() {
		return isPaired;
	}
	
	/**
	 * Sets if the boolean is already compared or not.
	 * @param isPaired
	 */
	public void setPaired(boolean isPaired) {
		this.isPaired = isPaired;
	}
	
	/**
	 * Gets the binary representation of the implicant.
	 * @return String
	 */
	public String getBinaryValue(){
		return binaryValue;
	}
	
	/**
	 * Returns the string representation of the implicant. 
	 */
	public String toString(){
		return String.format("| %8d | %11s | %5s | %s ", bitCount, binaryValue, isPaired, Arrays.toString(minterms));
	}	
}
