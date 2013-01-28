package org.bmi.cchmc.cohorttool.patient;

import java.util.ArrayList;
import java.util.List;
import org.bmi.cchmc.cohorttool.mutation.*;

import com.mongodb.*;

public class Individual {

	public void addMutation(Mutation m){
		if(this.mutations==null) this.mutations = new ArrayList<PatientMutation>();
		mutations.add( (PatientMutation) m);
	}
	
	public void setMutations(List<PatientMutation> m){
		this.mutations=m;
	}
	
	public List<PatientMutation> getMutations(){
		return this.mutations;
	}

	public Individual getMother() {
		return mother;
	}

	public void setMother(Individual mother) {
		this.mother = mother;
	}

	public Individual getFather() {
		return father;
	}

	public void setFather(Individual father) {
		this.father = father;
	}

	public boolean isAfflicted() {
		return isAfflicted;
	}

	public void setAfflicted(boolean isAfflicted) {
		this.isAfflicted = isAfflicted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id.replace(".","_");
	}

	public Individual(){
		this.mutations = new ArrayList<PatientMutation>();
		this.mother=null;
		this.father=null;
		this.isAfflicted=(Boolean) null;
		this.id=null;
	}
	
	public Individual(DBObject o){
		this.mutations = new ArrayList<PatientMutation>();
		this.mother=null;
		this.father=null;
		try{
			this.isAfflicted = (Boolean) o.get("isAfflicted");		}
		catch(Exception e){
			this.isAfflicted=false;
		}
		this.id= (String) o.get("id");
	}
	
	public Individual(String id){
		this.setId(id.replace(".","_"));
	}
	
	public BasicDBObject getBSON(){
		BasicDBObject info = new BasicDBObject();
		info.append("id",this.getId());
		if(getMother()!=null)
			info.append("mother",this.getMother().id);
		if(getFather()!=null)
			info.append("father",this.getFather().id);
		info.append("isAfflicted",this.isAfflicted());
		info.append("Gender",this.getSex());
		
		try{
			this.mutations.size();
		}
		catch(Exception e){
			this.mutations = new ArrayList<PatientMutation>();
		}
		if(this.mutations.size()>0){
			BasicDBObject muts = new BasicDBObject();
			for(PatientMutation pm: this.mutations) muts.put(pm.toString(),pm.toBSON());
			info.put("mutations",muts);
		}
		
		return info;
	}
	
	public String getSex() {
		return sex ? "female":"male";
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	
	public int countMutations(){
		try{
		return this.mutations.size();
		}catch(Exception e){
			return 0;
		}
	}

	private List<PatientMutation> mutations;
	private Individual mother;
	private Individual father;
	private boolean isAfflicted;
	private String id;
	private boolean sex; // female=true male=false
	private String familyId;
	
}
