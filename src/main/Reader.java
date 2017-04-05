package main;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader {
	
	/**
	 * Reader class, reads input from files found next to the src folder and stores them into appropriate arrays
	 * @version 1.00 05 Apr 2017
	 * @author David Ping, Luke Waldren, Hassan Mender
	 * */

	Scanner keyboard = new Scanner(System.in);
	Main main = new Main();
	Owner owner = new Owner();
	private static final Logger LOGGER = Logger.getLogger("MyLog");

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

			while (inputStream3.hasNextLine()) {
				String line = inputStream3.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, "|");
				int dayOfWeek = 0;
				
				String id = stringToken.nextToken();
				String firstName = stringToken.nextToken();
				String lastName = stringToken.nextToken();
				String startTimes = stringToken.nextToken();
				String endTimes = stringToken.nextToken();
				ArrayList<LocalTime> startTimeList = new ArrayList<LocalTime>();
				ArrayList<LocalTime> endTimeList = new ArrayList<LocalTime>();
				for(int i=0;i<7;i++){
					startTimeList.add(null);
					endTimeList.add(null);
				}
				
				StringTokenizer stringToken2 = new StringTokenizer(startTimes, ",");

				while (stringToken2.hasMoreTokens()) {
					LocalTime newStartTime = LocalTime.parse(stringToken2.nextToken());
					startTimeList.set(dayOfWeek, newStartTime);
					dayOfWeek++;
				}
				dayOfWeek=0;
				StringTokenizer stringToken3 = new StringTokenizer(endTimes, ",");
				while (stringToken3.hasMoreTokens()) {
					LocalTime newEndTime = LocalTime.parse(stringToken3.nextToken());
					endTimeList.set(dayOfWeek, newEndTime);
					dayOfWeek++;
				}
				Employee employee = new Employee(firstName, lastName, id, startTimeList, endTimeList);
				owner.getEmployeeArray().add(employee);
			}

			while (inputStream4.hasNextLine()) {
				String line = inputStream4.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, "|");

				String CustomerName = stringToken.nextToken();
				String EmployeeId = stringToken.nextToken();
				String apptTimes = stringToken.nextToken();
				LocalDateTime appointmentTime = LocalDateTime.parse(apptTimes);

				Appointment appointment = new Appointment(appointmentTime, CustomerName, EmployeeId);

				main.getAppointmentArray().add(appointment);

			}

		} catch (Exception e) {
			System.out.println(e);
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			inputStream.close();
			inputStream2.close();
			inputStream3.close();
			inputStream4.close();
		}

		LOGGER.info("Files read.");
	}
}
