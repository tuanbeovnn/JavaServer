package ws.customer.service;
import ws.customer.data.Customer;
import ws.customer.data.CustomerDB;
public class CustomerService {
    public void setCustomer(String customerName, Integer customerID, float shoppingAmount, boolean privileged, int discountPercentage ) {
        Customer customer = new Customer(customerName, customerID, shoppingAmount, privileged, discountPercentage);
        CustomerDB.addCustomer(customer);
    }
    public String getCustomer(int id) {
        Customer customer = CustomerDB.getCustomer(id);
        if (customer == null) {
            customer = new Customer();
            customer.setCustomerID(id);
        }
        return customer.toString();
    }
    
    public String deleteById(int id) {
    	return CustomerDB.deleteCustomer(id);	
    }
    public String update(String customerName, Integer customerID, float shoppingAmount, boolean privileged, int discountPercentage ) {
    	Customer customer = new Customer(customerName, customerID, shoppingAmount, privileged, discountPercentage);
    	CustomerDB.update(customer);
    	return customer.toString();
    }
}