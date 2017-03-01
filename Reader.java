package task1;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Reader {

	Scanner keyboard = new Scanner(System.in);
	Driver driver = new Driver();
	
	public void read(){
		String customerFile = "customerinfo.txt";
		Scanner inputStream = null;
		
		try{
			inputStream = new Scanner(new File(customerFile));
			while(inputStream.hasNextLine()){
				String line = inputStream.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line,":");
				
				String username = stringToken.nextToken();
				String password = stringToken.nextToken();
				String firstName = stringToken.nextToken();
				String lastName = stringToken.nextToken();
				String address = stringToken.nextToken();
				String contactNumber = stringToken.nextToken();
				
				System.out.println(username+" "+password+" "+firstName+" "+lastName+" "+address+" "+contactNumber);
				
				Customer customer = new Customer(username,password,firstName,lastName,address,contactNumber);
				//eg in the text file: username:password:firstName:secondName:address:contactNumber
				driver.getCustomerArray().add(customer);
				
				//System.out.println(driver.getCustomerArray().get(0).getUsername());
				//Used this to test
				
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}

	
}
