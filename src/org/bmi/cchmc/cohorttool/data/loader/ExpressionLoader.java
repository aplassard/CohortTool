package org.bmi.cchmc.cohorttool.data.loader;

import com.mongodb.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ExpressionLoader {

	/**
	 * @param args
	 * String of files containing expression data in the form of
	 * 		#meta=info
	 * 		IDType	Name1	Name2	etc.
	 * 		ID		expval	expval	etc.
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception  {
		Mongo m = new Mongo("localhost",27017);
		DB db = m.getDB("CohortTool");
		DBCollection expvals = db.getCollection("expressionvalues");
		DBCollection geneinfo = db.getCollection("geneinfo");
		System.out.println("Connected to MongoDB Host");
		for(int i=0;i<args.length;i++){
			String line,inf;
			BasicDBObject metaInfo=new BasicDBObject();
			BufferedReader in = new BufferedReader(new FileReader(args[i]));
			while((line=in.readLine())!=null){
				if(!line.substring(0,1).equals("#")){
					break;
				}
				else{
					inf=line.substring(1,line.length());
					String[] meta = inf.split("=");
					metaInfo.put(meta[0].replaceAll("\\s",""),meta[1].trim().replaceAll("\\s",""));
				}
			}
			String[] header=line.split("\t");
			String type=header[0].toLowerCase();
			String[] names = new String[header.length-1];
			for(int j=1;j<header.length;j++){
				names[j-1]=header[j];
			}
			BasicDBObject[] expressionValues=new BasicDBObject[names.length];
			for(int j=0;j<expressionValues.length;j++){
				expressionValues[j]=new BasicDBObject();
			}
			
			String[] vals;
			System.out.println();
			System.out.println("Loading from file " + args[i]+"!");
			String gene;
			while((line=in.readLine())!=null){
				vals=line.split("\t");
				gene=null;
				try{
					gene=(String) geneinfo.findOne(new BasicDBObject(type,vals[0])).get("entrez");
					for(int j=1;j<vals.length;j++){
						expressionValues[j-1].put(gene,Float.parseFloat(vals[j]));
					}
				}
				catch(Exception e){
					System.out.println(vals[0]);
				}
			}
			in.close();
			BasicDBObject[] inputs = new BasicDBObject[names.length];
			for(int j=0;j<inputs.length;j++){
				inputs[j]=new BasicDBObject();
			}
			for(int j = 0;j<inputs.length;j++){
				inputs[j].put("name",names[j]);
				inputs[j].put("Meta Info",metaInfo);
				inputs[j].put("genes",expressionValues[j]);
				expvals.insert(inputs[j]);
			}
		}
		System.out.println("Done Loading Objects into database");
		expvals.createIndex(new BasicDBObject("name", 1));
	}

}
