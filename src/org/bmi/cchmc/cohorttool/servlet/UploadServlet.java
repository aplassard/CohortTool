package org.bmi.cchmc.cohorttool.servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.bmi.cchmc.cohorttool.patient.PatientSet;

import com.mongodb.*;


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
		out.println("Go Home");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        DB db = mongoClient.getDB("CohortTool");
        DBCollection coll = db.getCollection("projects");
        
        try {
        	java.util.Date date= new java.util.Date();
        	long time = (new Timestamp(date.getTime())).getTime();
            Part p1 = request.getPart("vcf");
            Part p2  = request.getPart("ped");
            Part p3 = request.getPart("name");
            Part p4 = request.getPart("username");
            Scanner s = new Scanner(p3.getInputStream());
            String filename = s.nextLine();
            s.close();
            s = new Scanner(p4.getInputStream());
            String username = s.nextLine();
            String outputfile1 = this.getServletContext().getRealPath("")+"/uploads/"+filename+"-"+time+".vcf";  // get path on the server
            String outputfile2 = this.getServletContext().getRealPath("")+"/uploads/"+filename+"-"+time+".ped";  // get path on the server
            
            FileOutputStream os1 = new FileOutputStream(outputfile1);
            FileOutputStream os2 = new FileOutputStream(outputfile2);
                        
            InputStream is1 = p1.getInputStream();
            int ch = is1.read();
            while (ch != -1) {
                 os1.write(ch);
                 ch = is1.read();
            }
            os1.close();
            out.println("<h3 class=\"text-success\">First file uploaded successfully!</h3>");
            
            InputStream is2 = p2.getInputStream();
            ch = is2.read();
            while (ch != -1) {
                 os2.write(ch);
                 ch = is2.read();
            }
            os2.close();
            out.println("<h3 class=\"text-success\">Second file uploaded successfully!</h3>");
            
            if(!this.validateFiles(filename+"-"+time)){
            	out.println("Error: VCF is invalid");
            	return;
            }
            out.println("<h3 class=\"text-success\">VCF is Valid</h3>");
            
            PatientSet ps = new PatientSet(outputfile2);
            BasicDBObject patientInfo = ps.getBSON();
            BasicDBObject infoToLoad = new BasicDBObject();
            infoToLoad.append("Patient Info", patientInfo);
            infoToLoad.append("Username",username);
            infoToLoad.append("Analysis Name",filename);
            infoToLoad.append("VCF File Location",outputfile1);
            infoToLoad.append("PED File Location",outputfile2);
            
            infoToLoad.append("Time Loaded",time);
            infoToLoad.append("Analysis Name",filename+"-"+time);
            coll.insert(infoToLoad);
            System.out.println("Loaded: "+infoToLoad);
            out.println("<form action=\"/CohortTool/RunSnpomics\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"file\" value=\""+filename+"-"+time+"\">");
            out.println("<input type=\"hidden\" name=\"id\" value=\""+infoToLoad.get("_id")+"\">");
            out.println("<input type=\"submit\" value=\"continue\">");
            out.println("</form>");
        }
        catch(Exception ex) {
           out.println("Exception -->" + ex.getMessage());
           ex.printStackTrace();
        }
        finally { 
            out.close();
        }
	}

	@SuppressWarnings("resource")
	private boolean validateFiles(String filename){
		try {
			BufferedReader ped = new BufferedReader(new FileReader( this.getServletContext().getRealPath("uploads/"+filename+".ped")));
			BufferedReader vcf = new BufferedReader(new FileReader( this.getServletContext().getRealPath("uploads/"+filename+".vcf")));
			String line="";
            boolean end=true;
            while((line=vcf.readLine())!=null){
            	if(line.startsWith("#")&&!line.startsWith("##")){
            		end=false;
            		break;
            	}
            }
            String[] header = line.split("\t");
            if(end){
            	vcf.close();
            	ped.close();
            	return false;
            }
			Set<String> patients = new HashSet<String>();
			line=ped.readLine();
			String[] info;
			while((line=ped.readLine())!=null){
				info=line.split(",");
				patients.add(info[0]);
			}
			boolean bad=false;
			for(int i=9;i<header.length;i++){
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
				vcf.close();
				return false;
			}
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
