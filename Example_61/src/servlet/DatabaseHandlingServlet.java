package servlet;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.DBHandler;
import helper.Utility;

public class DatabaseHandlingServlet extends HttpServlet {
	 /**
    *
    */
    private static final long serialVersionUID = 1L;
     String resourceDir;
     String tempImageDirAbsolute;
     String tempImageDir;
    
    private DBHandler dbHandler;
    
     public void init() {
        
         String separator = File.separator;
          // Here we define the path for directory under which we have images
          resourceDir = getServletContext().getRealPath(getServletContext().getInitParameter("resource_dir")) + separator;
          
          tempImageDir = getServletContext().getInitParameter("temp_image_dir");
          // Here we define the absolute path for directory under which the Servlet saves
          // images
          // read from the database
          tempImageDirAbsolute = getServletContext().getRealPath((tempImageDir==null ? "temp_images":tempImageDir)) + separator;
        
          DBHandler.setResourceDir(resourceDir);
          DBHandler.setTempImageDir(tempImageDir);
          DBHandler.setTempImageDirAbsolute(tempImageDirAbsolute);
          
          
         }
    
    public void sendError(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Database Access Servlet</title></head><body>");
        out.println("<h1>Error</h1>");
        out.println("<p style='text-align:center;'>" + message + "</p>");
        out.println("<hr>");
        out.println("<hr><p style='text-align: center;'> <a href='index.html'>Back</a></p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("submit");
        
        String dbUsername = request.getParameter("db_username");
        String dbPassword = request.getParameter("db_password");
        String dbName = request.getParameter("db_name");
        String dbTableName = request.getParameter("db_table_name");
        String encryptData=  request.getParameter("encrypt_data");
    
        
        String tableRowData="";
        if (!Utility.validateTextParameter(dbName) || !Utility.validateTextParameter(dbTableName)) {
            sendError(request, response, Utility.getInvalidDataErrorMessage());
            return;
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Database Access Servlet</title></head><body>");
        
        
        // Here we create connection to the database
        try {
            dbHandler = new DBHandler(dbName, dbUsername, dbPassword);
            
        switch(action.toLowerCase().trim()) {
        case "read table":
            out.println(dbHandler.getDataTable(dbTableName));
            break;
        case "create table":
            out.println(dbHandler.createTable(dbTableName));
            break;
            
        case "insert data":
               tableRowData = request.getParameter("table_row_data");
              if(!Utility.validateInsertDateParameter(tableRowData)) {
                  sendError(request, response, Utility.getInValidTableRowDataErrorMessage());
                return;
              }
              
              out.println(dbHandler.preparedStatementInsertData(dbTableName, tableRowData, encryptData));
              
              break;
              
        case "update data":
               tableRowData = request.getParameter("table_row_data");
              if(!Utility.validateInsertDateParameter(tableRowData)) {
                  sendError(request, response, Utility.getInValidTableRowDataErrorMessage());
                return;
              }
              out.println(dbHandler.preparedStatemenUpdateData(dbTableName, tableRowData));
              
              //Here we call the method, which uses PreparedStatement object to execute the SQL query
             // out.println(dbHandler.preparedStatementInsertData(dbTableName, tableRowData));
              
              break;
            
        case "read images":
              
            //Here we call the method to write images to the database
//            out.println(dbHandler.WriteImageToDB(dbTableName));
            
            //Here we call the method to read images from the database
             out.println(dbHandler.readImageFromDB(dbTableName));
              break;
              
            default:
                break;
            
        }
        } catch (Exception e) {
            out.println(e.getLocalizedMessage());
        }
        
    
        out.println("<hr><p style='text-align: center;'> <a href='index.html'>Main Page</a></p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
