package main;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Owner extends Member {

	/**
	 * Owner class, contains attributes and behaviors of owner
	 * 
	 * @version 1.00 05 Apr 2017
	 * @author David Ping, Hassan Mender, Luke Waldren
	 */

	private static ArrayList<Employee> employeeArray = new ArrayList<Employee>();
	private static final Logger LOGGER = Logger.getLogger("MyLog");
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

	/**
	 * get the new employee's info
	 * then create an employee object and return it
	 * if any of the info is null the employee object is null
	 * and creation fails But if all info is provided
	 * employee created successfully
	 * @author Hassan Mender
	 */
	public Boolean createEmployee() {

		System.out.println("                     Adding New Employee");
		System.out.println("**********************************************************");
		System.out.println("Enter 'q','b','quit' to exit at anytime ");
		System.out.println("");

		Employee newEmployee;
		Writer writer = new Writer();

		newEmployee = getEmployeeInfo();
		if (newEmployee == null) {
			System.out.println("Adding new employee failed");
			return false;
		}
		addEmployee(newEmployee);

		System.out.println("Employee " + newEmployee.getFirstName() + " " + newEmployee.getLastName() + " added.");
		
		try {
			writer.saveEmployees(getEmployeeArray());
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		return true;
	}

	/**
	 * 
	 * @author Hassan Mender
	 */
	public Boolean addEmployee(Employee employee) {
		if (employee == null) {
			return false;
		}
		getEmployeeArray().add(employee);
		LOGGER.info("Employee Added.");
		
		return true;
	}

	/**
	 * 
	 * @author Hassan Mender, Luke Waldren
	 */
	public Employee getEmployeeInfo() {
		// Add error checking make sure id is unique

		Utility util = new Utility();
		String id = util.createID();
		System.out.println("Employee id: " + id);
		System.out.println("Employee First Name: ");
		String firstName = input.nextLine();
		if (util.quitFunction(firstName)) {
			return null;
		}
		while (util.validateName(firstName) == false) {
			firstName = input.nextLine();
			if (util.quitFunction(firstName)) {
				return null;
			}
		}

		System.out.println("Employee Last Name: ");
		String lastName = input.nextLine();
		if (util.quitFunction(lastName)) {
			return null;
		}
		while (util.validateName(lastName) == false) {
			lastName = input.nextLine();
			if (util.quitFunction(lastName)) {
				return null;
			}
		}

		Employee employee = makeEmployeeObj(firstName, lastName, id);
		LOGGER.info("Employee information returned");
		return employee;
	}

	/**
	 * times are null because they are added in a different method
	 * new employees dont have any work times assigned
	 * @author Hassan Mender
	 */
	public Employee makeEmployeeObj(String firstName, String lastName, String id) {
		Utility util = new Utility();
		ArrayList<LocalTime> startTimes = new ArrayList<LocalTime>();
		ArrayList<LocalTime> endTimes = new ArrayList<LocalTime>();
		for(int i=0;i<7;i++){
			startTimes.add(null);
			endTimes.add(null);
		}
		if ((util.checkString(firstName) == false) || (util.checkString(lastName) == false)
				|| (util.checkString(id) == false)) {
			return null;
		} else {
			Employee newEmployee = new Employee(firstName, lastName, id, startTimes, endTimes);
			return newEmployee;
		}
	}

	/**
	 * prompt the owner with employee Id 
	 * If employee exists in the System confirm request
	 * if confirmed remove employee from system and from file
	 * @author Hassan Mender
	 */
	public Boolean deleteEmployee() {
		Utility util = new Utility();
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		String employeeId;
		String sure;
		Writer writer = new Writer();
		Owner owner = new Owner();
		System.out.println("               Delete Employee");
		System.out.println("************************************************");

		int j = 0;
		while (j < owner.getEmployeeArray().size()) {
			System.out.print(owner.getEmployeeArray().get(j).getId() + " | ");
			System.out.print(owner.getEmployeeArray().get(j).getFirstName() + " ");
			System.out.println(owner.getEmployeeArray().get(j).getLastName() + " ");
			j++;
		}
		System.out.println("");
		System.out.println("Select an employee, input Id: ");
		do {
			employeeId = keyboard.nextLine();
			if (util.quitFunction(employeeId)) {
				return false;
			}
		} while (util.validateEmployeeId(employeeId) == false);

		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			if (owner.getEmployeeArray().get(i).getId().equals(employeeId)) {
				System.out.println("are you sure you want to delete " + getEmployeeArray().get(i).getId() + " Y - N");
				sure = keyboard.nextLine();
				if ((sure.equals("Y")) || (sure.equals("y"))) {
					System.out.println("Employee " + owner.getEmployeeArray().get(i).getFirstName() + " "
							+ owner.getEmployeeArray().get(i).getLastName() + " deleted successfully");
					owner.getEmployeeArray().remove(i);
					LOGGER.info("Employee Deleted");
				} else {
					return false;
				}
			}
		}
		try {
			writer.saveEmployees(getEmployeeArray());
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		return true;
	}

	/**
	 * Function for owner to add working times to employees, first specify which
	 * employee from the list using the id then picking a day and finally adding
	 * the times
	 * 
	 * @author David Ping
	 */
	public void addWorkingTimes() {
		Owner owner = new Owner();
		Utility util = new Utility();
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		String employeeId, newTime, newDay;
		DateTimeFormatter timeFormatter;
		LocalTime startTime, endTime;
		int counter = 0;
		int dayOfWeek = 0;

		System.out.println("Add employee work time");
		System.out.println("************************************************1");
		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			System.out.println(
					owner.getEmployeeArray().get(i).getId() + " | " + owner.getEmployeeArray().get(i).getFirstName()
							+ " " + owner.getEmployeeArray().get(i).getLastName());
		}
		System.out.println("\nSelect an employee, input Id: ");
		do {
			employeeId = keyboard.nextLine();
			if (util.quitFunction(employeeId)) {
				return;
			}
		} while (util.validateEmployeeId(employeeId) == false);
		printDaysOfWeek();
		System.out.println("Select day of the week to add working times:");
		System.out.println("(Previous working times on this day will be overwritten)");
		do {
			newDay = keyboard.nextLine();
			if (util.quitFunction(newDay)) {
				return;
			}
			try {
				dayOfWeek = Integer.parseInt(newDay);
			} catch (Exception e) {
				System.err.println("Not a valid input");
				continue;
			}
		} while (util.validateDayOfWeek(dayOfWeek) == false);

		System.out.println("Type in start time (hours:minutes)");
		do {
			newTime = keyboard.nextLine();
			if (util.quitFunction(newTime)) {
				return;
			}
		} while (util.validateTime(newTime) == false);
		timeFormatter = util.assignTimeFormat(newTime);
		startTime = LocalTime.parse(newTime, timeFormatter);

		System.out.println("Type in end time (hours:minutes)");
		do {
			newTime = keyboard.nextLine();
			if (util.quitFunction(newTime)) {
				return;
			}
		} while (util.validateTime(newTime) == false);
		timeFormatter = util.assignTimeFormat(newTime);
		endTime = LocalTime.parse(newTime, timeFormatter);

		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			if (owner.getEmployeeArray().get(i).getId().equals(employeeId)) {
				counter = i;
				break;
			}
		}

		if (util.validateNewWorkTime(startTime, endTime)) {
			owner.getEmployeeArray().get(counter).getStartTimes().set(dayOfWeek - 1, startTime);
			owner.getEmployeeArray().get(counter).getEndTimes().set(dayOfWeek - 1, endTime);
			System.out.println("New work hours added for " + employeeId);
		} else {
			System.out.println("New work hours were not added");
		}
		Writer writer = new Writer();
		try {
			writer.saveEmployees(owner.getEmployeeArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Prints days of the week where the business is open along with the days
	 * corresponding number
	 * 
	 * @author David Ping
	 */
	public void printDaysOfWeek() {
		Business business = new Business();
		for (int i = 1; i <= 7; i++) {
			for (int j = 0; j < business.getOpeningDays().length; j++) {
				if (business.getOpeningDays()[j].equals(DayOfWeek.of(i))) {
					System.out.println(i + ". " + DayOfWeek.of(i));
				}
			}
		}
		return;
	}

	/**
	 * Shows the employees availability any particular week, this will print out
	 * employee details followed by the day and times when they are available
	 * 
	 * @author Luke Waldren, David Ping
	 */
	public void showAllWorkerAvailability() {
		Owner owner = new Owner();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		boolean available = false;

		System.out.println("View employee availability");
		System.out.println("**********************************************************");

		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			if (!(i == 0)) {
				System.out.println("----------------------------------------------------------");
			}
			System.out.println("Name :" + owner.getEmployeeArray().get(i).getFirstName() + " "
					+ owner.getEmployeeArray().get(i).getLastName());
			System.out.println("ID: " + owner.getEmployeeArray().get(i).getId());

			available = false;
			for (int j = 0; j < owner.getEmployeeArray().get(i).getStartTimes().size(); j++) {

				if (!(owner.getEmployeeArray().get(i).getStartTimes().get(j) == null)) {
					if (available == false) {
						System.out.println("Is available on:");
						available = true;
					}
					System.out.println(DayOfWeek.of(j + 1) + " "
							+ owner.getEmployeeArray().get(i).getStartTimes().get(j).format(timeFormat) + "-"
							+ owner.getEmployeeArray().get(i).getEndTimes().get(j).format(timeFormat));
				}
			}
			if (available == false) {
				System.out.println("Has no work times yet");
			}
		}
	}

	/**
	 * Prints out all the bookings made in the past month along with its details
	 * of the booking, the employee conducting the appointment, the customer
	 * attending and the date and time
	 * 
	 * @author David Ping
	 */
	public void viewBookingSummary() {
		Utility util = new Utility();
		Appointment appointment = new Appointment();
		String formattedTime, formattedTimePlusDuration, dateAndDay;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		List<Appointment> sortedList = new ArrayList<Appointment>();

		sortedList = util.sortPastAppointments();

		if (sortedList.isEmpty()) {
			System.out.println("No appointments in the past month");
			return;
		}
		System.out.println("Booking Summary");
		System.out.println("**********************************************************");

		for (int i = 0; i < sortedList.size(); i++) {
			if (validateBookingSummaryDate(sortedList.get(i).getDateAndTime().toLocalDate()) == true) {
				dateAndDay = sortedList.get(i).getDateAndTime().format(dateAndDayFormat);
				formattedTime = sortedList.get(i).getDateAndTime().format(timeFormat);
				formattedTimePlusDuration = sortedList.get(i).getDateAndTime()
						.plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
				printAppointmentDetails(i, sortedList);
				System.out.println(
						"Appointment time: " + dateAndDay + " " + formattedTime + "-" + formattedTimePlusDuration);
				System.out.println("----------------------------------------------------------");
			}
		}

		System.out.println("\n**********************************************************\n");
		return;
	}

	/**
	 * Extension of viewBookingSummary, checks the date input to ensure it is
	 * within the time frame between now and 1 month ago
	 * 
	 * @author David Ping
	 */
	public boolean validateBookingSummaryDate(LocalDate date) {
		LocalDateTime now = LocalDateTime.now();
		if (date.isAfter(now.minusMonths(1).toLocalDate()) && date.isBefore(now.toLocalDate())
				|| date.equals(now.minusMonths(1).toLocalDate())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Prints out all bookings coming up from the current day to 1 week ahead
	 * 
	 * @author David Ping
	 */
	public void viewUpcomingBookings() {
		Utility util = new Utility();
		Appointment appointment = new Appointment();
		String formattedTime, formattedTimePlusDuration, dateAndDay;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		List<Appointment> sortedList = new ArrayList<Appointment>();

		sortedList = util.sortFutureAppointments();

		if (sortedList.isEmpty()) {
			System.out.println("No appointments in the next week");
			return;
		}
		System.out.println("Upcoming Appointments");
		System.out.println("**********************************************************");

		for (int i = 0; i < sortedList.size(); i++) {
			if (validateUpcomingBookingDate(sortedList.get(i).getDateAndTime().toLocalDate()) == true) {
				dateAndDay = sortedList.get(i).getDateAndTime().format(dateAndDayFormat);
				formattedTime = sortedList.get(i).getDateAndTime().format(timeFormat);
				formattedTimePlusDuration = sortedList.get(i).getDateAndTime()
						.plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
				printAppointmentDetails(i, sortedList);
				System.out.println(
						"Appointment time: " + dateAndDay + " " + formattedTime + "-" + formattedTimePlusDuration);
				System.out.println("----------------------------------------------------------");
			}
		}

		System.out.println("\n**********************************************************\n");
		LOGGER.info("");
		return;
	}

	/**
	 * Extension of viewUpcomingBookings(), checks the date input to ensure it
	 * is within the time frame between now and 1 week ahead
	 * 
	 * @author David Ping
	 */
	public boolean validateUpcomingBookingDate(LocalDate date) {
		LocalDateTime now = LocalDateTime.now();
		if (date.isBefore(now.plusWeeks(1).toLocalDate()) && date.isAfter(now.toLocalDate())
				|| date.equals(now.toLocalDate().plusWeeks(1)) || date.equals(now.toLocalDate())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Extension of viewBookingSummary() and viewUpcomingBookings(), used to
	 * print out details of the appointments
	 * 
	 * @author David Ping
	 */
	public void printAppointmentDetails(int counter, List<Appointment> arrayList) {
		Main main = new Main();
		Owner owner = new Owner();

		for (int j = 0; j < main.getCustomerArray().size(); j++) {
			if (main.getCustomerArray().get(j).getUsername().equals(arrayList.get(counter).getCustomerUsername())) {
				System.out.println("Customer Name: " + main.getCustomerArray().get(j).getFirstName() + " "
						+ main.getCustomerArray().get(j).getLastname());
				System.out.println("Address: " + main.getCustomerArray().get(j).getAddress());
				System.out.println("Contact Number: " + main.getCustomerArray().get(j).getContactNumber());
			}
		}
		for (int k = 0; k < owner.getEmployeeArray().size(); k++) {
			if (owner.getEmployeeArray().get(k).getId().equals(arrayList.get(counter).getEmployeeId())) {
				System.out.println("Employee Name: " + owner.getEmployeeArray().get(k).getFirstName() + " "
						+ owner.getEmployeeArray().get(k).getLastName());
				System.out.println("Employee ID: " + owner.getEmployeeArray().get(k).getId());
			}
		}
	}

	/**
	 * Login function for owner, after logging in successfully owner is given a
	 * menu with many options for tasks to do
	 * @author Luke Waldren
	 */
	public Boolean login(String username, String password) {
		Main main = new Main();
		
		if (checkLogin(username, password) == true) {
			System.out.println("Login Successful (Owner)");
			LOGGER.info("Owner logged in");
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
					LOGGER.log(Level.SEVERE, e.toString(), e);
				}
				switch (selection) {
				case 1: {
					createEmployee();
					break;
				}

				case 2: {
					deleteEmployee();
					break;
				}
				case 3: {
					addWorkingTimes();
					break;
				}
				case 4: {
					showAllWorkerAvailability();
					break;
				}
				case 5: {
					viewBookingSummary();
					break;
				}
				case 6: {
					viewUpcomingBookings();
					break;
				}
				case 7: {
					return true;
				}
				default: {
					System.out.println("Invalid Input, please try again:");
					break;
				}
				}

			} while (selection != 7);
		} else {
			return false;
		}
		return true;
	}

	public ArrayList<Employee> getEmployeeArray() {
		return employeeArray;
	}
	
	public Boolean checkLogin(String username, String password){
		Main main = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Owner> ownerArray = main.getOwnerArray();

		int index = 0;
		while (index < ownerArray.size()) {
			MembersSearch.add(ownerArray.get(index).getUsername() + ownerArray.get(index).getPassword());
			index++;
		}
		if (MembersSearch.contains(username + password)){
			return true;
		}
		return false;
	}

}
