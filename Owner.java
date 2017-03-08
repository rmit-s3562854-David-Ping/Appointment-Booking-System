package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Owner extends Member {

	private static ArrayList<Employee> employeeArray = new ArrayList<Employee>();

	private String businessName;

	public Owner() {
		super(null, null, null, null, null, null);
	}

	public Owner(String username, String password, String firstName, String lastName, String address,
			String contactNumber, String businessName) {
		super(username, password, firstName, lastName, address, contactNumber);
		this.businessName = businessName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void createEmployee() {
	}

	public void addWorkingTimes() {
	}

	public void addNewBooking() {
	}

	public void viewBookingSummary() {
	}

	public void showAllWorkerAvailability() {
	}

	public Boolean login() {
		Main driver = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Owner> ownerArray = driver.getOwnerArray();

		// create input variable to record input from user.
		Scanner input = new Scanner(System.in);
		String username = "";
		String password = "";

		System.out.println("Please enter username: ");
		username = input.next();
		System.out.println("Please enter password: ");
		password = input.next();

		int index = 0;
		while (index < ownerArray.size()) {
			MembersSearch.add(ownerArray.get(index).getUsername() + ownerArray.get(index).getPassword());

			if (MembersSearch.contains(username + password)) {
				System.out.println("Login Successful (Owner)");
				// Put Owner menu here
				return true;
			}
			index++;
		}
		return false;
	}

	public ArrayList<Employee> getEmployeeArray() {
		return employeeArray;
	}

}
