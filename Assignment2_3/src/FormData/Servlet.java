package FormData;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

@WebServlet(name = "AnotherServlet", urlPatterns = { "/formServlet", "/AnotherServlet" })
public class Servlet extends HttpServlet {

	SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		PrintWriter out = response.getWriter();
		
		
//        if(request.getParameter("username") != null && request.getParameter("password") != null) {
        	out.println("<!DOCTYPE html>\n" + 
        			"<html>\n" + 
        			"<head>\n" + 
        			"<meta charset=\"UTF-8\">\n" + 
        			"<title>Information</title>\n" + 
        			"<style>\n" + 
        			"body {font-family: Arial, Helvetica, sans-serif;}\n" + 
        			"* {box-sizing: border-box;}\n" + 
        			"\n" + 
        			"input[type=text], select, textarea {\n" + 
        			"  width: 100%;\n" + 
        			"  padding: 12px;\n" + 
        			"  border: 1px solid #ccc;\n" + 
        			"  border-radius: 4px;\n" + 
        			"  box-sizing: border-box;\n" + 
        			"  margin-top: 6px;\n" + 
        			"  margin-bottom: 16px;\n" + 
        			"  resize: vertical;\n" + 
        			"}\n" + 
        			"\n" + 
        			"input[type=submit] {\n" + 
        			"  background-color: #4CAF50;\n" + 
        			"  color: white;\n" + 
        			"  padding: 12px 20px;\n" + 
        			"  border: none;\n" + 
        			"  border-radius: 4px;\n" + 
        			"  cursor: pointer;\n" + 
        			"}\n" + 
        			"\n" + 
        			"input[type=submit]:hover {\n" + 
        			"  background-color: #45a049;\n" + 
        			"}\n" + 
        			"\n" + 
        			".container {\n" + 
        			"  border-radius: 5px;\n" + 
        			"  background-color: #f2f2f2;\n" + 
        			"  padding: 20px;\n" + 
        			"}\n" + 
        			"</style>\n" + 
        			"</head>\n" + 
        			"<body>\n" + 
        			"	<h3>INFORMATION</h3>\n" + 
        			"\n" + 
        			"<div class=\"container\">\n" + 
        			"  <form action=\"formServlet\" method=\"POST\">\n" + 
        			"    <label for=\"fname\">Name</label>\n" + 
        			"    <input type=\"text\" id=\"fname\" name=\"firstname\" placeholder=\"Your name..\" required>\n" + 
        			"    <label for=\"subject\">Message</label>\n" + 
        			"    <textarea id=\"subject\" name=\"message\" placeholder=\"Write something..\" style=\"height:200px\" required></textarea>\n" + 
        			"    <input type=\"submit\" value=\"submit\" name=\"submit\">\n" + 
        			"  </form>\n" + 
        			"</div>\n" + 
        			"</body>\n" + 
        			"</html>");
//        };
       

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		response.getWriter().append("Servlet: ").append(request.getContextPath());
		List<MessageForm> saveMess = MessageData.getListMess();

		String submitBtn = request.getParameter("submit");
		if (submitBtn != null) {
			String firstName = request.getParameter("firstname");
			String message = request.getParameter("message");
			MessageForm mess = new MessageForm(firstName, new Date(), message);
			saveMess.add(mess);
		}
		response.setContentType("text/html");

		// get response writer
		PrintWriter pw = response.getWriter();
		pw.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/Style/style.css' />");
		pw.println("<html><head>");

		if (saveMess.size() != 0) {
			pw.write("<h2> Following data received sucessfully.. <h2> <br>");
			pw.println("<body>");
			pw.println("<table>");
			pw.println("<tr>");
			pw.println("<th>FirstName</th>");
			pw.println("<th>Message</th>");
			pw.println("<th>Date</th>");
			pw.println("</tr>");

			for (MessageForm mess : saveMess) {
				pw.println("<tr>");
				pw.println("<td>" + mess.getFirstName() + "</td>");
				pw.println("<td>" + mess.getContentMessage() + "</td>");
				pw.println("<td>" + simple.format(mess.getDate()) + "</td>");
				pw.println("</tr>");
			}
			pw.println("</table>");

			pw.println("<button class='button button2'><a href='index.html'>Back</a></button>");

		} else {
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
