 //This is the content of utility.Helper.java file:

package utility;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
public class Helper {
    
    
    private static ResourceBundle resourceBundle;
    private static String[] productList, amountList, priceList;
    private static String productHtmlList="";
    private static String amountHtmlList="";
    //private static Map<String, Double> defaultPriceMap;
    
    public static Map<String, Double> initPriceMap() {
    	Map<String, Double> defaultPriceMap = new HashMap<String, Double>();
    	resourceBundle = ResourceBundle.getBundle("utility.settings", new Locale(""));
        priceList=resourceBundle.getString("price_list").split(";");
        resourceBundle = ResourceBundle.getBundle("utility.settings", new Locale(""));
        productList=resourceBundle.getString("product_list").split(";");
        
        for(int i=0; i<priceList.length;i++) {
        	defaultPriceMap.put(productList[i],Double.parseDouble(priceList[i]));
        }
        
        return defaultPriceMap;
    }
    
    public static String getUnitPrice() {
    	resourceBundle = ResourceBundle.getBundle("utility.settings", new Locale(""));

        
        return resourceBundle.getString("price_list");
    }
    
    public static String getProductHtmlList() {
        
         resourceBundle = ResourceBundle.getBundle("utility.settings", new Locale(""));
        
        //In the following we build HTML lists for products
        productList=resourceBundle.getString("product_list").split(";");
    
        
        for(String product : productList) {
            productHtmlList += "<div class=\"product\">";
            productHtmlList +=  "<input type='text' name='product' value='"+product+"' readonly>";
            productHtmlList += getAmounttHtmlList(product);
            productHtmlList += "<input type='submit' name='submit' value='Add "+ product +"' class=\"element\">";
            productHtmlList += "</div><br>";
        }
          
        
          return productHtmlList;
    }
    
    
    public static String getAmounttHtmlList(String product) {
        
          resourceBundle = ResourceBundle.getBundle("utility.settings", new Locale(""));
          
           //In the following we build HTML lists for amounts
          amountList=resourceBundle.getString("amount_list").split(";");
          amountHtmlList="<select name='"+product+"amount' class=\"element\">";
          for(String amount : amountList)
                amountHtmlList += "<option value='" + amount + "'>" + amount;
          amountHtmlList +="</select>";
          
          return amountHtmlList;
    }
    
    public static SimpleDateFormat getDateFormat() {
         resourceBundle = ResourceBundle.getBundle("utility.settings", new Locale(""));
         String dateTimePattern=resourceBundle.getString("date_time_pattern").trim();
    
         return new SimpleDateFormat(dateTimePattern);
    }
    
}
