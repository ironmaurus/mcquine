package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.*;

/**
 * Represents a table especially designed for Quine-McCluskey
 * @author ironmaurus
 *
 */
public class McQuineTable {
	/**
	 * size of the table/ number of sections
	 */
	private final int size;
	/**
	 * Maps a list of implicant to its bit count( number of ones). 
	 */
	private HashMap<Integer, ArrayList<Implicant>> mcQuineSections; 
	
	/**
	 * McQuineTable constructor.
	 * @param size
	 */
	public McQuineTable(int size){
		this.size = size;
		mcQuineSections = new HashMap<Integer, ArrayList<Implicant>>();
		for(int bitCount = 0; bitCount < size; bitCount++){
			mcQuineSections.put(bitCount, new ArrayList<Implicant>());
		}
	}
	
	/**
	 * Gets the size of the table.
	 * @return int
	 */
	public int getSize(){
		return size;
	}

	/**
	 * Adds the implicant to one of its sections.
	 * @param bitCount
	 * @param imp
	 */
	public void addImplicant(int bitCount, Implicant imp) {
		ArrayList<Implicant> bufferGroup = mcQuineSections.get(bitCount);
		bufferGroup.add(imp);
	}
	
	/**
	 * Gets the sections of the table.
	 * @return the HashMap of the list of implicants and the corresponding bit count.
	 */
	public HashMap<Integer, ArrayList<Implicant>> getMcQuineSections(){
		return mcQuineSections;
	}
	
	/**
	 * Prints the section.
	 */
	public void printTable(){
		ArrayList<Implicant> bufferGroup;
		for(int i = 0; i < size; i++){
			
			bufferGroup = mcQuineSections.get(i);
			
			if(bufferGroup.isEmpty()){
				System.out.println("SECTION: "+i+"");
				System.out.println("EMPTY!");
			}
			else{
				System.out.println("SECTION: "+i+"");
				System.out.println("| BitCount | BinaryValue |  Mark | MintermList");
				for(Implicant imp :  bufferGroup){
					System.out.println(imp.toString());
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Gets the list of implicant.
	 * @return implicant list.
	 */
	public ArrayList<Implicant> getImplicantList(){
		ArrayList<Implicant> implicantList = new ArrayList<Implicant>();
		for(Map.Entry<Integer, ArrayList<Implicant>> entry : mcQuineSections.entrySet()){
			for(Implicant imp : entry.getValue()){
				implicantList.add(imp);
			}
		}

		return implicantList;
	}

	/**
	 * Checks if sections contained in the table can be compared with each other. That is, if there exists
	 * two or more sections.
	 * @return boolean
	 */
	public boolean isComparable() {
			return (mcQuineSections.size() > 1) ? true : false;
	}
}
