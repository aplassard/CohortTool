package org.bmi.cchmc.cohorttool.mutation;

public class SimplePatientMutation {

	public String id;
	public String alt;
	public boolean homozygous;
	
	public SimplePatientMutation(String id, String alt, boolean h) {
		this.id=id;
		this.alt=alt;
		this.homozygous=h;
	}
	
	public String toString(){
		return "id: "+this.id + ", alt: "+this.alt+", homozygous: "+this.homozygous;
	}
}
