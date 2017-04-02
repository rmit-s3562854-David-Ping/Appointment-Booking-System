package main;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader {

	Scanner keyboard = new Scanner(System.in);
	Main main = new Main();
	Owner owner = new Owner();
	private static final Logger LOGGER = Logger.getLogger("MyLog");
	public void read() {
		String customerFile = "customerinfo.txt";
		String ownerFile = "business.txt";
		String employeeFile = "Employeeinfo.txt";
		Scanner inputStream = null;
		Scanner inputStream2 = null;
		Scanner inputStream3 = null;

		try {
			inputStream = new Scanner(new File(customerFile));
			inputStream2 = new Scanner(new File(ownerFile));
			inputStream3 = new Scanner(new File(employeeFile));
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
			
			while (inputStream3.hasNextLine()){
				String line = inputStream3.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line,"|");
				
				String id = stringToken.nextToken();
				String firstName = stringToken.nextToken();
				String lastName = stringToken.nextToken();
				
				Employee employee = new Employee(firstName, lastName, id, null ,null);
				
				if (stringToken.hasMoreTokens()) {
					String startTimes = stringToken.nextToken();

					StringTokenizer stringToken2 = new StringTokenizer(startTimes, ",");

					while (stringToken2.hasMoreTokens()) {
						LocalDateTime newStartTime = LocalDateTime.parse(stringToken2.nextToken());
						employee.getStartTimes().add(newStartTime);
					}

					String endTimes = stringToken.nextToken();
					StringTokenizer stringToken3 = new StringTokenizer(endTimes, ",");
					while (stringToken3.hasMoreTokens()) {
						LocalDateTime newEndTime = LocalDateTime.parse(stringToken3.nextToken());
						employee.getEndTimes().add(newEndTime);
					}
					
					
				}
				
				owner.getEmployeeArray().add(employee);
				
			}
		} catch (Exception e) {
			System.out.println(e);
			LOGGER.log( Level.SEVERE, e.toString(), e );
		} finally {
			inputStream.close();
			inputStream2.close();
			inputStream3.close();
		}

		LOGGER.info("Files read.");
	}
}
