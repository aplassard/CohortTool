package org.bmi.cchmc.cohorttool.analysis;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bmi.cchmc.cohorttool.cohort.Cohort;
import org.bmi.cchmc.cohorttool.mutation.AnnotatedMutation;
import org.bmi.cchmc.cohorttool.mutation.SimpleMutation;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class Analysis extends Cohort {

	private String analysisName;
	
	public String getAnalysisName(){ return this.analysisName; }
	
	public void removeDBSNP(){
		ArrayList<SimpleMutation> toRemove = new ArrayList<SimpleMutation>();
		for(SimpleMutation SM: this.mutations.keySet()){
			AnnotatedMutation AM = this.mutations.get(SM);
			if(AM.inDBSNP()) toRemove.add(SM);
		}
		for(SimpleMutation SM: toRemove) this.mutations.remove(SM);
	}
	
	public void removeHeterozygous(){
		ArrayList<SimpleMutation> toRemove = new ArrayList<SimpleMutation>();
		for(SimpleMutation SM: this.mutations.keySet()){
			AnnotatedMutation AM = this.mutations.get(SM);
			AM.removeHeterozygous();
			if(AM.getPatientCount()>0)this.mutations.put(SM, AM);
			else toRemove.add(SM);
		}
		for(SimpleMutation SM: toRemove) this.mutations.remove(SM);
	}
	
	public Analysis(BasicDBObject o,String name) {
		super(o);
		this.analysisName = name;
	}
	
	public void removeHomozygous(){
		ArrayList<SimpleMutation> toRemove = new ArrayList<SimpleMutation>();
		for(SimpleMutation SM: this.mutations.keySet()){
			AnnotatedMutation AM = this.mutations.get(SM);
			AM.removeHomozygous();
			if(AM.getPatientCount()>0)this.mutations.put(SM, AM);
			else toRemove.add(SM);
		}
		for(SimpleMutation SM: toRemove) this.mutations.remove(SM);
	}
	
	public void loadIntoDatabase(){
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("analysis");
			coll.findAndRemove(this.getAnalysisQuery());
			coll.insert(this.getAnalysisBSON());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMutationsIntoDataBase(){
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("analysismutations");
			for(AnnotatedMutation AM: this.mutations.values()){
				BasicDBObject obj = AM.getBSON();
				obj.putAll(this.getAnalysisQuery().toMap());
				obj.putAll(this.getQuery().toMap());
				coll.insert(obj);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public BasicDBObject getAnalysisQuery(){
		BasicDBObject obj =  this.getQuery();
		obj.put("analysisname", this.analysisName);
		return obj;
	}
	
	public BasicDBObject getAnalysisBSON(){
		BasicDBObject obj = this.getBSON();
		obj.put("analysisname", this.analysisName);
		return obj;
	}

	public void loadMutationsFromAnalysisDatabase() {
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("analysismutations");
			DBCursor cursor = coll.find(this.getAnalysisQuery());
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject) cursor.next();
				AnnotatedMutation AM = new AnnotatedMutation( obj);
				this.mutations.put(AM.getSimpleMutation(), AM);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
