package org.bmi.cchmc.cohorttool.servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.RequestDispatcher;
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

	private String getFileName(Part part) {
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		Part p1 = request.getPart("vcf");
        Part p2  = request.getPart("ped");
        Part p3 = request.getPart("name");
        Part p4 = request.getPart("username");
        String filename = getFileName(p1);
        PrintWriter out = response.getWriter();
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        DB db = mongoClient.getDB("CohortTool");
        DBCollection coll = db.getCollection("projects");
        Scanner s = new Scanner(p3.getInputStream());
        String name = s.nextLine();
        s.close();
        s = new Scanner(p4.getInputStream());
        String user = s.nextLine();
        s.close();
        java.util.Date date= new java.util.Date();
    	long time = (new Timestamp(date.getTime())).getTime();
        String outputfile1 = this.getServletContext().getRealPath("")+"/uploads/"+name+"-"+time+".vcf";
        if (filename.endsWith(".gz")) outputfile1+=".gz";
        loadFile(p1,outputfile1);
        System.out.println("VCF file loaded Successfully");
        String outputfile2 = this.getServletContext().getRealPath("")+"/uploads/"+name+"-"+time+".ped";
        loadFile(p2,outputfile2);
        System.out.println("PED file loaded Successfully");
        if (filename.endsWith(".gz")) gunzipFile(outputfile1);
        if(!validateFiles(name+"-"+time)){
        	out.println("Files did not validate");
        	out.println("<br>Please Try again");
        	return;
        }
        PatientSet ps = new PatientSet(outputfile2);
        BasicDBObject patientInfo = ps.getBSON();
        BasicDBObject infoToLoad = new BasicDBObject();
        
        infoToLoad.append("Patient Info", patientInfo);
        infoToLoad.append("Username",user);
        infoToLoad.append("VCF File Location",outputfile1);
        infoToLoad.append("PED File Location",outputfile2);
        infoToLoad.append("Time Loaded",time);
        infoToLoad.append("Analysis Name",name+"-"+time);
        coll.insert(infoToLoad);
        System.out.println("Successfully added to database");
        RequestDispatcher r = request.getRequestDispatcher("/RunSNPomics.jsp");
        System.out.println("Got dispatcher");
        request.setAttribute("filename",name+"-"+time);
        request.setAttribute("id",  infoToLoad.get("_id").toString());
        
        r.forward(request,response);
	}
	
	private void loadFile(Part p, String loc){
		try {
			InputStream is = p.getInputStream();
			FileOutputStream os = new FileOutputStream(loc);
			int ch = is.read();
            while (ch != -1) {
                 os.write(ch);
                 ch = is.read();
            }
            os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gunzipFile(String loc){
		String SystemCall = "gunzip " + loc;
    	System.out.println("Calling "+SystemCall);
    	Process proc;
		try {
			proc = Runtime.getRuntime().exec(SystemCall);

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
