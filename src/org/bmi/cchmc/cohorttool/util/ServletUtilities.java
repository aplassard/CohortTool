package org.bmi.cchmc.cohorttool.util;

import java.net.UnknownHostException;

import org.bmi.cchmc.cohorttool.analysis.Analysis;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class ServletUtilities {
	public static String getBootStrapHeader(String title){
		String headers = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>";
		headers+=title;
		headers+="</title>\n<link href=\"http://twitter.github.com/bootstrap/assets/css/bootstrap.css\" rel=\"stylesheet\">\n<style>\nbody {\npadding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */\n}\n</style>";
	   	headers+="\n<link href=\"http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css\" rel=\"stylesheet\">";
	   	headers+="\n<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->";
	   	headers+="\n<!--[if lt IE 9]>";
	   	headers+="\n<script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>";
	   	headers+="\n<![endif]-->";
	   	headers+="\n<style type=\"text/css\"></style>";
	   	headers+="\n</head>";
	   	headers+="\n<body>";
		return headers;
	}
	
	public static String getBootStrapEnd(String content){
		String ends="\n"+content+"\n";
		ends+="<!-- Le javascript";
		ends+="\n================================================== -->";
		ends+="\n<!-- Placed at the end of the document so the pages load faster -->";
		ends+="<script src=\"http://twitter.github.com/bootstrap/assets/js/bootstrap.js\"></script>";
		/*
		ends+="\n<script src=\"./bootstrap/js/jquery.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-transition.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-alert.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-modal.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-dropdown.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-scrollspy.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-tab.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-tooltip.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-popover.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-button.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-collapse.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-carousel.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-typeahead.js\"></script>";
		*/
		ends+="\n</body>";
		ends+="\n</html>";
		return ends;
	}
	
	public static String wrapBootstrap(String title, String content){
		String output=getBootStrapHeader(title);
		output+=getBootStrapEnd(content);
		return output;
	}
	
	public static String getNormalNavbar(){
		String header="";
		header+="\n<div class=\"navbar navbar-inverse navbar-fixed-top\">";
		header+="\n<div class=\"navbar-inner\">";
		header+="\n<div class=\"container\">";
		header+="\n<a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">";
		header+="\n<span class=\"icon-bar\"></span>";
		header+="\n<span class=\"icon-bar\"></span>";
		header+="\n<span class=\"icon-bar\"></span>";
		header+="\n</a>";
		header+="\n<a class=\"brand\" href=\"/CohortTool/index.jsp\">Cohort Analyzer</a>";
		header+="\n <div class=\"nav-collapse collapse\">";
		header+="\n<ul class=\"nav\">";
		header+="\n<li><a href=\"/CohortTool/\">Home</a></li>";
		header+="\n<li><a href=\"/CohortTool/about.jsp\">About</a></li>";
		header+="\n<li><a href=\"/CohortTool/publications.jsp\">Publications</a></li>";
		header+="\n </ul>";
		header+="\n</div><!--/.nav-collapse -->";
		header+="\n</div>";
		header+="\n</div>";
		header+="\n</div>";
		return header;
	}
	
	public static String getGeneLists(){
		String o = "[ ";
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("genesets");
			DBCursor cursor = coll.find();
			while(cursor.hasNext()){
				o+="'";
				o+=cursor.next().get("name");
				o+="', ";
			}
			o+=" ]";
			MC.close();
			return o;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void main(String[] args){
		getAvailableAnalyses("test-1360181154202");
	}

	public static void getAvailableAnalyses(String name){
		MongoClient MC;
		try {
			MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("analysis");
			DBCursor c = coll.find(new BasicDBObject("name",name));
			while(c.hasNext()){
				BasicDBObject o = (BasicDBObject) c.next();
				System.out.println(o.get("name")+", analysis: "+o.getString("analysisname"));
			}
			MC.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public static String getAnalysisTabs(String name){
		String o="";
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("analysis");
			DBCursor c = coll.find(new BasicDBObject("name",name));
			while(c.hasNext()){
				BasicDBObject b = (BasicDBObject) c.next();
				System.out.println(b.toString());
				o+="<li><a href=\"#"+b.getString("analysisname").replace(' ','_')+"\" data-toggle=\"tab\">"+b.getString("analysisname") + "</a></li>\n";
			}
			MC.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public static String getAllAnalysisTables(String name){
		String o = "";
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("analysis");
			DBCursor c = coll.find(new BasicDBObject("name",name));
			while(c.hasNext()){
				BasicDBObject obj = (BasicDBObject) c.next();
				o+=	"<div class=\"tab-pane\" id=\""+obj.getString("analysisname").replace(' ','_')+"\"><br>\n";
				o+= getAnalysisTable(obj,obj.getString("analysisname"));
				o+= "</div>";
			}
			MC.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	public static String getAnalysisTable(BasicDBObject o ,String name){
		Analysis A = new Analysis(o,name);
		A.loadMutationsFromAnalysisDatabase();
		A.getMutationCounts();
		return A.getHTMLTable(A.getAnalysisName());
	}
}
