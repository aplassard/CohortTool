package org.bmi.cchmc.cohorttool.mutation;

public class SimpleMutation extends Mutation {

	protected String reference;
	protected String chr;
	protected int location;
	
	public SimpleMutation(String chr, String ref, int loc){
		this.reference=ref;
		this.chr=chr;
		this.location=loc;
	}
	
	@Override
	public void addAlternate(String m) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString(){
		return chr+"."+location+reference;
	}

	public SimpleMutation(PatientMutation PM){
		this.reference=PM.getReference();
		this.chr=PM.getChr();
		this.location=PM.getLocation();
	}
}
