package application.main;

import java.util.ArrayList;

import application.MainApp;

public class Customer extends Member {
	
	/**
	 * Customer class, attributes and behaviors of customers
	 *
	 * @version 1.00 05 Apr 2017
	 * @author David Ping, Hassan Mender, Luke Waldren
	 */

	public Customer() {
		super(null, null, null, null, null, null);
	}

	public Customer(String username, String password, String firstName, String lastName, String address,
			String contactNumber) {
		super(username, password, firstName, lastName, address, contactNumber);
	}
	
	public Boolean checkLogin(String username, String password){
		MainApp main = new MainApp();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Customer> customerArray = main.getCustomerArray();

		int index = 0;
		while (index < customerArray.size()) {
			MembersSearch.add(customerArray.get(index).getUsername() + customerArray.get(index).getPassword());
			index++;
		}
		if (MembersSearch.contains(username + password)){
			return true;
		}
		
		return false;
	}
	
	public Customer getCustomer(String customerUsername){
		MainApp mainApp = new MainApp();
		for(int i=0;i<mainApp.getCustomerArray().size();i++){
			if(mainApp.getCustomerArray().get(i).getUsername().equals(customerUsername)){
				return mainApp.getCustomerArray().get(i);
			}
		}
		return null;
	}
	
	public String toString() {
		return this.getUsername() + ":" + this.getPassword() + ":" + this.getFirstName() + ":" + this.getLastName()
				+ ":" + this.getAddress() + ":" + this.getContactNumber();
	}
}
