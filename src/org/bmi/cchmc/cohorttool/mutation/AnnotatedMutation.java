package org.bmi.cchmc.cohorttool.mutation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.bmi.cchmc.cohorttool.translation.*;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class AnnotatedMutation extends Mutation {

	private String rsID=null;
	private HashSet<String> alternate;
	private String[] genes;
	private String[] CDNA;
	private String[] protein;
	private HashSet<SimplePatientMutation> patients;
	
	public HashSet<SimplePatientMutation> getSimplePatientMutations(){ return this.patients; }
	
	public AnnotatedMutation(String chr, String reference, int location, String rsID, String[] genes, String[] CDNA, String[] protein) {
		this.chr=chr;
		this.reference=reference;
		this.location=location;
		this.rsID=rsID;
		this.genes=genes;
		this.CDNA=CDNA;
		this.protein=protein;
		this.patients = new HashSet<SimplePatientMutation>();
		this.alternate = new HashSet<String>();
	}

	@Override
	public void addAlternate(String m) {
		alternate.add(m);
	}
	
	public SimpleMutation getSimpleMutation(){
		return new SimpleMutation(this.chr,this.reference,this.location);
	}
	
	public void addSimplePatientMutation(SimplePatientMutation SM){
		this.patients.add(SM);
	}
	
	public String toString(){
		String o =  this.chr+this.location+"."+this.reference +"->"+this.alternate.toString()+" rsID: "+this.rsID;
		o += " [ ";
		for (SimplePatientMutation s: this.patients) o+= s.toString()+", ";
		o+="]";
		return o;
	}
	
	public BasicDBObject getBSON(){
		BasicDBObject obj = new BasicDBObject();
		obj.put("chr", this.chr);
		obj.put("reference", this.reference);
		obj.put("alternate", this.alternate.toArray());
		obj.put("location", this.location);
		if(this.rsID!=null) obj.put("rsID", this.rsID);
		obj.put("genes",this.genes);
		obj.put("CDNA", this.CDNA);
		obj.put("protein", this.protein);
		ArrayList<BasicDBObject> a = new ArrayList<BasicDBObject>(this.patients.size());
		for(SimplePatientMutation S: this.patients) a.add(S.getBSON());
		obj.put("patients", a);
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public AnnotatedMutation(BasicDBObject o){
		this.patients = new HashSet<SimplePatientMutation>();
		this.chr = o.getString("chr");
		this.reference = o.getString("reference");
		this.location = o.getInt("location");
		BasicDBList b = (BasicDBList) o.get("genes");
		this.genes = new String[b.size()];
		for(int i = 0; i < b.size(); i++){
			this.genes[i] = b.get(i).toString();
		}
		b = (BasicDBList) o.get("CDNA");
		this.CDNA = new String[b.size()];
		for(int i = 0; i < b.size(); i++){
			this.CDNA[i] = b.get(i).toString();
		}
		b = (BasicDBList) o.get("protein");
		this.protein = new String[b.size()];
		for(int i = 0; i < b.size(); i++){
			this.protein[i] = b.get(i).toString();
		}
		this.rsID = o.getString("rsID");
		this.alternate = new HashSet<String>((Collection<? extends String>) o.get("alternate"));
		b = (BasicDBList) o.get("patients");
		for(Object obj: b){
			this.patients.add(new SimplePatientMutation((BasicDBObject) obj));
		}
	}

	public void removeHomozygous() {
		ArrayList<SimplePatientMutation> toRemove = new ArrayList<SimplePatientMutation>();
		for(SimplePatientMutation SPM: this.patients){
			if(SPM.homozygous) toRemove.add(SPM);
		}
		for(SimplePatientMutation SPM: toRemove) this.patients.remove(SPM);
	}
	
	public int getPatientCount(){
		return this.patients.size();
	}

	public void removeHeterozygous() {
		ArrayList<SimplePatientMutation> toRemove = new ArrayList<SimplePatientMutation>();
		for(SimplePatientMutation SPM: this.patients){
			if(!SPM.homozygous) toRemove.add(SPM);
		}
		for(SimplePatientMutation SPM: toRemove) this.patients.remove(SPM);
	}
	
	public boolean inDBSNP(){
		return this.rsID!=null;
	}

	public String toSimpleString() {
		return this.chr+this.location+this.reference+"->"+this.alternate.toString();
	}

	public String getCDNAString(){
		String o="";
		for(String s: this.CDNA){
			o+=s+",";
		}
		return o;
	}
	
	public String getGeneString(){
		String o="";
		for(String s: this.genes){
			o+=s+",";
		}
		return o;
	}
	
	public String getProteinString(){
		String o="";
		for(String s: this.protein){
			o+=s+",";
		}
		return o;
	}
	
	public String getRsID(){
		return this.rsID==null ? "null":this.rsID;
	}

	public String[] getGenes() {
		return this.genes;
	}

	public void removeNonComplemented(
			HashMap<String, Integer> PC) { // Patient, Count
		ArrayList<SimplePatientMutation> toRemove = new ArrayList<SimplePatientMutation>();
		for(SimplePatientMutation SPM: this.patients){
			if(!PC.containsKey(SPM.id)||PC.get(SPM.id)<2) toRemove.add(SPM);
		}
		for(SimplePatientMutation SPM: toRemove) this.patients.remove(SPM);
	}
	
	public void removeGranthamChange(int n){
		ArrayList<String> newp = new ArrayList<String>();
		for(String p: this.protein){
			if(p.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+[a-zA-Z]{3}\\)")){
				String[] t = p.split("([a-zA-Z0-9-.]+(-[0-9])?:p.\\(|[0-9]+|\\))"); ; // Substitution
				if(GranthamDistance.get(AminoAcid.lookup(t[1]), AminoAcid.lookup(t[2]))<=n){
					newp.add(p);
				}
			}
		}
		this.protein = new String[newp.size()];
		for(int i = 0; i < newp.size(); i++) this.protein[i]=newp.get(i);
	}
	
	public static void main(String[] args){
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			String line;
			System.out.println("Starting");
			int n = 0;
			int m =0;
			while((line=br.readLine())!=null){
				m++;
				if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+[a-zA-Z]{3}\\)")) {
					String[] t = line.split("([a-zA-Z0-9-.]+(-[0-9])?:p.\\(|[0-9]+|\\))"); ; // Substitution
					for(String a : t) System.out.print(a+" "+a.length()+" ");
					System.out.println();
				}
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+[a-zA-Z]{3}fs\\*[0-9]+\\)")) continue; // FrameShift 
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\(\\=\\)")) continue;  // No Change
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\?")) continue; // Unknown Change
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+[a-zA-Z]{3}fs\\*\\?\\)")) continue; //  FrameShift Unknown Effect
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+\\*\\)")) continue; // Early Stop
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\(Met1\\?\\)")) continue; // Start Mutilated, unknown effect
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+_[a-zA-Z]{3}[0-9]+del\\)")) continue; // Deletion
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+_([a-zA-Z]{3}[0-9]+)+ins([a-zA-Z]{3})+\\)")) continue; // Insertion
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+_[a-zA-Z]{3}[0-9]+dup\\)")) continue; // Duplication
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+_[a-zA-Z]{3}[0-9]+delins([a-zA-Z]{3})+\\)")) continue; // 3 bp Deletion
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\([a-zA-Z]{3}[0-9]+delins([a-zA-Z]{3})+\\)")) continue; // 3 bp insertion
				else if(line.matches("[a-zA-Z0-9-.]+(-[0-9])?:p.\\(\\*[0-9]+[a-zA-Z0-9]+\\*([0-9]+|\\?)\\)")) continue; // Stop codon mutilated
				else if(line.equals("")) continue; // blank line
				else{
					n++;
					System.out.println(line+" didn't pass");
				}
				
			}
			br.close();
			System.out.println("Done\n"+n+" didn't pass out of "+m);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getProteins() {
		return this.protein;
	}
}
