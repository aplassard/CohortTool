package org.bmi.cchmc.cohorttool.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RunSnpomics
 */
@WebServlet("/RunSnpomics")
public class RunSnpomics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RunSnpomics() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("There's Nothing Here");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Started");
		response.setContentType("text/html;charset=UTF-8");
		String[] name = request.getParameterValues("file");
		System.out.println("got "+name.length+" parameters");
		PrintWriter out = response.getWriter();
		if(name.length != 1) out.println("Incorrect Number of values in name");
		else{
			try{
			
				runSNPomics(name[0]);
			}
			catch(Exception e){
				e.printStackTrace();
				out.println("There was an error the annotator");
			}
			out.println("<h2>Completed Successfully!</h2>");
		}
		out.close();
	}
	
	private void runSNPomics(String filename) throws IOException{
		String jarfile = this.getServletContext().getRealPath("SNPomics/SNPomics-1.0-SNAPSHOT-jar-with-dependencies.jar");
		String inputfile = this.getServletContext().getRealPath("uploads/"+filename+".vcf");
		String outputfile = this.getServletContext().getRealPath("SNPomics/output/"+filename+".txt");
		String arguements = "-jar -Xmx3G ";
		String systemcall = "java " + arguements + jarfile+" annot -i "+inputfile+" -o " + outputfile;
		System.out.println("Running Command: "+systemcall);
		Process proc = Runtime.getRuntime().exec(systemcall);
		
	}

}