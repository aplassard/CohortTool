package org.bmi.cchmc.cohorttool.cohort;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bmi.cchmc.cohorttool.mutation.PatientMutation;

import com.mongodb.*;

public class Patient {
	private String mother;
	private String father;
	private boolean gender; //female: true; male: false
	private String id;
	private ArrayList<PatientMutation> mutations=null;
	private boolean mutationsInDatabase=false;
	private String project;
	private boolean afflicted;
	
	public Patient() {
		
	}
	
	public Patient(String id, String mother, String father, String project, boolean gender, boolean afflicted){
		this.id = id;
		this.mother = mother;
		this.father = father;
		this.gender = gender;
		this.afflicted = afflicted;
		this.project = project;
	}
	
	public void setFather(String father){
		this.father = father;
	}
	
	public void setMother(String mother){
		this.mother = mother;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getFather(){
		return this.father;
	}
	
	public String getMother(){
		return this.mother;
	}
	
	public String getGender(){
		return gender ? "mother" : "father";
	}

	public String getId(){
		return this.id;
	}

	public void setProject(String set){
		this.project=set;
	}
	
	public void setAfflicted(boolean afflicted){
		this.afflicted = afflicted;
	}
	
	public boolean isAfflicted(){
			return this.afflicted;
		}
	
	public void addMutation(PatientMutation pm){
		
		if(this.mutationsInDatabase){
			MongoClient mongoClient = null;
			try {
				mongoClient = new MongoClient( "localhost" , 27017 );
				DB db = mongoClient.getDB("CohortTool");
				DBCollection coll = db.getCollection("patientmutations");
				BasicDBObject q = new BasicDBObject();
				q.put("project",project);
				q.put("id",id);
				DBObject m = coll.findOne(q);
				PatientMutation[] PM = (PatientMutation[]) m.get("mutations");
				this.mutations = new ArrayList<PatientMutation>(Arrays.asList(PM));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		} else if(this.mutations==null) this.mutations = new ArrayList<PatientMutation>();
		this.mutations.add(pm);
	}

	public BasicDBObject getBSON(){
		BasicDBObject o = new BasicDBObject();
		
		return o;
	}

	public BasicDBObject getQuery(){
		BasicDBObject q = new BasicDBObject();
		q.put("project",this.project);
		q.put("id",this.id);
		return q;
	}

	public void loadMutations(){
		try {
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB("CohortTool");
	        DBCollection coll = db.getCollection("mutations");
	        BasicDBObject m = (BasicDBObject) coll.findOne(this.getQuery());
	        
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
