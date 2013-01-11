package org.bmi.cchmc.cohorttool.tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        ArrayList<String> paramNames = Collections.list(request.getParameterNames());
        out.println("<html>");
        out.println("<head>");
        out.println("<title> A very simple gene analyzer example</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("Parameters:");
        for(int i = 0; i < paramNames.size(); i++){
        	String paramName=paramNames.get(i);
        	String[] paramValues=request.getParameterValues(paramName);
        	
        	for(int j = 0; j < paramValues.length;j++){
        		out.println("<br>"+paramName+": ");
        		out.println(paramValues[j]);
        	}
        	
        }
        out.println("</body>");
        out.println("</html>");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doGet(request,response);
	}

}
