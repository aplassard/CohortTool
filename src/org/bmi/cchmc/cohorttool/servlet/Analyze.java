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
		MongoClient MC = new MongoClient("localhost",27017);
		DB db = MC.getDB("CohortTool");
		DBCollection coll = db.getCollection("projects");
		BasicDBObject o = (BasicDBObject) coll.findOne(new BasicDBObject("name", request.getParameterValues("name")[0]));
		Analysis A = new Analysis(o, request.getParameterValues("analysisname")[0]);
		A.loadMutationsFromDatabase();
		if(request.getParameterMap().containsKey("homozygous")) A.removeHomozygous();
		if(request.getParameterMap().containsKey("heterozygous")) A.removeHeterozygous();
		if(request.getParameterMap().containsKey("rsID")) A.removeDBSNP();
		if(request.getParameterMap().containsKey("complemented")) A.leaveComplemented();
		if(request.getParameterValues("granthamscore")[0] != null&&!request.getParameterValues("granthamscore")[0].equals("0")) A.removeGrantham(Integer.parseInt(request.getParameterValues("granthamscore")[0]));
		if(request.getParameterValues("blosumscore")[0] != null&&!request.getParameterValues("blosumscore")[0].equals("11")) A.removeBlosum(Integer.parseInt(request.getParameterValues("blosumscore")[0]));
		A.loadIntoDatabase();
		A.loadMutationsIntoDataBase();
		A.getMutationCounts();
		coll = db.getCollection("htmltables");
		BasicDBObject obj = (BasicDBObject) coll.findOne(new BasicDBObject("name",request.getParameterValues("name")[0]));
		BasicDBList l;
		if(obj.containsField("analysis")) l = (BasicDBList) obj.get("analysis");
		else l = new BasicDBList();
		BasicDBObject an = new BasicDBObject();
		an.put("id", request.getParameterValues("analysisname")[0].replace(' ', '_'));
		an.put("name", request.getParameterValues("analysisname")[0]);
		an.put("table",A.getHTMLTable(A.getAnalysisName()));
		l.add(an);
		obj.remove("analysis");
		obj.put("analysis",l);
		coll.remove(new BasicDBObject("name",request.getParameterValues("name")[0]));
		coll.insert(obj);
		MC.close();
		request.setAttribute("name", request.getParameterValues("name")[0]);
		request.getRequestDispatcher("/Analysis.jsp").forward(request,response);
	}

}
