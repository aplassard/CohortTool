package org.bmi.cchmc.cohorttool.tests;

import java.net.UnknownHostException;

import com.mongodb.*;

public class Gene {

	/**
	 * @param args
	 */
	private String entrezID=null;
	
	public String getEntrezid() {
		return this.entrezID;
	}
	public void setEntrezid(String e){
		this.entrezID=e;
	}
	
	public Gene(String name, String type) throws UnknownHostException{
		Mongo m = new Mongo("localhost",27017);
		DB db = m.getDB("CohortTool");
		DBCollection expvals = db.getCollection("geneinfo");
		BasicDBObject query=new BasicDBObject();
		query.put(type,name);
		DBObject gene = expvals.findOne(query);
		if(gene==null){
			System.out.println("Gene: "+name+" not found!");
			return;
		}
		else{
			this.setEntrezid((String)gene.get("entrez"));
		}
	}

}
