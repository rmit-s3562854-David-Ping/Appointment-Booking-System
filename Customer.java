package main;

import java.util.ArrayList;

public class Customer extends Member {

	public Customer(){
		super(null, null, null, null, null, null);
	}
	
	public Customer(String username, String password, String firstName, String lastName, String address, String contactNumber) {
		super(username, password, firstName, lastName, address, contactNumber);
	}

	public void viewAppointmentTimes(){}
	
	public Boolean login(String username, String password) {
		Main driver = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Customer> customerArray = driver.getCustomerArray();

		int index = 0;
		while (index < customerArray.size()) {
			MembersSearch.add(customerArray.get(index).getUsername() + customerArray.get(index).getPassword());

			if (MembersSearch.contains(username + password)) {
				System.out.println("Login Successful (Customer)");
				//Put Customer menu here
				return true;
			} 
			index++;
		}
		return false;
	}
}
