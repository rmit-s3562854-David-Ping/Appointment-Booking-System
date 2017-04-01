package main;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Member {

	public Customer() {
		super(null, null, null, null, null, null);
	}

	public Customer(String username, String password, String firstName, String lastName, String address,
			String contactNumber) {
		super(username, password, firstName, lastName, address, contactNumber);
	}

	public Boolean login(String username, String password) {
		Main main = new Main();
		Scanner input = new Scanner(System.in);
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Customer> customerArray = main.getCustomerArray();

		int index = 0;
		while (index < customerArray.size()) {
			MembersSearch.add(customerArray.get(index).getUsername() + customerArray.get(index).getPassword());
			index++;
		}
		if (MembersSearch.contains(username + password)) {
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
		return true;
	}

	public Boolean register() {
		System.out.println("                      REGISTRATION");
		System.out.println("**********************************************************");
		System.out.println("Enter 'q','b','quit' to exit at anytime ");
		System.out.println("");
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

	public Customer getRegisterInformation() {

		Utility util = new Utility();
		Main driver = new Main();
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
		boolean validUsername;

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

	public Customer makeObj(String username, String password, String firstName, String lastName, String address,
			String contactNumber, Boolean valid) {

		if (valid == false) {
			return null;
		} else {
			Customer customer = new Customer(username, password, firstName, lastName, address, contactNumber);
			return customer;
		}
	}

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

	public void viewAppointmentTimes() {
		Owner owner = new Owner();
		Customer customer = new Customer();
		Appointment appointment = new Appointment();
		Business business = new Business();
		Utility util = new Utility();

		LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		boolean open;
		String dateAndDay;
		int counter = 0;

		if (owner.getEmployeeArray().isEmpty()) {
			System.out.println("No employees working for this company");
			return;
		}
		if (currentTime.toLocalTime().compareTo(business.getClosingTime()) == 1) {
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
		while (counter < 7) {
			currentTime = currentTime.withHour(business.getOpeningTime().getHour());
			currentTime = currentTime.withMinute(business.getOpeningTime().getMinute());
			util.adjustTimePresentDay(currentTime);
			open = util.validateDayOfWeek(currentTime);
			if (open == false) {
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
				continue;
			} else if (open == true) {
				dateAndDay = currentTime.format(formatter2);
				System.out.println("================");
				System.out.println(dateAndDay);
				System.out.println("================");
				while (currentTime.toLocalTime().compareTo(business.getClosingTime()) == -1
						|| currentTime.getHour() == 0) {
					util.printAvailableAppointment(currentTime);

					currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
				}
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
			}
			counter++;
		}
	}

	public String toString() {
		return this.getUsername() + ":" + this.getPassword() + ":" + this.getFirstName() + ":" + this.getLastname()
				+ ":" + this.getAddress() + ":" + this.getContactNumber();
	}
}
