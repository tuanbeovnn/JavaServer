package Login;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index.html")
public class login extends HttpServlet {
	private final String validUserName = "admin";
	private final String validPassWord = "root";
	private boolean checkParameter(String params) {
		if(params!=null && !params.trim().isEmpty() && !params.equals("null")) {
			return true;
		}else {
			return false;
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        // Here we read the value of the submit button
        String action = request.getParameter("submit");
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("password");
        
        
        boolean userNameCheck = checkParameter(userName);
        boolean passCheck = checkParameter(passWord);
        /*
         * boolean emailCheck = (email != null && !email.trim().isEmpty() && email
         * .indexOf("@") != -1);
         */
       
       
        // Here we set the MIME type of the response, "text/html"
        response.setContentType("text/html");
        // Here we use a PrintWriter to send text data
        // to the client who has requested the servlet
        PrintWriter out = response.getWriter();
        
        // Here we start assembling the HTML content
        out.println("<!Doctype html><html><head>");
        out.println("<title>User Registeration Page</title>");
        out.println("<link rel='stylesheet' type='text/css' href='Style/style.css'>");
        out.println("</head><body>");
        
        out.println("<h2>Log In</h2>");
        // Here we set the value for method to post, so that
        // the servlet service method calls doPost in the
        // response to this form submit
//        out.println("<form method='GET' + action='index.html'>");
        
		out.println("<div class='container'>");
		out.println("<form method='GET' + action='index.html'>");
		out.println("<div class='row'>");
		out.println("<div class='col'>");
		if (action != null && !userNameCheck)
			out.println("<h4>Enter User Name</h4>");
		out.println("<input type='text' name='userName' placeholder='Username' value='"
				+ (userName == null ? "" : userName) + "'>");
		if (action != null && !passCheck)
			out.println("<h4>Enter Pass Word</h4>");
		out.println("<input type='password' name='password' placeholder='Password'value='"
				+ (passWord == null ? "" : passWord) + "'>");
		out.println("<input type='submit' value='Login' name='submit'>");
		out.println("</div>");
		out.println("</div>");
		out.println("</form>");
		out.println("</div>");
        out.println("</body></html>");
        
        
        if (userNameCheck && passCheck) {
        	doPost(request, response);
        }
        out.close();	
            
    }
    
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			   throws ServletException, java.io.IOException {
			
		  String userName = request.getParameter("userName");
		  String password = request.getParameter("password");
		  
		  response.reset();
		  
		  String message= "";
		  PrintWriter out=response.getWriter();
		  
		  GregorianCalendar calendar=new GregorianCalendar();
		  
		  
		  if(calendar.get(Calendar.AM_PM)==Calendar.AM) {
		   message="Good Morning";
		   
		  }else {
		   
		   message="Good Afternoon";
		   
		  }

		  response.setContentType("text/html");
		  
		  
		  out.println("<html>");
		  out.println("<body>");

		  out.println("<a href='index.html'>Back</a>");
		  if(userName.equals(validUserName) && password.equals(validPassWord)) {
			     out.println("<h1>" + message + ", " + userName + "</h1>");
		  } else {
			    out.println("<h1>" + message+ "! "+ userName + " not known! </h1>"); 
		  } 
		  out.println("</body>");
		  out.println("</html>");
		  out.close();	
		  
		  		    
		  
		  
		 
		 
	}
}
