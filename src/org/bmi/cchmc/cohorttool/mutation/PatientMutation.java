package org.bmi.cchmc.cohorttool.mutation;

import com.mongodb.BasicDBObject;


public class PatientMutation extends Mutation {

	public boolean homozygous=false;
	
	public void setHomozygous(boolean b){
		homozygous = b;
	}
	
	public boolean isHomozygous(){
		return homozygous;
	}
	/**
	 * @param args
	 */
	private String alternate;
	
	@Override
	public void addAlternate(String m) {
		this.alternate = m;
	}
	
	public String getAlternate(){
		return alternate;
	}
	
	public String getOutputForm(){
		String vcfForm="";
		vcfForm += this.getChr();
		vcfForm+="_";
		vcfForm+=this.getLocation();
		vcfForm+=this.getReference();
		vcfForm+="->";
		vcfForm+=this.getAlternate();
		vcfForm+=",";
		vcfForm+=homozygous ?  "hom" : "het"; 
		return vcfForm;
	}
	
	public PatientMutation(){
		
	}
	
	public PatientMutation(String chr, int loc, String ref, String alt, boolean h){
		this.setReference(ref);
		this.setChr(chr);
		this.alternate = alt;
		this.homozygous = h;
		this.setLocation(loc);
	}
	
	public PatientMutation(BasicDBObject obj) {
		this.chr=(String) obj.get("chr");
		this.reference=(String) obj.get("ref");
		this.alternate=(String) obj.getString("alt");
		this.homozygous=obj.getBoolean("homozygous");
		this.location=obj.getInt("location");
	}

	public String toString(){
		return getOutputForm();
	}
	
	public BasicDBObject toBSON(){
		BasicDBObject o = new BasicDBObject();
		o.put("chr",getChr());
		o.put("ref",getReference());
		o.put("alt",getAlternate());
		o.put("location",getLocation());
		o.put("homozygous",isHomozygous());
		return o;
	}
}
