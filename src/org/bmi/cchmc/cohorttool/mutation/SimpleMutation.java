package org.bmi.cchmc.cohorttool.mutation;

public class SimpleMutation extends Mutation {

	protected String reference;
	protected String chr;
	protected int location;
	private String alt;
	
	public SimpleMutation(String chr, String ref, String alt, int loc){
		this.reference=ref;
		this.chr=chr;
		this.alt=alt;
		this.location=loc;
	}
	
	@Override
	public void addAlternate(String m) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString(){
		return chr+"."+location+reference+"->"+alt;
	}

	public SimpleMutation(PatientMutation PM){
		this.reference=PM.getReference();
		this.chr=PM.getChr();
		this.alt=PM.getAlternate();
		this.location=PM.getLocation();
	}
}
