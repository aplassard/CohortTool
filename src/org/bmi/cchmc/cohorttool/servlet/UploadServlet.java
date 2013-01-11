package org.bmi.cchmc.cohorttool.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name="UploadServlet", urlPatterns={"/upload"})     // specify urlPattern for servlet
@MultipartConfig                                               // specifies servlet takes multipart/form-data
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("There's Nothing Here");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
        	// get access to file that is uploaded from client
            Part p1 = request.getPart("vcf");

            // read filename which is sent as a part
            Part p2  = request.getPart("ped");
            
            Part p3 = request.getPart("name");
            
            Scanner s = new Scanner(p3.getInputStream());
            String filename = s.nextLine();    // read filename from stream
            // get filename to use on the server
            String outputfile1 = this.getServletContext().getRealPath("")+"/uploads/"+filename+".vcf";  // get path on the server
            String outputfile2 = this.getServletContext().getRealPath("")+"/uploads/"+filename+".ped";  // get path on the server
            
            FileOutputStream os1 = new FileOutputStream(outputfile1);
            FileOutputStream os2 = new FileOutputStream(outputfile2);
                        
            InputStream is1 = p1.getInputStream();
            int ch = is1.read();
            while (ch != -1) {
                 os1.write(ch);
                 ch = is1.read();
            }
            os1.close();
            out.println("<h3>First file uploaded successfully!</h3>");
            
            InputStream is2 = p2.getInputStream();
            ch = is2.read();
            while (ch != -1) {
                 os2.write(ch);
                 ch = is2.read();
            }
            os2.close();
            out.println("<h3>Second file uploaded successfully!</h3>");
        }
        catch(Exception ex) {
           out.println("Exception -->" + ex.getMessage());
        }
        finally { 
            out.close();
        }
	}

}
