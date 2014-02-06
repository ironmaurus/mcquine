package ph.edu.upm.dpsm.cmsc130.mcquine;

import java.math.*;
import java.util.*;
import ph.edu.upm.dpsm.cmsc130.mcquine.model.*;

/**
 * The McQuineController represents the engine in performing the Quine-McCluskey Algorithm that is used
 * in generating a simplified boolean expression using the list of minterms as an input.
 * @author ironmaurus
 */
public class McQuineController {
	/**
	 * Number of variables
	 */
	private final int LITERAL_COUNT;
	
	/**
	 * Array of Implicants See implicant class.
	 */
	private Implicant[] implicants;
	
	/**
	 * Array of minterms (in decimal representation).
	 */
	private int[] minterms;

	/**
	 * list of tables that are especially designed for Quine-McCluskey
	 */
	private ArrayList<McQuineTable> mcquineTables;
	
	/**
	 * data structure used in storing the Prime Implicants
	 */
	private ArrayList<PrimeImplicant> primeImps, finalImps;
	
	/**
	 * The simplified boolean expression.
	 */
	private String output;
	
	/**
	 * Controller constructor. Initializes the quine-mccluskey engine. 
	 * @param input string containing the minterms separated by space.
	 */
	public McQuineController(String input) {
		System.out.println("\nInitializing Quine-McCluskey data structures...\n");
		String[] tokens = input.split(" ");
		int MINTERM_COUNT = tokens.length;
		minterms = new int[MINTERM_COUNT];
		for(int i = 0; i < MINTERM_COUNT; i++){ //parse minterms
			try{
				minterms[i] = Integer.parseInt(tokens[i]);
			}
			catch(NumberFormatException e){
				System.out.println("ERROR: The input contains a non-numerical value.");
				e.printStackTrace();
			}
		}
		Arrays.sort(minterms); //sort minterms in ascending order to place the highest item in the last entry of the array 
		System.out.print("MINTERMS:\t\t\t");
		print(minterms);
		System.out.println("NUMBER OF MINTERMS:\t\t" + minterms.length);
		LITERAL_COUNT = evaluateLiteralCount(minterms[MINTERM_COUNT-1]);
		System.out.println("NUMBER OF LITERALS TO USE:\t" + LITERAL_COUNT);
		implicants = new Implicant[MINTERM_COUNT];
		System.out.println("\nInitializing list of implicants...\n");
		String raw, formatted;
		int[] mintermList;
		for(int i = 0; i < MINTERM_COUNT; i++){
			mintermList = new int[]{minterms[i]};
			raw = Integer.toBinaryString(minterms[i]);
			formatted = String.format("%0"+LITERAL_COUNT+"d", new BigInteger(raw));
			implicants[i] = new Implicant(mintermList, formatted);
		}
		System.out.println("IMPLICANT LIST:\n| BitCount | BinaryValue |  Mark | MintermList");
		print(implicants);
		mcquineTables = new ArrayList<McQuineTable>(); //other initializations
		primeImps = new ArrayList<PrimeImplicant>();
		finalImps = new ArrayList<PrimeImplicant>();
		output = new String("");
	}

	/**
	 * Evaluates the number of literals/variables using the highest minterm value.
	 * @param maxMinterm the highest minterm value. 
	 * @return number of literals/variables to use for generating the simplified boolean expression.
	 */
	private int evaluateLiteralCount(int maxMinterm){
		int exponent = 0;
		while(maxMinterm >= Math.pow(2, exponent)){
			exponent++;
		}
		return exponent; 
	}

	/**
	 * Runs the core Quine-McCluskey algorithm in generating a simplified boolean expression.
	 */
	public void runQuineMcCluskey(){
		System.out.println("\nRunning Quine McCluskey...");
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
		while(bufferTable.isComparable()){ //loop to compare all binary values. Needs refactoring.
			impList = new ArrayList<Implicant>();
			sections = bufferTable.getMcQuineSections();
			Map.Entry<Integer, ArrayList<Implicant>> secAbove = null;
			for(Map.Entry<Integer, ArrayList<Implicant>> secBelow : sections.entrySet()){
				if(secAbove != null){
					above = secAbove.getValue();
					below = secBelow.getValue();
					for(int i = 0; i < above.size(); i++){
						for(int j = 0; j < below.size(); j++){
							imp1 = above.get(i);
							imp2 = below.get(j);
							if(difference(imp1.getBinaryValue(), imp2.getBinaryValue()) == 1){
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
		System.out.println("GENERATED TABLES:");
		for(int i = 0; i < mcquineTables.size(); i++){
			System.out.print("\n------------------------------------------TABLE "+i+"\n");
			mcquineTables.get(i).printTable();
		}
		ArrayList<Implicant> primeImplicants = new ArrayList<Implicant>(); 
		boolean duplicate = false;
		for(McQuineTable table : mcquineTables){
			for(Implicant imp : table.getImplicantList()){ //delete duplicates if existing. needs refactoring.
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
				duplicate = false;
			}
		}
		ArrayList<Integer> mints = new ArrayList<Integer>();
		for(int m : minterms){
			mints.add(m);
		}
		primeImps = new ArrayList<PrimeImplicant>();
		for(Implicant imp : primeImplicants){
			primeImps.add(new PrimeImplicant(imp, minterms));
		}
		System.out.println("\nPrime Implicants Table");
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
		for(int mint : minterms){
			int count = 0;
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

			for(int m : fimp.getImplicant().getMinterms()){
				for(PrimeImplicant pi : primeImps){
					//pi.removeMinterm(m);
					pi.markMinterm(m, false);
				}
			}
		}
		while(!primeImps.isEmpty()){
			PrimeImplicant dominant = primeImps.get(0);
			for(PrimeImplicant pi : primeImps){
				if(pi.getMarkCount() > dominant.getMarkCount()){
					dominant = pi;
				}
			}
			if(dominant.getMarkCount() != 0){
				finalImps.add(dominant);
				primeImps.remove(dominant);
			}
			for(int m : dominant.getImplicant().getMinterms()){
				for(PrimeImplicant pi: primeImps){
//					pi.removeMinterm(m);
					pi.markMinterm(m, false);
				}
			}
			boolean mark = true;
			for(PrimeImplicant pi : primeImps){
				if(pi.getMarkCount() > 0){
					mark = false;
				}
			}
			if(mark == true){
				break;
			}

		}
		if(!finalImps.isEmpty()){
			System.out.println("\nEssential Prime Implicants");
			print(minterms);
			for(PrimeImplicant pi : finalImps){
				System.out.println(pi.toString());
			}
			output = evaluateExpression(finalImps);
		}
		else{
			output = "1";
		}
		System.out.println("\nThe simplified expression is: "+output);
	}
	
	/**
	 * Evaluates the resulting binary string after comparing two implicants' binary representations.   
	 * @param binary1 binary string to be compared. 
	 * @param binary2 binary string to be compared.
	 * @return binary string with characters either 1, 0, -
	 */
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
		return result;
	}

	/**
	 * Use in converting the binaryValue into a meaningful boolean expression.
	 * @param primeImplicants list of Prime Implicants 
	 * @return the final boolean expression.
	 */
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

	/**
	 * Getting the character representation of the number.
	 * @param n the number to be converted as literal.
	 * @return literal character
	 */
	private char getTerm(int n){
		return new Character((char) (n + 'a'));
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

	/**
	 * Counts the difference between bits of two binary representation.
	 * @param binary1 binary string to compare
	 * @param binary2 vinary string to compare
	 * @return the number of bits
	 */
	private int difference(String binary1, String binary2){
		int difference = 0;

		for(int i = 0; i < binary1.length(); i++){
			if(binary1.charAt(i) != binary2.charAt(i)){
				difference++;
			}
		}

		return difference;
	}

	/**
	 * Prints the list of minterms.
	 * @param minterms
	 */
	private void print(int[] minterms){
		for(int item : minterms){
			System.out.print(String.format("| %s |", item));
		}
		System.out.println();
	}

	/**
	 * Prints the list of implicants.
	 * @param implicants
	 */
	private void print(Implicant[] implicants) {
		for(Implicant item : implicants){
			System.out.println(item.toString());
		}
	}
	
	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner mcQuineView = new Scanner(System.in);
		String input = new String("");
		System.out.println("Quine-McCluskey Simulator!");
		System.out.println("Please input all the minterms that evaluate to 1 separated by space:");
		
		if(mcQuineView.hasNextLine()){
			input = mcQuineView.nextLine();
		}
		
		if(!input.isEmpty()){
			McQuineController engine = new McQuineController(input);
			engine.runQuineMcCluskey();
		}
	}
}

