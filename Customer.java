package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Member {

	private static ArrayList<Appointment> appointmentArray = new ArrayList<Appointment>();

	public Customer() {
		super(null, null, null, null, null, null);
	}

	public Customer(String username, String password, String firstName, String lastName, String address,
			String contactNumber) {
		super(username, password, firstName, lastName, address, contactNumber);
	}

	public Boolean login() {
		Main driver = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Customer> customerArray = driver.getCustomerArray();

		// create input variable to record input from user.
		Scanner input = new Scanner(System.in);
		String username = "";
		String password = "";

		System.out.println("Please enter username: ");
		username = input.next();
		System.out.println("Please enter password: ");
		password = input.next();

		int index = 0;
		while (index < customerArray.size()) {
			MembersSearch.add(customerArray.get(index).getUsername() + customerArray.get(index).getPassword());

			if (MembersSearch.contains(username + password)) {
				System.out.println("Login Successful (Customer)");
				// Put Customer menu here
				return true;
			}
			index++;
		}
		return false;
	}

	public Boolean register() {
		Scanner keyboard = new Scanner(System.in);
		Main driver = new Main();
		// first name, last name, address, contact details, username, password,
		// re-enter password
		String firstName, lastName, address, contactNumber, username, password, password2;

		System.out.println("                      REGISTRATION");
		System.out.println("**********************************************************");
		System.out.println("First Name:");
		firstName = keyboard.nextLine();
		System.out.println("Last Name:");
		lastName = keyboard.nextLine();
		System.out.println("Address:");
		address = keyboard.nextLine();
		System.out.println("Contact Number:");
		contactNumber = keyboard.nextLine();

		// If username already exists
		int index = 0;
		Boolean duplicate = null;
		do {
			System.out.println("Username:");
			username = keyboard.nextLine();
			while (index < driver.getCustomerArray().size()) {
				if (driver.getCustomerArray().get(index).getUsername().equals(username)) {
					duplicate = true;
					System.out.println("This username already exists, please try a different one");
					index = 0;
					break;
				} else if (index == driver.getCustomerArray().size() - 1) {
					duplicate = false;
					System.out.println("Username is available");
				}
				index++;
			}
		} while (duplicate == true);

		System.out.println("Password:");
		password = keyboard.nextLine();

		Customer customer = new Customer(username, password, firstName, lastName, address, contactNumber);

		driver.getCustomerArray().add(customer);
		System.out.println("Registration complete");
		return true;
	}

	public ArrayList<Appointment> getAppointmentArray() {
		return appointmentArray;
	}
}
