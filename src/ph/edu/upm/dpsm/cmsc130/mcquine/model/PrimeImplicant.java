package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.*;
import java.util.Map.Entry;

/**
 * Represents a prime implicant class.
 * @author ironmaurus
 *
 */
public class PrimeImplicant {
	/**
	 * The given implicant.
	 */
	private Implicant imp;
	
	/**
	 * A hashmap mapping the minterms and its corresponding mark given by the implicant.
	 */
	private HashMap<Integer, Boolean> mintermList;
	
	/**
	 * PrimeImplicant constructor.
	 * @param imp
	 * @param mintermList
	 */
	public PrimeImplicant(Implicant imp, int[] mintermList){
		this.imp = imp;
		this.mintermList= new HashMap<Integer, Boolean>();
		initializeMintermList(mintermList);
	}

	/**
	 * Initializes the list of minterms.
	 * @param mintlist
	 */
	private void initializeMintermList(int[] mintlist) {
		for(Integer mint : mintlist){
			mintermList.put(mint, false);
		}
	}
	
	/**
	 * Marks a minterm.
	 * @param minterm
	 * @param key
	 */
	public void markMinterm(int minterm, boolean key){
		mintermList.put(minterm, key);
	}
	
	/**
	 * Checks if the given minterm is in the prime implicant's list.
	 * @param minterm
	 * @return true if the list contains the minterm, else false.
	 */
	public boolean contains(int minterm){
		for(int mint : imp.getMinterms()){
			if(mint == minterm){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a string representation of a prime implicant.
	 */
	public String toString(){
		String s = new String("");
		for(Entry<Integer, Boolean> mint : mintermList.entrySet()){
			s+="| ";
			if(mint.getValue() == true){
				s += "x";
			}
			else{
				s +="_";
			}
			s+=" |";
		}
		
		s += "\t" + Arrays.toString(imp.getMinterms());
		s+= "\t" + this.getMarkCount();
		return s;
	}
	
	/**
	 * Gets the number of marks of a prime implicant.
	 * @return number of marks
	 */
	public int getMarkCount(){
		int count = 0;
		
		for(Entry<Integer, Boolean> mint : mintermList.entrySet()){
			if(mint.getValue() == true){
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Gets the implicant data structure of the prime implicant. 
	 * @return Implicant
	 */
	public Implicant getImplicant(){
		return imp;
	}
}
