package application.main;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Reader {

	/**
	 * Reader class, reads input from files found next to the src folder and
	 * stores them into appropriate arrays
	 * 
	 * @version 1.00 05 Apr 2017
	 * @author David Ping, Luke Waldren, Hassan Mender
	 */

	Scanner keyboard = new Scanner(System.in);
	MainApp main = new MainApp();
	Owner owner = new Owner();
	private static final Logger LOGGER = Logger.getLogger("MyLog");

	// Reads user files (business and customer). Dont read the business files
	// until we know which business it is.
	public void readUsers() {
		String customerFile = "customerinfo.txt";
		String ownerFile = "business.txt";
		String adminFile = "admins.txt";
		Scanner inputStream = null;
		Scanner inputStream2 = null;
		Scanner inputStream3 = null;
		try {
			inputStream = new Scanner(new File(customerFile));
			inputStream2 = new Scanner(new File(ownerFile));
			inputStream3 = new Scanner(new File(adminFile));

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

				main.getOwnerArray().add(owner);
			}
			while (inputStream3.hasNextLine()) {
				String line = inputStream3.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, ":");

				String username = stringToken.nextToken();
				String password = stringToken.nextToken();
				String firstName = stringToken.nextToken();
				String lastName = stringToken.nextToken();
				String address = stringToken.nextToken();
				String contactNumber = stringToken.nextToken();
				Admin admin = new Admin(username, password, firstName, lastName, address, contactNumber);

				main.getAdminArray().add(admin);
			}

		} catch (Exception e) {
			System.out.println(e);
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			inputStream.close();
			inputStream2.close();
		}

		LOGGER.info("Files read.");
	}

	// Reads the business files. Is called when owner logs in or customer picks
	// a business.
	public void readBusiness(String business) {
		// Save businessName for future use for writer etc.
		main.setBusinessName(business);
		String employeeFile = "businesses/" + business + "/employeeinfo.txt";
		String apptFile = "businesses/" + business + "/appointmentinfo.txt";
		String servicesFile = "businesses/" + business + "/servicesinfo.txt";
		String workTimesFile = "businesses/" + business + "/worktimesinfo.txt";

		Scanner inputStream3 = null;
		Scanner inputStream4 = null;
		Scanner inputStream5 = null;
		Scanner inputStream6 = null;

		try {

			inputStream3 = new Scanner(new File(employeeFile));
			inputStream4 = new Scanner(new File(apptFile));
			inputStream5 = new Scanner(new File(servicesFile));
			inputStream6 = new Scanner(new File(workTimesFile));

			while (inputStream3.hasNextLine()) {
				String line = inputStream3.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, "|");

				String id = stringToken.nextToken();
				String firstName = stringToken.nextToken();
				String lastName = stringToken.nextToken();
				String servicesString = stringToken.nextToken().replace("[", "").replace("]", "");
				String workTimes = null;
				if (stringToken.hasMoreTokens()) {
					workTimes = stringToken.nextToken();
				}

				List<WorkTime> workingTimes = new ArrayList<WorkTime>();
				ObservableList<String> services = FXCollections.observableArrayList();

				if (workTimes != null) {
					StringTokenizer stringToken2 = new StringTokenizer(workTimes, ",");
					while (stringToken2.hasMoreTokens()) {
						String workDayTime = stringToken2.nextToken();
						StringTokenizer stringToken3 = new StringTokenizer(workDayTime, "-");
						while (stringToken3.hasMoreTokens()) {
							DayOfWeek day = DayOfWeek.valueOf(stringToken3.nextToken());
							LocalTime startTime = LocalTime.parse(stringToken3.nextToken());
							LocalTime endTime = LocalTime.parse(stringToken3.nextToken());

							WorkTime newWorkTime = new WorkTime(day, startTime, endTime);
							workingTimes.add(newWorkTime);
						}
					}
				}

				if (servicesString != "") {
					String[] sArray = servicesString.split(", ");
					for (int i = 0; i < sArray.length; i++) {
						String service = sArray[i];
						services.add(service);
					}
				}
				Employee employee = new Employee(firstName, lastName, id, workingTimes, services);
				main.getEmployeeData().add(employee);
			}

			while (inputStream4.hasNextLine()) {
				String line = inputStream4.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, "|");

				String customerUsername = stringToken.nextToken();
				String employeeId = stringToken.nextToken();
				String apptTimes = stringToken.nextToken();
				LocalDateTime appointmentTime = LocalDateTime.parse(apptTimes);
				String serviceName = stringToken.nextToken();
				Appointment appointment = new Appointment(appointmentTime, customerUsername, employeeId, serviceName);

				main.getAppointmentArray().add(appointment);

			}

			while (inputStream5.hasNextLine()) {
				String line = inputStream5.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, "|");
				while (stringToken.hasMoreTokens()) {
					String services = stringToken.nextToken();
					StringTokenizer stringToken2 = new StringTokenizer(services, "-");
					String serviceName = stringToken2.nextToken();
					String durationLength = stringToken2.nextToken();
					int duration = Integer.parseInt(durationLength);
					Service service = new Service(serviceName, duration);
					main.getServiceArray().add(service);
				}
			}
			
			while(inputStream6.hasNextLine()){
				String line = inputStream6.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, "|");
				while(stringToken.hasMoreTokens()){
					String workTimes = stringToken.nextToken();
					StringTokenizer stringToken2 = new StringTokenizer(workTimes, "-");
					DayOfWeek dayOfWeek = DayOfWeek.valueOf(stringToken2.nextToken());
					LocalTime startTime = LocalTime.parse(stringToken2.nextToken());
					LocalTime endTime = LocalTime.parse(stringToken2.nextToken());
					WorkTime newWorkTime = new WorkTime(dayOfWeek, startTime, endTime);
					main.getBusinessWorkTimes().add(newWorkTime);
				}
			}

		} catch (Exception e) {
			System.out.println(e);
			// LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			inputStream3.close();
			inputStream4.close();
			inputStream5.close();
			inputStream6.close();
		}

		LOGGER.info("Files read.");
	}
}
