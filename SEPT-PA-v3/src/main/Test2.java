package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test2 {

	public static void main(String[] args) {
		Main main = new Main();
		Customer customer = new Customer();
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		Business business = new Business();

		// Create a customer
		Customer cust = new Customer("username", "password", "firstName", "secondName", "address", "contactNumber");
		Customer cust2 = new Customer("username2", "password", "firstName2", "secondName2", "address", "contactNumber");
		Customer cust3 = new Customer("username3", "password", "firstName3", "secondName3", "address", "contactNumber");

		// Add to array in Main
		main.getCustomerArray().add(cust);
		main.getCustomerArray().add(cust2);
		main.getCustomerArray().add(cust3);
		
		//Make a employee
		Employee dummy = new Employee("Bob", "Fisher", "s123");
		
		// Add to array in in Owner
		owner.getEmployeeArray().add(dummy);

		LocalDateTime asd = LocalDateTime.of(2017, 3, 17, 10, 0);
		LocalDateTime asd1 = LocalDateTime.of(2017, 3, 16, 10, 0);
		LocalDateTime asd2 = LocalDateTime.of(2017, 3, 18, 12, 0);
		LocalDateTime asd3 = LocalDateTime.of(2017, 3, 20, 10, 0);
		LocalDateTime asd4 = LocalDateTime.of(2017, 3, 17, 9, 0);

		Appointment newAppointment = new Appointment(asd, cust.getUsername(), dummy.getId());
		Appointment newAppointment1 = new Appointment(asd1, cust3.getUsername(), dummy.getId());
		Appointment newAppointment2 = new Appointment(asd2, cust3.getUsername(), dummy.getId());
		Appointment newAppointment3 = new Appointment(asd3, cust2.getUsername(), dummy.getId());
		Appointment newAppointment4 = new Appointment(asd4, cust.getUsername(), dummy.getId());

		customer.getAppointmentArray().add(newAppointment);
		customer.getAppointmentArray().add(newAppointment1);
		customer.getAppointmentArray().add(newAppointment2);
		customer.getAppointmentArray().add(newAppointment3);
		customer.getAppointmentArray().add(newAppointment4);

		// Function for viewUpcomingBookings()

		// For all bookings in the bookings array
		// Any thats within currentTime-closing time of today
		// And 7 days in the future (including current day i guess)
		// Then print out the date/day/time/customer/employee

		
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		String formattedTime, formattedTimePlusDuration, dateAndDay;
		LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		int counter = 0;
		
		if(customer.getAppointmentArray().isEmpty()){
			System.out.println("No appointments made yet");
			return;
		}

		if (currentTime.toLocalTime().compareTo(business.getClosingTime()) > 0) {
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
		
		System.out.println("Bookings over the next week");
		while (currentTime.compareTo(now.plusDays(7)) < 0) {
			dateAndDay = currentTime.format(dateAndDayFormat);
			currentTime=currentTime.withHour(business.getOpeningTime().getHour());
			currentTime=currentTime.withMinute(business.getOpeningTime().getMinute());
			
			while(now.getDayOfWeek().equals(currentTime.getDayOfWeek()) && now.getHour() >= currentTime.getHour() && 
					now.getDayOfYear()==currentTime.getDayOfYear()){
				if(now.getHour() >= currentTime.getHour()){
					currentTime=currentTime.plusMinutes(appointment.getAppointmentDuration());
				}
			}
			
			System.out.println("==================================================");
			System.out.println(dateAndDay);
			System.out.println("==================================================");	
			
			while(!(currentTime.toLocalTime().compareTo(business.getClosingTime())==0)) {
				if(currentTime.compareTo(customer.getAppointmentArray().get(counter).getDateAndTime())==0){
					formattedTime = customer.getAppointmentArray().get(counter).getDateAndTime().format(timeFormat);
					formattedTimePlusDuration = customer.getAppointmentArray().get(counter).getDateAndTime().plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
					System.out.println("--------------------------------------------------");
					System.out.println(formattedTime+"-"+formattedTimePlusDuration);		
							
					//for all customers in the customer array check if it is the same customer which is assigned the current appointment
					for(int j=0;j<main.getCustomerArray().size();j++){
						if(main.getCustomerArray().get(j).getUsername().equals(customer.getAppointmentArray().get(counter).getCustomerUsername())){
							System.out.println("Customer Name: "+main.getCustomerArray().get(j).getFirstName()+" "+main.getCustomerArray().get(j).getLastname());
						}
					}
					//for all employees in the employee array check if the same employee is assigned to the current appointment
					for(int k=0;k<owner.getEmployeeArray().size();k++){
						if(owner.getEmployeeArray().get(k).getId().equals(customer.getAppointmentArray().get(counter).getEmployeeId())){
							System.out.println("Employee Name: "+owner.getEmployeeArray().get(k).getFirstName()+" "+owner.getEmployeeArray().get(k).getLastName());
							System.out.println("Employee ID: "+owner.getEmployeeArray().get(k).getId());
						}
					}
					System.out.println("--------------------------------------------------");
				}
				if(counter==customer.getAppointmentArray().size()-1){
					currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
					counter=0;
				}else{
					counter++;
				}
			}
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
		return;
	}
}
