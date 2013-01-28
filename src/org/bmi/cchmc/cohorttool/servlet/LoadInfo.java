package org.bmi.cchmc.cohorttool.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.*;

/**
 * Servlet implementation class LoadInfo
 */
@WebServlet("/LoadInfo")
public class LoadInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("There's Nothing Here");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("filename");
		PrintWriter out = response.getWriter();
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        DB db = mongoClient.getDB("CohortTool");
        DBCollection projects = db.getCollection("projects");
        BasicDBObject p = (BasicDBObject) projects.findOne(new BasicDBObject("Analysis Name",name));
        System.out.println(p.toString());
        BasicDBObject patients = (BasicDBObject) ((BasicDBObject) p.get("Patient Info")).get("Patients");
        Set<String> patientIds = patients.keySet();
        System.out.println("Patients: ");
        for(String K: patientIds) System.out.println(K + ": "+patients.get(K));
		
	}

}
