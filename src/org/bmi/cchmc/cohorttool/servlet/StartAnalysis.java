package org.bmi.cchmc.cohorttool.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import org.bmi.cchmc.cohorttool.util.*;
import org.bmi.cchmc.cohorttool.patient.PatientSet;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StartAnalysis
 */
@WebServlet("/StartAnalysis")
public class StartAnalysis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartAnalysis() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("There's nothing here");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String id = request.getParameterValues("id")[0];
		PatientSet PS = new PatientSet(new ObjectId(id));
		String filename = request.getParameterValues("file")[0];
		PS.getMutations(this.getServletContext().getRealPath("SNPomics/output/"+filename+".txt"));
		try {
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB("CohortTool");
	        DBCollection coll = db.getCollection("projects");
	        ObjectId o = new ObjectId(id);
	        coll.findAndModify(new BasicDBObject("_id",o),PS.getBSON());
	        out.print(ServletUtilities.getBootStrapHeader("Start Analysis"));
			String content="";
			content+="<div class=\"container\">\n";
			content+=ServletUtilities.getNormalNavbar();
			content+="\n<div>\n";
			content+="<h2 class=\"text-success\">Start Analysis</h2>\n";
			content+=PS.getHTMLTable();
			out.print(ServletUtilities.getBootStrapEnd(content));
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			out.println("Something went wrong");
		}
		
	}

}
