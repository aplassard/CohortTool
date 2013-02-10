package org.bmi.cchmc.cohorttool.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bmi.cchmc.cohorttool.analysis.*;
import org.bmi.cchmc.cohorttool.cohort.Cohort;

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
		MongoClient MC = new MongoClient("localhost",27017);
		DB db = MC.getDB("CohortTool");
		DBCollection coll = db.getCollection("projects");
		BasicDBObject o = (BasicDBObject) coll.findOne(new BasicDBObject("name", request.getParameterValues("id")[0]));
		Analysis A = new Analysis(o, request.getParameterValues("name")[0]);
		A.loadMutationsFromDatabase();
		Cohort C = new Cohort(o);
		C.loadMutationsFromDatabase();
		C.getMutationCounts();
		if(request.getParameterMap().containsKey("homozygous")) A.removeHomozygous();
		if(request.getParameterMap().containsKey("heterozygous")) A.removeHeterozygous();
		if(request.getParameterMap().containsKey("rsID")) A.removeDBSNP();
		if(request.getParameterMap().containsKey("complemented")) A.leaveComplemented();
		A.loadIntoDatabase();
		A.loadMutationsIntoDataBase();
		MC.close();
		request.setAttribute("patientset", C.getHTMLTable(C.getName()));
		request.setAttribute("id", request.getParameterValues("id")[0]);
		request.getRequestDispatcher("/ContinueAnalysis.jsp").forward(request,response);
	}

}
