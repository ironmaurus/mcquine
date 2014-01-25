package ph.edu.upm.dpsm.cmsc130.mcquine;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import ph.edu.upm.dpsm.cmsc130.mcquine.model.Minterm;

public class McquineController {
	private final int MINTERM_COUNT;
	private final int LITERAL_COUNT;
	private int[] mintermsInDecimal;
	
	private String[] mintermsInBinary; 
	
	private List<Minterm> minterms;
	

	/**
	 * Controller constructor.
	 * @param input string containing the minterms input by the user separated by space.
	 */
	public McquineController(String input) {
		String[] tokens = input.split(" ");
		
		MINTERM_COUNT = tokens.length; 
		mintermsInDecimal = new int[MINTERM_COUNT];
		mintermsInBinary = new String[MINTERM_COUNT];

		for(int i = 0; i < tokens.length; i++){
			try{
				mintermsInDecimal[i] = Integer.parseInt(tokens[i]);
			}
			catch(NumberFormatException e){
				System.out.println("ERROR: The input contains a non-numerical value.");
				e.printStackTrace();
			}
		}
		
		Arrays.sort(mintermsInDecimal);
		LITERAL_COUNT = evaluateNumberOfLiterals(mintermsInDecimal[MINTERM_COUNT-1]);
		Arrays.sort(tokens);
		
		BigInteger temp;
		for(int i = 0; i < mintermsInDecimal.length; i++){
			temp = new BigInteger(Integer.toString(mintermsInDecimal[i]));
			System.out.println(mintermsInDecimal[i] + ": " + String.format("%0"+LITERAL_COUNT+"d", new BigInteger(temp.toString(2))));
			mintermsInBinary[i] = new String(String.format("%0"+LITERAL_COUNT+"d", new BigInteger(temp.toString(2))));
		}
		
		System.exit(1);
		//group them by 1s

		/*
		int[] decimalMinterms;
		for(int i = 0; i < tokens.length; i++){
			decimalMinterms[i] = Integer.parseInt(tokens[i]);


			minterms.add(new Minterm(Integer.parseInt(token)));
		}
		 */

		//arrange minterms numerically
	}
	
	/**
	 * Evaluates the number of literals using the maximum/highest minterm value.
	 * @param maxMinterm the maximum/highest minterm value. 
	 * @return number of literals/variables to use for the boolean expression.
	 */
	public int evaluateNumberOfLiterals(int maxMinterm){
		int exponent = 0;
		while(maxMinterm > Math.pow(2, exponent)){
			exponent++;
		}
		System.out.println("number of literals found is: " + exponent);
		return exponent; 
	}
	
	public void print(int[] minterms){
		for(int item : minterms){
			System.out.print(item + " ");
		}
		System.out.println();
	}
	
	public void print(String[] minterms){
		for(String item : minterms){
			System.out.print(item + " ");
		}
		System.out.println();
	}

	public void run(){
		System.out.println("Simulating Quine McCluskey...");

	}

	public static void main(String[] args) {
		McquineController engine = new McquineController("15 4 6 7 8 9 10 11 1");
		//engine.run();
	}
}
