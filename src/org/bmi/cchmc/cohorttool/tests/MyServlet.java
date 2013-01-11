package org.bmi.cchmc.cohorttool.tests;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet1")
public class MyServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title> A very simple gene analyzer example</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form>");
        out.println("Enter Gene Annotation Type<br>");
        out.println("<input type=\"radio\" value=\"HGNC\">");
        out.println("HGNC<br>");
        out.println("<input type=\"radio\" value=\"Entrez\">");
        out.println("Entrez<br>");
        out.println("<input type=\"radio\" value=\"Refseq\">");
        out.println("Refseq<br>");
        out.println("Enter Genes Here:");
        out.println("<br>");
        out.println("<form method=\"post\" action=\"servlet.jsp\">");
        out.println("<textarea name=\"genes\" cols=\"25\" rows=\"15\">");
        out.println("</textarea><br><input type=\"submit\" value=\"Submit\" />");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}