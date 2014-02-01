package ph.edu.upm.dpsm.cmsc130.mcquine;

import java.math.*;
import java.util.*;

import ph.edu.upm.dpsm.cmsc130.mcquine.model.*;

public class McQuineController {
	private final int LITERAL_COUNT;
	private Implicant[] implicants;

	private ArrayList<McQuineTable> mcquineTables;

	public static void main(String[] args) {
		McQuineController engine = new McQuineController("15 4 6 7 8 9 10 11 1");
		engine.runQuineMcCluskey();
	}

	/**
	 * Controller constructor. Initializes the mcquine engine. 
	 * @param input string containing the minterms separated by space.
	 */
	public McQuineController(String input) {
		System.out.println("Initializing Quine McCluskey data structures...\n");

		String[] tokens = input.split(" ");
		int MINTERM_COUNT = tokens.length;
		int[] minterms = new int[MINTERM_COUNT];

		for(int i = 0; i < MINTERM_COUNT; i++){ //get minterms
			try{
				minterms[i] = Integer.parseInt(tokens[i]);
			}
			catch(NumberFormatException e){
				System.out.println("ERROR: The input contains a non-numerical value.");
				e.printStackTrace();
			}
		}

		Arrays.sort(minterms);
		System.out.print("Minterms:\t");
		print(minterms);

		LITERAL_COUNT = evaluateLiteralCount(minterms[MINTERM_COUNT-1]);
		System.out.println("Number of Literals:\t" + LITERAL_COUNT);

		implicants = new Implicant[MINTERM_COUNT];

		System.out.println("\nInitializing list of implicants...\n");
		String raw, formatted;
		int[] minterm, weightPositions;
		for(int i = 0; i < MINTERM_COUNT; i++){
			minterm = new int[]{minterms[i]};
			raw = Integer.toBinaryString(minterms[i]);
			formatted = String.format("%0"+LITERAL_COUNT+"d", new BigInteger(raw));
			weightPositions = getWeightPositions(formatted);
			implicants[i] = new Implicant(minterm, formatted, weightPositions);
		}

		print(implicants);

		/*
		 * Insert other initializations here....
		 */
		mcquineTables = new ArrayList<McQuineTable>();
	}

	private int[] getWeightPositions(String binaryString) {
		ArrayList<Integer> weightList = new ArrayList<Integer>();
		String s = new StringBuilder(binaryString).reverse().toString();
		char bit = (s.contains("-")) ? '-':  '1';
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == bit){
				weightList.add(new Double(Math.pow(2, i)).intValue());
			}
		}
		
		System.out.println(weightList.toArray());
		
		return null;
	}

	/**
	 * Evaluates the number of literals using the maximum/highest minterm value.
	 * @param maxMinterm the maximum/highest minterm value. 
	 * @return number of literals/variables to use for the boolean expression.
	 */
	private int evaluateLiteralCount(int maxMinterm){
		int exponent = 0;
		while(maxMinterm > Math.pow(2, exponent)){
			exponent++;
		}
		return exponent; 
	}


	/**
	 * Runs the core algorithm of...
	 * 
	 */
	public void runQuineMcCluskey(){
		System.out.println("\nRunning Quine McCluskey...");

		int size = LITERAL_COUNT;
		McQuineTable bufferTable = new McQuineTable(size+1);

		for(Implicant imp : implicants){ 
			bufferTable.addImplicant(imp.getBitCount(), imp);
		}

		mcquineTables.add(bufferTable); //simple addition for first table

		ArrayList<Implicant> impList, compareList;
		HashMap<Integer, ArrayList<Implicant>> bufferSections;
		while(bufferTable.isComparable()){
			impList = bufferTable.getImplicantList();
			bufferSections = bufferTable.getMcQuineSections();

			for(Implicant imp : impList){
				for(Map.Entry<Integer, ArrayList<Implicant>> section : bufferSections.entrySet()){
					if(imp.getBitCount()+1 == section.getKey()){
						for(Implicant compareImp : section.getValue()){
							if(bufferTable.getSize() == size+1){ //if true, the implicants have no dash positions, they are contained in the first mcquine table
								System.out.println("The implicants are from the first McQuineTable!");

							}
							else{
								System.out.println(imp.getWeightPositions() + "*");
								System.out.println(compareImp.getWeightPositions() + "*");
								if(imp.getWeightPositions() == compareImp.getWeightPositions()){
									System.out.println("comparing " + imp.getBinaryValue() + " and " + compareImp.getBinaryValue());

								}
							}
						}
					}
				}
			}			

			bufferTable = new McQuineTable(size--); //initialization for new table
			System.out.println("size for the next loop: " + size);

			System.exit(1);


			//bufferTable = new McQuineTable(sections.size()-1);

			System.exit(1);

			mcquineTables.add(bufferTable);
		};

		System.out.println("Comparison is complete.");

		for(int i = 0; i < mcquineTables.size(); i++){
			System.out.println("\n**************************Table "+i+"************************\n");
			mcquineTables.get(i).printTable();
		}
	}

	private void print(Integer[] items){
		for(int item : items){
			System.out.print(item + " ");
		}
		System.out.println();
	}

	private void print(int[] minterms){
		for(int item : minterms){
			System.out.print(item + " ");
		}
		System.out.println();
	}

	private void print(Implicant[] implicants) {
		for(Implicant item : implicants){
			System.out.println(item.toString());
		}
	}
}
