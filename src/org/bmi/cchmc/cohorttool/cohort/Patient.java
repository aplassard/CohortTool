package org.bmi.cchmc.cohorttool.cohort;

import com.mongodb.BasicDBObject;

public class Patient {

	private String id;
	private String mother;
	private String father;
	private String gender;
	private String project;
	private String family;
	private int mutationCount=0;
	private boolean afflicted;
	
	public Patient(String id, String mother, String father, String family, String gender, String project, boolean afflicted) {
		this.id = id;
		this.mother = mother;
		this.father = father;
		this.gender = gender;
		this.project = project;
		this.afflicted=afflicted;
		this.family = family;
	}
	
	public String getID(){
		return this.id;
	}
	
	public String getMother(){
		return this.mother;
	}
	
	public String getFather(){
		return this.father;
	}
	
	public String getGender(){
		return this.gender;
	}
	
	public String getProject(){
		return this.project;
	}

	public void setMutationCount(int n){
		this.mutationCount=n;
	}
	
	public int getMutationCount(){ return this.mutationCount; };

	public String toString(){
		return this.getBSON().toString();
	}
	
	public boolean isAfflicted(){ return this.afflicted; }
	
	public BasicDBObject getBSON(){
		BasicDBObject obj = new BasicDBObject();
		obj.put("id",this.id);
		obj.put("gender", this.gender);
		obj.put("family",this.family);
		if(this.mother!=null) obj.put("mother", this.mother);
		if(this.family!=null) obj.put("father", this.father);
		if(this.mutationCount >0) obj.put("mutationcount", this.mutationCount);
		obj.put("afflicted",this.afflicted);
		return obj;
	}
	
	public Patient(BasicDBObject o){
		this.id = o.getString("id");
		this.gender = o.getString("gender");
		this.family = o.getString("family");
		this.mother = o.getString("mother");
		this.father = o.getString("father");
		this.afflicted = o.getBoolean("afflicted");
		this.mutationCount = o.containsField("mutationcount") ? o.getInt("mutationcount") : 0;
	}
}
