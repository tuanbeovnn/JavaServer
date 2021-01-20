package ws.customer.service;
import java.util.ArrayList;

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
    
    public Customer[] getCustomerShopping(float lower, float upper) {
    	ArrayList<Customer> cus = CustomerDB.getCustomerShoppingAmount(lower, upper);
    	Customer[] customers = new Customer[cus.size()];
    	cus.toArray(customers);
    	return customers;
    }
}