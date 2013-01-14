package org.bmi.cchmc.cohorttool.patient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import com.mongodb.BasicDBObject;

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
	

}
