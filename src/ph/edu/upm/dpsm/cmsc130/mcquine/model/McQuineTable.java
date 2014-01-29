package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.ArrayList;
import java.util.HashMap;

public class McQuineTable {
	private final int size;
	private HashMap<Integer, ArrayList<Implicant>> implicantGroups; 
	
	public McQuineTable(int size){
		this.size = size;
		implicantGroups = new HashMap<Integer, ArrayList<Implicant>>();
		for(int bitCount = 0; bitCount <= size; bitCount++){
			 implicantGroups.put(bitCount, new ArrayList<Implicant>());
		}
	}

	public void addImplicant(int bitCount, Implicant imp) {
		ArrayList<Implicant> bufferGroup = implicantGroups.get(bitCount);
		bufferGroup.add(imp);
	}
	
	public void printTable(){
		ArrayList<Implicant> bufferGroup;
		for(int i = 0; i <= size; i++){
			System.out.println("-----------------BITCOUNT: "+i+" --------------------------");
			bufferGroup = implicantGroups.get(i);
			
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
}
