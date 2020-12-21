//This is the content of servlet/ShopServlet.java file.
package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DBHandler;
import utility.Helper;
public class ShopServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String productHtmlList;
    private String amountHtmlList;
    private static Map<String, Double> defaultPriceMap;

    public ShopServlet() {
        super();
    }
    public void init() {
        
       productHtmlList=Helper.getProductHtmlList();
   	   defaultPriceMap = Helper.initPriceMap();
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
		
		// Set response content type
		response.setContentType("text/html");
		
        
        // Here we get access to the current session
        HttpSession session = request.getSession();
        
        if(session.getAttribute("isUserLogined") != null) {
	        Object customer = null;
	        if (session != null) {
	            customer = session.getAttribute("customer");
	           
	        }
	        if(session.getAttribute("objects.ShoppingCart") == null) {
	        	
	        	objects.ShoppingCart shoppingCart = new objects.ShoppingCart((String) session.getAttribute("customer"));
	        	String products = "";
	        	String amounts = "";
	        	String price_unit = Helper.getUnitPrice();
	        	
	        	shoppingCart.initShoppingCartOnCookies(products, amounts, price_unit);
	        	session.setAttribute("objects.ShoppingCart", shoppingCart);
	        }
//	        out.println("<!DOCTYPE html><html><head>"
//	        		+"<link rel=\"stylesheet\" href=\"mystyle.css\">"
//	        		+ "<title>Online Shop</title><style>th { text-align:left;}</style></head><body><h1 style='text-align: center;'>Hello "+ (customer == null ? "" : customer.toString()) +"</h1>");
	        out.println("<html><head>");
	        out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/Style/style.css' />");
	        out.println("<body>");
	        out.println("<h1 style='text-align: center;'>Hello "+ (customer == null ? "" : customer.toString()) +"</h1>");
	        out.println("<p style='text-align: center;'>" + (request.getParameter("info") == null ? "" : request.getParameter("info")) + "</p>");
	        out.println("<form action=\"checkout\" method=\"get\">\r\n" + 
					"  		<input class=\"button button2\" type=\"submit\" name='submit' value=\"Cart Info\">\r\n" + 
					"	</form> ");
	        out.println("<form style='text-align: center;' method='post' action='handle_shopping_cart'>");
	     
	        out.println("<div>");
	        out.println(productHtmlList);
	        out.println("</div>");
	        out.println("<button type='submit' name='submit' value='Visit cart' class=\"button\">Visit Cart</button>");
	        out.println("<button type='submit' name='submit' value='Empty Cart' class=\"button\">Remove Cart</button>");
	        out.println("</form>");
	        out.println("<hr/>");   
	        out.println("</form>");
	        out.println("	<form style='text-align:center' class=\"form-style\" action=\"login\" method=\"post\">\r\n" + 
					"  		<input class=\"button button3\" type=\"submit\" name='submit' value=\"Log out\">\r\n" + 
					"	</form> ");
        }else {
        	out.println("<p style='color:red;text-align: center;'>Please login</p>");
        	out.println("<p style='text-align: center;'><a href='http://localhost:8080/Assignment9/'>Login</a></p>");
        }
        out.println("</body></html>");
        out.close();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}