package formdata;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.sun.jmx.snmp.SnmpStringFixed;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import db.DBHandler;

import java.text.SimpleDateFormat;


public class Servlet extends HttpServlet{
	String uploadFilePath;
	String relativePath;
	SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
	DBHandler dbHandler = new DBHandler();
	String tempImageDirAbsolute;
    String tempImageDir;
    private final String dbTableName = "dataImage";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	public void init() {
		
		relativePath = getServletContext().getInitParameter("upload_path");

		uploadFilePath = this.getServletContext().getRealPath(File.separator + relativePath) + File.separator;
		String temp = getServletContext().getInitParameter("temp_images");

		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}
	    tempImageDir = getServletContext().getInitParameter("temp_image_dir");
	    tempImageDirAbsolute = getServletContext().getRealPath((tempImageDir == null ? "temp_images":tempImageDir)) + File.separator;
        DBHandler.setTempImageDir(tempImageDir);
        DBHandler.setTempImageDirAbsolute(tempImageDirAbsolute);
        
	}
	
	private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		if (contentDisp != null) {
			String[] tokens = contentDisp.split(";");
			for (String token : tokens) {
				if (token.trim().startsWith("filename")) {
					return new File(token.split("=")[1].replace('\\', '/')).getName().replace("\"", "");
				}
			}
		}
		return "";
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			   throws ServletException, java.io.IOException {
		String fileName = null;
		File fileObj = null;
		String file = null;
		List<MessageForm> saveMess = MessageData.getListMess();
		
		
		
		String submitBtn = request.getParameter("submit");
//		if(submitBtn !=null ) {
			Part part = request.getPart("fileName");
			fileName = getFileName(part);
			file = fileName;
			if (!fileName.equals("")) {
				fileObj = new File(fileName);
				fileName = fileObj.getName();

				fileObj = new File(uploadFilePath + fileName);
				part.write(fileObj.getAbsolutePath());

			}

        	String firstName = request.getParameter("firstname");
    		String message = request.getParameter("message");
    		MessageForm mess = new MessageForm(firstName,formatter.format(new Date()),message,fileName);
    		saveMess.add(mess);
    		
    		dbHandler.openConnection();
    		dbHandler.createImageTable(dbTableName);
    		dbHandler.WriteImageToDB(dbTableName,firstName,message,formatter.format(new Date()),fileName,part.getInputStream());
    		List<MessageForm> list = dbHandler.readImageFromDB(dbTableName);
    		dbHandler.closeConnection();
    		
//        }
		
		
		response.setContentType("text/html");

        PrintWriter pw = response.getWriter();
        pw.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/Style/style.css' />");
        pw.println("<html><head>");
        
        
        if(saveMess.size()!=0) {
	        	pw.write("<h2> Following data received sucessfully.. <h2> <br>");
	        	pw.println("<body>");
	        	pw.println("<table>");
	            pw.println("<tr>");
	            pw.println("<th>FirstName</th>");
	            pw.println("<th>Message</th>");
	            pw.println("<th>Date</th>");
	            pw.println("<th>Image</th>");
	            pw.println("</tr>");
            
            for(MessageForm mess1 : saveMess) {
            	String dir = tempImageDir+File.separator+ mess1.getImage();
            	System.out.println(mess1.getImage());
            	pw.println("<tr>");
				pw.println("<td>" + mess1.getFirstName() + "</td>");
				pw.println("<td>" + mess1.getContentMessage() + "</td>");
				pw.println("<td>" + mess1.getDate() + "</td>");
				pw.println("<td> <img src='" +dir+"' border=3 height=100 width=100></img> </td>");
				pw.println("</tr>");
            }
            	pw.println("</table>");         
            
              pw.println("<button class='button button2'><a href='index.html'>Back</a></button>");
            
        }else {
        	pw.println("Not Found");
        }
        
        pw.println("</body>");
        pw.println("</html>");
        pw.close();

       
	}

}
