package org.bmi.cchmc.cohorttool.patient;

import java.util.ArrayList;
import java.util.List;
import org.bmi.cchmc.cohorttool.mutation.*;

import com.mongodb.*;

public class Individual {

	public void addMutation(Mutation m){
		if(this.mutations!=null) mutations.add( (PatientMutation) m);
		else{
			this.mutations = new ArrayList<PatientMutation>();
			mutations.add( (PatientMutation) m);
		}
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
		this.isAfflicted = (Boolean) o.get("isAfflicted");
		this.id= (String) o.get("id");
	}
	
	public Individual(String id){
		this.setId(id.replace(".","_"));
	}
	
	public BasicDBObject getBson(){
		BasicDBObject info = new BasicDBObject();
		info.append("id",this.getId());
		if(getMother()!=null)
			info.append("mother",this.getMother().id);
		if(getFather()!=null)
			info.append("father",this.getFather().id);
		info.append("isAfflicted",this.isAfflicted());
		info.append("Gender",this.getSex() ? "Female":"Male");
		if(getMutations()!=null&&this.getMutations().size()>0){
			ArrayList<PatientMutation> m = (ArrayList<PatientMutation>) this.getMutations();
			info.append("mutations",m);
		}
		return info;
	}
	
	public boolean getSex() {
		return sex;
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

	private List<PatientMutation> mutations;
	private Individual mother;
	private Individual father;
	private boolean isAfflicted;
	private String id;
	private boolean sex; // female=true male=false
	private String familyId;
	
}
