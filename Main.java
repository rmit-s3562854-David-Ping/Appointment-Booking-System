package main;

import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	private ArrayList<Customer> customerArray = new ArrayList<Customer>();
	private ArrayList<Owner> ownerArray = new ArrayList<Owner>();

	public static void main(String[] args) throws IOException {
		// create input variable to record input from user.
		Scanner input = new Scanner(System.in);
		String username = "";
		String password = "";
		// function to create menu UI.
		createMenu();

		int selection = input.nextInt();
		if (selection == 1) {
			System.out.println("Please enter username: ");
			username = input.next();
			System.out.println("Please enter password: ");
			password = input.next();
			arraySearch(username, password);

		}
		if (selection == 0) {
			// closes the program.
			System.out.println("System Closed....Please close the program.");
			System.exit(0);

		}
	}

	public static void createMenu() {
		System.out.println("*************** RMIT Massage Booking System **************\n");
		System.out.println("1.   Login");
		System.out.println("0.   Exit\n");
		System.out.println("**********************************************************");

	}

	public ArrayList<Customer> getCustomerArray() {
		return customerArray;
	}

	public ArrayList<Owner> getOwnerArray() {
		return ownerArray;
	}

	public static String arraySearch(String username, String password) {
		Main driver = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		// Just some dummy customers to test login.
		Customer customer2 = new Customer("somethingElse", "password", "John", "Smith", "1 test rd", "12345");
		Customer customer = new Customer("username", "password", "John", "Smith", "1 test rd", "12345");
		ArrayList<Customer> customerArray = driver.getCustomerArray();
		customerArray.add(customer2);
		customerArray.add(customer);

		int index = 0;
		while (index < customerArray.size()) {
			MembersSearch.add(customerArray.get(index).getUsername() + customerArray.get(index).getPassword());

			if (MembersSearch.contains(username + password)) {
				System.out.println("Login Successful");
				return "";
			} else {
			}
			index++;
		}

		System.out.println("Error: user not found, please check login details.");
		return "";
	}
}
