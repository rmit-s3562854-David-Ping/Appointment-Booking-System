package main;

import java.time.*;

public class Appointment {

	/**
	 * Appointment class contains attributes of appointment
	 *
	 * @version 1.00 05 Mar 2017
	 * @author David Ping
	 */

	private LocalDateTime dateAndTime;
	private int appointmentDuration = 60;
	// (Minutes) used, will join constructor when services are made
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

	/*
	 * to write Appointment info
	 * customer|employee|appointment datetime
	 * @author Hassen Mender
	 */

	public String toString() {
		return this.getCustomerUsername() + "|" + this.getEmployeeId() + "|" + this.getDateAndTime();
	}
}
