package FormData;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.text.SimpleDateFormat;


@WebServlet("/formServlet")
public class Servlet extends HttpServlet{
	
SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
	
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			   throws ServletException, java.io.IOException {
		
		List<MessageForm> saveMess = MessageData.getListMess();
		
		String submitBtn = request.getParameter("submit");
		if(submitBtn !=null ) {
        	String firstName = request.getParameter("firstname");
    		String message = request.getParameter("message");
    		MessageForm mess = new MessageForm(firstName,new Date(),message);
    		saveMess.add(mess);
        }
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
	            pw.println("</tr>");
            
            for(MessageForm mess : saveMess) {
            	pw.println("<tr>");
				pw.println("<td>" + mess.getFirstName() + "</td>");
				pw.println("<td>" + mess.getContentMessage() + "</td>");
				pw.println("<td>" + simple.format(mess.getDate()) + "</td>");
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
        
        pw.println("</body>");
        pw.println("</html>");
        pw.close();

       
	}

}
