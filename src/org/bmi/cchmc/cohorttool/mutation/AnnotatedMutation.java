package org.bmi.cchmc.cohorttool.mutation;

import java.util.*;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class AnnotatedMutation extends Mutation {

	private String rsID=null;
	private HashSet<String> alternate;
	private String[] genes;
	private String[] CDNA;
	private String[] protein;
	private HashSet<SimplePatientMutation> patients;
	
	public HashSet<SimplePatientMutation> getSimplePatientMutations(){ return this.patients; }
	
	public AnnotatedMutation(String chr, String reference, int location, String rsID, String[] genes, String[] CDNA, String[] protein) {
		this.chr=chr;
		this.reference=reference;
		this.location=location;
		this.rsID=rsID;
		this.genes=genes;
		this.CDNA=CDNA;
		this.protein=protein;
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
		obj.put("protein", this.protein);
		ArrayList<BasicDBObject> a = new ArrayList<BasicDBObject>(this.patients.size());
		for(SimplePatientMutation S: this.patients) a.add(S.getBSON());
		obj.put("patients", a);
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public AnnotatedMutation(BasicDBObject o){
		this.patients = new HashSet<SimplePatientMutation>();
		this.chr = o.getString("chr");
		this.reference = o.getString("reference");
		this.location = o.getInt("location");
		BasicDBList b = (BasicDBList) o.get("genes");
		this.genes = new String[b.size()];
		for(int i = 0; i < b.size(); i++){
			this.genes[i] = b.get(i).toString();
		}
		b = (BasicDBList) o.get("CDNA");
		this.CDNA = new String[b.size()];
		for(int i = 0; i < b.size(); i++){
			this.CDNA[i] = b.get(i).toString();
		}
		b = (BasicDBList) o.get("protein");
		this.protein = new String[b.size()];
		for(int i = 0; i < b.size(); i++){
			this.protein[i] = b.get(i).toString();
		}
		this.rsID = o.getString("rsID");
		this.alternate = new HashSet<String>((Collection<? extends String>) o.get("alternate"));
		b = (BasicDBList) o.get("patients");
		for(Object obj: b){
			this.patients.add(new SimplePatientMutation((BasicDBObject) obj));
		}
	}

	public void removeHomozygous() {
		ArrayList<SimplePatientMutation> toRemove = new ArrayList<SimplePatientMutation>();
		for(SimplePatientMutation SPM: this.patients){
			if(SPM.homozygous) toRemove.add(SPM);
		}
		for(SimplePatientMutation SPM: toRemove) this.patients.remove(SPM);
	}
	
	public int getPatientCount(){
		return this.patients.size();
	}
}
