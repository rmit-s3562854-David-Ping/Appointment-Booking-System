package main;

import java.io.IOException;
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
	
	public Boolean register(){
		System.out.println("                      REGISTRATION");
		System.out.println("**********************************************************");
		System.out.println("Enter 'q','b','quit' to exit at anytime ");
		System.out.println("");
		Customer customer ;
		customer = getRegisterInformation();
		if(customer == null){
			System.out.println("Registration Failed");
			return false;
		}
		addCustomer(customer.getUsername(), customer.getPassword(), customer.getFirstName(), customer.getLastname(), customer.getAddress(), customer.getContactNumber());
		System.out.println("Registration complete");
		return true;
	}

	public Customer getRegisterInformation() {

		Utility util = new Utility();
		Main driver = new Main();
		Scanner keyboard = new Scanner(System.in);
		String username = null,password, firstName,lastName, address,contactNumber;
		Boolean valid = true;
		System.out.println("First name: ");
		firstName = keyboard.nextLine();
		
		if((util.quitFunction(firstName))== true)
		{
			valid = false;
			return null;
		}
		while(util.validateName(firstName) == false)
		{
			firstName = keyboard.nextLine();
			if(util.quitFunction(firstName)){
				return null;
			}
		}
		
		System.out.println("Last name: ");
		lastName = keyboard.nextLine();
		if((util.quitFunction(lastName))== true)
		{
			valid = false;
			return null;
		}
		
		while(util.validateName(lastName) == false)
		{
			lastName = keyboard.nextLine();
			if(util.quitFunction(lastName)){
				return null;
			}
		}
		System.out.println("Address: ");
		address = keyboard.nextLine();
		if((util.quitFunction(address))== true)
		{
			valid = false;
			return null;
		}
		
		while(util.validateAddress(address) == false)
		{
			address = keyboard.nextLine();
			if(util.quitFunction(address)){
				return null;
			}
		}
		System.out.println("Contact Number: ");
		contactNumber = keyboard.nextLine();
		if((util.quitFunction(contactNumber))== true)
		{
			valid = false;
			return null;
		}
		while(util.validateContactNumber(contactNumber) == false)
		{
			contactNumber = keyboard.nextLine();
			if(util.quitFunction(contactNumber) == true)
			{
				return null;
			} 
		}
		System.out.println("Username: ");
		username = keyboard.nextLine();
		boolean validUsername;
		
		if((util.quitFunction(username))== true)
		{
			valid = false;
			return null;
		}
		
		while((util.validateLogin(username) == false) || (util.customerUsernameIsDuplicate(username) == true)){
			username = keyboard.nextLine();
		}
	
		System.out.println("Password: ");
		password = keyboard.nextLine();
		if((util.quitFunction(password))== true)
		{
			valid = false;
			return null;
		}
		while(util.validateLogin(password) == false)
		{
			password = keyboard.nextLine();
			if(util.quitFunction(password)){
				return null;
			}
		}
		
		
		Customer customer = makeObj(username,password,firstName,lastName,address,contactNumber,valid);
		return customer;
		
	}
	
	public Customer makeObj(String username, String password, String firstName,String lastName,String address,String contactNumber, Boolean valid){
		
		if(valid == false){
			return null;
		}
		else{
		Customer customer = new Customer(username, password, firstName,lastName,address,contactNumber);
		return customer;
		}
	}
	
	public Boolean addCustomer(String username, String password, String firstName,String lastName,String address,String contactNumber){
		Main driver = new Main();

		Customer customer = new Customer(username, password, firstName, lastName, address,contactNumber );
		driver.getCustomerArray().add(customer);
		Writer writer = new Writer();
		try{
			writer.save(driver.getCustomerArray());
		}catch (IOException e){
			e.printStackTrace();
		}
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
	
	public String toString() {
		 		return this.getUsername()+":"+this.getPassword()+":"+this.getFirstName()+":"+this.getLastname()+":"+this.getAddress()+":"+this.getContactNumber();
		 	}
}
