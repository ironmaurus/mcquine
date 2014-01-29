package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.*;

public class McQuineTable {
	private final int size;
	private HashMap<Integer, ArrayList<Implicant>> mcQuineSections; 
	
	public McQuineTable(int size){
		this.size = size;
		mcQuineSections = new HashMap<Integer, ArrayList<Implicant>>();
		for(int bitCount = 0; bitCount <= size; bitCount++){
			mcQuineSections.put(bitCount, new ArrayList<Implicant>());
		}
	}
	
	public int getSize(){
		return size;
	}

	public void addImplicant(int bitCount, Implicant imp) {
		ArrayList<Implicant> bufferGroup = mcQuineSections.get(bitCount);
		bufferGroup.add(imp);
	}
	
	public HashMap<Integer, ArrayList<Implicant>> getMcQuineSections(){
		return mcQuineSections;
	}
	
	public void printTable(){
		ArrayList<Implicant> bufferGroup;
		for(int i = 0; i <= size; i++){
			System.out.println("-----------------MCQUINESECTION: "+i+" --------------------------");
			bufferGroup = mcQuineSections.get(i);
			
			if(bufferGroup.isEmpty()){
				System.out.println("EMPTY!");
			}
			else{
				System.out.println("Minterms\tBinaryValue\tDashes\tisPaired");
				for(Implicant imp :  bufferGroup){
					System.out.println(imp.toString());
				}
			}
		}
	}

	public boolean isComparable() {
			return (mcQuineSections.size() > 1) ? true : false;
	}
}
