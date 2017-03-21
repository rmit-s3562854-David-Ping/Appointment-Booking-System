package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Utility {
	//when user inputs 'q' for input quit task
	public boolean quitFunction(String input){
		String quit = "q";
		System.out.println("quit function here");
		boolean exit = false;
		if(input.equals(quit))
		{
			System.out.println("exit is true");
			exit = true;
		}
		return exit;
	}
	
	public boolean checkString(String string){
		Scanner keyboard = new Scanner(System.in);
		Utility util = new Utility();
		
		while((string == null) || (string.trim().isEmpty()))
		{
			System.out.println("Could you please enter a valid data");
			string = keyboard.nextLine();
			
			if((util.quitFunction(string))== false)
			{
				return false;
			}
			
		}
		return true;
	}
	
	public String customerUsernameIsDuplicate(String username){
		Scanner keyboard = new Scanner(System.in);
		Main driver = new Main();
		
		// If username already exists
		int index = 0;
		Boolean duplicate = false;
		do {
			System.out.println("Username:");
			username = keyboard.nextLine();
/*			if((util.quitFunction(username))==false)
			{
				return false;
			}
			while ((username == null) || (username.trim().isEmpty()) || (username.trim().length() <= index + 1))
			{
				System.out.println("Please enter a valid username * ");
				System.out.println("username cant be less than 2 letters");
				username = keyboard.nextLine();
				if((util.quitFunction(username))==false)
				{
					return false;
				}
			}*/
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
		} while (duplicate == true);
		
		return username;
		
	}

}
