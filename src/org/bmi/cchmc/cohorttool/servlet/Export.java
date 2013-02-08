package org.bmi.cchmc.cohorttool.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bmi.cchmc.cohorttool.analysis.Analysis;

import com.mongodb.*;

/**
 * Servlet implementation class Export
 */
@WebServlet("/Export")
public class Export extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Export() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			MongoClient MC = new MongoClient("localhost",27017);
			DB db = MC.getDB("CohortTool");
			DBCollection coll = db.getCollection("analysis");
			BasicDBObject q = new BasicDBObject();
			q.put("name", request.getParameterValues("name")[0] );
			q.put("analysisname", request.getParameterValues("analysisname")[0]);
			q = (BasicDBObject) coll.findOne(q);
			Analysis A = new Analysis(q, q.getString("analysisname"));
			System.out.println("Loading Mutations");
			A.loadMutationsFromAnalysisDatabase();
			String filename = request.getParameterValues("name")[0]+"-"+request.getParameterValues("analysisname")[0]+"-"+request.getParameterValues("setsToExport")[0]+".txt";
			A.exportMutations(request.getParameterValues("setsToExport")[0].equals("all"), this.getServletContext().getRealPath("output/"+filename));
			MC.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
