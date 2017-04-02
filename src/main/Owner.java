package main;

import java.io.IOException;
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

	public Boolean createEmployee() {

		System.out.println("                     Adding New Employee");
		System.out.println("**********************************************************");
		System.out.println("Enter 'q','b','quit' to exit at anytime ");
		System.out.println("");

		Employee newEmployee;

		newEmployee = getEmployeeInfo();
		if (newEmployee == null) {
			System.out.println("Adding new emplyee failed");
			return false;
		}
		addEmployee(newEmployee);
		// getEmployeeArray().add(newEmployee);
		System.out.println("Employee " + newEmployee.getFirstName() + " " + newEmployee.getLastName() + " added.");

		return true;
	}

	public Boolean addEmployee(Employee employee) {
		Writer writer = new Writer();
		if (employee == null) {
			return false;
		}
		getEmployeeArray().add(employee);
		LOGGER.info("Employee Added.");
		try {
			writer.saveEmployees(getEmployeeArray());
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log( Level.SEVERE, e.toString(), e );
		}
		return true;
	}

	public Employee getEmployeeInfo() {
		// Add error checking make sure id is unique

		Utility util = new Utility();
		String id = util.createID();
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

	public Employee makeEmployeeObj(String firstName, String lastName, String id) {
		Utility util = new Utility();
		if ((util.checkString(firstName) == false) || (util.checkString(lastName) == false)
				|| (util.checkString(id) == false)) {
			return null;
		} else {
			Employee newEmployee = new Employee(firstName, lastName, id, null, null);
			return newEmployee;
		}
	}

	public Boolean deleteEmployee() {

		Utility util = new Utility();
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
			LOGGER.log( Level.SEVERE, e.toString(), e );
		}
		return true;
	}

	public void addWorkingTimes() {
		Owner owner = new Owner();
		Utility util = new Utility();
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		String employeeId, newDate, newTime;
		DateTimeFormatter timeFormatter;
		LocalDate date;
		LocalTime startTime, endTime;
		LocalDateTime startDateTime, endDateTime;
		boolean validEntry;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");

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

		System.out.println("Type in new work date (day/month/year)");
		do {
			newDate = keyboard.nextLine();
			if (util.quitFunction(newDate)) {
				return;
			}
		} while (util.validateDate(newDate) == false);
		date = LocalDate.parse(newDate, dateFormat);

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

		startDateTime = LocalDateTime.of(date, startTime);
		endDateTime = LocalDateTime.of(date, endTime);

		if (util.validateDayOfWeek(startDateTime) == false) {
			System.out.println("Invalid day of the week");
			return;
		}

		validEntry = util.validateNewWorkTime(employeeId, startDateTime, endDateTime);
		if (validEntry == true) {
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

	public void showAllWorkerAvailability() {
		Owner owner = new Owner();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		LocalDateTime now = LocalDateTime.now();
		boolean printed = false;
		boolean validEntry = false;
		System.out.println("View employee availability");
		System.out.println("**********************************************************");
		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			validEntry = false;
			for (int j = 0; j < owner.getEmployeeArray().get(i).getStartTimes().size(); j++) {
				if (owner.getEmployeeArray().get(i).getStartTimes().get(j).isBefore(now.plusDays(7))
						&& owner.getEmployeeArray().get(i).getStartTimes().get(j).isAfter(now.plusHours(1))) {
					if (printed == false) {
						System.out.println("Name :" + owner.getEmployeeArray().get(i).getFirstName() + " "
								+ owner.getEmployeeArray().get(i).getLastName());
						System.out.println("ID: " + owner.getEmployeeArray().get(i).getId());
						System.out.println("Is available on:");
						printed = true;
					}
					validEntry = true;
					System.out.println(owner.getEmployeeArray().get(i).getStartTimes().get(j).format(dateAndDayFormat)
							+ " " + owner.getEmployeeArray().get(i).getStartTimes().get(j).format(timeFormat) + "-"
							+ owner.getEmployeeArray().get(i).getEndTimes().get(j).format(timeFormat));
				}

			}
			if (validEntry == false) {
				System.out.println(owner.getEmployeeArray().get(i).getId() + " has no work hours this week");
			}
			if (!(i == owner.getEmployeeArray().size() - 1)) {
				System.out.println("--------------------------");
			}
			printed = false;
		}
	}
	
	public void viewBookingSummary() {
		Utility util = new Utility();
		Appointment appointment = new Appointment();
		boolean printDay, printLine;
		LocalDateTime currentDay = LocalDateTime.now().minusMonths(1);
		String formattedTime, formattedTimePlusDuration, dateAndDay;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		LocalDateTime now = LocalDateTime.now();
		List<Appointment> sortedList = new ArrayList<Appointment>();

		sortedList = util.sortPastAppointments();

		if (sortedList.isEmpty()) {
			System.out.println("No appointments in the past month");
			return;
		}
		currentDay = sortedList.get(0).getDateAndTime();
		System.out.println("Booking Summary");
		System.out.println("**********************************************************");
		while (currentDay.compareTo(now) < 0) {
			printDay = false;
			printLine = false;
			for (int i = 0; i < sortedList.size(); i++) {
				if (sortedList.get(i).getDateAndTime().toLocalDate().equals(currentDay.toLocalDate())) {
					if (printDay == false) {
						dateAndDay = currentDay.format(dateAndDayFormat);
						System.out.println("==================================================");
						System.out.println(dateAndDay);
						System.out.println("==================================================");
						printDay = true;
					}
					if (printLine == true) {
						System.out.println("--------------------------------------------------");
					}
					formattedTime = sortedList.get(i).getDateAndTime().format(timeFormat);
					formattedTimePlusDuration = sortedList.get(i).getDateAndTime()
							.plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
					System.out.println("Appointment time: " + formattedTime + "-" + formattedTimePlusDuration);
					util.printAppointmentDetails(i, sortedList);
					printLine = true;
				}
			}
			currentDay = currentDay.plusDays(1);
		}
		System.out.println("\n**********************************************************\n");
		return;
	}

	public void viewUpcomingBookings() {
		Utility util = new Utility();
		Appointment appointment = new Appointment();
		boolean printDay, printLine;
		LocalDateTime currentDay = LocalDateTime.now().minusMonths(1);
		String formattedTime, formattedTimePlusDuration, dateAndDay;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		LocalDateTime now = LocalDateTime.now();
		List<Appointment> sortedList = new ArrayList<Appointment>();

		sortedList = util.sortFutureAppointments();

		if (sortedList.isEmpty()) {
			System.out.println("No appointments in the next week");
			return;
		}
		currentDay = sortedList.get(0).getDateAndTime();
		System.out.println("Upcoming Appointments");
		System.out.println("**********************************************************");
		while (currentDay.compareTo(now.plusWeeks(1)) < 0) {
			printDay = false;
			printLine = false;
			for (int i = 0; i < sortedList.size(); i++) {
				if (sortedList.get(i).getDateAndTime().toLocalDate().equals(currentDay.toLocalDate())) {
					if (printDay == false) {
						dateAndDay = currentDay.format(dateAndDayFormat);
						System.out.println("==================================================");
						System.out.println(dateAndDay);
						System.out.println("==================================================");
						printDay = true;
					}
					if (printLine == true) {
						System.out.println("--------------------------------------------------");
					}
					formattedTime = sortedList.get(i).getDateAndTime().format(timeFormat);
					formattedTimePlusDuration = sortedList.get(i).getDateAndTime()
							.plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
					System.out.println("Appointment time: " + formattedTime + "-" + formattedTimePlusDuration);
					util.printAppointmentDetails(i, sortedList);
					printLine = true;
				}
			}
			currentDay = currentDay.plusDays(1);
		}
		System.out.println("\n**********************************************************\n");
		LOGGER.info("");
		return;
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
					LOGGER.log( Level.SEVERE, e.toString(), e );
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

}
