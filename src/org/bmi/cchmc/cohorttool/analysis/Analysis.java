package org.bmi.cchmc.cohorttool.analysis;

import org.bmi.cchmc.cohorttool.cohort.Cohort;
import org.bmi.cchmc.cohorttool.cohort.Patient;

import java.net.UnknownHostException;
import java.util.*;

import org.bmi.cchmc.cohorttool.mutation.FileMutation;
import org.bmi.cchmc.cohorttool.mutation.SimpleMutation;
import org.bmi.cchmc.cohorttool.translation.*;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

public class Analysis {
	private HashMap<String,Patient> patients;
	private HashMap<SimpleMutation,FileMutation> mutations;
	private int blosum80Cutoff;
	private int granthamDistanceCutoff=0;
	private String info;
	private String name;
	private boolean allowHomozygous=true;
	private boolean allowInDBSNP=true;
	
	public Analysis(Cohort C){
		this.patients = C.getPatients();
		this.mutations = C.getPatientFileHashMap();
	}
	
	public void setBlosum80Cutoff(int n){
		this.blosum80Cutoff=n;
	}
	
	public void setGranthamDistanceCutoff(int n){
		this.granthamDistanceCutoff=n;
	}

	public void Run(){
		if(this.granthamDistanceCutoff>0){
			runGrantham();
		}
		if(!this.allowInDBSNP){
			System.out.println("Running allow in DBSNP");
			System.out.println("There were " + this.mutations.size()+" to start.");
			runRemoveDBSNP();
			System.out.println("There were " + this.mutations.size()+" to end.");
		}
		
		if(!this.allowHomozygous){
			System.out.println("Running remove het");
			System.out.println("There were " + this.mutations.size()+" to start.");
			runRemoveHet();
			System.out.println("There were " + this.mutations.size()+" to end.");
		}
	}
	
	private void runGrantham(){
		ArrayList<SimpleMutation> toRemove = new ArrayList<SimpleMutation>();
		for(SimpleMutation SM: mutations.keySet()){
			if(deleteBasedOnGrantham(mutations.get(SM))){
				toRemove.add(SM);
			}
		}
		for(SimpleMutation SM: toRemove) this.mutations.remove(SM);
	}
	
	private boolean deleteBasedOnGrantham(FileMutation FM){
		String[] P = FM.getProtein();
		String[] AA;
		for(String p: P){
			if(p.contains("p.(")){
				System.out.println("Protein: "+p);
				AA=splitProtein(p);
				if(AA.length>0){
					AminoAcid a = AminoAcid.lookup(AA[0]);
					AminoAcid b = AminoAcid.lookup(AA[AA.length-1]);
					System.out.println("A: "+AA[0]);
					System.out.println("B: "+AA[AA.length-1]);
					System.out.println("A: "+a.toString());
					System.out.println("B: "+b.toString());
					if(GranthamDistance.get(a, b)>this.granthamDistanceCutoff) return true;
				}
			}
		}
		return false;
	}
	
	private String[] splitProtein(String P){
		if(P.contains("(=)")||P.equals("")) return new String[0];
		else{
			P = P.split("\\(")[1];
			System.out.println("Front Removed "+P);
			P = P.split("\\)")[0];
			System.out.println("End Removed "+P);
			return P.split("[0-9]");
		}
	}
	
	public void runRemoveDBSNP(){
		ArrayList<SimpleMutation> toRemove = new ArrayList<SimpleMutation>();
		for(SimpleMutation SM: this.mutations.keySet()){
			FileMutation FM = this.mutations.get(SM);
			if(FM.hasRSID()) toRemove.add(SM);
		}
		for(SimpleMutation SM: toRemove) this.mutations.remove(SM);
		
	}
	
	public void setAllowInDBSNP(boolean t){
		this.allowInDBSNP=t;
	}
	
	public void setAllowHomozygous(boolean t){
		this.allowHomozygous=t;
	}
	
	
	public static void main(String[] args) throws UnknownHostException{
		BasicDBObject o = new BasicDBObject("Analysis Name" , "test-1359487437014");
		BasicDBObject p = (BasicDBObject) new MongoClient("localhost",27017).getDB("CohortTool").getCollection("projects").findOne(o);
		
		Cohort C = new Cohort(p);
		C.getMutationsFromDataBase();
		
		Analysis A = new Analysis(C);
		A.setAllowInDBSNP(false);
		A.Run();
	}

}
