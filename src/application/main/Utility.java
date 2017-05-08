package application.main;

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

	public final int TIME_BLOCK = 30;
	public final int MIN_TOTAL_LENGTH = 180;
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
				LOGGER.info("Username already exists.");
				return true;
			}
		}
		for (int i = 0; i < main.getCustomerArray().size(); i++) {
			if (main.getCustomerArray().get(i).getUsername().equals(username)) {
				LOGGER.info("Username already exists.");
				return true;
			}
		}
		return false;
	}
	
	public boolean customerUsernameExists(String username){
		MainApp main = new MainApp();
		for (int i = 0; i < main.getCustomerArray().size(); i++) {
			if (main.getCustomerArray().get(i).getUsername().equals(username)) {
				LOGGER.info("Username already exists.");
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
			LOGGER.info("Vaild username.");
			return true;
		} else {
			LOGGER.info("Invaild username.");
			return false;
		}
	}
	
	public boolean validatePassword(String password){
		String pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{7,30}$";
		if(password.matches(pattern)){
			LOGGER.info("Valid password.");
			return true;
		}else{
			LOGGER.info("Invaild password.");
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
			LOGGER.info("Valid name.");
			return true;
		} else {
			LOGGER.info("Invaild name.");
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
			LOGGER.info("Valid contact number.");
			return true;
		} else {
			LOGGER.info("Invaild contact number.");
			return false;
		}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, e.toString(), e);
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
					LOGGER.info("Valid employee id.");
					return true;
				} else {
					LOGGER.info("Invaild employee id.");
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
	 * @author David Ping
	 * This function validations whether or not the business name entered is valid, the entry must be
	 * between 5 and 20 in length and it can contain letters, numbers and space
	 * */
	public boolean validateBusinessName(String businessName){
		String pattern = "^[0-9a-zA-Z0-9 ]{1,20}$";
		if (businessName.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @author David Ping
	 * This function checks if the business already exists by going through all the owners and checking
	 * the names of their businesses
	 * */
	public boolean businessExists(String businessName){
		MainApp mainApp = new MainApp();
		for(int i=0;i<mainApp.getOwnerArray().size();i++){
			if(mainApp.getOwnerArray().get(i).getBusinessName().equals(businessName)){
				return true;
			}
		}
		return false;
	}
	
}
