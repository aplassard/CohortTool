package org.bmi.cchmc.cohorttool.tests;

import java.io.Serializable;
import java.util.ArrayList;


public class UserSession  implements Serializable {

	/**
	 * 
	 */
	private ArrayList<Gene> genes;
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Gene> getGenes() {
		return genes;
	}

	public void setGenes(ArrayList<Gene> genes) {
		this.genes = genes;
	}

}
