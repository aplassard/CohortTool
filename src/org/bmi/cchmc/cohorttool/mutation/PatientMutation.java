package org.bmi.cchmc.cohorttool.mutation;


public class PatientMutation extends Mutation {


	/**
	 * @param args
	 */
	private String alternate;
	
	@Override
	public void addAlternate(String m) {
		this.alternate = m;
	}
	
	public String getAlternate(){
		return alternate;
	}
	
	public String getOutputForm(){
		String vcfForm="";
		vcfForm += this.getChr();
		vcfForm+=".";
		vcfForm+=this.getLocation();
		vcfForm+=this.getReference();
		vcfForm+="->";
		vcfForm+=this.getAlternate();
		return vcfForm;
	}

}
