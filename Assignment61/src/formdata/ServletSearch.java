package formdata;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/searchName")
public class ServletSearch extends HttpServlet {
	
	SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			   throws ServletException, java.io.IOException {
		List<MessageForm> saveMess = MessageData.getListMess();
		List<MessageForm> res = new ArrayList<>();
		
		String submit = request.getParameter("submit");
		if(submit != null ) {
        	String name = request.getParameter("searchByName");
        	for(MessageForm m : saveMess) {
        		if(m.getFirstName().equalsIgnoreCase(name)) {
        			res.add(m);
        		}
        	}
		}
		PrintWriter pw = response.getWriter();
        pw.println("<html><head>");
        pw.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/Style/style.css' />");
        if(res.size()!=0) {
	        	pw.write("<h2> Following data received sucessfully.. <h2> <br>");
	        	pw.println("<body>");
	        	pw.println("<table>");
	            pw.println("<tr>");
	            pw.println("<th>FirstName</th>");
	            pw.println("<th>Message</th>");
	            pw.println("<th>Date</th>");
	            pw.println("</tr>");
            
            for(MessageForm mess : res) {
            	pw.println("<tr>");
				pw.println("<td>" + mess.getFirstName() + "</td>");
				pw.println("<td>" + mess.getContentMessage() + "</td>");
				pw.println("<td>" + simple.format(mess.getDate()) + "</td>");
				pw.println("<td>" + "<img src='" + mess.getImage() + "' border=3 height=100 width=100></img>" + "</td>");
				pw.println("</tr>");
            }
            	pw.println("</table>");

            	
              pw.println("<button class='button'><a href='index.html'>BACK</a></button>");
              pw.println("</body>");
            
        }else {
        	pw.println("Not Found");
        }
	}
		 
}
