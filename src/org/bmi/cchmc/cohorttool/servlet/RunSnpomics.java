package org.bmi.cchmc.cohorttool.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RunSnpomics
 */
@WebServlet("/RunSNPomics")
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
		response.setContentType("text/html;charset=UTF-8");
		String name = request.getParameter("filename");
		runSNPomics(name);
		request.getRequestDispatcher("/LoadInfo.jsp").forward(request,response);
	}
	
	private void runSNPomics(String filename) throws IOException{
		String jarfile = this.getServletContext().getRealPath("SNPomics/SNPomics-1.0-SNAPSHOT-jar-with-dependencies.jar");
		String inputfile = this.getServletContext().getRealPath("uploads/"+filename+".vcf");
		String outputfile = this.getServletContext().getRealPath("SNPomics/output/"+filename+".txt");
		String arguements = "-jar -Xmx2G ";
		String systemcall = "java " + arguements + jarfile+" annot -i "+inputfile+" -o " + outputfile;
		System.out.println("Running Command: "+systemcall);
		Process proc = Runtime.getRuntime().exec(systemcall);
		try{
			BufferedReader b = new BufferedReader( new InputStreamReader(proc.getInputStream()));
			String line="";
			System.out.println("Output Stream:");
			while((line=b.readLine())!=null){
				System.out.println(line);
			}
			b.close();
			b = new BufferedReader( new InputStreamReader(proc.getErrorStream()));
			line="";
			System.out.println("Error Stream:");
			while((line=b.readLine())!=null){
				System.out.println(line);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
