package org.bmi.cchmc.cohorttool.tests;

import com.mongodb.*;

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ExpressionSets")
public class ExpressionValues extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void main(String[] args) throws IOException{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		doGet(request,response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        ArrayList<String> paramNames = Collections.list(request.getParameterNames());
        out.println("<html>\n");
        out.println("<head>\n");
        out.println("<title> A very simple gene analyzer example</title>\n");
        out.println("</head>\n");
        out.println("<body>\n");
        out.println("<center>\n");
        out.println("<h2>Select Expression Sets:</h2>\n");
        out.println(getForm());
        out.println("</center>\n");
        out.println("</body>\n");
        out.println("</html>\n");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doGet(request,response);
	}

	private String getForm() throws UnknownHostException{
		System.out.println("Connected to MongoDB Host");
		Mongo m = new Mongo();
		System.out.println("Connected to MongoDB Host");
		DB db = m.getDB("CohortTool");
		System.out.println("Connected to MongoDB Host");
		DBCollection expvals = db.getCollection("expressionvalues");
		System.out.println("Connected to MongoDB Host");
		DBCursor cursor=expvals.find();
		String form="<form action=\"Servlet2\" method=\"post\">";
		DBObject a;
		try {
            while(cursor.hasNext()) {
                form+="\n<br><input type=\"checkbox\" name=\"datasets\" value=\"";
                a=cursor.next();
                form+=a.get("_id");
                form+="\">";
                form+=a.get("name");
            }
        } finally {
            cursor.close();
        }
		form+="\n<br>\n<input type=\"submit\" value=\"Submit\">";
		form+="\n</form>";
		return form;
	}
}
