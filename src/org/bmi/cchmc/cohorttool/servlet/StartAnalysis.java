package org.bmi.cchmc.cohorttool.servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

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
		String name = request.getParameterValues("file")[0];
		if(!validateFiles(name)) out.println("<h2>Something went wrong</h2>");
		else{
			out.println("Everything Checks Out!");
		}
		out.close();
	}

	private boolean validateFiles(String filename){
		try {
			BufferedReader ped = new BufferedReader(new FileReader( this.getServletContext().getRealPath("uploads/"+filename+".ped")));
			BufferedReader txt = new BufferedReader(new FileReader( this.getServletContext().getRealPath("SNPomics/output/"+filename+".txt")));
			String[] header = txt.readLine().split("\t");
			Set<String> patients = new HashSet<String>();
			String line=ped.readLine();
			String[] info;
			while((line=ped.readLine())!=null){
				info=line.split(",");
				patients.add(info[0]);
			}
			boolean bad=false;
			System.out.println("Elements in header: "+header.length);
			System.out.println("Expected elements in set: "+(header.length-8)/3);
			for(int i=8;i<=((header.length)/3);i++){
				if(!patients.contains(header[i])){
					System.out.println("Could not find "+header[i]);
					bad=true;
				}
				else{
					System.out.println("Found " + header[i]);
				}
			}
			if(bad){
				ped.close();
				txt.close();
				return false;
			}
			ped.close();
			txt.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
