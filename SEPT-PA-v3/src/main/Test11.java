package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Test11 {

	public static void main(String[] args) {
		Main main = new Main();
		Employee employee = new Employee();
		Customer customer = new Customer();
		Owner owner = new Owner();
		Test test = new Test();
		Appointment appointment = new Appointment();
		Business business = new Business();

		// Lots of test data below this

		// Make employee

		LocalDateTime start = LocalDateTime.of(2017, 3, 15, 9, 0);// year,
																	// month,
																	// day,
																	// hour, min
		LocalDateTime start2 = LocalDateTime.of(2017, 3, 23, 9, 0);// year,
																	// month,
																	// day,
																	// hour, min
		LocalDateTime start3 = LocalDateTime.of(2017, 3, 17, 9, 0);// year,
																	// month,
																	// day,
																	// hour, min
		LocalDateTime start4 = LocalDateTime.of(2017, 3, 18, 9, 0);// year,
																	// month,
																	// day,
																	// hour, min
		LocalDateTime end = LocalDateTime.of(2017, 3, 15, 19, 0);// year, month,
																	// day,
																	// hour, min
		LocalDateTime end2 = LocalDateTime.of(2017, 3, 23, 19, 0);// year,
																	// month,
																	// day,
																	// hour, min
		LocalDateTime end3 = LocalDateTime.of(2017, 3, 17, 19, 0);// year,
																	// month,
																	// day,
																	// hour, min
		LocalDateTime end4 = LocalDateTime.of(2017, 3, 18, 19, 0);// year,
																	// month,
																	// day,
																	// hour, min

		Employee dummy = new Employee("Bob", "Fisher", "s123");
		Employee dummy2 = new Employee("Ben", "Tark", "s132");

		dummy.getStartTimes().add(start);
		dummy.getEndTimes().add(end);
		dummy.getStartTimes().add(start3);
		dummy.getEndTimes().add(end3);
		dummy.getStartTimes().add(start4);
		dummy.getEndTimes().add(end4);
		dummy2.getStartTimes().add(start2);
		dummy2.getEndTimes().add(end2);

		// Add to array in Owner
		owner.getEmployeeArray().add(dummy);
		owner.getEmployeeArray().add(dummy2);

		// Create a customer
		Customer cust = new Customer("username", "password", "firstName", "secondName", "address", "contactNumber");

		// Add to array in Main
		main.getCustomerArray().add(cust);

		LocalTime time = LocalTime.of(11, 0);
		LocalDate date = LocalDate.of(2017, 3, 8);
		LocalDateTime asd = LocalDateTime.of(date, time);

		Appointment newAppointment = new Appointment(asd, "customerUsername", "employeeId");

		customer.getAppointmentArray().add(newAppointment);

		// Loop Rules
		// Question: can the customer book on the same day as the appointment?

		// If current hour is passed closingTime go to next day
		// If current day of week is not an opening day go to the next day until
		// it is
		// go to opening hour (9:00)
		// if passed go forward 1 hour (appointmentDuration) until it is not yet
		// passed
		// Show the all available appointment hours (has employee working at
		// that time and day)
		// No other customer has booked that appointment yet
		// Do for next 7 days
		// If invalid/already booked/no worker ask them to re-enter

		// From the business class

		// Dates used
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		Boolean open, appointmentPrinted;
		int counter = 0;
		String dateAndDay, formattedTime;
		if (owner.getEmployeeArray().isEmpty()) {
			System.out.println("No employees working for this company");
			return;
		}
		if (currentTime.toLocalTime().compareTo(business.getClosingTime()) == 1) {
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
		while (counter < 10) {
			currentTime = currentTime.withHour(business.getOpeningTime().getHour());
			currentTime = currentTime.withMinute(business.getOpeningTime().getMinute());
			currentTime = currentTime.withSecond(0).withNano(0);
			while (now.getDayOfWeek().equals(currentTime.getDayOfWeek()) && now.getHour() >= currentTime.getHour()
					&& now.getDayOfYear() == currentTime.getDayOfYear()) {
				if (now.getHour() >= currentTime.getHour()) {
					currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
				}
			}
			open = null;
			for (int i = 0; i < business.getOpeningDays().length; i++) {
				if (i == business.getOpeningDays().length - 1
						&& !(business.getOpeningDays()[i].equals(currentTime.getDayOfWeek()))) {
					open = false;
				} else if (business.getOpeningDays()[i].equals(currentTime.getDayOfWeek())) {
					open = true;
					break;
				}
			}
			if (open == false) {
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
			} else if (open == true) {
				dateAndDay = currentTime.format(formatter2);
				System.out.println("================");
				System.out.println(dateAndDay);
				System.out.println("================");

				while (currentTime.toLocalTime().compareTo(business.getClosingTime()) == -1
						|| currentTime.getHour() == 0) {
					if (!(customer.getAppointmentArray().isEmpty())) {
						for (int i = 0; i < customer.getAppointmentArray().size(); i++) {
							if (customer.getAppointmentArray().get(i).getDateAndTime().equals(currentTime)) {
								currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
								i = 0;
							}
						}
					}
					appointmentPrinted = false;
					for (int j = 0; j < owner.getEmployeeArray().size(); j++) {
						if (appointmentPrinted == true) {
							appointmentPrinted = false;
							break;
						}
						for (int k = 0; k < owner.getEmployeeArray().get(j).getStartTimes().size(); k++) {
							if ((owner.getEmployeeArray().get(j).getStartTimes().get(k).compareTo(currentTime) == 0
									|| owner.getEmployeeArray().get(j).getStartTimes().get(k)
											.compareTo(currentTime) == -1)
									&& ((owner.getEmployeeArray().get(j).getEndTimes().get(k).compareTo(
											currentTime.plusMinutes(appointment.getAppointmentDuration())) == 0)
											|| owner.getEmployeeArray().get(j).getEndTimes().get(k)
													.compareTo(currentTime
															.plusMinutes(appointment.getAppointmentDuration())) == 1)) {
								formattedTime = currentTime.format(formatter);
								System.out.println(formattedTime);
								appointmentPrinted = true;
							}
						}
					}
					currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
				}
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
			}
			counter++;
		}
	}
}
