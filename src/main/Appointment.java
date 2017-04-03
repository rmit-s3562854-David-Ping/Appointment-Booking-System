package main;

import java.time.*;

public class Appointment {

	private LocalDateTime dateAndTime;
	private int appointmentDuration = 60;// Minutes will be used
	private String customerUsername;
	private String employeeId;

	public Appointment() {
	}

	public Appointment(LocalDateTime dateAndTime, String customerUsername, String employeeId) {
		this.dateAndTime = dateAndTime;
		this.customerUsername = customerUsername;
		this.employeeId = employeeId;
	}
	
	
	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	
	public int getAppointmentDuration() {
		return appointmentDuration;
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public String getEmployeeId() {
		return employeeId;
	}
	
	public String toString() {
 		return this.getCustomerUsername()+ "|" +this.getEmployeeId()+ "|" +this.getDateAndTime();
 	}
}
