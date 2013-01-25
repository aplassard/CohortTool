package org.bmi.cchmc.cohorttool.patient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.bmi.cchmc.cohorttool.mutation.PatientMutation;
import org.bson.types.ObjectId;

import com.mongodb.*;

public class PatientSet {

	public PatientSet(){
		this.patients = new HashMap<String,Individual>();
	}
	
	public PatientSet(String file){
		this.patients = new HashMap<String,Individual>();
		getPatients(file);
	}
	
	public void getPatients(String file){
		BufferedReader ped;
		try {
			ped = new BufferedReader(new FileReader(file));
		
		try {
			String line = ped.readLine();
			while((line=ped.readLine())!=null){
				String[] info = line.split(",");
				Individual p = new Individual(info[2]);
				p.setSex(info[5].equals("1"));
				p.setAfflicted(info[6].equals("1"));
				p.setFamilyId(info[1]);
				this.patients.put(p.getId(),p);
			}
			ped.close();
			@SuppressWarnings("resource")
			BufferedReader ped1 = new BufferedReader(new FileReader(file));
			line = ped1.readLine();
			while((line=ped1.readLine())!=null){
				String[] info = line.split(",");
				Individual p = this.patients.get(info[2].replace(".","_"));
				if(!info[4].equals("?"))
					p.setMother(this.patients.get(info[4].replace(".","_")));
				if(!info[3].equals("?"))
					p.setFather(this.patients.get(info[3].replace(".","_")));
				this.patients.remove(p.getId());
				this.patients.put(p.getId(),p);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public BasicDBObject getBSON(){
		BasicDBObject o = new BasicDBObject();
		for(String k: patients.keySet()){
			o.append(k,patients.get(k).getBson());
		}
		return o;
	}
	
	public Map<String,Individual> getPatients(){
		return patients;
	}
	
	private Map<String,Individual> patients;
	private boolean hasMutations=false;
	
	public PatientSet(ObjectId o){
		// TODO create constructor from ObjectId
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB("CohortTool");
	        DBCollection coll = db.getCollection("projects");
	        DBObject P = coll.findOne(new BasicDBObject("_id",o));
	        DBObject Patients = (DBObject) P.get("Patient Info");
	        Set<String> keys = Patients.keySet();
	        this.patients = new HashMap<String,Individual>();
	        Individual I;
	        for(String K : keys){
	        	I = new Individual((DBObject) Patients.get(K));
	        	this.patients.put(K, I);
	        }
	        for(String K: keys){
	        	P = (DBObject) Patients.get(K);
	        	I = this.patients.get(K);
	        	if(P.containsField("mother")){
	        		I.setMother(this.patients.get(P.get("mother")));
	        	}
	        	if(P.containsField("father")){
	        		I.setFather(this.patients.get(P.get("father")));
	        	}
	        	this.patients.remove(K);
	        	this.patients.put(K, I);
	        }
	        
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	/*
	 * Params filename
	 * Process:
	 * 		Reads file header and splits to get information
	 * 		Create list of file mutations
	 * 		Read file line by line
	 * 			Add filemutation on that line to the list
	 * 				rsID?
	 * 				chromosome
	 * 				position
	 * 				reference
	 * 				alternate
	 * 				gene?
	 * 				CdnaVariation?
	 * 				ProteinVariation?
	 * 			Read through the patients in the line
	 * 			If patient has mutation
	 * 				create new patient mutation
	 * 					add reference
	 * 					add alternate
	 * 					add chromosome
	 * 					add position
	 * 					add zygosity
	 * 				add mutation to patient
	 * 		Resave "Patient Info" to database
	 */
	// TODO create filemutation
	public void getMutations(String filename){
		try {
			BufferedReader txt = new BufferedReader(new FileReader(filename));
			String[] header = txt.readLine().split("\t");
			DBObject mutations = new BasicDBObject();
			String line;
			String[] lineinfo;
			DBObject mutation;
			PatientMutation m;
			String a;
			String[] alleles;
			ArrayList<BasicDBObject> FileMutations = new ArrayList<BasicDBObject>();
			while((line=txt.readLine())!=null){
				mutation = new BasicDBObject();
				lineinfo=line.split("\t");
				mutation.put("DBSNP", lineinfo[0]);
				m=new PatientMutation();
				a=lineinfo[4];
				m.addAlternate(lineinfo[4]);
				m.setReference(lineinfo[3]);
				m.setChr(lineinfo[1]);
				m.setLocation(Integer.parseInt(lineinfo[2]));
				mutation.put("info", m.toString());
				mutation.put("gene", lineinfo[5]);
				mutation.put("CDNAVariation", lineinfo[6]);
				mutation.put("ProteinVariation", lineinfo[7]);
				FileMutations.add((BasicDBObject) mutation);
				Individual I;
				for(int i=8;i<(header.length-8)/3 + 8; i++){
					I = this.patients.get(header[i].replace(".","_"));
					alleles = lineinfo[i].split("/");
					if(alleles[0].equals(a)&&alleles[1].equals(a)){
						
					} else if(alleles[0].equals(a)||alleles[1].equals(a)){
						
					}
				}
			}
			txt.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
