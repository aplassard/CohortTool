package org.bmi.cchmc.cohorttool.analysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bmi.cchmc.cohorttool.cohort.Cohort;
import org.bmi.cchmc.cohorttool.mutation.AnnotatedMutation;
import org.bmi.cchmc.cohorttool.mutation.SimpleMutation;
import org.bmi.cchmc.cohorttool.mutation.SimplePatientMutation;

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

	public void exportMutations(boolean all, String filename){
		HashMap<String,Integer> toExport = new HashMap<String, Integer>();
		int n = 0;
		if(all){
			for(String p: this.patients.keySet()){
				toExport.put(p, n++);
			}
		}
		else{
			for( String p:this.patients.keySet()){
				if( this.patients.get(p).isAfflicted()) toExport.put(p, n++);
			}
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			String[] names = new String[n];
			out.write("Mutations");
			out.write("\tCDNA");
			out.write("\tProtein");
			out.write("\tGene");
			out.write("\trsID");
			for(String S: toExport.keySet()) names[toExport.get(S)]=S;
			for(String S: names) out.write("\t"+S);
			out.write("\n");
			for(SimpleMutation SM: this.mutations.keySet()){
				AnnotatedMutation AM = this.mutations.get(SM);
				String[] loc = new String[n];
				for(int i = 0; i < n; i++) loc[i]="0";
				for(SimplePatientMutation SPM: AM.getSimplePatientMutations()){
					if(toExport.containsKey(SPM.id)) loc[toExport.get(SPM.id)]= SPM.homozygous ? "2" : "1";
				}
				boolean good=false;
				for(String S: loc){
					if(!S.equals("0")){
						good=true;
						break;
					}
				}
				if(good){
					out.write(AM.toSimpleString());
					out.write("\t");
					out.write(AM.getCDNAString());
					out.write("\t");
					out.write(AM.getProteinString());
					out.write("\t");
					out.write(AM.getGeneString());
					out.write("\t");
					out.write(AM.getRsID());
					for(String S: loc) out.write("\t"+S);
					out.write("\n");
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void leaveComplemented(){
		HashMap<String,HashMap<String,Integer>> GPC = new HashMap<String,HashMap<String,Integer>>(); // Gene, Patient, Count
		System.out.println("Getting Counts of Mutations in each gene");
		for(AnnotatedMutation AM: this.mutations.values()){
			HashMap<String,Integer> PC;
			if(!AM.getGenes()[0].equals("")){
				if(!GPC.containsKey(AM.getGenes()[0])){
					PC = new HashMap<String,Integer>();
				}else {
					PC = GPC.get(AM.getGenes()[0]);
				}
				for(SimplePatientMutation SPM: AM.getSimplePatientMutations()){
					if(PC.containsKey(SPM.id))PC.put(SPM.id, PC.get(SPM.id)+1);
					else PC.put(SPM.id,1);
				}
				GPC.put(AM.getGenes()[0], PC);
			}
		}
		HashMap<SimpleMutation,AnnotatedMutation> newmuts = new HashMap<SimpleMutation,AnnotatedMutation>();
		for(AnnotatedMutation AM: this.mutations.values()){
			if(!AM.getGenes()[0].equals("")){
				AM.removeNonComplemented(GPC.get(AM.getGenes()[0]));
				if(AM.getSimplePatientMutations().size()>0) newmuts.put(AM.getSimpleMutation(), AM);
			}
		}
		this.mutations=newmuts;
	}
}
