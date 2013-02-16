package org.bmi.cchmc.cohorttool.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.URL;

import org.bmi.cchmc.cohorttool.analysis.Analysis;
import org.bmi.cchmc.cohorttool.cohort.Cohort;

import com.mongodb.BasicDBList;
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
		String charset = "UTF-8";
		String form = "single";
		String separator = ",";
		String symbolType="entrez";
		String genelist = "1584,adrenal\n";
		String request = "http://toppcluster.cchmc.org/CheckGenes";
		String query;
		try {
			System.out.println("Started");
			query = String.format("form=%s&separator=%s&symbolType=%s&genelist=%s", 
					URLEncoder.encode(form, charset),
					URLEncoder.encode(separator, charset),
					URLEncoder.encode(symbolType, charset),
					URLEncoder.encode(genelist, charset)
					);
			URLConnection connection = new URL(request).openConnection();
			connection.setDoOutput(true); // Triggers POST.
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			OutputStream output = null;
			try {
			     output = connection.getOutputStream();
			     output.write(query.getBytes(charset));
			} finally {
			     if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
			}
			InputStream response = connection.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				o+=	"<div class=\"tab-pane\" id=\""+obj.getString("analysisname").replace(' ','_')+"\">\n";
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
	
	public static String getCohortTable(String name){
		String o = "";
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("projects");
			System.out.println(name);
			Cohort C = new Cohort( (BasicDBObject) coll.findOne(new BasicDBObject("name",name)));
			MC.close();
			C.loadMutationsFromDatabase();
			C.getMutationCounts();
			return C.getHTMLTable(C.getName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	public static String getExportList(String name){
		String o = "";
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("analysis");
			System.out.println(name);
			DBCursor c = coll.find(new BasicDBObject("name",name));
			while(c.hasNext()){
				BasicDBObject obj = (BasicDBObject) c.next();
				o+="<label class=\"radio\">\n";
				o+="<input type=\"radio\" value=\""+obj.get("analysisname")+"\" name=\"analysisname\">"+obj.get("analysisname")+"</input><br>\n";
				o+="</label>";
			}
			MC.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	public static String getTabContent(String name){
		String o ="";
		try {
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("htmltables");
			System.out.println(name);
			BasicDBObject obj = (BasicDBObject) coll.findOne(new BasicDBObject("name",name));
			o+="<ul class=\"nav nav-pills\">\n";
			o+="<li class=\"active\"><a href=\"#patients\" data-toggle=\"tab\">Patient Info</a></li>\n";
			o+="<li><a href=\"#new\" data-toggle=\"tab\">Add New Analysis</a></li>\n";
			o+="<li><a href=\"#long\" data-toggle=\"tab\">Long Term Link</a></li>\n";
			if(obj.containsField("analysis")){
				o+="<li class=\"dropdown\">\n";
				o+="<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">Available Analyses<b class=\"caret\"></b></a>";
				o+="<ul class=\"dropdown-menu\">";
				BasicDBList a = (BasicDBList) obj.get("analysis");
				for(int i = 0; i < a.size(); i++){
					BasicDBObject t = (BasicDBObject) a.get(i);
					o+="<li><a href=\"#";
					o+=t.get("id");
					o+="\"data-toggle=\"tab\">";
					o+=t.get("name");
					o+="</a></li>\n";
				}
				o+="</ul></li>\n";
				o+="<li><a href=\"#export\" data-toggle=\"tab\">Export</a></li>\n";
			}
			o+="</ul>";
			o+="<div class=\"tab-content\">\n";
			o+="<div class=\"tab-pane active\" id=\"patients\">\n<br>\n";
			o+=obj.getString("patientinfo");
			o+="</div>\n";
			o+="<div class=\"tab-pane\" id=\"new\">\n";
			o+="New Analysis<br>\n<form action=\"/CohortTool/Analysis/\" method=\"post\">\n<fieldset>\n<input type=\"text\" placeholder=\"Name of Analysis\" class=\"span3\" name=\"analysisname\"/>\n<label class=\"checkbox\">\n";
			o+="<input type=\"checkbox\" name=\"heterozygous\"/> Remove Heterozygous Mutations<br>\n</label>\n<label class=\"checkbox\">\n<input type=\"checkbox\" name=\"homozygous\"/> Remove Homozygous Mutations </label>\n";
			o+="<label class=\"checkbox\">\n<input type=\"checkbox\" name=\"rsID\"/> Remove Mutations in dbSNP </label>\n<label class=\"checkbox\">\n";
			o+="<input type=\"checkbox\" name=\"complemented\"/> Get Complemented Mutations </label>\n<input type=\"hidden\" name=\"name\" value=\""+name+"\"/>\n";
			o+="<p>";
			o+="<label for=\"granthamscore\">Grantham Score Cutoff: <a href=\"#\" id=\"granthaminfo\" rel=\"popover\">(Click For More Info)</a></label>\n";
			o+="<input type=\"text\" name=\"granthamscore\" id=\"granthamscore\" style=\"border: 0; color: #f6931f; font-weight: bold;\" />\n";
			o+="</p>\n";
			o+="<div class=\"span3\" id=\"slider\"></div><br>\n";
			o+="<p>";
			o+="<label for=\"blosumscore\">Blosum Score Cutoff: <a href=\"#\" id=\"blosuminfo\" rel=\"popover\">(Click For More Info)</a></label>\n";
			o+="<input type=\"text\" name=\"blosumscore\" id=\"blosumscore\" style=\"border: 0; color: #f6931f; font-weight: bold;\" />\n";
			o+="</p>";
			o+="<div class=\"span3\" id=\"blosumslider\"></div>\n";
			o+="</fieldset>\n<button type=\"submit\" class=\"btn\">Submit</button>\n</form>\n</div>\n";
			if(obj.containsField("analysis")){
				BasicDBList a = (BasicDBList) obj.get("analysis");
				for(int i = 0; i < a.size(); i++){
					BasicDBObject t = (BasicDBObject) a.get(i);
					o+="<div class=\"tab-pane\" id=\"";
					o+=t.getString("id");
					o+="\">\n<br>\n";
					o+=t.getString("table");
					o+="</div>\n";
				}
				o+="<div class=\"tab-pane\" id=\"export\">\n";
				o+="<h3>Export</h3><br>\n";
				o+="<form action=\"/CohortTool/Export\" method=\"get\">\n";
	  			o+="<fieldset>\n";
  				o+="<h3>Select Lists to Export</h3><br>\n";
  				for(int i = 0; i < a.size(); i++){
					BasicDBObject t = (BasicDBObject) a.get(i);
					o+="<label class=\"radio\">\n";
					o+="<input type=\"radio\" value=\""+t.get("name")+"\" name=\"analysisname\">"+t.get("name")+"</input><br>\n";
					o+="</label>\n";
				}
  				o+="<label class=\"radio inline\">\n";
  				o+="<input type=\"radio\" class=\"inline\" name=\"setsToExport\" value=\"all\" checked />All\n";
  				o+="</label>\n";
  				o+="<label class=\"radio inline\">\n";
  				o+="<input type=\"radio\" class=\"inline\" name=\"setsToExport\" value=\"afflicted\" checked />Afflicted\n";
  				o+="</label>\n";
  				o+="<input type=\"hidden\" name=\"name\" value="+name+" /><br>\n";
  				o+="<input type=\"submit\" />\n";
  				o+="</fieldset>\n";
  				o+="</form>\n";
  				o+="</div>\n";
			}
			MC.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return o;
	}
}
