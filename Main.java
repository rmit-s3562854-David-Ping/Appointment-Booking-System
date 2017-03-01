package main;

import java.util.Scanner;

public class Main {
	
	private ArrayList<Customer> customerArray = new ArrayList<Customer>();
	private ArrayList<Owner> ownerArray = new ArrayList<Owner>();

	public static void main(String[] args) {
		// create input variable to record input from user.
		Scanner input = new Scanner(System.in);
		String username = "";
		String password = "";
		//function to create menu UI.
		createMenu();
		int selection = input.nextInt();
		if(selection ==1) {
			System.out.println("Please enter username: ");
			username = input.next();
			System.out.println("Please enter password: ");
			password = input.next();
			
		}
		if(selection == 0) {
			//closes the program.
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
	
	public ArrayList<Customer> getCustomerArray(){
		return customerArray;
	}
	
	public ArrayList<Owner> getOwnerArray(){
		return ownerArray;
	}
}
