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

	public Boolean login(String username, String password) {
		Main driver = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Customer> customerArray = driver.getCustomerArray();

		int index = 0;
		while (index < customerArray.size()) {
			MembersSearch.add(customerArray.get(index).getUsername() + customerArray.get(index).getPassword());
			index++;
		}
		if (MembersSearch.contains(username + password)) {
			System.out.println("Login Successful (Customer)");
			// Put Customer menu here
			return true;
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

	public void viewAppointments() {
		Customer customer = new Customer();
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		Business business = new Business();

		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
		String formattedDateTime = currentTime.format(formatter);
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		String formattedDateTime2 = currentTime.format(formatter2);

		if (owner.getEmployeeArray().isEmpty()) {
			System.out.println("No employees working for this company");
			return;
		}

		if (currentTime.toLocalTime().compareTo(business.getClosingTime()) == 1) {
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}

		Boolean open = null;
		Boolean appointmentPrinted = null;//
		int counter = 0;

		while (counter < 7) {

			currentTime = currentTime.withHour(business.getOpeningTime().getHour());
			currentTime = currentTime.withMinute(business.getOpeningTime().getMinute());
			currentTime = currentTime.withSecond(0).withNano(0);

			while (now.getDayOfWeek().equals(currentTime.getDayOfWeek()) && now.getHour() >= currentTime.getHour()
					&& now.getDayOfYear() == currentTime.getDayOfYear()) {
				if (now.getHour() >= currentTime.getHour()) {
					currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
				}
			}

			open = null;
			for (int i = 0; i < business.getOpeningDays().length; i++) {
				if (i == business.getOpeningDays().length - 1
						&& !(business.getOpeningDays()[i].equals(currentTime.getDayOfWeek()))) {
					open = false;
				} else if (business.getOpeningDays()[i].equals(currentTime.getDayOfWeek())) {
					open = true;
					break;
				}
			}

			if (open == false) {
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
			} else if (open == true) {

				System.out.println("================");
				System.out.println(formattedDateTime2);
				System.out.println("================");

				while (currentTime.toLocalTime().compareTo(business.getClosingTime()) == -1
						|| currentTime.getHour() == 0) {
					if (!(customer.getAppointmentArray().isEmpty())) {
						for (int i = 0; i < customer.getAppointmentArray().size(); i++) {
							if (customer.getAppointmentArray().get(i).getDateAndTime().equals(currentTime)) {
								currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
								i = 0;
							}
						}
					}

					appointmentPrinted = false;

					for (int j = 0; j < owner.getEmployeeArray().size(); j++) {
						if (appointmentPrinted == true) {
							appointmentPrinted = false;
							break;
						}
						for (int k = 0; k < owner.getEmployeeArray().get(j).getStartTimes().size(); k++) {
							if ((owner.getEmployeeArray().get(j).getStartTimes().get(k).compareTo(currentTime) == 0
									|| owner.getEmployeeArray().get(j).getStartTimes().get(k)
											.compareTo(currentTime) == -1)
									&& ((owner.getEmployeeArray().get(j).getEndTimes().get(k).compareTo(
											currentTime.plusMinutes(appointment.getAppointmentDuration())) == 0)
											|| owner.getEmployeeArray().get(j).getEndTimes().get(k)
													.compareTo(currentTime
															.plusMinutes(appointment.getAppointmentDuration())) == 1)) {
								System.out.println(formattedDateTime);
								appointmentPrinted = true;
							}
						}
					}
					currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
				}
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
			}
			counter++;
		}
	}

	public ArrayList<Appointment> getAppointmentArray() {
		return appointmentArray;
	}
}
