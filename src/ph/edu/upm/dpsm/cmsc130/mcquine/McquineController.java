package ph.edu.upm.dpsm.cmsc130.mcquine;

import java.math.*;
import java.util.*;

import ph.edu.upm.dpsm.cmsc130.mcquine.model.*;

public class McQuineController {
	private final int LITERAL_COUNT;
	private Implicant[] implicants;

	private ArrayList<McQuineTable> mcquineTables;

	public static void main(String[] args) {
		McQuineController engine = new McQuineController("1 4 6 7 8 9 10 11 15");
		//McQuineController engine = new McQuineController("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
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
		int[] mintermList;
		for(int i = 0; i < MINTERM_COUNT; i++){
			mintermList = new int[]{minterms[i]};
			raw = Integer.toBinaryString(minterms[i]);
			formatted = String.format("%0"+LITERAL_COUNT+"d", new BigInteger(raw));
			//weightPositions = setWeightPositions(formatted);
			implicants[i] = new Implicant(mintermList, formatted);
			//implicants[i].setWeightPositions(formatted);
		}

		print(implicants);

		/*
		 * Insert other initializations here....
		 */
		mcquineTables = new ArrayList<McQuineTable>();
	}

	//	private int[] getWeightPositions(String binaryString) {
	//		ArrayList<Integer> weightList = new ArrayList<Integer>();
	//		String s = new StringBuilder(binaryString).reverse().toString();
	//		char bit = (s.contains("-")) ? '-':  '1';
	//		for(int i = 0; i < s.length(); i++){
	//			if(s.charAt(i) == bit){
	//				weightList.add(new Double(Math.pow(2, i)).intValue());
	//			}
	//		}
	//		
	//		System.out.println(weightList.toString());
	//		
	//		int[] weightPos = new int[weightList.size()];
	//	    for (int i=0; i < weightPos.length; i++)
	//	    {
	//	        weightPos[i] = weightList.get(i).intValue();
	//	    }
	//		
	//		return weightPos;
	//	}

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

		/*
		 * 1 add implicant list to a buffer table
		 * 2 loop while buffertable is comparable //bufferTable.isEmpty() == false
		 * 		compare implicantList
		 * 		store result to new list, update boolean of buffertable //buffertable updated, new implicant list
		 * 		store updated buffertable to mcquinetables
		 *      initialize buffertable with the new implicantlist
		 *      
		 * if buffertable is not comparable,
		 * 		store result in mcquinetables
		 * else
		 * 		continue loop     
		 */

		int size = LITERAL_COUNT;
		McQuineTable bufferTable = new McQuineTable(size+1);

		for(Implicant imp : implicants){ 
			bufferTable.addImplicant(imp.getBitCount(), imp);
		}

		String binaryResult;
		int[] mintermList;
		ArrayList<Implicant> above, below, impList = new ArrayList<Implicant>();
		Implicant imp1, imp2;
		HashMap<Integer, ArrayList<Implicant>> sections;
		while(bufferTable.isComparable()){
			System.out.println("------------------------------------------------------ " + bufferTable.getSize());

			//impList = bufferTable.getImplicantList();
			sections = bufferTable.getMcQuineSections();

			Map.Entry<Integer, ArrayList<Implicant>> secAbove = null;
			for(Map.Entry<Integer, ArrayList<Implicant>> secBelow : sections.entrySet()){
				if(secAbove != null){
					above = secAbove.getValue();
					below = secBelow.getValue();

					System.out.println("Comparing: "+secAbove.getKey() + "||" + secBelow.getKey());

					for(int i = 0; i < above.size(); i++){
						for(int j = 0; j < below.size(); j++){
							imp1 = above.get(i);
							imp2 = below.get(j);

							if(isComparable(imp1.getBinaryValue(), imp2.getBinaryValue())){
								System.out.println(imp1.getBinaryValue()+" || "+imp2.getBinaryValue());

								binaryResult = evaluateBinaryValue(imp1.getBinaryValue(), imp2.getBinaryValue());
								mintermList = combineMinterms(imp1.getMinterms(), imp2.getMinterms());

								impList.add(new Implicant(mintermList, binaryResult));

								imp1.setPaired(true);
								imp2.setPaired(true);
							}
						}
					}
				}
				secAbove = secBelow;
			}

			mcquineTables.add(bufferTable);
			bufferTable = new McQuineTable(size--);
			for(Implicant imp: impList){
				bufferTable.addImplicant(imp.getBitCount(), imp);
			}
			impList = new ArrayList<Implicant>();
		};

		System.out.println("Comparison is complete.");

		for(int i = 0; i < mcquineTables.size(); i++){
			System.out.println("\n**************************Table "+i+"************************\n");
			mcquineTables.get(i).printTable();

		}
	}

	private int[] combineMinterms(int[] mints1, int[] mints2) {
		ArrayList<Integer> mintBuffer = new ArrayList<Integer>();
		for(int i = 0; i < mints1.length; i++){
			mintBuffer.add(mints1[i]);
		}

		for(int i = 0; i < mints2.length; i++){
			mintBuffer.add(mints2[i]);
		}

		int[] mints = new int[mintBuffer.size()];
		for(int i = 0; i < mints.length; i++){
			mints[i] = mintBuffer.get(i);
		}

		return mints;
	}

	private String evaluateBinaryValue(String binary1, String binary2) {
		String result = new String("");
		for(int i = 0; i < binary1.length(); i++){
			if(binary1.charAt(i) == binary2.charAt(i)){
				result+= binary1.charAt(i);
			}
			else{
				result += "-";
			}
		}

		System.out.println(result);
		return result;
	}

	private boolean isPowerOfTwo(int n){
		return ((n & (n - 1)) == 0) ? true : false;
	}

	private boolean isComparable(String binary1, String binary2){
		int difference = 0;

		for(int i = 0; i < binary1.length(); i++){
			if(binary1.charAt(i) != binary2.charAt(i)){
				difference++;
			}
		}

		return (difference > 1) ? false : true;
	}

	private void print(ArrayList<Implicant> impList) {
		for(Implicant item : impList){
			System.out.println(item + " ");
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
