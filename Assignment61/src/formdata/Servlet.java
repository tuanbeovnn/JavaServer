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


import java.text.SimpleDateFormat;


public class Servlet extends HttpServlet{
	String uploadFilePath;
	String relativePath;
	SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
	public void init() {
		
		relativePath = getServletContext().getInitParameter("upload_path");

		uploadFilePath = this.getServletContext().getRealPath(File.separator + relativePath) + File.separator;
		
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}
		
		System.out.println(uploadFilePath);
		System.out.println(relativePath);
		
	}
	
	private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		if (contentDisp != null) {
			/*
			 * In the following line we split a piece of text like the following: form-data;
			 * name="fileName"; filename="C:\Users\Public\Pictures\Sample Pictures\img.jpg"
			 */
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
    		MessageForm mess = new MessageForm(firstName,new Date(),message,relativePath + File.separator + fileName);
    		saveMess.add(mess);
//        }
		
		
		response.setContentType("text/html");
		
		 // get response writer
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
            	pw.println("<tr>");
				pw.println("<td>" + mess1.getFirstName() + "</td>");
				pw.println("<td>" + mess1.getContentMessage() + "</td>");
				pw.println("<td>" + simple.format(mess1.getDate()) + "</td>");
				pw.println("<td>" + "<img src='" + mess1.getImage() + "' border=3 height=100 width=100></img>" + "</td>");
//				pw.println("<td>" + mess1.getImage() + "</td>");
				pw.println("</tr>");
            }
            	pw.println("</table>");         
            
              pw.println("<button class='button button2'><a href='index.html'>Back</a></button>");
            
        }else {
        	pw.println("Not Found");
        }
        
        pw.println("<form action='searchName' method='POST'>");
        pw.println("<h6>Search By First Name</h6>");
        pw.println("<input type='text' name ='searchByName'>");
        pw.println("<button class='button button3' type='submit' name='submit'>Search</button>");
        pw.println("</form>");
        
        pw.println("<form action='searchDate' method='POST'>");
        pw.println("<h5>Search By Date</h5>");
        pw.println("<input type='date' name ='searchByDate'>");
        pw.println("<button class='button button4' type='submit' name='submitDate'>Search</button>");
        pw.println("</form>");
        pw.println("<p style='text-align:center;'><a href='logout.html'>Logout</a></p>");
        pw.println("<script>\n" + 
        		"(function() {\n" + 
        		"\n" + 
        		"    const idleDurationSecs = 30;    \n" + 
        		"    const redirectUrl = 'logout.html';  \n" + 
        		"    let idleTimeout;\n" + 
        		"\n" + 
        		"    const resetIdleTimeout = function() {\n" + 
        		"\n" + 
        		"       \n" + 
        		"        if(idleTimeout) clearTimeout(idleTimeout);\n" + 
        		"\n" + 
        		"       \n" + 
        		"        idleTimeout = setTimeout(() => location.href = redirectUrl, idleDurationSecs * 1000);\n" + 
        		"    };\n" + 
        		"\n" + 
        		"   \n" + 
        		"    resetIdleTimeout();\n" + 
        		"\n" + 
        		"   \n" + 
        		"    ['click', 'touchstart', 'mousemove'].forEach(evt =>\n" + 
        		"        document.addEventListener(evt, resetIdleTimeout, false)\n" + 
        		"    );\n" + 
        		"\n" + 
        		"})();\n" + 
        		"\n" + 
        		"</script>\n" + 
        		"\n" + 
        		"");
        
        pw.println("</body>");
        pw.println("</html>");
        pw.close();

       
	}

}
