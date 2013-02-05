package org.bmi.cchmc.cohorttool.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bmi.cchmc.cohorttool.analysis.*;
import com.mongodb.*;

/**
 * Servlet implementation class Analysis
 */
@WebServlet("/Analysis/")
public class Analyze extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Analyze() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("We're Here");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		for(String K: request.getParameterMap().keySet()){
			out.println(K+": "+request.getParameter(K));
		}
		MongoClient MC = new MongoClient("localhost",27017);
		DB db = MC.getDB("CohortTool");
		DBCollection coll = db.getCollection("projects");
		BasicDBObject o = (BasicDBObject) coll.findOne(new BasicDBObject("name", request.getParameterValues("id")[0]));
		Analysis A = new Analysis(o);
		A.loadMutationsFromDatabase();
		if(request.getParameterMap().containsKey("homozygous")) A.removeHomozygous();
		out.close();
	}

}
