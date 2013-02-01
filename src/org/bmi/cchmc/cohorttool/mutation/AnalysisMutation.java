package org.bmi.cchmc.cohorttool.mutation;

import java.util.*;

public class AnalysisMutation {
	private String ref;
	private ArrayList<String> alt;
	private HashMap<String,AnalysisSpecificMutation> patients; // String 1 is the patient ID String to is the mutation,zygosity
	private String[] genes;
	private String[] CDNA;
	private String[] Protein;
	private String[] rsID;
	
	public AnalysisMutation(String ref, ArrayList<String> alt){
		this.ref = ref;
		this.alt = alt;
		this.patients = new HashMap<String,AnalysisSpecificMutation>();
	}
	
	public void addPatient(String id, AnalysisSpecificMutation a){
		this.patients.put(id, a);
	}
	
	public String getRef(){
		return this.ref;
	}
	
	public ArrayList<String> getAlt(){
		return this.alt;
	}

	public String[] getCDNA() {
		return CDNA;
	}

	public String[] getGenes() {
		return genes;
	}

	public String[] getProtein() {
		return Protein;
	}
	
	public String[] getRSID(){
		return this.rsID;
	}

}
