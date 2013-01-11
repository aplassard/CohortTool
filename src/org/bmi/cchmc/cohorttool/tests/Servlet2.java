package org.bmi.cchmc.cohorttool.tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

import com.mongodb.*;

@WebServlet("/Servlet2")
public class Servlet2 extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        ArrayList<String> paramNames = Collections.list(request.getParameterNames());
        String[] paramValues=request.getParameterValues("datasets");
        Mongo m = new Mongo("localhost",27017);
        DB db = m.getDB("CohortTool");
        DBCollection expvals = db.getCollection("expressionvalues");
        System.out.println("Connected to MongoDB Host on Servlet2");
        ArrayList<DBObject> IDs = new ArrayList<DBObject>();
        ArrayList<String> smps = new ArrayList<String>();
        ArrayList<String> genes = new ArrayList<String>();
        ArrayList<ArrayList<Double>> data= new ArrayList<ArrayList<Double>>();
        ArrayList<String> geneinfo=new ArrayList<String>();
        for(int j = 0; j < paramValues.length;j++){
        	DBObject searchById = new BasicDBObject("_id", new ObjectId(paramValues[j]));
        	DBObject found = expvals.findOne(searchById);
        	Map<String,Double> gene= (Map<String, Double>) found.get("genes");
        	Iterator iterator=gene.entrySet().iterator();
        	ArrayList<Double>newdata=new ArrayList<Double>();
        	smps.add((String)found.get("name"));
        	geneinfo=new ArrayList<String>();
        	int n=0;
        	while(n<100&&iterator.hasNext()){
        		n++;
        		Map.Entry mapEntry=(Map.Entry)iterator.next();
        		newdata.add((Double) mapEntry.getValue());
        		geneinfo.add((String) mapEntry.getKey());
        	}
        	data.add(newdata);
    	}
        DBObject y = new BasicDBObject();
        y.put("smps",geneinfo);
        y.put("data",data);
        String[] desc=new String[1];
        desc[0]="Intensity";
        y.put("desc",desc);
        y.put("vars",smps);
        DBObject d = new BasicDBObject();
        d.put("y",y);
        DBObject info = new BasicDBObject();
        info.put("graphType","Heatmap");
        info.put("gradient",true);
        info.put("centerData",true);
        info.put("indicatorWidth", 3);
        info.put("linkage","average");
        info.put("distance","euclidian");
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Heatmap</title>");
        out.println("<!--[if IE]><script type=\"text/javascript\" src=\"./js/excanvas.js\"></script><![endif]-->");
        out.println("<!--[if IE]><script type=\"text/javascript\" src=\"./js/extent.js\"></script><![endif]-->");
        out.println("<script type=\"text/javascript\" src=\"./js/canvasXpress.min.js\"></script>");
        out.println();
        out.println("<script>");
        out.println();
        out.println("var showHeatmap = function() {");
        out.println("var cx = new CanvasXpress(\"canvas\", ");
        out.println(d.toString());
        out.println(" , ");
        out.println(info.toString());
        out.println(");");
        out.println("cx.clusterSamples();");
        out.println("cx.clusterVariables();");
        out.println("cx.kmeansSamples();");
        out.println("cx.kmeansVariables();");
        out.println("}");
        out.println("</script>");
        out.println("</head>");
        out.println("<body onload=\"showHeatmap();\">");
        out.println("<center>");
        out.println("<h1>Heatmap</h1>");
        out.println("<canvas id=\"canvas\" width=\"613\" height=\"500\"></canvas>");
        out.println("</center>");
        out.println("</body>");
        out.println("</html>");
        
        
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
			doGet(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
