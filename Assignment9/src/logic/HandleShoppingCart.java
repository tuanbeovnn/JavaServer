//This is the content of logic.HandleShoppingCart.java file
package logic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import objects.ShoppingCart;
import utility.Helper;
public class HandleShoppingCart extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Map<String, Double> defaultPriceMap;
    @Override
    public void init() {
    	defaultPriceMap = Helper.initPriceMap();
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Here we get access to the current session
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
       	Object customer = null;
       	
        if (session != null) {
            customer = session.getAttribute("customer");
           
        }
        
        if(session.getAttribute("isUserLogined") != null) {
	        String action = request.getParameter("submit");
	        if (action == null)
	            response.sendRedirect("index.html");
	        out.println("<html><head>");
	        out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/Style/style.css' />");
	        out.println("<body>");
	        out.println("<h1 style='text-align: center;'>Hello "+ (customer == null ? "" : customer.toString()) +"</h1>");
	        // Here we create a local variable which holds the shoppinCart
	        objects.ShoppingCart shoppingCart = null;
	        if (session.isNew()) {
	            session.setAttribute("accessCounter", new Integer(1));
	            // Here we bind the ShoppingCart to the session scope
	            // session.setAttribute("objects.ShoppingCart", new
	            // objects.ShoppingCart(request.getParameter("customer")));
	        } else {
	            session.setAttribute("accessCounter", session.getAttribute("accessCounter") == null ? 1
	                    : ((Integer) session.getAttribute("accessCounter")) + 1);
	        }
	        // Here we get access to the shopping cart
	        if (session != null)
	            shoppingCart = (objects.ShoppingCart) session.getAttribute("objects.ShoppingCart");
	        if (action.equalsIgnoreCase("Delete")) {
	            String key = request.getParameter("product");
	            shoppingCart.remove(key);
	        }
	        if (action.equalsIgnoreCase("Update")) {
	        	
	            String key = request.getParameter("product");
	            String amount = request.getParameter("amount");
	            if(amount.equals("0")) {
	            	shoppingCart.remove(key);
	            }
	            else {
		            double priceOfProduct = defaultPriceMap.get(key);
		            
		            shoppingCart.remove(key);
		            shoppingCart.put(key, amount);
		            shoppingCart.updatePrice(key, priceOfProduct+" ");
		            shoppingCart.totalPayment();
		            
	            }
	        }
	        if (action.contains("Add")) {
	            //session.setAttribute("customer", request.getParameter("customer"));
	        	String productString = action.substring(4);
	        	String amountString = request.getParameter(productString+"amount");
	        	double priceOfProduct = defaultPriceMap.get(productString);
	        
	            shoppingCart.put(productString, amountString);
	            
	            shoppingCart.updatePrice(productString, priceOfProduct+" ");
	            response.sendRedirect("shop_servlet?info="+amountString+" "+productString+" have been added");
	        }
	        if (action.equalsIgnoreCase("Empty Cart")) {
	        	
	            if (session != null)
	            	session.setAttribute("objects.ShoppingCart", new objects.ShoppingCart((String) session.getAttribute("customer")));
	            response.sendRedirect("shop_servlet?info=Shopping cart is empty now!");
	            return;
	        }
          
	        out.println("<table>");
	        out.println("<tr><th>Customer:</th><td>" + session.getAttribute("customer") + "</td></tr>");
	        out.println("<tr><th>Session ID:</th><td>" + session.getAttribute("sessionID") + "</td></tr>");
	        out.println("<tr><th>Shopping cart created at :</th><td>"
	                + session.getAttribute("sessionCreateTionime") + "</td></tr>");
	        out.println("<tr><th>Shopping cart created last time:</th><td>"
	                +  Helper.getDateFormat().format(new Date(session.getLastAccessedTime())) + "</td></tr>");
	        out.println("<tr><th>Shopping Cart counter:</th><td>" + session.getAttribute("accessCounter")
	                + "</td></tr>");
	        out.println("<br>");
	        if (shoppingCart.getSize() < 1) {
	            out.println("<p style='color:red;'>Shopping Cart is empty!</p>");

	        } else {
	            out.println("<p>Shopping Cart:</p>");
	            // Here we get access to the shopping cart
	            out.println(shoppingCart.getValues());
	        }
	        out.println("<tr><th>Checkout total: </th><td><form method='post' action='checkout'><input type='text' name='payment' value='"+shoppingCart.totalPayment()+"' readonly><input class=\"button button3\" type='submit' name='submit' value='Checkout'></form></td></tr>");
	        out.println("</table>");
	        out.println("<hr />");
	        out.println("<p style='text-align: center;'><a href='http://localhost:8080/Assignment9/shop_servlet'>Continue Shopping</a></p>");
	        out.println("	<form class=\"form-style\" action=\"login\" method=\"post\">\r\n" + 
					"  		<input class=\"button button1\" type=\"submit\" name='submit' value=\"Log out\">\r\n" + 
					"	</form> ");
        }else {
        	out.println("<p style='color:red;text-align: center;'>Please login</p>");
        	out.println("<p style='text-align: center;'><a href='http://localhost:8080/Assignment9/'>Login</a></p>");
        }
        out.println("</body></html>");
        out.close();
    }
}