package JunitTest;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import main.*;

public class ValidateUpcomingBookingDate_Test {


	/**
	 * Tests if the date being considered for upcoming bookings is after the
	 * current date and up to 1 week ahead
	 * Used boundary checking to see if working as intended
	 * 
	 * @author David Ping
	 * 
	 * Latest test date: 8/4/2017 
	 * Status: Passed
	 */

	// Checks if current date which is valid
	@Test
	public void test() {
		Owner owner = new Owner();
		assertTrue(owner.validateUpcomingBookingDate(LocalDate.now()));
	}

	// Checks if day after the current date is valid
	@Test
	public void test1() {
		Owner owner = new Owner();
		assertTrue(owner.validateUpcomingBookingDate(LocalDate.now().plusDays(1)));
	}

	// Checks if the day before current date is invalid
	@Test
	public void test2() {
		Owner owner = new Owner();
		assertFalse(owner.validateUpcomingBookingDate(LocalDate.now().minusDays(1)));
	}

	// Checks if the date one week ahead is valid
	@Test
	public void test3() {
		Owner owner = new Owner();
		assertTrue(owner.validateUpcomingBookingDate(LocalDate.now().plusWeeks(1)));
	}

	// Checks if the date one week ahead plus 1 day is invalid (more than a
	// week)
	@Test
	public void test4() {
		Owner owner = new Owner();
		assertFalse(owner.validateUpcomingBookingDate(LocalDate.now().plusWeeks(1).plusDays(1)));
	}

	// Checks if the date one week ahead minus 1 day is valid
	@Test
	public void test5() {
		Owner owner = new Owner();
		assertTrue(owner.validateUpcomingBookingDate(LocalDate.now().plusWeeks(1).minusDays(1)));
	}

}
