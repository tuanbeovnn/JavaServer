package ws.customer.data;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
public class CustomerDB {
    
    //Here we define a collection for customers.
   private static Hashtable<Integer, Customer> customers = new Hashtable<Integer, Customer>();
    
    public static String addCustomer(Customer customer) {
        
        String result="Current number of entries: ";
        if(customer !=null)
           customers.put(customer.getCustomerID(), customer);
        
        return result + customers.size();
        
    }
    
    
     public static Customer getCustomer(int id) {
          return  customers.get(id);
        
     }
     
     public static String deleteCustomer(int id) {
     	String result = null;
     	Iterator<Integer> iterators = customers.keySet().iterator();
         while(iterators.hasNext()) {
             int key = iterators.next();
             if(customers.get(key).getCustomerID() == id) {
             	result = customers.get(key).toString();
                 iterators.remove();
                 break;
             }
         }
         return result;
     }
     
     public static Customer update (Customer cs) {
    	 if (customers.get(cs.getCustomerID()) != null) {
    		 customers.get(cs.getCustomerID()).setCustomerName(cs.getCustomerName());
    		 customers.get(cs.getCustomerID()).setDiscountPercentage(cs.getDiscountPercentage());
    		 customers.get(cs.getCustomerID()).setPrivileged(cs.getPrivileged());
    		 customers.get(cs.getCustomerID()).setShoppingAmount(cs.getShoppingAmount());
    	 }
    	 return cs;
     }
     
     public static ArrayList<Customer> getCustomerShoppingAmount (float lower, float upper){
    	 ArrayList<Customer> cus = new ArrayList<Customer>();
    	 Iterator<Integer> iterators = customers.keySet().iterator();
    	 while(iterators.hasNext()) {
             int key = iterators.next();
             float shopping = customers.get(key).getShoppingAmount();
             if ((shopping <= upper) && (shopping >= lower)) {
            	 cus.add(customers.get(key));
             }
         }
    	 return cus;
     }

}