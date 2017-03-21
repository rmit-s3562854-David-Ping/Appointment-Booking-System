package main;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Reader {

	Scanner keyboard = new Scanner(System.in);
	Main main = new Main();

	public void read() {
		String customerFile = "customerinfo.txt";
		String ownerFile = "business.txt";
		Scanner inputStream = null;
		Scanner inputStream2 = null;

		try {
			inputStream = new Scanner(new File(customerFile));
			inputStream2 = new Scanner(new File(ownerFile));
			while (inputStream.hasNextLine()) {
				String line = inputStream.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, ":");

				String username = stringToken.nextToken();
				String password = stringToken.nextToken();
				String firstName = stringToken.nextToken();
				String lastName = stringToken.nextToken();
				String address = stringToken.nextToken();
				String contactNumber = stringToken.nextToken();

				Customer customer = new Customer(username, password, firstName, lastName, address, contactNumber);
				// eg in the text file:
				// username:password:firstName:secondName:address:contactNumber
				main.getCustomerArray().add(customer);
			}
			
			while (inputStream2.hasNextLine()) {
				String line = inputStream2.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, ":");

				String username = stringToken.nextToken();
				String password = stringToken.nextToken();
				String firstName = stringToken.nextToken();
				String lastName = stringToken.nextToken();
				String address = stringToken.nextToken();
				String contactNumber = stringToken.nextToken();
				String businessName = stringToken.nextToken();

				Owner owner = new Owner(username, password, firstName, lastName, address, contactNumber, businessName);
				// eg in the text file:
				// username:password:firstName:secondName:address:contactNumber:businessName
				main.getOwnerArray().add(owner);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			inputStream.close();
			inputStream2.close();
		}

	}

}
