package task1;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	private ArrayList<Customer> customerArray = new ArrayList<Customer>();
	private ArrayList<Owner> ownerArray = new ArrayList<Owner>();
	
	static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) {

		System.out.println("==============================");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. Exit");
		System.out.println("==============================");
		System.out.println("Please select: ");
		
		
		String option = keyboard.nextLine();

		


	}

	public ArrayList<Customer> getCustomerArray(){
		return customerArray;
	}
	
	public ArrayList<Owner> getOwnerArray(){
		return ownerArray;
	}
}
