package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBHandler;

public class Signup extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        //response.sendRedirect("index.html");
    	PrintWriter out = response.getWriter();
  
    	response.setContentType("text/html");
    	
    	String errorString = request.getParameter("error");
    	
    	out.print("<!DOCTYPE html>\r\n" + 
   				"<html>\r\n" + 
   				"<head>\r\n" + 
   				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
   				"<style>\r\n" + 
   				"body {font-family: Arial, Helvetica, sans-serif;}\r\n" + 
   				"form {border: 3px solid #f1f1f1;}\r\n" + 
   				"\r\n" + 
   				"input[type=text], input[type=password] {\r\n" + 
   				"  width: 100%;\r\n" + 
   				"  padding: 12px 20px;\r\n" + 
   				"  margin: 8px 0;\r\n" + 
   				"  display: inline-block;\r\n" + 
   				"  border: 1px solid #ccc;\r\n" + 
   				"  box-sizing: border-box;\r\n" + 
   				"}\r\n" + 
   				"\r\n" + 
   				"button {\r\n" + 
   				"  background-color: #4CAF50;\r\n" + 
   				"  color: white;\r\n" + 
   				"  padding: 14px 20px;\r\n" + 
   				"  margin: 8px 0;\r\n" + 
   				"  border: none;\r\n" + 
   				"  cursor: pointer;\r\n" + 
   				"  width: 100%;\r\n" + 
   				"}\r\n" + 
   				"\r\n" + 
   				"button:hover {\r\n" + 
   				"  opacity: 0.8;\r\n" + 
   				"}\r\n" + 
   				"\r\n" + 
   				".container {\r\n" + 
   				"  padding: 16px;\r\n" + 
   				"}\r\n" + 
   				"\r\n" + 
   				"span.psw {\r\n" + 
   				"  float: right;\r\n" + 
   				"  padding-top: 16px;\r\n" + 
   				"}\r\n" + 
   				"\r\n" + 
   				"/* Change styles for span and cancel button on extra small screens */\r\n" + 
   				"@media screen and (max-width: 300px) {\r\n" + 
   				"  span.psw {\r\n" + 
   				"     display: block;\r\n" + 
   				"     float: none;\r\n" + 
   				"  }\r\n" + 
   				"  .cancelbtn {\r\n" + 
   				"     width: 100%;\r\n" + 
   				"  }\r\n" + 
   				"}\r\n" + 
   				"</style>\r\n" + 
   				"</head>\r\n" + 
   				"<body>\r\n" + 
   				"\r\n" + 
   				
   				"<h2>Sign up Form</h2>\r\n" + 
   				"<p>" + (request.getParameter("error") == null ? "" : request.getParameter("error")) + "</p>"+
   				"\r\n" + 
   				"<form action=\"sign_up\" method=\"post\">\r\n" + 
   				"\r\n" + 
   				"  <div class=\"container\">\r\n" + 
   				"    <label for=\"uname\"><b>Username</b></label>\r\n" + 
   				"    <input type=\"text\" placeholder=\"Enter Username\" name=\"username\" required>\r\n" + 
   				"\r\n" + 
   				"    <label for=\"psw\"><b>Password</b></label>\r\n" + 
   				"    <input type=\"password\" placeholder=\"Enter Password\" name=\"password\" required>\r\n" + 
   				"        \r\n" + 
   				"    <button value=\"Submit\" type=\"submit\">Submit</button>\r\n" + 
   				" \r\n" + 
   				"  </div>\r\n" + 
   				"</form>\r\n" + 
   				
   				"<form action=\"index.html\" method=\"get\">\r\n" + 
   				"  <div class=\"container\">\r\n" + 
   				"   <button type=\"submit\">Log in</button>\r\n" + 
   				"  </div>\r\n" + 
   				"</form>\r\n" + 
   				"\r\n" + 
   				"</body>\r\n" + 
   				"</html>\r\n" + 
   				"");
    	

//    	out.print("<!-- This is the content of index.html file -->\r\n" + 
//    			"<!Doctype html>\r\n" + 
//    			"<html>\r\n" + 
//    			"<head>\r\n" + 
//    			"<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">"+
//    			"<style type=\"text/css\">\r\n" + 
//    			"th, td {\r\n" + 
//    			"	background-color:  #afcdcd;\r\n" + 
//    			"	color: #d97077;\r\n" + 
//    			"	border: 2px solid white;\r\n" + 
//    			"	text-align: left;\r\n" + 
//    			"}\r\n" + 
//    			"h1 {\r\n"+
//    			" text-align: center;\r\n "+
//    			"}\r\n"+
//    			".form-style {\r\n" + 
//    			"	font-weight: bold;\r\n" + 
//    			"	font-style: italic;\r\n" + 
//    			"	border-bottom: 2px solid #ddd;\r\n" + 
//    			"	margin-bottom: 20px;\r\n" + 
//    			"	font-size: 15px;\r\n" + 
//    			"	padding-bottom: 3px;\r\n" + 
//    			"	display: flex;\r\n" + 
//    			"  	flex-direction: column;\r\n" + 
//    			"  	align-items: center;\r\n" + 
//    			"  	text-align: center;\r\n" + 
//    			"}\r\n" + 
//    			"p {\r\n"+
//    			" text-align: center;\r\n "+
//    			" color: red;\r\n" +
//    			"}\r\n"+
//    			".form-style-2 input[type=submit],\r\n" + 
//    			".form-style-2 input[type=button]{\r\n" + 
//    			"	border: none;\r\n" + 
//    			"	padding: 8px 15px 8px 15px;\r\n" + 
//    			"	background: #FF8500;\r\n" + 
//    			"	color: #fff;\r\n" + 
//    			"	box-shadow: 1px 1px 4px #DADADA;\r\n" + 
//    			"	-moz-box-shadow: 1px 1px 4px #DADADA;\r\n" + 
//    			"	-webkit-box-shadow: 1px 1px 4px #DADADA;\r\n" + 
//    			"	border-radius: 3px;\r\n" + 
//    			"	-webkit-border-radius: 3px;\r\n" + 
//    			"	-moz-border-radius: 3px;\r\n" + 
//    			"}\r\n" + 
//    			".form-style input[type=submit]:hover,\r\n" + 
//    			".form-style input[type=button]:hover{\r\n" + 
//    			"	background: #EA7B00;\r\n" + 
//    			"	color: #fff;\r\n" + 
//    			"}\r\n" + 
//    			"</style>\r\n" + 
//    			"</head>\r\n" + 
//    			"<body>\r\n" + 
//    			"<h1>Sign up page</h1>"+
//    			"<p>" + (request.getParameter("error") == null ? "" : request.getParameter("error")) + "</p>"+
//    			"	<form class=\"form-style\" action=\"sign_up\" method=\"post\">\r\n" + 
//    			"		<table>\r\n" + 
//    			"			<tr>\r\n" + 
//    			"				<th>User name</th>\r\n" + 
//    			"				<th><input type=\"text\" name=\"username\" placeholder=\"Your name\" required='required'></th>\r\n" + 
//    			"			</tr>\r\n" + 
//    			"			<tr>\r\n" + 
//    			"				<th>Passowrd: </th>\r\n" + 
//    			"				<th><input type=\"password\" name=\"password\" placeholder=\"Password\" required='required'></th>\r\n" + 
//    			"			</tr>\r\n" + 
//    			"			<tr>\r\n" + 
//    			"				<th><input type=\"submit\" value=\"Submit\"></th>\r\n" + 
//    			"			</tr>\r\n" + 
//    			"		</table>\r\n" + 
//    			"	</form>\r\n" +  
//				"	<form class=\"form-style\" action=\"index.html\" method=\"get\">\r\n" + 
//				"  		<input type=\"submit\" value=\"Sign in\">\r\n" + 
//				"	</form> "+
//    			"</body>\r\n" + 
//    			"</html>");


    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String isSuccess = "";
    	
    	try {
			DBHandler dbHandler = new DBHandler();
			isSuccess = dbHandler.createUserAccount(username, password);
			if(isSuccess.equalsIgnoreCase("exist")) {
				response.sendRedirect("sign_up?error=User exist, please choose another username");
			}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("<html>\r\n" + 
        		"  <head>\r\n" + 
        		"    <link href=\"https://fonts.googleapis.com/css?family=Nunito+Sans:400,400i,700,900&display=swap\" rel=\"stylesheet\">\r\n" + 
        		"  </head>\r\n" + 
        		"    <style>\r\n" + 
        		"    .button {\r\n" + 
        		"  background-color: #4CAF50; /* Green */\r\n" + 
        		"  border: none;\r\n" + 
        		"  color: white;\r\n" + 
        		"  padding: 16px 32px;\r\n" + 
        		"  text-align: center;\r\n" + 
        		"  text-decoration: none;\r\n" + 
        		"  display: inline-block;\r\n" + 
        		"  font-size: 16px;\r\n" + 
        		"  margin: 4px 2px;\r\n" + 
        		"  transition-duration: 0.4s;\r\n" + 
        		"  cursor: pointer;\r\n" + 
        		"}\r\n" + 
        		"      body {\r\n" + 
        		"        text-align: center;\r\n" + 
        		"        padding: 40px 0;\r\n" + 
        		"        background: #EBF0F5;\r\n" + 
        		"      }\r\n" + 
        		"        h1 {\r\n" + 
        		"          color: #88B04B;\r\n" + 
        		"          font-family: \"Nunito Sans\", \"Helvetica Neue\", sans-serif;\r\n" + 
        		"          font-weight: 900;\r\n" + 
        		"          font-size: 40px;\r\n" + 
        		"          margin-bottom: 10px;\r\n" + 
        		"        }\r\n" + 
        		"        p {\r\n" + 
        		"          color: #404F5E;\r\n" + 
        		"          font-family: \"Nunito Sans\", \"Helvetica Neue\", sans-serif;\r\n" + 
        		"          font-size:20px;\r\n" + 
        		"          margin: 0;\r\n" + 
        		"        }\r\n" + 
        		"      i {\r\n" + 
        		"        color: #9ABC66;\r\n" + 
        		"        font-size: 100px;\r\n" + 
        		"        line-height: 200px;\r\n" + 
        		"        margin-left:-15px;\r\n" + 
        		"      }\r\n" + 
        		"      .card {\r\n" + 
        		"        background: white;\r\n" + 
        		"        padding: 60px;\r\n" + 
        		"        border-radius: 4px;\r\n" + 
        		"        box-shadow: 0 2px 3px #C8D0D8;\r\n" + 
        		"        display: inline-block;\r\n" + 
        		"        margin: 0 auto;\r\n" + 
        		"      }\r\n" + 
        		"    </style>\r\n" + 
        		"    <body>\r\n" + 
        		"      <div class=\"card\">\r\n" + 
        		"      <div style=\"border-radius:200px; height:200px; width:200px; background: #F8FAF5; margin:0 auto;\">\r\n" + 
        		"        <i class=\"checkmark\">✓</i>\r\n" + 
        		"      </div>\r\n" + 
        		"        <h1>Success</h1> \r\n" + 
        		"        <button class=\"button button1\"><a href='http://localhost:8080/Assignment9/'>Login</a></button>\r\n" + 
        		"       \r\n" + 
        		"      </div>\r\n" + 
        		"    </body>\r\n" + 
        		"</html>");
        
        
//        out.println("<!DOCTYPE html><html><head>"
//        		+ "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">"
//        		+ "<title>Online Shop</title><style>th { text-align:left;}</style></head><body>");
//        out.println("<p>"+isSuccess+"</p>");
//        out.println("<p style='text-align: center;'><a href='http://localhost:8080/Assignment9/'>Login</a></p>");
//        out.println("</body></html>");
        out.close();
	}
}
