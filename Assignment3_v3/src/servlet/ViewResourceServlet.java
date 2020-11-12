package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewResourceServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String error = "";
	private final String separator = File.separator;
	private String downloadDir;
	public List<String> listFile = new ArrayList<String>();

	public void init() {
		
		downloadDir = this.getServletContext().getRealPath(getServletContext().getInitParameter("download-dir"))
				+ separator;

		File[] files = new File(downloadDir).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				listFile.add(file.getName());
			}
		}
	}
	private void displayError(HttpServletRequest request, HttpServletResponse response, String error) {
		response.setContentType("text/html");
		try {
			// Here we initialise the PrintWriter object
			PrintWriter out = response.getWriter();
			// Here we print HTML tags
			out.println("<html>");
			out.println("<head>");
			out.println("<title>View Resource Servlet Error Message</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<center>");
			out.println("<h1>" + error + "</h1>");
			out.println("<p><b>Error:</b> " + error);
			out.println("<p><a href='index.html'>Back</a>");
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getContentType(String fileName) {

		// report.pdf
		String fileExt = "";
		String contentType = "";
		int i;
		if ((i = fileName.indexOf(".")) != -1) {
			/*
			 * Here we read s substring of filename starting from ".", which will be the
			 * file extension
			 */
			fileExt = fileName.substring(i);
		}
		if (fileExt.equals("doc") || fileExt.equals("docx"))
			contentType = "application/msword";
		else if (fileExt.equals("pdf"))
			contentType = "application/pdf";
		else if (fileExt.equals("mp3"))
			contentType = "audio/mpeg";
		else if (fileExt.equals("jpg") || fileExt.equals("gif") || fileExt.equals("tif") || fileExt.equals("jpeg")
				|| fileExt.equals("bmp"))
			contentType = "application/img";
		else if (fileExt.equals("xml"))
			contentType = "text/xml";
		else if (fileExt.equals("rtf"))
			contentType = "applictaion/rtf";
		else
			contentType = "application/unknown";

		return contentType;
	}

	
	private void sendHTMLErrorMessage(HttpServletRequest request, HttpServletResponse response, String error) {
		response.setContentType("text/html");
		try {
			// Here we initialise the PrintWriter object
			PrintWriter out = response.getWriter();
			// Here we print HTML tags
			out.println("<html>");
			out.println("<head>");
			out.println("<title>File Download Error Message</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Error</h1>");
			out.println("<p><b>Error:</b> " + error);
			out.println("<p><a href='index.html'>Back</a>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String error = null;
		URL url = null;
		URLConnection urlConnection = null;
		PrintWriter printWriter = null;
		String extension = "";
		String siteName = request.getParameter("site_name");
		String file = request.getParameter("search_phrase");
		
		BufferedInputStream inputStream= null ;
		printWriter = response.getWriter();
		try {
			// Here we access the web resource within the web application
			// as a URL object
			// url = getServletContext().getResource(filePath);

			

			if (siteName == null || siteName.isEmpty() || file == null || file.isEmpty()) {
				error = "Siten name or search phrase has not been specified!";
				return;

			}

			url = new URL(siteName);

			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-16");
			urlConnection = url.openConnection();
			// Here we establish connection with URL representing web.xml
			urlConnection.connect();
			int contentLength = urlConnection.getContentLength();
			extension= getContentType(urlConnection.getContentType());
			byte[] data = new byte[contentLength];
			inputStream = new BufferedInputStream(urlConnection.getInputStream());
			int bytesRead = 0;
			int offset = 0;
			while (offset < contentLength) {
			  bytesRead = inputStream.read(data, offset, data.length - offset);
			  if (bytesRead == -1)
			    break;
			  offset += bytesRead;
			}
			inputStream.close();
			FileOutputStream out = new FileOutputStream(downloadDir + file + extension);
			out.write(data);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			error = "Something wrong with: " + url.toString() + " " + e;
			displayError(request, response, error);
		} finally {
			// Here we close the input/output streams
			error= "File has been saved as "+ file + extension;
//			response= "Success";
			sendHTMLErrorMessage(request, response ,error);
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Here we redirect the request to the index page
		response.sendRedirect("index.html");
		return;
	}
}