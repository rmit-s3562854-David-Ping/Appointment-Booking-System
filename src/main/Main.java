package main;

import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

	private static ArrayList<Customer> customerArray = new ArrayList<Customer>();
	private static ArrayList<Owner> ownerArray = new ArrayList<Owner>();
	private static ArrayList<Appointment> appointmentArray = new ArrayList<Appointment>();
	private static final Logger LOGGER = Logger.getLogger("MyLog");
	
	public static void main(String[] args) {
		Reader reader = new Reader();
		Customer customer = new Customer();
		Owner owner = new Owner();
		Utility util = new Utility();

		Scanner input = new Scanner(System.in);
		//Setup the logger
		myLogger logger = new myLogger();
		try {
			myLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can not create log files");
		}
		LOGGER.info("Program started.");
		reader.read();





		//Temporary Appointment Objects
		LocalDateTime dateAndTime = LocalDateTime.of(2017, 4, 3, 11, 0);
		LocalDateTime dateAndTime2 = LocalDateTime.of(2017, 4, 5, 8, 0);
		LocalDateTime dateAndTime3 = LocalDateTime.of(2017, 3, 29, 9, 0);
		LocalDateTime dateAndTime4 = LocalDateTime.of(2017, 3, 9, 11, 0);
		LocalDateTime dateAndTime5 = LocalDateTime.of(2017, 3, 25, 8, 0);
		LocalDateTime dateAndTime6 = LocalDateTime.of(2017, 3, 9, 9, 0);
		Appointment newAppointment = new Appointment(dateAndTime,customerArray.get(0).getUsername(),owner.getEmployeeArray().get(0).getId());
		Appointment newAppointment2 = new Appointment(dateAndTime2,customerArray.get(0).getUsername(),owner.getEmployeeArray().get(0).getId());
		Appointment newAppointment3 = new Appointment(dateAndTime3,customerArray.get(1).getUsername(),owner.getEmployeeArray().get(0).getId());
		Appointment newAppointment4 = new Appointment(dateAndTime4,customerArray.get(0).getUsername(),owner.getEmployeeArray().get(0).getId());
		Appointment newAppointment5 = new Appointment(dateAndTime5,customerArray.get(1).getUsername(),owner.getEmployeeArray().get(0).getId());
		Appointment newAppointment6 = new Appointment(dateAndTime6,customerArray.get(0).getUsername(),owner.getEmployeeArray().get(0).getId());
		appointmentArray.add(newAppointment);
		appointmentArray.add(newAppointment2);
		appointmentArray.add(newAppointment3);
		appointmentArray.add(newAppointment4);
		appointmentArray.add(newAppointment5);
		appointmentArray.add(newAppointment6);
		//Delete when reader/writer is done for appointments

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
				LOGGER.log( Level.SEVERE, e.toString(), e );
			}

			switch (selection) {
			case 1: {

				do {
					String username = "";
					String password = "";
					System.out.println("Please enter username: ");
					username = input.nextLine();
					if((util.quitFunction(username))== true)
					{
						break;
					}
					System.out.println("Please enter password: ");
					password = input.nextLine();
					if((util.quitFunction(username))== true)
					{
						break;
					}
					if (!owner.login(username, password) && !customer.login(username, password)) {
						System.out.println("Invalid login");
					} else {
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
				util.exit();
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
		LOGGER.info("Menu created.");

	}

	public void createOwnerMenu() {
		System.out.println("*************** Appointment Booking System ***************\n");
		System.out.println("Owner's Page\n");
		System.out.println("1.   Add employee");
		System.out.println("2.   Delete employee");
		System.out.println("3.   Add employee working times");
		System.out.println("4.   Show employee availability times");
		System.out.println("5.   View booking summary");
		System.out.println("6.   View upcoming appointments");
		System.out.println("7.   Exit\n");
		System.out.println("**********************************************************");
		LOGGER.info("Owner menu created.");
	}
	
	public void createCustomerMenu() {
		System.out.println("*************** Appointment Booking System ***************\n");
		System.out.println("Customer's Page\n");
		System.out.println("1.   View Appointment Times");
		System.out.println("2.   Exit\n");
		System.out.println("**********************************************************");
		LOGGER.info("Customer menu created.");
	}

	public ArrayList<Customer> getCustomerArray() {
		return customerArray;
	}

	public ArrayList<Owner> getOwnerArray() {
		return ownerArray;
	}

	public ArrayList<Appointment> getAppointmentArray() {
		return appointmentArray;
	}
}
