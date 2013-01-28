package org.bmi.cchmc.cohorttool.mutation;

import java.util.HashSet;

import com.mongodb.BasicDBObject;

public class FileMutation extends Mutation {
	private String rsID=null;
	private HashSet<String> alternate;
	private String[] genes;
	private String[] CDNA;
	private String[] Protein;
	public FileMutation() {
		this.alternate=new HashSet<String>();
	}

	@Override
	public void addAlternate(String m) {
		this.alternate.add(m);
	}
	public FileMutation(String chr, String ref, String[] alt, int location, String rsID,String[] genes,String[] CDNA, String[] Protein){
		this.chr = chr;
		this.reference = ref;
		this.location = location;
		this.alternate = new HashSet<String>();
		for(String a: alt){
			this.alternate.add(a);
		}
		if(rsID.startsWith("rs")) this.rsID=rsID;
		this.genes = genes;
		this.CDNA = CDNA;
		this.Protein = Protein;
	}
	
	@Override
	public String toString(){
		String vcfForm="";
		vcfForm += this.getChr();
		vcfForm+="_";
		vcfForm+=this.getLocation();
		vcfForm+=this.getReference();
		vcfForm+="->";
		for(String a: this.alternate) vcfForm += a+",";
		return vcfForm;
	}
	
	public BasicDBObject getBSON(){
		BasicDBObject o = new BasicDBObject();
		o.put("chr",getChr());
		o.put("ref",getReference());
		o.put("alt",alternate.toArray());
		o.put("location",getLocation());
		o.put("CDNA",this.CDNA);
		o.put("Protein",this.Protein);
		o.put("gene",this.genes);
		o.put("rsID", this.rsID);
		return o;
	}
}
