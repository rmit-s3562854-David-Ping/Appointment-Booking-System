package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Owner extends Member {

	private static ArrayList<Employee> employeeArray = new ArrayList<Employee>();

	private String businessName;
	Scanner input = new Scanner(System.in);

	public Owner() {
		super(null, null, null, null, null, null);
	}

	public Owner(String username, String password, String firstName, String lastName, String address,
			String contactNumber, String businessName) {
		super(username, password, firstName, lastName, address, contactNumber);
		this.businessName = businessName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void createEmployee() {
		// Add error checking make sure id is unique
		System.out.println("Employee ID: ");
		String id = input.nextLine();
		System.out.println("Employee First Name: ");
		String firstName = input.nextLine();
		System.out.println("Employee Last Name: ");
		String lastName = input.nextLine();

		Employee newEmployee = new Employee(firstName, lastName, id);

		getEmployeeArray().add(newEmployee);

		System.out.println("Employee " + firstName + " " + lastName + " added.");
		return;
	}

	public void addWorkingTimes() {
	}

	public void showAllWorkerAvailability() {
	}

	public void viewBookingSummary() {
		Main main = new Main();
		Customer customer = new Customer();
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		Business business = new Business();

		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		String time, time2;
		String dateAndDay;
		int counter = customer.getAppointmentArray().size();
		Boolean open = null;

		System.out.println("Booking Summary");

		if (currentTime.toLocalTime().compareTo(business.getClosingTime()) > 0) {
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}

		while (counter > 0) {
			// Go through each day from start to close time
			// Every time an appointment is booked counter = counter - 1;
			// will keep going through each day and all working hours until
			// counter reaches 0

			currentTime = currentTime.withHour(business.getOpeningTime().getHour());// Set
																					// clock
																					// to
																					// opening
																					// Time
																					// hour
			currentTime = currentTime.withMinute(business.getOpeningTime().getMinute());// Set
																						// the
																						// minutes
																						// to
																						// opening
																						// time
																						// minutes
			currentTime = currentTime.withSecond(0).withNano(0);// Get rid of
																// seconds and
																// nano seconds
																// for coding
																// purposes

			// Only needs to be run if currentTime.day is current day and is a
			// valid day
			while (now.getDayOfWeek().equals(currentTime.getDayOfWeek()) && now.getHour() >= currentTime.getHour()
					&& now.getDayOfYear() == currentTime.getDayOfYear()) {
				// While current day
				if (now.getHour() >= currentTime.getHour()) {
					// if now hour is currentTimes hour
					// this means that it is too late to make an appointment at
					// this time today
					currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
					// Move the currentTime forward 1 appointment duration, it
					// is now valid and will exit the while loop
				}
			}
			open = null;

			// This for loop checks whether the business is open on the current
			// day
			for (int i = 0; i < business.getOpeningDays().length; i++) {
				if (i == business.getOpeningDays().length - 1
						&& !(business.getOpeningDays()[i].equals(currentTime.getDayOfWeek()))) {
					// Not open today
					open = false;
				} else if (business.getOpeningDays()[i].equals(currentTime.getDayOfWeek())) {
					// Open today
					open = true;
					break;
				}
			}

			if (open == false) {
				// If the business is not open today move to the next day
				// 24-current hour
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
			} else if (open == true) {
				dateAndDay = currentTime.format(dateAndDayFormat);
				System.out.println("==================================================");
				System.out.println(dateAndDay);
				System.out.println("==================================================");
			}

			// While the current time is before the closing time and not at
			// 12:00 am (because 12:00am is a new day)
			while (currentTime.toLocalTime().compareTo(business.getClosingTime()) == -1 || currentTime.getHour() == 0) {

				if (!(customer.getAppointmentArray().isEmpty())) {
					// If appointments have been made check if the time is same
					// as current time
					for (int i = 0; i < customer.getAppointmentArray().size(); i++) {
						if (customer.getAppointmentArray().get(i).getDateAndTime().equals(currentTime)) {

							time = currentTime.format(timeFormat);
							time2 = currentTime.plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
							System.out.println("--------------------------------------------------");
							System.out.println(time + "-" + time2);

							// for all customers in the customer array check if
							// it is the same customer which is assigned the
							// current appointment
							for (int j = 0; j < main.getCustomerArray().size(); j++) {
								if (main.getCustomerArray().get(j).getUsername()
										.equals(customer.getAppointmentArray().get(i).getCustomerUsername())) {
									System.out.print("Customer Name: ");
									System.out.print(main.getCustomerArray().get(j).getFirstName());
									System.out.print(" ");
									System.out.println(main.getCustomerArray().get(j).getLastname());
									System.out.print("Address: ");
									System.out.println(main.getCustomerArray().get(j).getAddress());
									System.out.print("Contact Number: ");
									System.out.println(main.getCustomerArray().get(j).getContactNumber());
									break;
								}
							}

							// for all employees in the employee array check if
							// the same employee is assigned to the current
							// appointment
							for (int k = 0; k < owner.getEmployeeArray().size(); k++) {
								if (owner.getEmployeeArray().get(k).getId()
										.equals(customer.getAppointmentArray().get(i).getEmployeeId())) {
									System.out.print("Employee Name: ");
									System.out.print(owner.getEmployeeArray().get(k).getFirstName());
									System.out.print(" ");
									System.out.println(owner.getEmployeeArray().get(k).getLastName());
									System.out.print("Employee ID: ");
									System.out.println(owner.getEmployeeArray().get(k).getId());
									break;
								}
							}
							System.out.println("--------------------------------------------------");
							counter--;
							currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
							i = 0;// Make i 0 again to check the new time
									// against all the arrays again
						}
					}
				}
				currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
			}
			currentTime = currentTime.plusHours(24 - currentTime.getHour());

		}
	}

	public void viewUpcomingBookings() {
		Main main = new Main();
		Customer customer = new Customer();
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		Business business = new Business();

		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		String time, time2;
		String dateAndDay;

		System.out.println("Bookings over the next week");

		currentTime = currentTime.withSecond(0).withNano(0);
		now = now.withSecond(0).withNano(0);

		if (currentTime.toLocalTime().compareTo(business.getClosingTime()) > 0) {
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}

		while (currentTime.compareTo(now.plusDays(7)) < 0) {
			dateAndDay = currentTime.format(dateAndDayFormat);
			System.out.println("==================================================");
			System.out.println(dateAndDay);
			System.out.println("==================================================");

			for (int i = 0; i < customer.getAppointmentArray().size(); i++) {
				if (currentTime
						.getDayOfYear() == (customer.getAppointmentArray().get(i).getDateAndTime().getDayOfYear())) {
					time = customer.getAppointmentArray().get(i).getDateAndTime().format(timeFormat);
					time2 = customer.getAppointmentArray().get(i).getDateAndTime()
							.plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
					System.out.println("--------------------------------------------------");
					System.out.println(time + "-" + time2);

					// for all customers in the customer array check if it is
					// the same customer which is assigned the current
					// appointment
					for (int j = 0; j < main.getCustomerArray().size(); j++) {
						if (main.getCustomerArray().get(j).getUsername()
								.equals(customer.getAppointmentArray().get(i).getCustomerUsername())) {
							System.out.print("Customer Name: ");
							System.out.print(main.getCustomerArray().get(j).getFirstName());
							System.out.print(" ");
							System.out.println(main.getCustomerArray().get(j).getLastname());
							break;
						}
					}

					// for all employees in the employee array check if the same
					// employee is assigned to the current appointment
					for (int k = 0; k < owner.getEmployeeArray().size(); k++) {
						if (owner.getEmployeeArray().get(k).getId()
								.equals(customer.getAppointmentArray().get(i).getEmployeeId())) {
							System.out.print("Employee Name: ");
							System.out.print(owner.getEmployeeArray().get(k).getFirstName());
							System.out.print(" ");
							System.out.println(owner.getEmployeeArray().get(k).getLastName());
							System.out.print("Employee ID: ");
							System.out.println(owner.getEmployeeArray().get(k).getId());
						}
					}
					System.out.println("--------------------------------------------------");

				}
			}
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
	}

	public Boolean login(String username, String password) {
		Main main = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Owner> ownerArray = main.getOwnerArray();

		int index = 0;
		while (index < ownerArray.size()) {
			MembersSearch.add(ownerArray.get(index).getUsername() + ownerArray.get(index).getPassword());
			index++;
		}
		if (MembersSearch.contains(username + password)) {
			System.out.println("Login Successful (Owner)");

			int selection;
			String select;
			do {
				main.createOwnerMenu();
				selection = 0;
				select = null;
				try {
					select = input.nextLine();
					selection = Integer.parseInt(select);
				} catch (Exception e) {
					
				}
				switch (selection) {
				case 1: {
					createEmployee();
					break;
				}
				case 2: {
					addWorkingTimes();
					break;
				}
				case 3: {
					showAllWorkerAvailability();
					break;
				}
				case 4: {
					viewBookingSummary();
					break;
				}
				case 5: {
					viewUpcomingBookings();
					break;
				}
				case 6: {
					return true;
				}
				default: {
					System.out.println("Invalid Input, please try again:");
					break;
				}
				}
				
			} while (selection != 6);
		} else {
			return false;
		}
		return true;
	}

	public ArrayList<Employee> getEmployeeArray() {
		return employeeArray;
	}

}
