package org.bmi.cchmc.cohorttool.mutation;

import java.util.*;

import com.mongodb.BasicDBObject;

public class AnnotatedMutation extends Mutation {

	private String rsID=null;
	private HashSet<String> alternate;
	private String[] genes;
	private String[] CDNA;
	private String[] Protein;
	private HashSet<SimplePatientMutation> patients;
	
	public AnnotatedMutation(String chr, String reference, int location, String rsID, String[] genes, String[] CDNA, String[] Protein) {
		this.chr=chr;
		this.reference=reference;
		this.location=location;
		this.rsID=rsID;
		this.genes=genes;
		this.CDNA=CDNA;
		this.Protein=Protein;
		this.patients = new HashSet<SimplePatientMutation>();
		this.alternate = new HashSet<String>();
	}

	@Override
	public void addAlternate(String m) {
		alternate.add(m);
	}
	
	public SimpleMutation getSimpleMutation(){
		return new SimpleMutation(this.chr,this.reference,this.location);
	}
	
	public void addSimplePatientMutation(SimplePatientMutation SM){
		this.patients.add(SM);
	}
	
	public String toString(){
		String o =  this.chr+this.location+"."+this.reference +"->"+this.alternate.toString()+" rsID: "+this.rsID;
		o += " [ ";
		for (SimplePatientMutation s: this.patients) o+= s.toString()+", ";
		o+="]";
		return o;
	}
	
	public BasicDBObject getBSON(){
		BasicDBObject obj = new BasicDBObject();
		obj.put("chr", this.chr);
		obj.put("reference", this.reference);
		obj.put("alternate", this.alternate.toArray());
		obj.put("location", this.location);
		if(this.rsID!=null) obj.put("rsID", this.rsID);
		obj.put("genes",this.genes);
		obj.put("CDNA", this.CDNA);
		obj.put("protein", this.Protein);
		return obj;
	}
}
