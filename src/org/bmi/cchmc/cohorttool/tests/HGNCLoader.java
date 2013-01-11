package org.bmi.cchmc.cohorttool.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.mongodb.*;

public class HGNCLoader {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Mongo m = new Mongo("localhost",27017);
		DB db = m.getDB("CohortTool");
		DBCollection genes = db.getCollection("geneinfo");
		System.out.println("Connected to MongoDB Host");
		for(int i=0;i<args.length;i++){
			String line;
			BufferedReader in = new BufferedReader(new FileReader(args[i]));
			line=in.readLine();
			String[] header=line.split("\t");
			String[] vals;
			while((line=in.readLine())!=null){
				vals=line.split("\t");
				if(vals.length>0){
					BasicDBObject gene=new BasicDBObject();
					for(int j = 0; j<vals.length;j++){
						if(!vals[j].equals("")){
							gene.put(header[j].toLowerCase(),vals[j].toLowerCase());
						}
					}
					System.out.println(gene.toString());
					//genes.insert(gene);
				}
				
				
			}
			in.close();
			System.out.println("Finished Loading Documents");
		}
		System.out.println("Generating Indices");
		genes.createIndex(new BasicDBObject("symbol", 1));
		genes.createIndex(new BasicDBObject("entrez", 1));
		genes.createIndex(new BasicDBObject("refseq", 1));
		genes.createIndex(new BasicDBObject("ensembl", 1));
	}

}
