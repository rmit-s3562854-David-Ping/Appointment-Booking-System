package main;

public class Owner extends Member {
	
	private String businessName;
	
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
	

	
	
}
