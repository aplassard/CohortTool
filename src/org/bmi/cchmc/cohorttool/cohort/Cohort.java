package org.bmi.cchmc.cohorttool.cohort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import org.bmi.cchmc.cohorttool.mutation.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class Cohort {

	private String htmlTable;
	private HashMap<String,Patient>  patients;
	protected HashMap<SimpleMutation,AnnotatedMutation> mutations;
	private String name;
	@SuppressWarnings("unused")
	private boolean setMutationCount=false;
	
	public String getName(){ return this.name; }
	
	public void getMutationCounts(){
		HashMap<String,Integer> p = new HashMap<String,Integer>(this.patients.size());
		for(String S: this.patients.keySet()) p.put(S, 0);
		for(AnnotatedMutation AM: this.mutations.values()){
			for(SimplePatientMutation SPM: AM.getSimplePatientMutations()) p.put(SPM.id, p.get(SPM.id)+1);
		}
		for(String S: this.patients.keySet()){
			Patient P = patients.get(S);
			P.setMutationCount(p.get(S));
			this.patients.put(S, P);
		}
		this.setMutationCount=true;
	}
	
	public Cohort(String name) {
		this.patients = new HashMap<String,Patient>();
		this.mutations = new HashMap<SimpleMutation,AnnotatedMutation>();
		this.name = name;
	}
	
	public Cohort(BasicDBObject o){
		BasicDBObject p = (BasicDBObject) o.get("patients");
		this.patients = new HashMap<String,Patient>();
		this.mutations = new HashMap<SimpleMutation,AnnotatedMutation>();
		this.name = o.getString("name");
		for(String pa:  p.keySet()){
			this.patients.put(pa, new Patient((BasicDBObject) p.get(pa)));
		}
	}
	
	public void addPatient(Patient P){
		this.patients.put(P.getID(), P);
	}
	
	public void addMutation(AnnotatedMutation AM){
		this.mutations.put(AM.getSimpleMutation(), AM);
	}
	
	public Cohort(String filename, String name){
		this(name);
		try{
			BufferedReader txt = new BufferedReader(new FileReader(filename));
			String line;
			txt.readLine();
			while((line=txt.readLine())!=null){		
				String[] lineinf = line.replace('.', '_').split(","); //ID , Family, Patient, Father, Mother, Gender, Affliction
				this.patients.put(lineinf[0], new Patient(lineinf[0], (lineinf[4].equals("?") ? null : lineinf[4]), (lineinf[3].equals("?") ? null : lineinf[3]), lineinf[1], lineinf[5].equals("0") ? "male" : "female", this.name, lineinf[6].equals("1")));
				// Patient(String id, String mother, String father, String family, String gender, String project, boolean afflicted)
			}
			txt.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return this.getBSON().toString();
	}
	
	public BasicDBObject getBSON(){
		BasicDBObject obj = new BasicDBObject();
		obj.put("name",this.name);
		BasicDBObject p = new BasicDBObject();
		for(Patient P : this.patients.values()) p.put(P.getID(), P.getBSON());
		obj.put("patients", p);
		if(htmlTable!=null) obj.put("htmlTable", this.htmlTable);
		return obj;
	}
	
	public void loadMutationsFromDatabase(){
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
	        DBCollection coll = db.getCollection("projectmutations");
	        BasicDBObject obj;
	        DBCursor cursor = coll.find(this.getQuery());
	        int n = 0;
	        while(cursor.hasNext()){
	        	n++;
	        	obj = (BasicDBObject) cursor.next();
	        	AnnotatedMutation AM = new AnnotatedMutation(obj);
	        	this.mutations.put(AM.getSimpleMutation(), AM);
	        }
	        MC.close();
	        System.out.println(n + " mutations found in database");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BasicDBObject getQuery(){
		return new BasicDBObject("name",this.name);
	}
	
	public void loadMutationsFromFile(FileReader f){
		try {
			BufferedReader br = new BufferedReader(f);
			String line = br.readLine();
			String[] header = line.split("\t");
			AnnotatedMutation AM;
			SimplePatientMutation SPM;
			while((line=br.readLine())!=null){
				String[] lineinfo = line.split("\t"); // ID	Chromosome	Position	Reference	Alternate	Gene	CdnaVariation	ProteinVariation
				AM = new AnnotatedMutation(lineinfo[1],lineinfo[3],Integer.parseInt(lineinfo[2]),lineinfo[0].equals(".")? null : lineinfo[0], lineinfo[5].split("\\|"), lineinfo[6].split("\\|"), lineinfo[7].split("\\|"));
				for(String al: lineinfo[4].split(",")) AM.addAlternate(al);
				// AnnotatedMutation(String chr, String reference, int location, String rsID, String[] genes, String[] CDNA, String[] Protein)
				String ref = lineinfo[3];
				for(int i = 8; i < (header.length - 8)/3 + 8; i++){
					String[] alleles = lineinfo[i].split("/");
					if(alleles.length>1 && alleles[1].equals(alleles[0])&& !alleles[0].equals(ref)){
						SPM = new SimplePatientMutation(header[i].replace(".","_"),alleles[0],true);
						AM.addSimplePatientMutation(SPM);
					}
					else if(alleles.length>1 && !alleles[0].equals(alleles[1])){
						
						SPM = new SimplePatientMutation(header[i].replace(".","_"),alleles[0].equals(ref)? alleles[1] : alleles[0],false);
						AM.addSimplePatientMutation(SPM);
					}
				}
				this.mutations.put(AM.getSimpleMutation(), AM);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadMutationsIntoDatabase(){
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
	        DBCollection coll = db.getCollection("projectmutations");
	        BasicDBObject obj;
	        for(AnnotatedMutation AM: this.mutations.values()){
	        	obj = AM.getBSON();
	        	obj.put("name", this.name);
	        	coll.insert(obj);
	        }
	        MC.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void printMutations(){
		for(AnnotatedMutation AM: this.mutations.values()){
			System.out.println(AM.toString());
		}
	}
	
	public String getHTMLTable(String title){
		String o = "";
		o += "<div>";
		o += "<table class=\"table table-striped table table-hover\">\n";
		o += "\t<caption><h3>"+title+"</h3><caption>\n";
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
		Patient I;
		Object[] n =  this.patients.keySet().toArray();
		Arrays.sort(n);
		for(Object K: n){
			I=this.patients.get(K);
			if(I.getMutationCount()>0){
				o += "\t\t<tr>";
				o += "\t\t<td>";
				o += I.getID();
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				o += I.getMutationCount();
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				try{
					o += I.getFather()==null ? "." : I.getFather();
				}catch(Exception e){
					o += ".";
				}
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				try{
					o += I.getMother()==null ? "." : I.getMother();
				}catch(Exception e){
					o += ".";
				}
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				if(I.isAfflicted()) o+="Afflicted";
				else o += "Not Afflicted";
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				o += I.getGender();
				o += "\t\t</td>\n";
				o += "\t\t<td>";
			}
		}
		o += "\t</tbody>";
		o += "</table>";
		o += "</div>";
		this.htmlTable=o;
		return o;
	}
}
