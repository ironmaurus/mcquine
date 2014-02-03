package ph.edu.upm.dpsm.cmsc130.mcquine;

import java.math.*;
import java.util.*;

import ph.edu.upm.dpsm.cmsc130.mcquine.model.*;

public class McQuineController {
	private final int LITERAL_COUNT;
	private Implicant[] implicants;
	private int[] minterms;

	private ArrayList<McQuineTable> mcquineTables;
	private ArrayList<PrimeImplicant> primeImps, finalImps;
	private String output;

	public static void main(String[] args) {
		//McQuineController engine = new McQuineController("1 4 6 7 8 9 10 11 15");
		McQuineController engine = new McQuineController("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
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
		minterms = new int[MINTERM_COUNT];

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
		primeImps = new ArrayList<PrimeImplicant>();
		finalImps = new ArrayList<PrimeImplicant>();
		output = new String("");
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
		ArrayList<Implicant> above, below, impList;
		Implicant imp1, imp2;
		HashMap<Integer, ArrayList<Implicant>> sections;
		while(bufferTable.isComparable()){
			System.out.println("------------------------------------------------------ " + bufferTable.getSize());

			impList = new ArrayList<Implicant>();
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

							if(difference(imp1.getBinaryValue(), imp2.getBinaryValue()) == 1){
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
		};

		System.out.println("Comparison is complete.");

		for(int i = 0; i < mcquineTables.size(); i++){
			System.out.println("\n**************************Table "+i+"************************\n");
			mcquineTables.get(i).printTable();
		}

		ArrayList<Implicant> primeImplicants = new ArrayList<Implicant>();
		boolean duplicate = false;
		for(McQuineTable table : mcquineTables){
			for(Implicant imp : table.getImplicantList()){
				if(imp.isPaired() == false){
					for(Implicant pi : primeImplicants){
						if(imp.getBinaryValue().equals(pi.getBinaryValue())){
							duplicate = true;
						}
					}

					if(duplicate == false){
						primeImplicants.add(imp);
					}

				}
			}
		}

		printTable(primeImplicants);

		primeImps = new ArrayList<PrimeImplicant>();
		for(Implicant imp : primeImplicants){
			primeImps.add(new PrimeImplicant(imp, minterms));
		}

		System.out.println("\nPrime Implicants Table");

		//		for(PrimeImplicant prim : primeImps){
		//			System.out.println(prim.toString());
		//		}

		for(int mint : minterms){
			for(PrimeImplicant prim : primeImps){
				if(prim.contains(mint)){
					prim.markMinterm(mint, true);
				}
			}
		}

		for(PrimeImplicant prim : primeImps){
			System.out.println(prim.toString());
		}

		int count;
		for(int mint : minterms){
			count = 0;
			for(PrimeImplicant prim : primeImps){
				if(prim.contains(mint)){
					count++;
				}
			}
			if(count == 1){
				for(PrimeImplicant pi : primeImps){
					if(pi.contains(mint)){
						if(finalImps.isEmpty()){
							finalImps.add(pi);
						}
						else{
							if(!finalImps.contains(pi)){
								finalImps.add(pi);
							}
						}
					}
				}
			}
		}


		for(PrimeImplicant fimp : finalImps){
			primeImps.remove(fimp);
		}

		System.out.println("\nEssential Prime Implicants...");

		for(PrimeImplicant prim : finalImps){
			System.out.println(prim.toString());
		}

		ArrayList<Integer> mintermsLeft = new ArrayList<Integer>();
		for(PrimeImplicant prim : primeImps){
			for(int imp : prim.getImplicant().getMinterms()){
				mintermsLeft.add(imp);
			}
		}
		
		for(Integer n : mintermsLeft){
			count = 0;
			for(PrimeImplicant pi : primeImps){
				if(pi.contains(n)){
					count++;
				}
			}
			
			if(count == 1){
				for(PrimeImplicant pi : primeImps){
					if(pi.contains(n)){
						pi.markMinterm(n, false);
					}
				}
			}
		}

		System.out.println();
		for(PrimeImplicant prim : primeImps){
			System.out.println(prim.toString());
		}
		
		if(!primeImps.isEmpty()){
			PrimeImplicant dominant = primeImps.get(0);
			for(PrimeImplicant pi : primeImps){
				if(pi.getMarkCount() > dominant.getMarkCount()){
					dominant = pi;
				}
			}
			
			finalImps.add(dominant);
		}
		
		if(!finalImps.isEmpty()){
			output = evaluateExpression(finalImps);
		}
		else{
			output = "1";
		}
		
		
//		for(int i = 0; i < 26; i++){
//			System.out.println(getTerm(i));
//		}
//				
		System.out.println("\nThe simplified expression is: "+output);
	}

	private String evaluateExpression(ArrayList<PrimeImplicant> primeImplicants) {
		String expression = new String("");
		String binaryBuffer, term = new String("");
		for(PrimeImplicant pi : primeImplicants){
			binaryBuffer = pi.getImplicant().getBinaryValue();
			for(int i = 0; i < binaryBuffer.length(); i++){
				if(binaryBuffer.charAt(i) != '-'){
					expression += getTerm(i);
					if(binaryBuffer.charAt(i) == '0'){
						expression += "'"; 
					}
				}
			}
			expression += "+";
		}
		
		return expression.substring(0, expression.length()-1);
	}
	
	private char getTerm(int n){
		return new Character((char) (n + 'a'));
	}

	private void printTable(ArrayList<Implicant> imps) {
		for(Implicant imp : imps){
			System.out.println(Arrays.toString(imp.getMinterms())+"||"+imp.getBinaryValue());
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

	private int difference(String binary1, String binary2){
		int difference = 0;

		for(int i = 0; i < binary1.length(); i++){
			if(binary1.charAt(i) != binary2.charAt(i)){
				difference++;
			}
		}

		return difference;
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
