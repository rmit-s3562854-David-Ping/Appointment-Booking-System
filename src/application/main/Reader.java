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

	public void read() {
		String customerFile = "customerinfo.txt";
		String ownerFile = "business.txt";
		String employeeFile = "employeeinfo.txt";
		String apptFile = "appointmentinfo.txt";
		String servFile = "servicesinfo.txt";
		Scanner inputStream = null;
		Scanner inputStream2 = null;
		Scanner inputStream3 = null;
		Scanner inputStream4 = null;
		Scanner inputStream5 = null;

		try {
			inputStream = new Scanner(new File(customerFile));
			inputStream2 = new Scanner(new File(ownerFile));
			inputStream3 = new Scanner(new File(employeeFile));
			inputStream4 = new Scanner(new File(apptFile));
			inputStream5 = new Scanner(new File(servFile));

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
				String services = stringToken.nextToken();

				StringTokenizer stringToken2 = new StringTokenizer(services, "|");
				while (stringToken2.hasMoreTokens()) {
					StringTokenizer stringToken3 = new StringTokenizer(stringToken2.nextToken(), "-");
					String serviceName = stringToken3.nextToken();
					String durationLength = stringToken3.nextToken();
					int duration = Integer.parseInt(durationLength);
					Service service = new Service(serviceName, duration);
					main.getServiceArray().add(service);
				}

				Owner owner = new Owner(username, password, firstName, lastName, address, contactNumber, businessName);

				main.getOwnerArray().add(owner);
			}

			while (inputStream3.hasNextLine()) {
				String line = inputStream3.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, "|");

				String id = stringToken.nextToken();
				String firstName = stringToken.nextToken();
				String lastName = stringToken.nextToken();
				String servicesString = stringToken.nextToken().replace("[", "").replace ("]", "");
				String workTimes=null;
				if(stringToken.hasMoreTokens()){
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
				
				if(servicesString!=""){
					StringTokenizer stringToken4 = new StringTokenizer(servicesString, ", ");
					while(stringToken4.hasMoreTokens()){
						String service = stringToken4.nextToken();
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
			
			while (inputStream5.hasNextLine()){
				String line = inputStream5.nextLine();
				StringTokenizer stringToken = new StringTokenizer(line, "-");
				
				String serviceName = stringToken.nextToken();
				String length = stringToken.nextToken();
				int duration = Integer.parseInt(length);
				Service service = new Service(serviceName, duration);
				
				main.getServiceArray().add(service);
				
			}

		} catch (Exception e) {
			System.out.println(e);
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			inputStream.close();
			inputStream2.close();
			inputStream3.close();
			inputStream4.close();
			inputStream5.close();
		}

		LOGGER.info("Files read.");
	}
}
