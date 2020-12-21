package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DBHandler;
import servlet.ShopServlet;
import utility.Helper;

public class Login extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5777030616914939743L;
	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        //response.sendRedirect("index.html");
    	PrintWriter out = response.getWriter();
  
    	response.setContentType("text/html");
    	
    	//Invalidate session when user go to this page
    	HttpSession session = request.getSession();
   		if(!session.isNew()) {
   			session.invalidate();
   		}
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
   				
   				"<h2>Login Form</h2>\r\n" + 
   				"<p>" + (request.getParameter("error") == null ? "" : request.getParameter("error")) + "</p>"+
   				"\r\n" + 
   				"<form action=\"login\" method=\"post\">\r\n" + 
   				"\r\n" + 
   				"  <div class=\"container\">\r\n" + 
   				"    <label for=\"uname\"><b>Username</b></label>\r\n" + 
   				"    <input type=\"text\" placeholder=\"Enter Username\" name=\"username\" required>\r\n" + 
   				"\r\n" + 
   				"    <label for=\"psw\"><b>Password</b></label>\r\n" + 
   				"    <input type=\"password\" placeholder=\"Enter Password\" name=\"password\" required>\r\n" + 
   				"        \r\n" + 
   				"    <button value=\"Sign in\" name='submit' type=\"submit\">Login</button>\r\n" + 
   				" \r\n" + 
   				"  </div>\r\n" + 
   				"</form>\r\n" + 
   				
   				"<form action=\"sign_up\" method=\"get\">\r\n" + 
   				"  <div class=\"container\">\r\n" + 
   				"   <button type=\"submit\">Sign up</button>\r\n" + 
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
//    			"p {\r\n"+
//    			" text-align: center;\r\n "+
//    			" color: red;\r\n" +
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
//    			"<h1>Sign in page</h1>\r\n"+
//    			"<p>" + (request.getParameter("error") == null ? "" : request.getParameter("error")) + "</p>"+
//    			"	<form class=\"form-style\" action=\"login\" method=\"post\"> \r\n" + 
//    			"		<table>\r\n" + 
//    			"			<tr>\r\n" + 
//    			"				<th>User name</th>\r\n" + 
//    			"				<th><input type=\"text\" name=\"username\" placeholder=\"Your name\" required='required'></th>\r\n" + 
//    			"			</tr>\r\n" + 
//    			"			<tr>\r\n" + 
//    			"				<th>Passowrd: </th>\r\n" + 
//    			"				<th><input type=\"password\" name='password' placeholder=\"Password\" required='required'></th>\r\n" + 
//    			"			</tr>\r\n" + 
//    			"			<tr>\r\n" + 
//    			"				<th><input type=\"submit\" name='submit' value=\"Sign in\"></th>\r\n" + 
//    			"			</tr>\r\n" + 
//    			"		</table>\r\n" + 
//    			"	</form>\r\n" +  
//				"	<form class=\"form-style\" action=\"sign_up\" method=\"get\">\r\n" + 
//				"  		<input type=\"submit\" value=\"Sign up\">\r\n" + 
//				"	</form> "+
//    			"</body>\r\n" + 
//    			"</html>");


    }
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String actionString = request.getParameter("submit");
		if(actionString.equalsIgnoreCase("Sign in")) {
			String name = request.getParameter("username");
			String password = request.getParameter("password");
	    	System.out.println("Login: "+name+ " "+password);
	    	String isSuccess = "";
	    	try {
				DBHandler dbHandler = new DBHandler();
				isSuccess = dbHandler.userLogin(name, password);
	    	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isSuccess = "Something wrong with connection";
			}
	    	if(isSuccess.contains("ID")) {
	    		
	    		//Save data as session
	       		HttpSession session = request.getSession(true);
	            session.setAttribute("sessionID", session.getId());
	            session.setAttribute("sessionCreateTionime", Helper.getDateFormat().format(new Date(session.getCreationTime())));
	            // session.setAttribute("sessionLastAccessTime", new
	            // Date(session.getLastAccessedTime()));
	            session.setAttribute("isUserLogined", true);
	            session.setAttribute("customer", name);
	            session.setAttribute("iduser", isSuccess.substring(2));
	            //session.setAttribute("objects.ShoppingCart", new objects.ShoppingCart(name));
	            
	    		response.sendRedirect("shop_servlet");

	    	}else {
	    		response.sendRedirect("login?error="+isSuccess);
	    	}
    	}else { // this is for log out
    		HttpSession session = request.getSession();
    		session.setAttribute("isUserLogined", null); // make sure this attribute is null
    		session.invalidate(); // then invalidate 
    		response.sendRedirect("login?error=you have been logged out"); // redirect to login page
    	}
    }
}
