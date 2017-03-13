package main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Test {
	private static ArrayList<Customer> customerArray = new ArrayList<Customer>();

	public static void main(String[] args) {
		
		Employee employee = new Employee();
		Customer customer = new Customer();
		Owner owner = new Owner();
		Test test = new Test();
		Appointment appointment = new Appointment();
		Business business = new Business();
		
		
		
		//Lots of test data below this
		
		// Make employee
		
		LocalDateTime start = LocalDateTime.of(2017, 3, 10, 9, 0);//year, month, day, hour, min
		LocalDateTime start2 = LocalDateTime.of(2017, 3, 13, 9, 0);//year, month, day, hour, min
		LocalDateTime start3 = LocalDateTime.of(2017, 3, 7, 9, 0);//year, month, day, hour, min
		LocalDateTime start4 = LocalDateTime.of(2017, 3, 8, 9, 0);//year, month, day, hour, min
		LocalDateTime end = LocalDateTime.of(2017, 3, 10, 19, 0);//year, month, day, hour, min
		LocalDateTime end2 = LocalDateTime.of(2017, 3, 13, 19, 0);//year, month, day, hour, min
		LocalDateTime end3 = LocalDateTime.of(2017, 3, 7, 19, 0);//year, month, day, hour, min
		LocalDateTime end4 = LocalDateTime.of(2017, 3, 8, 19, 0);//year, month, day, hour, min

		Employee dummy = new Employee("Bob", "s123");
		Employee dummy2 = new Employee("Ben", "s132");
		
		dummy.getStartTimes().add(start);
		dummy.getEndTimes().add(end);
		dummy.getStartTimes().add(start3);
		dummy.getEndTimes().add(end3);
		dummy.getStartTimes().add(start4);
		dummy.getEndTimes().add(end4);
		dummy2.getStartTimes().add(start2);
		dummy2.getEndTimes().add(end2);
		
		// Add to array in Owner
		owner.getEmployeeArray().add(dummy);
		owner.getEmployeeArray().add(dummy2);

		// Create a customer
		Customer cust = new Customer("username", "password", "firstName", "secondName", "address", "contactNumber");

		// Add to array in Main
		test.getCustomerArray().add(cust);
		
		LocalTime time = LocalTime.of(11, 0);
		LocalDate date = LocalDate.of(2017, 3, 8);
		LocalDateTime asd = LocalDateTime.of(date, time);
		
		Appointment newAppointment = new Appointment(asd,cust,dummy);
		
		customer.getAppointmentArray().add(newAppointment);
		
		
		//String str = "29/10/1996 12:30";
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		//LocalDateTime dateTime = LocalDateTime.parse(str, formatter);


		//DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy E H:mma");
		//String formattedDateTime = now.format(formatter2);
		//System.out.println(formattedDateTime);


		//Loop Rules
		//Question: can the customer book on the same day as the appointment?
		
		//If current hour is passed closingTime go to next day
		//If current day of week is not an opening day go to the next day until it is
		//go to opening hour (9:00)
			//if passed go forward 1 hour (appointmentDuration) until it is not yet passed
		//Show the all available appointment hours (has employee working at that time and day)
			//No other customer has booked that appointment yet
		//Do for next 7 days 
		//If invalid/already booked/no worker ask them to re-enter
		
		//From the business class
		
		
			//Dates used
			LocalDateTime currentTime = LocalDateTime.now();
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
			
			if(owner.getEmployeeArray().isEmpty()){
				System.out.println("No employees working for this company");
				return;
			}
			//If the current time is
			if(currentTime.toLocalTime().compareTo(business.getClosingTime())==1){
				//currentTime would be between closing time to 24 so add hours 24-current time which will reset the clock
				currentTime=currentTime.plusHours(24-currentTime.getHour());//Set the clock to the next day because it has already closed for today
			}

			Boolean open = null;//Boolean to check whether or not current day of week is valid
			Boolean appointmentPrinted = null;//
			int counter=0;
			
			//7 is a temporary hard code, we were not told what range of appointment dates customers can view
			while(counter<7){
				
				currentTime=currentTime.withHour(business.getOpeningTime().getHour());//Set clock to opening Time hour
				currentTime=currentTime.withMinute(business.getOpeningTime().getMinute());//Set the minutes to opening time minutes
				currentTime=currentTime.withSecond(0).withNano(0);//Get rid of seconds and nano seconds for coding purposes
				
				//Only needs to be run if currentTime.day is current day and is a valid day
				while(now.getDayOfWeek().equals(currentTime.getDayOfWeek()) && now.getHour() >= currentTime.getHour() && 
						now.getDayOfYear()==currentTime.getDayOfYear()){
					//While current day
					if(now.getHour() >= currentTime.getHour()){
						//if now hour is currentTimes hour
						//this means that it is too late to make an appointment at this time today
						currentTime=currentTime.plusMinutes(appointment.getAppointmentDuration());
						//Move the currentTime forward 1 appointment duration, it is now valid and will exit the while loop
					}
				}
			
				//initial null assignment for boolean open
				open = null;
					//This for loop checks whether the business is open on the current day
					for(int i=0;i<business.getOpeningDays().length;i++){
						if(i==business.getOpeningDays().length-1 && !(business.getOpeningDays()[i].equals(currentTime.getDayOfWeek()))){
							//Not open today
							open=false;
						}else if(business.getOpeningDays()[i].equals(currentTime.getDayOfWeek())){
							//Open today
							open=true;
							break;
						}
					}
					
				if(open==false){
					//If the business is not open today move to the next day 24-current hour
					currentTime=currentTime.plusHours(24-currentTime.getHour());
				}else if(open==true){	
						//else if open move on through the code
					String formattedDateTime2 = currentTime.format(formatter2);
					//Use formatter from earlier to format the Date to print it out as dd/mm/yyyy (DAYOFWEEK)
					System.out.println("================");
					System.out.println(formattedDateTime2);	
					System.out.println("================");
				
						//While the current time is before the closing time and not at 12:00 am (because 12:00am is a new day)
						while(currentTime.toLocalTime().compareTo(business.getClosingTime())==-1 || currentTime.getHour()==0){
							
							if(!(customer.getAppointmentArray().isEmpty())){
								//If appointments have been made check if the time is same as current time
								for(int i=0;i<customer.getAppointmentArray().size();i++){
									if(customer.getAppointmentArray().get(i).getDateAndTime().equals(currentTime)){
										//Appointment exists at this time so move forward 1 duration
										currentTime=currentTime.plusMinutes(appointment.getAppointmentDuration());
										i=0;//Make i 0 again to check the new time against all the arrays again
									}else if(i==customer.getAppointmentArray().size()-1 && !(customer.getAppointmentArray().get(i).getDateAndTime().equals(currentTime))){
										//Appointment does not exist at this time
										//i has reached the end of the array and the appointment does not exist
									}
								}	
							}
							
							appointmentPrinted=false;
							
							//For the size of the employee array
							for(int j=0;j<owner.getEmployeeArray().size();j++){
								
								//Used to break out of nested loop later
								if(appointmentPrinted == true){
									appointmentPrinted=false;
									break;
								}
								
								for(int k=0;k<owner.getEmployeeArray().get(j).getStartTimes().size();k++){
								//New Code check every employees start time arraylist and if current time is after or equal to start time
								//And if it is before or equal to endtime+duration then the time is valid to be listed 
								
								
								//If the current employee (j) starting time is the same as the current time 
								//OR
								//The current employee (j) starting time is before the current time
								
								// AND
								
								//If the current employee (j) ending time is the same as currentTime plus duration of appointment
								//OR
								//If the current employee ending time is greater than currentTime plus the duration of appointment
								
									
								if((owner.getEmployeeArray().get(j).getStartTimes().get(k).compareTo(currentTime)==0
										||
										owner.getEmployeeArray().get(j).getStartTimes().get(k).compareTo(currentTime)==-1)
										&&
										((owner.getEmployeeArray().get(j).getEndTimes().get(k).compareTo(currentTime.plusMinutes
												(appointment.getAppointmentDuration()))==0)
												||
												owner.getEmployeeArray().get(j).getEndTimes().get(k).compareTo
												(currentTime.plusMinutes(appointment.getAppointmentDuration()))==1)){
								
									
									
									//If it passes all these tests it means the appointment is within the opening times-closing times
									//Appointment is within the employee working hours
									//On a valid day
									//Is not already booked
									//Therefore it can be viewed and later be booked
									
									String formattedDateTime = currentTime.format(formatter);
									System.out.println(formattedDateTime);
									appointmentPrinted=true;
									
									
								}
								
								}	
								
							}
							
							currentTime=currentTime.plusMinutes(appointment.getAppointmentDuration());
						}
						currentTime=currentTime.plusHours(24-currentTime.getHour());							
				}	
				counter++;
			}	
				
			
		}
		
		

	

	public ArrayList<Customer> getCustomerArray() {
		return customerArray;
	}

}
