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
			implicants[i] = new Implicant(minterm, formatted);
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
	 * 
	 */
	public void runQuineMcCluskey(){
		System.out.println("\nRunning Quine McCluskey...");
		
		/*
		 * 1. Group the minterms based on the number of 1's contained 
		 * in each binary representation.
		 * -establish list of implicant groups
		 * -add to table
		 */
		
		McQuineTable bufferTable;
		
		int bitCount;
		String format;
		for(Implicant imp : implicants){
			bitCount = Integer.bitCount(Integer.parseInt(imp.getMintermList()));

			format = String.format("%"+LITERAL_COUNT+"s | %2s | %s", imp.getBinaryValue(), imp.getMintermList(), bitCount);
			System.out.println(format);
		}
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
