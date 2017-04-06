package JunitTest;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.*;

public class ValidateAvailableTime_Test {

	/**
	 * Test validateAvailableTime(LocalDateTime) to ensure that
	 * 1. there is an employee available to work on that day
	 * 2. the employee does not already have an appointment at the time
	 * @author David Ping
	 * */
	
	/**
	 * Sets up an employee that works on Monday from openingTime - openingTime(plus 3 hours)
	 * eg 8:00AM-11:00AM
	 * Sets up an appointment on next weeks Monday at opening time
	 */
	@Before
	public void setupEmployeeAndAppointment() {
		Owner owner = new Owner();
		Main main = new Main();
		Business business = new Business();
		LocalTime monday = LocalTime.of(business.getOpeningTime().getHour(), 0);
		LocalTime monday2 = LocalTime.of(business.getOpeningTime().plusHours(3).getHour(), 0);
		ArrayList<LocalTime> startTimeList = new ArrayList<LocalTime>();
		ArrayList<LocalTime> endTimeList = new ArrayList<LocalTime>();
		for (int i = 0; i < 7; i++) {
			startTimeList.add(null);
			endTimeList.add(null);
		}
		startTimeList.set(0, monday);
		endTimeList.set(0, monday2);
		Employee employee = new Employee("Ben", "Rick", "e00001", startTimeList, endTimeList);
		owner.getEmployeeArray().add(employee);
		LocalDateTime dateAndTime = LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withSecond(0).withNano(0)
				.withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().getMinute());
		Appointment appointment = new Appointment(dateAndTime, "username", "e00001");
		main.getAppointmentArray().add(appointment);
	}

	/**
	 * Tests whether or not the method accepts a dateTime when the employee
	 * already has an appointment on that day
	 */
	@Test
	public void test() {
		Business business = new Business();
		Customer customer = new Customer();
		LocalDateTime dateAndTime = LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withSecond(0).withNano(0)
				.withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().getMinute());
		assertFalse(customer.validateAvailableTime(dateAndTime));
	}

	/**
	 * Tests whether or not the method accepts a dateTime when employee is
	 * available and no appointments on that day
	 */
	@Test
	public void test2() {
		Customer customer = new Customer();
		Business business = new Business();
		LocalDateTime dateAndTime = LocalDateTime.now().with(DayOfWeek.MONDAY).withSecond(0).withNano(0)
				.withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().getMinute());
		assertTrue(customer.validateAvailableTime(dateAndTime));
	}

	/**
	 * Tests whether or not the method accepts a dateTime when no employee is
	 * working on that day (Tuesday)
	 */
	@Test
	public void test3() {
		Customer customer = new Customer();
		Business business = new Business();
		LocalDateTime dateAndTime = LocalDateTime.now().with(DayOfWeek.TUESDAY).withSecond(0).withNano(0)
				.withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().getMinute());
		assertFalse(customer.validateAvailableTime(dateAndTime));
	}
	
	/**
	 * Tests whether or not the method accepts a dateTime when no employee is
	 * working on that day (Wednesday)
	 */
	@Test
	public void test4() {
		Customer customer = new Customer();
		Business business = new Business();
		LocalDateTime dateAndTime = LocalDateTime.now().with(DayOfWeek.WEDNESDAY).withSecond(0).withNano(0)
				.withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().getMinute());
		assertFalse(customer.validateAvailableTime(dateAndTime));
	}
	
	/**
	 * Tests whether or not the method accepts a dateTime when no employee is
	 * working on that day (Thursday)
	 */
	@Test
	public void test5() {
		Customer customer = new Customer();
		Business business = new Business();
		LocalDateTime dateAndTime = LocalDateTime.now().with(DayOfWeek.THURSDAY).withSecond(0).withNano(0)
				.withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().getMinute());
		assertFalse(customer.validateAvailableTime(dateAndTime));
	}
	
	/**
	 * Tests whether or not the method accepts a dateTime when no employee is
	 * working on that day (Friday)
	 */
	@Test
	public void test6() {
		Customer customer = new Customer();
		Business business = new Business();
		LocalDateTime dateAndTime = LocalDateTime.now().with(DayOfWeek.FRIDAY).withSecond(0).withNano(0)
				.withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().getMinute());
		assertFalse(customer.validateAvailableTime(dateAndTime));
	}
	
	
	
	

}
