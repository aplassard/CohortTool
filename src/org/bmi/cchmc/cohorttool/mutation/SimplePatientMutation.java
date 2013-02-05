package org.bmi.cchmc.cohorttool.mutation;

import com.mongodb.BasicDBObject;

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
	
	public BasicDBObject getBSON(){
		BasicDBObject obj = new BasicDBObject();
		obj.put("id", this.id);
		obj.put("alt",this.alt);
		obj.put("homozygous", this.homozygous);
		return obj;
	}
	
	public SimplePatientMutation(BasicDBObject o){
		this(o.getString("id"),o.getString("alt"),o.getBoolean("homozygous"));
	}
}
