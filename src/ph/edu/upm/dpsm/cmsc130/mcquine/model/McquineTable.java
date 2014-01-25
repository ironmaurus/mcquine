package ph.edu.upm.dpsm.cmsc130.mcquine.model;

import java.util.ArrayList;

/**
 * 
 * @author ironmaurus
 * 
 */
public class McquineTable {
	
	/**
	 * 
	 */
	private ArrayList<McquineGroup> mcquineGroups;
	
	/**
	 * Constructor
	 */
	public McquineTable(){
		mcquineGroups = new ArrayList<McquineGroup>();
	}

	
	public ArrayList<McquineGroup> getMcquineGroups() {
		return mcquineGroups;
	}

	public void setMcquineGroups() {
		//this.mcquineGroups = mcquineGroups;
	}
}