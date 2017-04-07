package main;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Member {
	
	/**
	 * Customer class, attributes and behaviors of customers
	 *
	 * @version 1.00 05 Apr 2017
	 * @author David Ping, Hassan Mender, Luke Waldren
	 */

	public Customer() {
		super(null, null, null, null, null, null);
	}

	public Customer(String username, String password, String firstName, String lastName, String address,
			String contactNumber) {
		super(username, password, firstName, lastName, address, contactNumber);
	}
	
	/**
	 * Login function for customers, takes values username and password
	 * @author Luke Waldren, David Ping
	 */
	public Boolean login(String username, String password) {
		Main main = new Main();
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		if (checkLogin(username, password) == true) {
			System.out.println("Login Successful (Customer)");

			int selection;
			String select;
			do {
				main.createCustomerMenu();
				selection = 0;
				select = null;
				try {
					select = input.nextLine();
					selection = Integer.parseInt(select);
				} catch (Exception e) {
				}
				switch (selection) {
				case 1: {
					viewAppointmentTimes();
					break;
				}
				case 2: {
					return true;
				}
				default: {
					System.out.println("Invalid Input, please try again:");
					break;
				}
				}

			} while (selection != 2);

		} else {
			return false;
		}
		return false;
	}
	
	public Boolean checkLogin(String username, String password){
		Main main = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Customer> customerArray = main.getCustomerArray();

		int index = 0;
		while (index < customerArray.size()) {
			MembersSearch.add(customerArray.get(index).getUsername() + customerArray.get(index).getPassword());
			index++;
		}
		if (MembersSearch.contains(username + password)){
			return true;
		}
		return false;
	}

	/**
	 * Registration function for customers, asks for input and submits it
	 * get the customer's Reg. info
	 * then create a customer object and return it
	 * if any of the info is null the customer object is null
	 * and registration fails But if all info is provided
	 * registration success and new customer added
	 * @author Hassan Mender
	 */
	public Boolean register() {
		System.out.println("                      REGISTRATION");
		System.out.println("**********************************************************");
		System.out.println("Enter 'q','b','quit' to exit at anytime \n");
		Customer customer;
			  
		customer = getRegisterInformation();
		if (customer == null) {
			System.out.println("Registration Failed");
			return false;
		}
		addCustomer(customer.getUsername(), customer.getPassword(), customer.getFirstName(), customer.getLastname(),
				customer.getAddress(), customer.getContactNumber());
		System.out.println("Registration complete");
		return true;
	}

	/**
	 * 
	 * @author Hassan Mender
	 */
	public Customer getRegisterInformation() {
		Utility util = new Utility();
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		String username = null, password, firstName, lastName, address, contactNumber;
		Boolean valid = true;
		System.out.println("First name: ");
		firstName = keyboard.nextLine();

		if ((util.quitFunction(firstName)) == true) {
			valid = false;
			return null;
		}
		while (util.validateName(firstName) == false) {
			firstName = keyboard.nextLine();
			if (util.quitFunction(firstName)) {
				return null;
			}
		}
		
		System.out.println("Last name: ");
		lastName = keyboard.nextLine();
		if ((util.quitFunction(lastName)) == true) {
			valid = false;
			return null;
		}

		while (util.validateName(lastName) == false) {
			lastName = keyboard.nextLine();
			if (util.quitFunction(lastName)) {
				return null;
			}
		}
		System.out.println("Address: ");
		address = keyboard.nextLine();
		if ((util.quitFunction(address)) == true) {
			valid = false;
			return null;
		}

		while (util.validateAddress(address) == false) {
			address = keyboard.nextLine();
			if (util.quitFunction(address)) {
				return null;
			}
		}
		System.out.println("Contact Number: ");
		contactNumber = keyboard.nextLine();
		if ((util.quitFunction(contactNumber)) == true) {
			valid = false;
			return null;
		}
		while (util.validateContactNumber(contactNumber) == false) {
			contactNumber = keyboard.nextLine();
			if (util.quitFunction(contactNumber) == true) {
				return null;
			}
		}
		System.out.println("Username: ");
		username = keyboard.nextLine();
		if ((util.quitFunction(username)) == true) {
			valid = false;
			return null;
		}

		while ((util.validateLogin(username) == false) || (util.customerUsernameIsDuplicate(username) == true)) {
			username = keyboard.nextLine();
		}

		System.out.println("Password: ");
		password = keyboard.nextLine();
		if ((util.quitFunction(password)) == true) {
			valid = false;
			return null;
		}
		while (util.validateLogin(password) == false) {
			password = keyboard.nextLine();
			if (util.quitFunction(password)) {
				return null;
			}
		}

		Customer customer = makeObj(username, password, firstName, lastName, address, contactNumber, valid);
		return customer;

	}

	/**
	 * 
	 * @author Hassan Mender
	 */
	public Customer makeObj(String username, String password, String firstName, String lastName, String address,
			String contactNumber, Boolean valid) {

		if (valid == false) {
			return null;
		} else {
			Customer customer = new Customer(username, password, firstName, lastName, address, contactNumber);
			return customer;
		}
	}
	
	/**
	 * 
	 * @author Hassan Mender
	 */
	public Boolean addCustomer(String username, String password, String firstName, String lastName, String address,
			String contactNumber) {
		Main driver = new Main();

		Customer customer = new Customer(username, password, firstName, lastName, address, contactNumber);
		driver.getCustomerArray().add(customer);
		Writer writer = new Writer();
		try {
			writer.save(driver.getCustomerArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * View Appointment time function, allows customers to view times which are available for appointments
	 * @author David Ping
	 */
	public void viewAppointmentTimes() {
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		Business business = new Business();
		Utility util = new Utility();

		LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);
		LocalDateTime now= LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		boolean open;
		String dateAndDay, formattedTime;

		if (owner.getEmployeeArray().isEmpty()) {
			System.out.println("No employees working for this company");
			return;
		}
		if (currentTime.toLocalTime().compareTo(business.getClosingTime()) == 1) {
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
		
		while (currentTime.isBefore(now.plusWeeks(1))||currentTime.toLocalDate().equals(now.plusWeeks(1).toLocalDate())) {
			currentTime = currentTime.withHour(business.getOpeningTime().getHour());
			currentTime = currentTime.withMinute(business.getOpeningTime().getMinute());
			currentTime = adjustTimePresentDay(currentTime);
			open = util.validateDayOfWeek(currentTime);
			if (open == false) {
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
				continue;
			} else if (open == true) {
				dateAndDay = currentTime.format(formatter2);
				System.out.println("================\n"+dateAndDay+"\n================");
				while (currentTime.toLocalTime().compareTo(business.getClosingTime()) == -1
						|| currentTime.getHour() == 0) {
					if(validateAvailableTime(currentTime)==true){
						formattedTime = currentTime.format(formatter);
						System.out.println(formattedTime);
					}
					currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
				}
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
			}
		}
	}
	
	/**
	 * Extension of above function viewAppointmentTimes(), if currentTime is on
	 * the present day the available appointment times must be adjusted
	 * accordingly to the current hour
	 * @author David Ping
	 */
	public LocalDateTime adjustTimePresentDay(LocalDateTime currentTime) {
		Appointment appointment = new Appointment();
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		while (now.toLocalDate().equals(currentTime.toLocalDate()) && now.toLocalTime().isAfter(currentTime.toLocalTime())){
			if (now.isAfter(currentTime)) {
				currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
			}
		}
		return currentTime;
	}
	
	/**
	 * Extension of function viewAppointmentTimes(), will check if an
	 * employee is available at the time being passed in by ensuring 1. The
	 * employee does not currently have an appointment at that exact time and 2.
	 * whether or not the employee is working on that day
	 * @author David Ping
	 */
	public boolean validateAvailableTime(LocalDateTime currentTime) {
		Main main = new Main();
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		Boolean employeeAvailable = null;

		for (int j = 0; j < owner.getEmployeeArray().size(); j++) {
			employeeAvailable = true;
			for (int i = 0; i < main.getAppointmentArray().size(); i++) {
				if (main.getAppointmentArray().get(i).getEmployeeId().equals(owner.getEmployeeArray().get(j).getId())
						&& main.getAppointmentArray().get(i).getDateAndTime().equals(currentTime)) {
					employeeAvailable = false;
					break;
				}
			}
			if (employeeAvailable == false) {
				continue;
			}
			for (int k = 0; k < owner.getEmployeeArray().get(j).getStartTimes().size(); k++) {
				if (owner.getEmployeeArray().get(j).getStartTimes().get(k) == null) {
					continue;
				}
				if ((owner.getEmployeeArray().get(j).getStartTimes().get(k).equals(currentTime.toLocalTime()) || owner
						.getEmployeeArray().get(j).getStartTimes().get(k).isBefore(currentTime.toLocalTime()))) {
					if ((owner.getEmployeeArray().get(j).getEndTimes().get(k)
							.equals(currentTime.plusMinutes(appointment.getAppointmentDuration()).toLocalTime()))
							|| owner.getEmployeeArray().get(j).getEndTimes().get(k).isAfter(
									currentTime.plusMinutes(appointment.getAppointmentDuration()).toLocalTime())) {
						if (DayOfWeek.of(k + 1).equals(currentTime.getDayOfWeek())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public String toString() {
		return this.getUsername() + ":" + this.getPassword() + ":" + this.getFirstName() + ":" + this.getLastname()
				+ ":" + this.getAddress() + ":" + this.getContactNumber();
	}
}
