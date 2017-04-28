package application.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import application.MainApp;
import javafx.collections.ObservableList;

public class Writer {

	/**
	 * Writer class, writes array contents to the files
	 * 
	 * @author David Ping, Luke Waldren, Hassan Mender
	 * @version 1.00 05 Apr 2017
	 */

	private static final Logger LOGGER = Logger.getLogger("MyLog");

	public void save(ArrayList<Customer> customers) throws IOException {

		BufferedWriter writerCustomers = new BufferedWriter(new FileWriter("customerinfo.txt"));
		int i = 0;
		// A loop that writes each element of the array line by line to both
		// files.
		while (i < customers.size()) {
			// Write to customerInfo.txt
			writerCustomers.write(customers.get(i).toString());
			writerCustomers.newLine();

			i++;

		}
		LOGGER.info("customers file saved");
		writerCustomers.close();

	}

	public void saveOwner() throws IOException {
		MainApp main = new MainApp();
		BufferedWriter writer = new BufferedWriter(new FileWriter("business.txt"));
		for (int i = 0; i < main.getOwnerArray().size(); i++) {
			writer.write(main.getOwnerArray().get(i).getUsername() + ":" + main.getOwnerArray().get(i).getPassword()
					+ ":" + main.getOwnerArray().get(i).getFirstName() + ":" + main.getOwnerArray().get(i).getLastName()
					+ ":" + main.getOwnerArray().get(i).getAddress() + ":"
					+ main.getOwnerArray().get(i).getContactNumber() + ":"
					+ main.getOwnerArray().get(i).getBusinessName() + ":");
			for (int j = 0; j < main.getServiceArray().size(); j++) {
				writer.write(main.getServiceArray().get(j).getServiceName() + "-"
						+ main.getServiceArray().get(j).getDuration() + "|");
			}
			writer.newLine();
		}
		writer.close();
	}

	public void saveEmployees(ObservableList<Employee> observableList) throws IOException {

		BufferedWriter writerEmployees = new BufferedWriter(new FileWriter("employeeinfo.txt"));

		int i = 0;
		// A loop that writes each element of the array line by line to both
		// files.
		while (i < observableList.size()) {
			// Write to employeeInfo.txt
			writerEmployees.write(observableList.get(i).getId() + "|" + observableList.get(i).getFirstName() + "|"
					+ observableList.get(i).getLastName() + "|"+observableList.get(i).getEmployeeServices()+"|");
			for (int j = 0; j < observableList.get(i).getWorkTimes().size(); j++) {
				if (!(j == 0)) {
					writerEmployees.write(",");
				}
				writerEmployees.write(observableList.get(i).getWorkTimes().get(j).getDayOfWeek().toString());
				writerEmployees.write("-");
				writerEmployees.write(observableList.get(i).getWorkTimes().get(j).getStartTime().toString());
				writerEmployees.write("-");
				writerEmployees.write(observableList.get(i).getWorkTimes().get(j).getEndTime().toString());

			}
			writerEmployees.newLine();
			i++;
		}
		LOGGER.info("employees file saved");
		writerEmployees.close();

	}

	public void saveAppointments(ObservableList<Appointment>appointment) throws IOException {

		BufferedWriter writerAppointments = new BufferedWriter(new FileWriter("appointmentinfo.txt"));
		int i = 0;
		// A loop that writes each element of the array line by line to files.
		while (i < appointment.size()) {
			// Write to appointmentinfo.txt
			writerAppointments.write(appointment.get(i).toString());
			writerAppointments.newLine();
			i++;
		}
		LOGGER.info("appointments file saved");
		System.out.println("Files saved.");
		writerAppointments.close();
	}


}
