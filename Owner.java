package main;

import java.util.ArrayList;

public class Owner extends Member {
	
	private String businessName;
	
	public Owner(){
		super(null, null, null, null, null, null);
	}
	
	public Owner(String username, String password, String firstName, String lastName, String address,
			String contactNumber, String businessName) {
		super(username, password, firstName, lastName, address, contactNumber);
		this.businessName=businessName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void createEmployee(){}
	public void addWorkingTimes(){}
	public void addNewBooking(){}
	public void viewBookingSummary(){}
	public void showAllWorkerAvailability(){}
	
	public Boolean login(String username, String password) {
		Main driver = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Owner> ownerArray = driver.getOwnerArray();
		
		int index = 0;
		while (index < ownerArray.size()) {
			MembersSearch.add(ownerArray.get(index).getUsername() + ownerArray.get(index).getPassword());

			if (MembersSearch.contains(username + password)) {
				System.out.println("Login Successful (Owner)");
				//Put Owner menu here
				return true;
			} 
			index++;
		}
		return false;
	}
	
	
}
