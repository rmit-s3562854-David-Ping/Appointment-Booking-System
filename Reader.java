package task1;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Reader {

	Scanner keyboard = new Scanner(System.in);
	Driver driver = new Driver();

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

				/*System.out.println(username + " " + password + " " + firstName + " " + lastName + " " + address + " "
						+ contactNumber);*/

				Customer customer = new Customer(username, password, firstName, lastName, address, contactNumber);
				// eg in the text file:
				// username:password:firstName:secondName:address:contactNumber
				driver.getCustomerArray().add(customer);
				// System.out.println(driver.getCustomerArray().get(0).getUsername());
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

				/*System.out.println(username + " " + password + " " + firstName + " " + lastName + " " + address + " "
						+ contactNumber+" "+businessName);*/

				Owner owner = new Owner(username, password, firstName, lastName, address, contactNumber, businessName);
				// eg in the text file:
				// username:password:firstName:secondName:address:contactNumber:businessName
				driver.getOwnerArray().add(owner);
				System.out.println(driver.getOwnerArray().get(0).getBusinessName());
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			inputStream.close();
			inputStream2.close();
		}

	}

}
