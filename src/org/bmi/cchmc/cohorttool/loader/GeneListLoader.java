package org.bmi.cchmc.cohorttool.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class GeneListLoader {

	public GeneListLoader() {
		
	}
	
	public static void main(String[] args){
		Scanner S;
		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yy");
			String formattedDate = df.format(new Date()); 
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("genesets");
			S = new Scanner(new BufferedReader(new FileReader(args[0])));
			S.nextLine();
			BasicDBObject obj = null;
			String curr="";
			ArrayList<String> genes = new ArrayList<String>();
			ArrayList<String> entrez = new ArrayList<String>();
			while(S.hasNext()){
				String[] line = S.nextLine().split("\t");
				if(!curr.equals(line[3])){
					if(obj!=null){
						obj.put("gene", genes.toArray());
						obj.put("entrez", entrez.toArray());
						coll.insert(obj);
						System.out.println(obj.toString());
					}
					obj=new BasicDBObject();
					obj.put("name", line[3]);
					curr=line[3];
					obj.put("source", line[5]);
					obj.put("id", line[2]);
					obj.put("date", formattedDate);
					genes = new ArrayList<String>();
					entrez = new ArrayList<String>();
				}
				genes.add(line[1]);
				entrez.add(line[0]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
