package servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final String separator = File.separator;
	private String downloadDir;
	public List<String> listFile = new ArrayList<String>();

	public void init() {
		// Here we specify the path to the directory where downloadable files
		// reside.
		// We read the name of the download directory from web.xml file, where
		// the name of
		// the directory is specified under "download-dir" parameter.

		downloadDir = this.getServletContext().getRealPath(getServletContext().getInitParameter("download-dir"))
				+ separator;

		File[] files = new File(downloadDir).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				listFile.add(file.getName());
			}
		}
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		String error = "";
        String fileName = request.getParameter("fileName");
        
        if (fileName == null || fileName.equals("")) {
            // throw new ServletException("Null or invalid file name!");
            error = "File name was missing! Please give a file name!";
            sendHTMLErrorMessage(request, response, error);
            return;
        }
        
        File downloadFile = new File(downloadDir + fileName);
        if (!downloadFile.exists()) {
            error = downloadFile.getAbsolutePath() + " does not exist!";
            sendHTMLErrorMessage(request, response, error);
            return;
        }
        ServletOutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setContentType(getContentType(fileName));
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", downloadFile.length()+ "");
            
            
            bufferedInputStream = new BufferedInputStream(new FileInputStream(downloadFile));
            int readBytes = 0;
            while ((readBytes = bufferedInputStream.read()) != -1)
                outputStream.write(readBytes);
        } catch (IOException ioex) {
            
            error = ioex.getMessage();
            sendHTMLErrorMessage(request, response, error);
        } finally {
            if (outputStream != null)
                outputStream.close();
            if (bufferedInputStream != null)
                bufferedInputStream.close();
        }
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		PrintWriter out = response.getWriter();
    	response.setContentType("text/html");
    	out.print("<html><head>");
    	out.print("<title>File Uploading Form</title>");
    	out.print("</head>");
    	out.print("<body style=\"background-color: #fffff2;\">");
    	out.print("<div>This is a list of downloadable files: </div>");
    	out.print("<form action=\"filedownloadservlet\" method=\"post\">");
    	 out.println("<select name=\"fileName\" >");
     	for(String fileSetName: listFile) {
     		out.println("<option value=\""+fileSetName+"\">"+fileSetName);
     	}
         out.println("</select>");
    	out.print("<br><br>");
    	out.print("<input type=\"submit\" value=\"Submit\">");
    	out.print("</form>");
		
	}
}