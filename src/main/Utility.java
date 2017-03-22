package main;

import java.util.Scanner;


public class Utility {
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
		return exit;
	}
	
	public boolean checkString(String string){
		Scanner keyboard = new Scanner(System.in);
		Utility util = new Utility();
		
		while((string == null) || (string.trim().isEmpty()))
		{
			System.out.println("Could you please enter a valid data");
			string = keyboard.nextLine();
			
			if((util.quitFunction(string))== true)
			{
				return false;
			}
			
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
    
    public boolean validateEmployeeId(String id){
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
    
    public boolean validateAddress(String address){
        String pattern = "^[0-9a-zA-Z0-9_ ]{5,45}$";
        if(address.matches(pattern)){
            return true;
        }else{
        	System.out.println("please enter valid Address");
            return false;
        }
    }

}
