package main;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Reader {

	Scanner keyboard = new Scanner(System.in);
	Main main = new Main();
	Owner owner = new Owner();

	public void read() {
		String customerFile = "customerinfo.txt";
		String ownerFile = "business.txt";
		String employeeFile = "Employeeinfo.txt";
		String apptFile = "appointmentinfo.txt";
		Scanner inputStream = null;
		Scanner inputStream2 = null;
		Scanner inputStream3 = null;
		Scanner inputStream4 = null;

		try {
			inputStream = new Scanner(new File(customerFile));
			inputStream2 = new Scanner(new File(ownerFile));
			inputStream3 = new Scanner(new File(employeeFile));
			inputStream4 = new Scanner(new File(apptFile));
			
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
			
			while(inputStream4.hasNextLine()){
				String line = inputStream4.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line,"|");
				
				String CustomerName = stringToken.nextToken();
				String EmployeeId = stringToken.nextToken();
				String apptTimes = stringToken.nextToken();
				LocalDateTime appointmentTime = LocalDateTime.parse(apptTimes);
				
				Appointment appointment = new Appointment(appointmentTime, CustomerName, EmployeeId);
				
				main.getAppointmentArray().add(appointment);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			inputStream.close();
			inputStream2.close();
			inputStream3.close();
			inputStream4.close();
		}

	}

}
