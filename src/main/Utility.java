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


public class Utility {
	private static final Logger LOGGER = Logger.getLogger("MyLog");
	//exit function to save all data to .txt files so it could be retrieved
	//when program starts
	public void exit(){
		Writer writer = new Writer();
		Owner owner = new Owner();
		
		try{
			writer.saveEmployees(owner.getEmployeeArray());
		}catch (IOException e){
			e.printStackTrace();
			LOGGER.log( Level.SEVERE, e.toString(), e );
		}
		
	}
	
	//when user inputs 'q' for input quit task
	public boolean quitFunction(String input){
		String pattern = "^b|q|quit| $";
		String quit = "q";
		//System.out.println("quit function here");
		boolean exit = false;
		if(input.matches(pattern))
		{
			//System.out.println("exit is true");
			exit = true;
		}
		LOGGER.info("Program Exited.");
		return exit;
	}
	
	public boolean checkString(String string){
		Scanner keyboard = new Scanner(System.in);
		Utility util = new Utility();
		
		while((string == null) || (string.trim().isEmpty()))
		{			
			return false;
		}	
		return true;
	}
	
	public boolean customerUsernameIsDuplicate(String username){
		Scanner keyboard = new Scanner(System.in);
		Main driver = new Main();
		
		// If username already exists
		int index = 0;
		Boolean duplicate = false;
		
			/*System.out.println("Username:");
			username = keyboard.nextLine();*/
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
		
		return duplicate;
		
	}
	
	public boolean validateLogin(String usernameOrPassword){
        String pattern = "^[a-zA-Z0-9-_]{6,20}$";
        if(usernameOrPassword.matches(pattern)){
            return true;
        }else{
        	System.out.println("invalid input, input has to be 6-20 letters");
            return false;
        }
    }
    
    public boolean validateName(String name){
        String pattern = "^[A-Z]{1}[a-z]{1,19}$";
        if(name.matches(pattern)){
            return true;
        }else{
        	System.out.println("please enter a valid name .i.e: David");
            return false;
        }
    }
    
    public boolean validateContactNumber(String number){
        String pattern = "^[(]?((0|[+]61)(2|4|3|7|8))?[)]?( |-)?[0-9]{2}( |-)?[0-9]{2}( |-)?[0-9]{1}( |-)?[0-9]{3}$";
        if(number.matches(pattern)){
            return true;
        }else{
        	System.out.println("please enter a valid phone number .i.e: 0412345610");
            return false;
        }
    }
    
    public boolean validateMakeEmployeeId(String id){
        Owner owner = new Owner();
        String pattern = "^e[0-9]{5}$";

        for(int i=0;i<owner.getEmployeeArray().size();i++){
            if(owner.getEmployeeArray().get(i).getId().equals(id)&&id.matches(pattern)){
                System.out.println("Id already exists");
                return false;
            }
        }
        if(id.matches(pattern)){
            return true;
        }
        System.out.println("Id is invalid, please enter in the format eXXXXX (X=[0-9])");
        return false;
    }
    
    public boolean validateEmployeeId(String id){
        Owner owner = new Owner();
        String pattern = "^e[0-9]{5}$";

        for(int i=0;i<owner.getEmployeeArray().size();i++){
            if(owner.getEmployeeArray().get(i).getId().equals(id)&&id.matches(pattern)){
                if(id.matches(pattern)){
                    return true;
                }else{
                	System.out.println("Id is invalid, please enter in the format eXXXXX (X=[0-9])");
                }
            }
        }
        System.out.println("Id does not exist");
        return false;
    }
    
    public boolean validateAddress(String address){
        String pattern = "^[0-9a-zA-Z0-9_ ]{5,45}$";
        if(address.matches(pattern)){
            return true;
        }else{
        	System.out.println("please enter valid Address");
            return false;
        }
    }
    
    public boolean validateDate(String date){
    	LocalDate currentDate;
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
    	try{
    		currentDate = LocalDate.parse(date, dateFormat);
    	}catch(Exception e){
            LOGGER.log( Level.SEVERE, e.toString(), e );
    		return false;
    	}
    	return true;
    }
    
    public boolean validateTime(String time){
    	LocalTime currentTime;
    	boolean valid =false;
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
		DateTimeFormatter timeFormat2 = DateTimeFormatter.ofPattern("h:mma");
		
    	try{
    		currentTime = LocalTime.parse(time, timeFormat);
    		valid = true;
    	}catch(Exception e){}

    	try{
    		currentTime = LocalTime.parse(time, timeFormat2);
    		valid = true;
    	}catch(Exception e){}
    	
    	if(valid==true){
    		return true;
    	}
    	return false;
    }
    //This method creates a unique ID in the format eXXXXX (X being a number).
	public  String createID() {
		//Sets ID to 0 then searches array, if ID exists it iterates and repeats.
		Owner owner= new Owner();

		ArrayList<Employee> EmployeeArray = owner.getEmployeeArray();
		int IDCounter = 1;
		int index = 0;
		while(index < EmployeeArray.size()) {
			index=0;
			while (index < EmployeeArray.size()) {
				if (Integer.parseInt(String.format("%05d", IDCounter)) == Integer.parseInt((EmployeeArray.get(index).getId()).substring(1,6))) {
					IDCounter++;
					break;
				} else {
					index++;

				}
			}
		}
		String ID = "e" + String.format("%05d", IDCounter);
		return ID;
	}
	
	public LocalDateTime adjustTimePresentDay(LocalDateTime currentTime) {
		Appointment appointment = new Appointment();
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		while (now.getDayOfWeek().equals(currentTime.getDayOfWeek()) && now.getHour() >= currentTime.getHour()
				&& now.getDayOfYear() == currentTime.getDayOfYear()) {
			if (now.getHour() >= currentTime.getHour()) {
				currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
			}
		}
		return currentTime;
	}
	
	public void printAppointmentDetails(int counter, List<Appointment> arrayList) {
		Main main = new Main();
		Owner owner = new Owner();

		for (int j = 0; j < main.getCustomerArray().size(); j++) {
			if (main.getCustomerArray().get(j).getUsername()
					.equals(arrayList.get(counter).getCustomerUsername())) {
				System.out.println("Customer Name: " + main.getCustomerArray().get(j).getFirstName() + " "
						+ main.getCustomerArray().get(j).getLastname());
				System.out.println("Address: " + main.getCustomerArray().get(j).getAddress());
				System.out.println("Contact Number: " + main.getCustomerArray().get(j).getContactNumber());
			}
		}
		for (int k = 0; k < owner.getEmployeeArray().size(); k++) {
			if (owner.getEmployeeArray().get(k).getId()
					.equals(arrayList.get(counter).getEmployeeId())) {
				System.out.println("Employee Name: " + owner.getEmployeeArray().get(k).getFirstName() + " "
						+ owner.getEmployeeArray().get(k).getLastName());
				System.out.println("Employee ID: " + owner.getEmployeeArray().get(k).getId());
			}
		}
	}
	
	public boolean validateDayOfWeek(LocalDateTime currentTime) {
		Business business = new Business();
		for (int i = 0; i < business.getOpeningDays().length; i++) {
			if (business.getOpeningDays()[i].equals(currentTime.getDayOfWeek())) {
				return true;
			}
		}
		return false;
	}
	
	public DateTimeFormatter assignTimeFormat(String time) {
		LocalTime currentTime;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
		DateTimeFormatter timeFormat2 = DateTimeFormatter.ofPattern("h:mma");

		try {
			currentTime = LocalTime.parse(time, timeFormat);
			return timeFormat;
		} catch (Exception e) {}

		try {
			currentTime = LocalTime.parse(time, timeFormat2);
			return timeFormat2;
		} catch (Exception e) {}

		return null;

	}
	
	public boolean validateNewWorkTime(String employeeId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		Owner owner = new Owner();
		Business business = new Business();
		LocalDateTime now = LocalDateTime.now();

		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			if (owner.getEmployeeArray().get(i).getId().equals(employeeId)) {
				for (int j = 0; j < owner.getEmployeeArray().get(i).getStartTimes().size(); j++) {
					if (owner.getEmployeeArray().get(i).getStartTimes().get(j).toLocalDate()
							.equals(startDateTime.toLocalDate())) {
						System.out.println("employee already working on this date");
						return false;
					}
				}
				

				if (startDateTime.compareTo(endDateTime) > 0) {
					System.out.println("end time is before start time");
					return false;
				} else if (startDateTime.toLocalTime().isBefore(business.getOpeningTime())) {
					System.out.println("cannot start before business is open");
					return false;
				} else if (endDateTime.toLocalTime().isAfter(business.getClosingTime())) {
					System.out.println("cannot have end after business is closed");
					return false;
				} else if (startDateTime.toLocalDate().isBefore(now.toLocalDate())) {
					System.out.println("cannot make work time in the past");
					return false;
				} else if (startDateTime.toLocalDate().isAfter(now.plusMonths(1).toLocalDate())) {
					System.out.println("cannot assign work time beyond one month");
					return false;
				} else if(endDateTime.getHour()-startDateTime.getHour()<3){
					System.out.println("cannot work for less than 3 hours");
					return false;
				}

				owner.getEmployeeArray().get(i).getStartTimes().add(startDateTime);
				owner.getEmployeeArray().get(i).getEndTimes().add(endDateTime);
				System.out.println("new work time added");
				return true;
			}
		}
		return false;
	}
	
	public List<Appointment> sortFutureAppointments(){
		Main main = new Main();
		LocalDateTime now = LocalDateTime.now();	
		List<Appointment> sortedList = new ArrayList<Appointment>();
		for(int i=0;i<main.getAppointmentArray().size();i++){
			if(main.getAppointmentArray().get(i).getDateAndTime().isBefore(now.plusWeeks(1))&&
					main.getAppointmentArray().get(i).getDateAndTime().isAfter(now)&&sortedList.isEmpty()){
				sortedList.add(main.getAppointmentArray().get(i));
				continue;
			}
			for(int j=0;j<sortedList.size();j++){
				if(main.getAppointmentArray().get(i).getDateAndTime().isBefore(now.plusWeeks(1))&&
						main.getAppointmentArray().get(i).getDateAndTime().isAfter(now)&&
					main.getAppointmentArray().get(i).getDateAndTime().isBefore(sortedList.get(j).getDateAndTime())){
					sortedList.add(j, main.getAppointmentArray().get(i));
					break;
				} else if(main.getAppointmentArray().get(i).getDateAndTime().isBefore(now.plusWeeks(1))&&
						main.getAppointmentArray().get(i).getDateAndTime().isAfter(now)&&j==sortedList.size()-1){
					sortedList.add(main.getAppointmentArray().get(i));
					break;
				}
			}
		}
		return sortedList;
	}
	
	public List<Appointment> sortPastAppointments(){
		Main main = new Main();
		LocalDateTime now = LocalDateTime.now();	
		List<Appointment> sortedList = new ArrayList<Appointment>();
		for(int i=0;i<main.getAppointmentArray().size();i++){
			if(main.getAppointmentArray().get(i).getDateAndTime().isAfter(now.minusMonths(1))&&
					main.getAppointmentArray().get(i).getDateAndTime().isBefore(now)&&sortedList.isEmpty()){
				sortedList.add(main.getAppointmentArray().get(i));
				continue;
			}
			for(int j=0;j<sortedList.size();j++){
				if(main.getAppointmentArray().get(i).getDateAndTime().isAfter(now.minusMonths(1))&&
						main.getAppointmentArray().get(i).getDateAndTime().isBefore(now)&&
					main.getAppointmentArray().get(i).getDateAndTime().isBefore(sortedList.get(j).getDateAndTime())){
					sortedList.add(j, main.getAppointmentArray().get(i));
					break;
				} else if(main.getAppointmentArray().get(i).getDateAndTime().isAfter(now.minusMonths(1))&&
						main.getAppointmentArray().get(i).getDateAndTime().isBefore(now)&&j==sortedList.size()-1){
					sortedList.add(main.getAppointmentArray().get(i));
					break;
				}
			}
		}
		return sortedList;
	}
	
	public void printAvailableAppointment(LocalDateTime currentTime) {
		Main main = new Main();
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		String formattedTime;
		Boolean employeeAvailable = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");

		for (int j = 0; j < owner.getEmployeeArray().size(); j++) {
			employeeAvailable = true;
			for (int i = 0; i < main.getAppointmentArray().size(); i++) {
				if (main.getAppointmentArray().get(i).getEmployeeId().equals(owner.getEmployeeArray().get(j).getId())
						&& main.getAppointmentArray().get(i).getDateAndTime().equals(currentTime)) {
					employeeAvailable = false;
					break;
				}
			}
			if (employeeAvailable == false) {
				continue;
			}

			for (int k = 0; k < owner.getEmployeeArray().get(j).getStartTimes().size(); k++) {
				if ((owner.getEmployeeArray().get(j).getStartTimes().get(k).compareTo(currentTime) == 0
						|| owner.getEmployeeArray().get(j).getStartTimes().get(k).compareTo(currentTime) == -1)
						&& ((owner.getEmployeeArray().get(j).getEndTimes().get(k)
								.compareTo(currentTime.plusMinutes(appointment.getAppointmentDuration())) == 0)
								|| owner.getEmployeeArray().get(j).getEndTimes().get(k).compareTo(
										currentTime.plusMinutes(appointment.getAppointmentDuration())) == 1)) {
					formattedTime = currentTime.format(formatter);
					System.out.println(formattedTime);
					return;
				}
			}
		}
		return;
	}

}
