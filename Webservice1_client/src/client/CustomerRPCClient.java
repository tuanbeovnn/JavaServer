package client;

import java.util.Scanner;

import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class CustomerRPCClient {

	public static void menu() {
		System.out.println("---Please choose ---");
		System.out.println("1. Insert a customer");
		System.out.println("2. Delete Customer");
		System.out.println("3. Update Customer");
		System.out.println("4. Print Customer");
		System.out.println("5. Exit");
		System.out.print(">>> Selection: ");
	}

	public static void main(String[] args1) throws AxisFault {
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		Scanner scanner = new Scanner(System.in);

		EndpointReference targetEPR = new EndpointReference("http://localhost:8080/axis2/services/t_customerService");
		options.setTo(targetEPR);
		while (true) {
			menu();
			int choiceList = scanner.nextInt();
			switch (choiceList) {
			case 1:
				System.out.println("Enter customer: name id amount priviliged discount ");
				String user_info[] = new Scanner(System.in).nextLine().split(" ");
				QName opSetCustomer = new QName("http://service.customer.ws", "setCustomer");
				Object[] opSetCustomerArgs = new Object[] { user_info[0], Integer.parseInt(user_info[1]),
						Float.parseFloat(user_info[2]), Boolean.parseBoolean(user_info[3]),
						Integer.parseInt(user_info[4]) };
				serviceClient.invokeRobust(opSetCustomer, opSetCustomerArgs);
				break;

			case 2:
				System.out.println("Enter ID: ");
				int del = new Scanner(System.in).nextInt();
				QName deleteCustomer = new QName("http://service.customer.ws", "deleteById");
				Object[] opDelCustomerArgs = new Object[] { del };
				Class<String>[] returntypes = new Class[] { String.class };
				Object[] res = serviceClient.invokeBlocking(deleteCustomer, opDelCustomerArgs, returntypes);
				break;
			case 3:
				System.out.println(
						"Enter customer need to be updated: name id amount priviliged discount ");
				String user_info1[] = new Scanner(System.in).nextLine().split(" ");
				QName updateCustomer = new QName("http://service.customer.ws", "update");
				Object[] opUpdateCustomerArgs = new Object[] { user_info1[0], Integer.parseInt(user_info1[1]),
						Float.parseFloat(user_info1[2]), Boolean.parseBoolean(user_info1[3]),
						Integer.parseInt(user_info1[4]) };
				Class<String>[] returntypes1 = new Class[] { String.class };
				serviceClient.invokeBlocking(updateCustomer, opUpdateCustomerArgs, returntypes1);
				break;
			case 4:
				System.out.println("Enter id: ");
				int id = new Scanner(System.in).nextInt();
				QName opGetCustomer = new QName("http://service.customer.ws", "getCustomer");
				Object[] opGetCustomerArgs = new Object[] { id };
				Class<String>[] returnTypes = new Class[] { String.class };
				Object[] response = serviceClient.invokeBlocking(opGetCustomer, opGetCustomerArgs, returnTypes);
				System.out.println(response[0].toString());
				break;
			case 5:
				break;
			}
		}
	}
}