package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.*;
import java.util.Map.Entry;

public class PrimeImplicant {
	private Implicant imp;
	private HashMap<Integer, Boolean> mintermList;
	
	public PrimeImplicant(Implicant imp, int[] mintermList){
		this.imp = imp;
		this.mintermList= new HashMap<Integer, Boolean>();
		initializeMintermList(mintermList);
	}

	private void initializeMintermList(int[] mintlist) {
		for(Integer mint : mintlist){
			mintermList.put(mint, false);
		}
	}
	
	public void markMinterm(int minterm, boolean key){
		mintermList.put(minterm, key);
	}
	
	public boolean contains(int minterm){
		for(int mint : imp.getMinterms()){
			if(mint == minterm){
				return true;
			}
		}
		return false;
	}
	
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
		
		return s;
	}
	
	public int getMarkCount(){
		int count = 0;
		
		for(Entry<Integer, Boolean> mint : mintermList.entrySet()){
			if(mint.getValue() == true){
				count++;
			}
		}
		
		return count;
	}
	
	public Implicant getImplicant(){
		return imp;
	}
}
