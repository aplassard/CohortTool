package org.bmi.cchmc.cohorttool.mutation;

public class AnalysisSpecificMutation {
	public String alt;
	public boolean homozygous;
	
	public AnalysisSpecificMutation(String alt, boolean homozygous){
		this.alt=alt;
		this.homozygous=homozygous;
	}
}
