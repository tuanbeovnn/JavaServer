package FormData;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletForm extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		Map<String, String[]> map = request.getParameterMap();
		String firstName = map.get("firstname")[0];
		String message = map.get("message")[0];
		List<String> toppings = Arrays.asList(map.get("toppings"));

		InforData info = FileRepository.readToFile();
		if (info == null) {
			info = new InforData();
		}

		Information information = new Information();
		information.setFirstName(firstName);
		information.setContentMessage(message);
		information.setFavorites(toppings);

		info.getListMess().add(information);

		FileRepository.writeToFile(info);

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println(
				"<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/Style/style.css' />");

		out.print("<html><body>");
		out.print("<h1> Your Information...</h1>");
		out.println("<form>");
		out.println("<table border=\"1\" cellpadding = \"5\"" + " cellspacing = \"5\">");
		out.println("<tr> <th> Name</th>" + "<th> Mess</th>" + "<th> Favorites</th>" + "</tr>");

		for (Information infor : info.getListMess()) {
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
