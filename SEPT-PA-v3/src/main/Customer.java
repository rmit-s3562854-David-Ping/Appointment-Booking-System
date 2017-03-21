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
	
	RegistrationInformation getRegisterInformation(String username, String password, String firstName,String lastName,String address,String contactNumber, Boolean valid ) {
		//RegistrationInformation info = new RegistrationInformation();
		Utility util = new Utility();
		Main driver = new Main();
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("What is your first name?");
		//firstName = info.firstName;
		firstName = keyboard.nextLine();
		if((util.quitFunction(firstName))== true)
		{
			valid = false;

		}
		
		if(util.checkString(firstName) == false){
			valid = false;
		}
		System.out.println("What is your last name?");
		//lastName = info.lastName;
		lastName = keyboard.nextLine();
		if((util.quitFunction(lastName))== true)
		{
			valid = false;
		}
		
		if(util.checkString(lastName) == false){
			valid = false;
		}
		System.out.println("What is your address ?");
		//address = info.address;
		address = keyboard.nextLine();
		if((util.quitFunction(address))== true)
		{
			valid = false;
		}
		
		if(util.checkString(address) == false){
			valid = false;
		}
		System.out.println("What is your contact num?");
		//contactNumber = info.contactNumber;
		contactNumber = keyboard.nextLine();
		if((util.quitFunction(contactNumber))== true)
		{
			valid = false;
		}
		
		if(util.checkString(contactNumber) == false){
			valid = false;
		}
		
		username = util.customerUsernameIsDuplicate(username);
		
		System.out.println("Password:");
		password = keyboard.nextLine();
		if((util.quitFunction(password))== true)
		{
			valid = false;
		}
		
		if(util.checkString(password) == false){
			valid = false;
		}
		
		RegistrationInformation info = makeObj(username,password,firstName,lastName,address,contactNumber,valid);
		return info;
		
	}
	
	RegistrationInformation makeObj(String username, String password, String firstName,String lastName,String address,String contactNumber, Boolean valid){
		RegistrationInformation info = new RegistrationInformation();
		info.username = username;
		info.password = password;
		info.firstName = firstName;
		info.lastName = lastName;
		info.address = address;
		info.contactNumber = contactNumber;
		info.valid = valid;
		if(info.valid == false){
			return null;
		}
		else
		return info;
	}
	
	public Boolean addCustomer(String username, String password, String firstName,String lastName,String address,String contactNumber){
		Main driver = new Main();
		Customer customer = new Customer(username, password, firstName, lastName, address,contactNumber );
		
		driver.getCustomerArray().add(customer);
		
		return true;
		
    }
	
	public Boolean register(){
		String username = "";
		String password = "";
		String firstName = "";
		String lastName = "";
		String address = "";
		String contactNumber = "";
		Boolean valid = true;
		
		RegistrationInformation info;
		info = getRegisterInformation(username, password, firstName, lastName, address, contactNumber, valid);
		if(info == null){
			return false;
		}
		
		addCustomer(info.username, info.password, info.firstName, info.lastName, info.address, info.contactNumber);
		System.out.println("Registration complete");
		return true;
		
	}
	

	public void viewAppointmentTimes() {
		Customer customer = new Customer();
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		Business business = new Business();

		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		Boolean open, appointmentPrinted;
		int counter = 0;
		String dateAndDay, formattedTime;
		if (owner.getEmployeeArray().isEmpty()) {
			System.out.println("No employees working for this company");
			return;
		}
		if (currentTime.toLocalTime().compareTo(business.getClosingTime()) == 1) {
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
		while (counter < 10) {
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
				dateAndDay = currentTime.format(formatter2);
				System.out.println("================");
				System.out.println(dateAndDay);
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
								formattedTime = currentTime.format(formatter);
								System.out.println(formattedTime);
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
