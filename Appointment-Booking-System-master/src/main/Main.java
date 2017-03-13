package main;

import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	private static ArrayList<Customer> customerArray = new ArrayList<Customer>();
	private static ArrayList<Owner> ownerArray = new ArrayList<Owner>();

	public static void main(String[] args) {
		Reader reader = new Reader();
		Member customer = new Customer();
		Member owner = new Owner();
		Scanner input = new Scanner(System.in);

		reader.read();

		// function to create menu UI.
		createMenu();

		int selection = 0;

		do {
			try {
				selection = input.nextInt();
			} catch (Exception e) {
				input.nextLine();
			}

			switch (selection) {
			case 1: {
				if (owner.login()) {
					System.out.println("Owner has logged in");
				} else if (customer.login()) {
					System.out.println("Customer has logged in");
				} else {
					System.out.println("Login failed");
				}
				break;
			}
			case 2: {
				customer.register();
				break;
			}
			case 3: {
				// closes the program.
				System.out.println("System Closed");
				System.exit(0);
			}
			default:
				System.out.println("Invalid Input, please try again:");
			}
		} while (!(selection == 3));
	}

	public static void createMenu() {
		System.out.println("*************** Appointment Booking System ***************\n");
		System.out.println("1.   Login");
		System.out.println("2.   Register");
		System.out.println("3.   Exit\n");
		System.out.println("**********************************************************");

	}

	public ArrayList<Customer> getCustomerArray() {
		return customerArray;
	}

	public ArrayList<Owner> getOwnerArray() {
		return ownerArray;
	}

}
