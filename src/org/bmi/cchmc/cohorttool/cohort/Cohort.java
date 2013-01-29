package org.bmi.cchmc.cohorttool.cohort;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.bmi.cchmc.cohorttool.mutation.FileMutation;
import org.bmi.cchmc.cohorttool.mutation.PatientMutation;
import org.bmi.cchmc.cohorttool.mutation.SimpleMutation;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Cohort {

	private HashMap<String,Patient> patients;
	private ArrayList<FileMutation> mutations;
	private boolean hasMutationsInDatabase = false;
	private String name;
	
	public Cohort() {

	}
	
	public Cohort(ObjectId o) throws UnknownHostException{

		this((BasicDBObject) new MongoClient("localhost",27017).getDB("CohortTool").getCollection("projects").findOne(new BasicDBObject("_id",o)));

	}
	

	public Cohort(BasicDBObject P){
		DBObject Patients = (DBObject) ((DBObject) P.get("Patient Info")).get("Patients");
        Set<String> keys = Patients.keySet();
        this.patients = new HashMap<String,Patient>();
        Patient I;
        for(String K : keys){
        	I = new Patient((DBObject) Patients.get(K));
        	this.patients.put(K, I);
        }
        if(P.containsKey((Object) "hasMutationsInDatabase")) this.hasMutationsInDatabase = (Boolean) P.get("hasMutationsInDatabase");
        else this.hasMutationsInDatabase = false;
        this.name = (String) P.get("Analysis Name");
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
		String r = lineinfo[3];
		String[] alleles;
		PatientMutation p;
		Patient I;
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

	public BasicDBObject getQuery(){
		return new BasicDBObject("name",this.name);
	}

	public void loadMutationsIntoDatabase(){
		if(this.mutations.size()==0) return;
		else{
			try {
				System.out.println("\n\nname "+this.name );
				MongoClient MC = new MongoClient("localhost",27017);
				DB db = MC.getDB("CohortTool");
		        DBCollection mutations = db.getCollection("mutations");
		        BasicDBObject m;
		        for(FileMutation fm : this.mutations){
		        	m = new BasicDBObject();
		        	m.putAll(fm.getBSON().toMap());
		        	m.put("name", this.name);
		        	mutations.insert(m);
		        }
		        System.out.println("\nDone");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void loadPatientMutationsIntoDatabase() {
		MongoClient MC;
		try {
			MC = new MongoClient("localhost",27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		}
		DB db = MC.getDB("CohortTool");
        DBCollection mutations = db.getCollection("patientmutations");
		System.out.println("Mutations");
		BasicDBObject b;
		int n =0, m= 10000;
		for(Patient P: this.patients.values()){
			for(PatientMutation PM: P.getMutations()){
				n++;
				if(n>m){
					m+=10000;
					System.out.println("Inserted " + (m-10000) + " mutations");
				}
				b = PM.toBSON();
				b.put("patient",P.getId());
				b.put("name", this.name);
				mutations.insert(b);
			}
		}
	}

	public void getMutationsFromDataBase(){
		try {
			this.mutations = new ArrayList<FileMutation>();
			System.out.println("Started loading file mutations");
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection mutations = db.getCollection("mutations");
			DBCollection patientMutations = db.getCollection("patientmutations");
			DBCursor cursor = mutations.find(this.getQuery());
			FileMutation fm;
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject) cursor.next();
				fm = new FileMutation(obj);
				this.mutations.add(fm);
			}
			
			cursor = patientMutations.find(this.getQuery());
			PatientMutation pm;
			Patient P;
			System.out.println("Started loading patient mutations");
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject) cursor.next();
				pm = new PatientMutation(obj);
				P = this.patients.get(obj.getString("patient"));
				P.addMutation(pm);
				this.patients.remove(obj.getString("patient"));
				this.patients.put(obj.getString("patient"), P);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
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
		Patient I;
		Object[] n =  this.patients.keySet().toArray();
		Arrays.sort(n);
		for(Object K: n){
			I=this.patients.get(K);
			if(I.getMutationCount()>0){
				o += "\t\t<tr>";
				o += "\t\t<td>";
				o += I.getId();
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				o += I.getMutationCount();
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				try{
				o += I.getFather()==null ? "" : I.getFather();
				}catch(Exception e){
					o += " ";
				}
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				try{
					o += I.getMother()==null ? "" : I.getMother();
					}catch(Exception e){
						o += " ";
					}
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				if(I.isAfflicted()) o+="Afflicted";
				else o += "Not Afflicted";
				o += "\t\t</td>\n";
				o += "\t\t<td>";
				o += I.getGender();
				o += "\t\t</td>\n";
				o += "\t\t</tr>\n";
			}
		}
		o += "\t</tbody>";
		o += "</table>";
		o += "</div>";
		return o;
	}

	public HashMap<String,Patient> getPatients(){
		return this.patients;
	}
	
	public static void main(String[] args) throws UnknownHostException{
		BasicDBObject o = new BasicDBObject("Analysis Name" , "test-1359487437014");
		BasicDBObject p = (BasicDBObject) new MongoClient("localhost",27017).getDB("CohortTool").getCollection("projects").findOne(o);
		
		Cohort C = new Cohort(p);
		C.getMutationsFromDataBase();
		
		HashMap<SimpleMutation,FileMutation> n = C.getPatientFileHashMap();
		for(SimpleMutation SM: n.keySet()){
			System.out.println(SM.toString()+" --> " + n.get(SM).toString());
		}
	}

	public HashMap<SimpleMutation,FileMutation> getPatientFileHashMap(){
		HashMap<SimpleMutation,FileMutation> n = new HashMap<SimpleMutation,FileMutation>();
		for(FileMutation FM: this.mutations){
			for(String a: FM.getAlternate()){
				n.put(new SimpleMutation(FM.getChr(),FM.getReference(),a,FM.getLocation()), FM);
			}
		}
		return n;
	}
}
