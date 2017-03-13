package main;

import java.time.*;

public class Appointment {

	private LocalDateTime dateAndTime;
	private int appointmentDuration = 60;//Minutes will be used


	public Appointment() {
	}

	public Appointment(LocalDateTime dateAndTime, Customer customer, Employee employee) {
		this.dateAndTime=dateAndTime;
	}

	public LocalDateTime getDateAndTime(){
		return dateAndTime;
	}
	
	public int getAppointmentDuration(){
		return appointmentDuration;
	}

}
