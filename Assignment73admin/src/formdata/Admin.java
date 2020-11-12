package formdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Admin extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		int time = Integer.parseInt(getServletContext().getInitParameter("time"));

		response.setIntHeader("Refresh", time);
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<!Doctype html>\n" + "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + "th, td {\n"
				+ "	text-align: left;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "	\n" + "	\n" + "	<div>\n"
				+ "	\n" + "		This is admin page!\n" + "	\n" + "	</div>\n" + "	<p style='text-align: center;'>\n"
				+ "		<a href='logout.html'>Logout</a>\n" + "	</p>\n" + "</body>\n" + "</html>");
		HttpSession session = req.getSession();
		
		session.setMaxInactiveInterval(time);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
