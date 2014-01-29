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
		int[] minterm;
		for(int i = 0; i < MINTERM_COUNT; i++){
			minterm = new int[]{minterms[i]};
			raw = Integer.toBinaryString(minterms[i]);
			formatted = String.format("%0"+LITERAL_COUNT+"d", new BigInteger(raw));
			implicants[i] = new Implicant(minterm, formatted, null);
		}

		print(implicants);
		
		/*
		 * Insert other initializations here....
		 */
		mcquineTables = new ArrayList<McQuineTable>();
	}

	/**
	 * Evaluates the number of literals using the maximum/highest minterm value.
	 * @param maxMinterm the maximum/highest minterm value. 
	 * @return number of literals/variables to use for the boolean expression.
	 */
	public int evaluateLiteralCount(int maxMinterm){
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
		McQuineTable bufferTable;
		
		bufferTable = new McQuineTable(size);
		/*
		 * Group implicants based on number of 1s 
		 */
		for(Implicant imp : implicants){ //Group 
			bufferTable.addImplicant(imp.getBitCount(), imp);
		}
		
		System.out.println("\nbufferTable:");
		
		mcquineTables.add(bufferTable);
		bufferTable.printTable();
		
		/*
		 * Compare sections to produce the next tables. Loop until all minterms compared 
		 */
		HashMap<Integer, ArrayList<Implicant>> sections;
		do{
			
			
			//bufferTable = new McQuineTable(sections.size()-1);
			
			sections = bufferTable.getMcQuineSections();
			
			
			for(int i = 0; i < bufferTable.getSize(); i++){
				
			}
		}while(bufferTable.isComparable());
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
