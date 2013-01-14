package org.bmi.cchmc.cohorttool.mutation;


public abstract class Mutation {
	
	private String reference;
	private String chr;
	private int location;
	private Object alternate;
		
	public abstract void addAlternate(String m);

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getChr() {
		return chr;
	}

	public void setChr(String chr) {
		this.chr = chr;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public Object getAlternate() {
		return alternate;
	}
	
}
