package org.bmi.cchmc.cohorttool.cohort;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

import org.bmi.cchmc.cohorttool.mutation.FileMutation;
import org.bmi.cchmc.cohorttool.mutation.PatientMutation;
import org.bmi.cchmc.cohorttool.patient.Individual;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Cohort {

	private HashMap<String,Patient> patients;
	private ArrayList<FileMutation> mutations;
	private boolean hasMutationsInDatabase = false;
	
	public Cohort() {

	}
	
	public Cohort(ObjectId o){
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
	        DBCollection coll = db.getCollection("projects");
	        DBObject P = coll.findOne(new BasicDBObject("_id",o));
	        DBObject Patients = (DBObject) ((DBObject) P.get("Patient Info")).get("Patients");
	        Set<String> keys = Patients.keySet();
	        System.out.println(keys);
	        this.patients = new HashMap<String,Patient>();
	        Patient I;
	        for(String K : keys){
	        	I = new Patient((DBObject) Patients.get(K));
	        	this.patients.put(K, I);
	        }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
