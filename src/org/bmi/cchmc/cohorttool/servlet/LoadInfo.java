package org.bmi.cchmc.cohorttool.servlet;

import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bmi.cchmc.cohorttool.cohort.Cohort;

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
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        DB db = mongoClient.getDB("CohortTool");
        DBCollection projects = db.getCollection("projects");
        BasicDBObject p = (BasicDBObject) projects.findOne(new BasicDBObject("name",name));
        Cohort C = new Cohort(p);
        System.out.println(C.toString());
        C.loadMutationsFromFile(new FileReader(this.getServletContext().getRealPath("SNPomics/output/"+name+".txt")));
        C.loadMutationsIntoDatabase();
        C.getMutationCounts();
		request.setAttribute("patientset", C.getHTMLTable(C.getName()));
		request.setAttribute("name", name);
		DBCollection htmltables = db.getCollection("htmltables");
		p = new BasicDBObject();
		p.put("name", C.getName());
		p.put("patientinfo",C.getHTMLTable(C.getName().split("-")[0].replace('_', ' ')));
		htmltables.insert(p);
		mongoClient.close();
		request.getRequestDispatcher("/Analysis.jsp").forward(request,response);
	}

}
