package org.bmi.cchmc.cohorttool.mutation;


public abstract class Mutation {
	
	protected String reference;
	protected String chr;
	protected int location;
		
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

	/**
	 * @param alternate the alternate to set
	 */
	
}
