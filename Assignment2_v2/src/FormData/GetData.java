package FormData;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/getdata")
public class GetData extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		String submit = request.getParameter("getdata");
		if (submit != null) {
			InforData info = FileRepository.readToFile();
			
			PrintWriter out = response.getWriter();
			 response.setContentType("text/html");
			 out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/Style/style.css' />");
			 
			 out.print("<html><body>");
			 out.print("<h1> Your Information...</h1>");
			 out.println("<form>");
			 out.println("<table border=\"1\" cellpadding = \"5\"" +
	                " cellspacing = \"5\">");
			 out.println("<tr> <th> Name</th>" +
	                "<th> Mess</th>" +  "<th> Favorites</th>"
	                + "</tr>");
			 
			 for(Information infor : info.getListMess()) {
				 out.println("<tr>");
				 out.println("<td>" + infor.getFirstName() + "</td>");
				 out.println("<td>" + infor.getContentMessage() + "</td>");
				 out.println("<td>" + infor.getFavorites() + "</td>");
				 out.println("</tr>");
		           
		        
			 }
			 out.println("</table></form>");
			
		        
		        out.println("</body></html>");
		        out.close();

		}

	}
}
