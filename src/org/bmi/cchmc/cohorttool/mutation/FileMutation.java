package org.bmi.cchmc.cohorttool.mutation;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

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
	
	@SuppressWarnings("unchecked")
	public FileMutation(DBObject next) {
		this.rsID=(String) next.get("rsID");
		this.alternate= new HashSet<String>();
		ArrayList<String> a = (ArrayList<String>) next.get("alt");
		for(String al: a) this.alternate.add(al);
		a = (ArrayList<String>) next.get("gene");
		if(a!=null){
			this.genes = new String[a.size()];
			for (int i = 0; i < a.size(); i++) {
				String al = a.get(i);
				this.genes[i]=al;
			}
		} else this.genes=null;
		if(a!=null){
			a = (ArrayList<String>) next.get("CDNA");
			this.CDNA = new String[a.size()];
			for (int i = 0; i < a.size(); i++) {
				String al = a.get(i);
				this.CDNA[i]=al;
			}
		} else this.CDNA=null;
		if(a!=null){
			a = (ArrayList<String>) next.get("Protein");
			this.Protein = new String[a.size()];
			for (int i = 0; i < a.size(); i++) {
				String al = a.get(i);
				this.Protein[i]=al;
			}
		} else this.Protein = null;
		this.chr = (String) next.get("chr");
		this.reference = (String) next.get("ref");
		this.location = (Integer) next.get("location");
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
