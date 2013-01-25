package org.bmi.cchmc.cohorttool.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import org.bmi.cchmc.cohorttol.util.*;
import org.bmi.cchmc.cohorttool.patient.PatientSet;
import org.bson.types.ObjectId;

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
		out.print(ServletUtilities.getBootStrapHeader("Start Analysis"));
		String content="";
		content+="<div class=\"container\">\n";
		content+=ServletUtilities.getNormalNavbar();
		content+="\n<div>\n";
		content+="<h2 class=\"text-success\">Start Analysis</h2>";
		out.print(ServletUtilities.getBootStrapEnd(content));
		out.close();
	}

}
