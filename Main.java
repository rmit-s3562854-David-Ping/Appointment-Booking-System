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

		int selection;
		String select;
		do {
			selection = 0;
			select = null;
			try {
				select = input.nextLine();
				selection = Integer.parseInt(select);
			} catch (Exception e) {

			}

			switch (selection) {
			case 1: {
				
				do {
					String username = "";
					String password = "";
					System.out.println("Please enter username: ");
					username = input.nextLine();
					System.out.println("Please enter password: ");
					password = input.nextLine();
					if (!owner.login(username, password) && !customer.login(username, password)) {
						System.out.println("Invalid login");
					}else{
						break;
					}
				} while (true);
				createMenu();
				break;
			}
			case 2: {
				customer.register();
				createMenu();
				break;
			}
			case 3: {
				// closes the program.
				System.out.println("System Closed");
				System.exit(0);
			}
			default:
				System.out.println("Invalid Input, please try again:");
				break;
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

	public void createOwnerMenu() {
		System.out.println("*************** Appointment Booking System ***************\n");
		System.out.println("Owner's Page\n");
		System.out.println("1.   Add employee");
		System.out.println("2.   Add employee working times");
		System.out.println("3.   Show employee availability times");
		System.out.println("4.   View booking summary");
		System.out.println("5.   View upcoming appointments");
		System.out.println("6.   Exit\n");
		System.out.println("**********************************************************");
	}

	public ArrayList<Customer> getCustomerArray() {
		return customerArray;
	}

	public ArrayList<Owner> getOwnerArray() {
		return ownerArray;
	}

}
