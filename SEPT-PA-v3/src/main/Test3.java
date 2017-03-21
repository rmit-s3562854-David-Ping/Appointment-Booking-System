package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test3 {
	public static void main(String args[]){
		//Function viewBookingSummary()
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

		//Current Day needs bug fixing
		LocalDateTime asd = LocalDateTime.of(2017, 3, 16, 9, 0);
		LocalDateTime asd1 = LocalDateTime.of(2017, 3, 11, 10, 0);
		LocalDateTime asd2 = LocalDateTime.of(2017, 3, 18, 12, 0);
		LocalDateTime asd3 = LocalDateTime.of(2017, 3, 16, 10, 0);
		LocalDateTime asd4 = LocalDateTime.of(2017, 3, 17, 10, 0);

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

		
		
		
		
		
		
		
		
		
		
		
		
		//Need to find the first date in the appointments array and make it currentTime
		
		
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		String formattedTime, formattedTimePlusDuration, dateAndDay;
		int counter = customer.getAppointmentArray().size();
		LocalDateTime currentTime = customer.getAppointmentArray().get(0).getDateAndTime().withSecond(0).withNano(0);
		Boolean open = null;
		
		if(customer.getAppointmentArray().isEmpty()){
			System.out.println("No appointments made yet");
			return;
		}
		for(int i=0;i<customer.getAppointmentArray().size();i++){
			if(customer.getAppointmentArray().get(i).getDateAndTime().compareTo(currentTime)<0){
				currentTime=customer.getAppointmentArray().get(i).getDateAndTime();
			}
		}
		System.out.println("Booking Summary");
		while(counter>0){
			currentTime=currentTime.withHour(business.getOpeningTime().getHour());
			currentTime=currentTime.withMinute(business.getOpeningTime().getMinute());	
			open = null;
			for(int i=0;i<business.getOpeningDays().length;i++){
				if(i==business.getOpeningDays().length-1 && !(business.getOpeningDays()[i].equals(currentTime.getDayOfWeek()))){
					open=false;
				}else if(business.getOpeningDays()[i].equals(currentTime.getDayOfWeek())){
					open=true;
					break;
				}
			}
			if(open==false){
				//If the business is not open today move to the next day 24-current hour
				currentTime=currentTime.plusHours(24-currentTime.getHour());
			}else if(open==true){
				dateAndDay = currentTime.format(dateAndDayFormat);
				System.out.println("==================================================");
				System.out.println(dateAndDay);
				System.out.println("==================================================");
			}
			
			while(currentTime.toLocalTime().compareTo(business.getClosingTime())==-1 || currentTime.getHour()==0){
					for(int i=0;i<customer.getAppointmentArray().size();i++){
						if(customer.getAppointmentArray().get(i).getDateAndTime().equals(currentTime)){
							formattedTime = currentTime.format(timeFormat);
							formattedTimePlusDuration = currentTime.plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
							System.out.println("--------------------------------------------------");
							System.out.println(formattedTime+"-"+formattedTimePlusDuration);					
							//for all customers in the customer array check if it is the same customer which is assigned the current appointment
							for(int j=0;j<main.getCustomerArray().size();j++){
								if(main.getCustomerArray().get(j).getUsername().equals(customer.getAppointmentArray().get(i).getCustomerUsername())){
									System.out.println("Customer Name: "+main.getCustomerArray().get(j).getFirstName()+" "+main.getCustomerArray().get(j).getLastname());									
									System.out.println("Address: "+main.getCustomerArray().get(j).getAddress());
									System.out.println("Contact Number: "+main.getCustomerArray().get(j).getContactNumber());
								}
							}	
							//for all employees in the employee array check if the same employee is assigned to the current appointment
							for(int k=0;k<owner.getEmployeeArray().size();k++){
								if(owner.getEmployeeArray().get(k).getId().equals(customer.getAppointmentArray().get(i).getEmployeeId())){
									System.out.println("Employee Name: "+owner.getEmployeeArray().get(k).getFirstName()+" "+owner.getEmployeeArray().get(k).getLastName());
									System.out.println("Employee ID: "+owner.getEmployeeArray().get(k).getId());
								}
							}
							System.out.println("--------------------------------------------------");
							counter--;
							currentTime=currentTime.plusMinutes(appointment.getAppointmentDuration());
							i=0;//Make i 0 again to check the new time against all the appointments again
						}
					}	
				currentTime=currentTime.plusMinutes(appointment.getAppointmentDuration());
			}
			currentTime=currentTime.plusHours(24-currentTime.getHour());
		}
		return;
	}

}
