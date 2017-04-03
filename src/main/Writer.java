package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Writer {
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
		System.out.println("Files saved.");
		writerCustomers.close();


	}
	
	
	public void saveEmployees(ArrayList<Employee> employees) throws IOException {

		BufferedWriter writerEmployees = new BufferedWriter(new FileWriter("Employeeinfo.txt"));

		int i = 0;
		// A loop that writes each element of the array line by line to both
		// files.
		while (i < employees.size()) {
			// Write to employeeInfo.txt
						writerEmployees.write(employees.get(i).getId()+"|"+employees.get(i).getFirstName()+"|"+employees.get(i).getLastName()+"|");
						for(int j=0;j<employees.get(i).getStartTimes().size();j++){
							if(!(j==0)){
								writerEmployees.write(",");
							}
							writerEmployees.write(employees.get(i).getStartTimes().get(j).toString());
						}
						writerEmployees.write("|");
						for(int j=0;j<employees.get(i).getEndTimes().size();j++){
							if(!(j==0)){
								writerEmployees.write(",");
							}
							writerEmployees.write(employees.get(i).getEndTimes().get(j).toString());
						}
						
						writerEmployees.newLine();

						i++;

		}
		LOGGER.info("employees file saved");
		System.out.println("Files saved.");
		writerEmployees.close();

	}
  
  	public void saveAppointments(ArrayList<Appointment> appointment) throws IOException {
		
		BufferedWriter writerAppointments = new BufferedWriter(new FileWriter("appointmentinfo.txt"));
		int i = 0;
		// A loop that writes each element of the array line by line to files.
		while(i < appointment.size()){
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

