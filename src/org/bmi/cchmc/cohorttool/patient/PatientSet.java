package org.bmi.cchmc.cohorttool.patient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.bmi.cchmc.cohorttool.mutation.FileMutation;
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
		BasicDBObject p = new BasicDBObject();
		for(Individual I: this.patients.values()) p.put(I.getId(),I.getBSON());
		o.put("Patients",p);
		p = new BasicDBObject();
		if(hasMutations) for(FileMutation pm: this.mutations) p.put(pm.toString(),pm.getBSON());
		o.put("Mutations",p);
		return o;
	}
	
	public Map<String,Individual> getPatients(){
		return patients;
	}
	
	private Map<String,Individual> patients;
	private boolean hasMutations=false;
	private ArrayList<FileMutation> mutations;
	
	public PatientSet(ObjectId o){
		// TODO create constructor from ObjectId
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB("CohortTool");
	        DBCollection coll = db.getCollection("projects");
	        DBObject P = coll.findOne(new BasicDBObject("_id",o));
	        DBObject Patients = (DBObject) ((DBObject) P.get("Patient Info")).get("Patients");
	        Set<String> keys = Patients.keySet();
	        System.out.println(keys);
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
	public void getMutations(String filename){
		try {
			BufferedReader txt = new BufferedReader(new FileReader(filename));
			String[] header = txt.readLine().split("\t");
			String line;
			String[] lineinfo;
			this.mutations = new ArrayList<FileMutation>();
			FileMutation m;
			while((line=txt.readLine())!=null){
				lineinfo = line.split("\t");
				m = new FileMutation(lineinfo[1],lineinfo[3],lineinfo[4].split(","),Integer.parseInt(lineinfo[2]),lineinfo[0],lineinfo[5].split("\\|"),lineinfo[6].split("\\|"),lineinfo[7].split("\\|"));
				this.mutations.add(m);
				this.parseMutationsFromLine(header,lineinfo);
			}
			txt.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 		
	}
	
	private void parseMutationsFromLine(String[] header, String[] lineinfo){
		hasMutations=true;
		String r = lineinfo[3];
		String[] alleles;
		PatientMutation p;
		Individual I;
		HashSet<String> a = new HashSet<String>(lineinfo[4].split(",").length);
		for(String al: lineinfo[4].split(",")) a.add(al);
		for(int i = 8; i < (lineinfo.length-8)/3;i++){
			alleles = lineinfo[i].split("/");
			if(alleles.length>1){
				if(alleles[0].equals(alleles[1])&&a.contains(alleles[1])){
					p = new PatientMutation();
					p.setHomozygous(true);
					p.addAlternate(alleles[0]);
					p.setReference(r);
					p.setChr(lineinfo[1]);
					p.setLocation(Integer.parseInt(lineinfo[2]));
					I = this.patients.get(header[i].replace(".","_"));
					this.patients.remove(header[i].replace(".","_"));
					try{
						I.addMutation(p);
					}catch(Exception e){
						System.out.println("line header "+header[i]);
						System.out.println(this.patients.keySet());
						return;
					}
					this.patients.put(header[i].replace(".","_"),I);
				} else if(!alleles[0].equals(alleles[1])){
					p = new PatientMutation();
					p.setHomozygous(false);
					p.addAlternate(alleles[1]);
					p.setReference(r);
					p.setChr(lineinfo[1]);
					p.setLocation(Integer.parseInt(lineinfo[2]));
					I = this.patients.get(header[i].replace(".","_"));
					this.patients.remove(header[i].replace(".","_"));
					I.addMutation(p);
					this.patients.put(header[i].replace(".","_"),I);
				}
			}
		}
	}

	public String getHTMLTable(){
		String o = "";
		o += "<div>";
		o += "<table class=\"table table-striped table table-hover\">\n";
		o += "\t<caption><h2>Patient Information</h2><caption>\n";
		o += "\t<thead>\n";
		o += "\t\t<tr>";
		o += "\t\t\t<th>ID</th>";
		o += "\t\t\t<th># of mutations</th>";
		o += "\t\t\t<th>Father</th>";
		o += "\t\t\t<th>Mother</th>";
		o += "\t\t\t<th>Affliction Status</th>";
		o += "\t\t\t<th>Gender</th>";
		o += "\t\t</tr>";
		o += "\t</thead>\n";
		o += "\t<tbody>\n";
		for(Individual I: this.patients.values()){
			o += "\t\t<tr>";
			o += "\t\t<td>";
			o += I.getId();
			o += "\t\t</td>\n";
			o += "\t\t<td>";
			o += I.countMutations();
			o += "\t\t</td>\n";
			o += "\t\t<td>";
			try{
			o += I.getFather().getId();
			}catch(Exception e){
				o += " ";
			}
			o += "\t\t</td>\n";
			o += "\t\t<td>";
			try{
				o += I.getMother().getId();
				}catch(Exception e){
					o += " ";
				}
			o += "\t\t</td>\n";
			o += "\t\t<td>";
			if(I.isAfflicted()) o+="Afflicted";
			else o += "Not Afflicted";
			o += "\t\t</td>\n";
			o += "\t\t<td>";
			o += I.getSex();
			o += "\t\t</td>\n";
			o += "\t\t</tr>\n";
		}
		o += "\t</tbody>";
		o += "</table>";
		o += "</div>";
		return o;
	}
}
