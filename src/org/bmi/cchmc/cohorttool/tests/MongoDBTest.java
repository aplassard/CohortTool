package org.bmi.cchmc.cohorttool.tests;

import com.mongodb.*;
import java.io.*;
import java.net.UnknownHostException;

public class MongoDBTest {

	
	public static void main(String[] args) throws UnknownHostException {
		Mongo m = new Mongo("localhost",27017);
		System.out.println("Connected to MongoDB Host");
		BasicDBObject doc = new BasicDBObject();
		doc.put("name","MongoDB");
		doc.put("type","database");
		doc.put("count",1);
		BasicDBObject info = new BasicDBObject();
		info.put("x",203);
		info.put("y",102);
		doc.put("info",info);
		DB db = m.getDB("test-db");
		DBCollection test = db.getCollection("test-connection");
		test.insert(doc);
		System.out.println("Successfully inserted document!");
		DBCursor cursor = test.find();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

}
