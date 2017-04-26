package application.main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.MainApp;

public class Utility {

	/**
	 * Utility class, which contains all validation methods as well as other
	 * functions for checking errors and allowing users to exit current
	 * activity, etc
	 * @version 1.00 05 Apr 2017
	 * @author David Ping, Hassan Mender, Luke Waldren
	 */

	private static final Logger LOGGER = Logger.getLogger("MyLog");

	/**
	 * when user is prompt any input
	 * if input is any of the strings b,q or quit 
	 * exit the function and return to menu
	 * @author Hassan Mender
	 * */
	public boolean quitFunction(String input) {
		String pattern = "^b|q|quit| $";
		boolean exit = false;
		if (input.matches(pattern)) {
			exit = true;
		}
		LOGGER.info("Program Exited.");
		return exit;
	}

	/**
	 * check that the String entered isnt empty 
	 * @author Hassan Mender
	 * */
	public boolean checkString(String string) {
		while ((string == null) || (string.trim().isEmpty())) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the username entered in the registration already exists,
	 * returns true if it does and false if it doesn't Will check both customer
	 * and owner array to avoid duplicate usernames
	 * 
	 * @author Hassan Mender, David Ping
	 */
	public boolean usernameExists(String username) {
		MainApp main = new MainApp();
		for (int i = 0; i < main.getOwnerArray().size(); i++) {
			if (main.getOwnerArray().get(i).getUsername().equals(username)) {
				return true;
			}
		}
		for (int i = 0; i < main.getCustomerArray().size(); i++) {
			if (main.getCustomerArray().get(i).getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Test username and password that is entered to ensure the length is
	 * between 6-20 characters
	 * 
	 * @author David Ping
	 */
	public boolean validateUsername(String username) {
		String pattern = "^[a-zA-Z0-9-_]{6,20}$";
		if (username.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validatePassword(String password){
		String pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{7,30}$";
		if(password.matches(pattern)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Test for names, to ensure name starts with a capital and is between 2-20
	 * characters in length
	 * 
	 * @author David Ping
	 */
	public boolean validateName(String name) {
		String pattern = "^[A-Z]{1}[a-z]{1,19}$";
		if (name.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validates the contact number entered to ensure it adheres to Australian
	 * phone number conventions
	 * 
	 * @author David Ping
	 */
	public boolean validateContactNumber(String number) {
		String pattern = "^(((0|[+]61)(2|3|4|7|8))|(\\((0|[+]61)(2|3|4|7|8)\\)))?( |-)?[0-9]{2}( |-)?[0-9]{2}( |-)?[0-9]{1}( |-)?[0-9]{3}$";
		try{
		if (number.matches(pattern)) {
			return true;
		} else {
			return false;
		}
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * Validates employee ID which must start with an 'e' and be followed by 5
	 * digits, also checks if the id exists in the employee array
	 * 
	 * @author David Ping
	 */
	public boolean validateEmployeeId(String id) {
		MainApp mainApp = new MainApp();
		String pattern = "^e[0-9]{5}$";

		for (int i = 0; i < mainApp.getEmployeeData().size(); i++) {
			if (mainApp.getEmployeeData().get(i).getId().equals(id) && id.matches(pattern)) {
				if (id.matches(pattern)) {
					return true;
				} else {
					System.out.println("Id is invalid, please enter in the format eXXXXX (X=[0-9])");
				}
			}
		}
		System.out.println("Id does not exist");
		return false;
	}

	/**
	 * Validates address input, not very specific due to the nature of
	 * addresses, address can contain digits, letters (lowercase and uppercase)
	 * and _. It must be between 5-45 characters in length
	 * 
	 * @author David Ping
	 */
	public boolean validateAddress(String address) {
		String pattern = "^[0-9a-zA-Z0-9_ ]{5,45}$";
		if (address.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Will test if the users input is in the correct format when entering a
	 * date, will return false if it catches an exception
	 * 
	 * @author David Ping
	 */
	public boolean validateDate(String date) {
		LocalDate currentDate;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
		try {
			currentDate = LocalDate.parse(date, dateFormat);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			return false;
		}
		return true;
	}

	/**
	 * Will test if the input adheres to one of 2 formats allowed either 14:00
	 * (24 hour time) or 2:00PM (12 hour time) function will return true if it
	 * is one of these 2 formats
	 * 
	 * @author David Ping
	 */
	public boolean validateTime(String time) {
		LocalTime currentTime;
		boolean valid = false;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
		DateTimeFormatter timeFormat2 = DateTimeFormatter.ofPattern("h:mma");

		try {
			currentTime = LocalTime.parse(time, timeFormat);
			valid = true;
		} catch (Exception e) {
		}

		try {
			currentTime = LocalTime.parse(time, timeFormat2);
			valid = true;
		} catch (Exception e) {
		}

		if (valid == true) {
			return true;
		}
		return false;
	}

	

	/**
	 * Checks whether or not the input is a valid day where the business is open
	 * returns true if yes and false if not open, uses DayOfWeek values
	 * 
	 * @author David Ping
	 */
	public boolean validateDayOfWeek(LocalDateTime currentTime) {
		Business business = new Business();
		for (int i = 0; i < business.getOpeningDays().length; i++) {
			if (business.getOpeningDays()[i].equals(currentTime.getDayOfWeek())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests whether or not the input is valid and the business is open, this
	 * one takes an integer input where 1= MONDAY .... 7 = SUNDAY
	 * 
	 * @author David Ping
	 */
	public boolean validateDayOfWeek(int counter) {
		Business business = new Business();
		for (int i = 0; i < business.getOpeningDays().length; i++) {
			if (business.getOpeningDays()[i].equals(DayOfWeek.of(counter))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests whether or not the LocalTime values entered are valid by ensuring
	 * that 1. the start time is before the end time 2. the start time is after
	 * the business opens 3. the end time is is before the business closes 4.
	 * the distance between the 2 times is at least 3 hours (assuming min work
	 * hours is 3)
	 * 
	 * @author David Ping
	 */
	public boolean validateNewWorkTime(LocalTime startTime, LocalTime endTime) {
		Business business = new Business();
		if (startTime.compareTo(endTime) > 0) {
			System.out.println("end time is before start time");
			return false;
		} else if (startTime.isBefore(business.getOpeningTime())) {
			System.out.println("cannot start before business is open");
			return false;
		} else if (endTime.isAfter(business.getClosingTime())) {
			System.out.println("cannot have end after business is closed");
			return false;
		} else if (endTime.getHour() - startTime.getHour() < 3) {
			System.out.println("cannot work for less than 3 hours");
			return false;
		} else if (endTime.getHour() - startTime.getHour() == 3 && startTime.getMinute() > endTime.getMinute()) {
			System.out.println("cannot work for less than 3 hours");
			return false;
		}
		return true;
	}

}
