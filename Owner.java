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
		System.out.println("Employee ID: ");
		String id = input.nextLine();
		System.out.println("Employee First Name: ");
		String firstName = input.nextLine();
		System.out.println("Employee Last Name: ");
		String lastName = input.nextLine();
		
		Employee newEmployee = new Employee(firstName, lastName, id);
		
		getEmployeeArray().add(newEmployee);

		System.out.println("Employee " + firstName + " " + lastName + " added.");

	}


	public void addWorkingTimes() {
	}

	

	public void viewBookingSummary() {
	}

	public void showAllWorkerAvailability() {
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
					time2 = customer.getAppointmentArray().get(i).getDateAndTime().plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
					System.out.println("--------------------------------------------------");
					System.out.println(time+"-"+time2);		
							
					//for all customers in the customer array check if it is the same customer which is assigned the current appointment
					for(int j=0;j<main.getCustomerArray().size();j++){
						if(main.getCustomerArray().get(j).getUsername().equals(customer.getAppointmentArray().get(i).getCustomerUsername())){
							System.out.print("Customer Name: ");
							System.out.print(main.getCustomerArray().get(j).getFirstName());
							System.out.print(" ");
							System.out.println(main.getCustomerArray().get(j).getLastname());
							break;
						}
					}
					
					//for all employees in the employee array check if the same employee is assigned to the current appointment
					for(int k=0;k<owner.getEmployeeArray().size();k++){
						if(owner.getEmployeeArray().get(k).getId().equals(customer.getAppointmentArray().get(i).getEmployeeId())){
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
		Main driver = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Owner> ownerArray = driver.getOwnerArray();

		

		int index = 0;
		while (index < ownerArray.size()) {
			MembersSearch.add(ownerArray.get(index).getUsername() + ownerArray.get(index).getPassword());

			if (MembersSearch.contains(username + password)) {
				System.out.println("Login Successful (Owner)");
				// Put Owner menu here
				return true;
			}
			index++;
		}
		return false;
	}

	public ArrayList<Employee> getEmployeeArray() {
		return employeeArray;
	}

}
