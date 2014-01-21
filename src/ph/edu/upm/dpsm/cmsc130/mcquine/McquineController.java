package ph.edu.upm.dpsm.cmsc130.mcquine;

import java.util.List;
import ph.edu.upm.dpsm.cmsc130.mcquine.model.Minterm;

public class McquineController {
	private List<Minterm> minterms;

	public McquineController(String input) {
		String[] tokens = input.split(" ");
		
		int[] decimalMinterms;
		for(int i = 0; i < tokens.length; i++){
			decimalMinterms[i] = Integer.parseInt(tokens[i]);
			
			
			minterms.add(new Minterm(Integer.parseInt(token)));
		}

		//arrange minterms numerically
	}

	public void run(){
		System.out.println("Simulating Quine McCluskey...");

	}

	public static void main(String[] args) {
		McquineController engine = new McquineController("1 4 6 7 8 9 10 11 15");
		engine.run();
	}
}
