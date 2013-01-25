package org.bmi.cchmc.cohorttool.mutation;


public class PatientMutation extends Mutation {

	public boolean homozygous=false;
	
	public void setHomozygous(boolean b){
		homozygous = b;
	}
	
	public boolean isHomozygous(){
		return homozygous;
	}
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
	
	public PatientMutation(){
		
	}
	
	public PatientMutation(String chr, int loc, String ref, String alt, boolean h){
		this.setReference(ref);
		this.setChr(chr);
		this.alternate = alt;
		this.homozygous = h;
		this.setLocation(loc);
	}
}
