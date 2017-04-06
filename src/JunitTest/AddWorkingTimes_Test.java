package JunitTest;

import static org.junit.Assert.*;
import java.time.LocalTime;

import org.junit.Test;

import main.*;


public class AddWorkingTimes_Test {
	/**
	 * Tests validateNewWorkTime(LocalTime startTime, LocalTime endTime) function
	 * takes in two LocalTime values and checks whether or not it is valid work times for employees
	 * @author David Ping
	 * */
	

	// Checks if valid times for working, returns true (uses opening time and
	// closing time of business)
	// Also checks if boundary is valid (it is)
	@Test
	public void test() {
		Business business = new Business();
		Utility util = new Utility();
		LocalTime startTime = LocalTime.now().withHour(business.getOpeningTime().getHour())
				.withMinute(business.getOpeningTime().getMinute()).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(business.getClosingTime().getHour())
				.withMinute(business.getClosingTime().getMinute()).withSecond(0).withNano(0);
		assertTrue(util.validateNewWorkTime(startTime, endTime));
	}
	
	
	//Checks if 1 nanosecond past closing time is valid, should return false
	@Test
	public void test1() {
		Business business = new Business();
		Utility util = new Utility();
		LocalTime startTime = LocalTime.now().withHour(business.getOpeningTime().getHour())
				.withMinute(business.getOpeningTime().getMinute()).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(business.getClosingTime().getHour())
				.withMinute(business.getClosingTime().getMinute()).withSecond(0).withNano(1);
		assertFalse(util.validateNewWorkTime(startTime, endTime));
	}
	
	//Checks if 1 nanosecond before opening time is valid, should return false
		@Test
		public void test2() {
			Business business = new Business();
			Utility util = new Utility();
			LocalTime startTime = LocalTime.now().withHour(business.getOpeningTime().getHour())
					.withMinute(business.getOpeningTime().getMinute()).withSecond(0).withNano(0).minusNanos(1);
			LocalTime endTime = LocalTime.now().withHour(business.getClosingTime().getHour())
					.withMinute(business.getClosingTime().getMinute()).withSecond(0).withNano(0);
			assertFalse(util.validateNewWorkTime(startTime, endTime));
		}

	// Test for ending time being before the starting time, this
	// should return a false
	@Test
	public void test3() {
		Utility util = new Utility();
		Business business = new Business();
		LocalTime startTime = LocalTime.now().withHour(business.getOpeningTime().plusHours(4).getHour()).withMinute(0).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(business.getOpeningTime().getHour()).withMinute(0).withSecond(0).withNano(0);
		assertFalse(util.validateNewWorkTime(startTime, endTime));
	}

	// Tests for starting time that is before the business is open, returns false
	@Test
	public void test4() {
		Utility util = new Utility();
		Business business = new Business();
		LocalTime startTime = LocalTime.now()
				.withHour(business.getOpeningTime().minusHours(4).getHour());
		LocalTime endTime = LocalTime.now()
				.withHour(business.getOpeningTime().minusHours(1).getHour());
		assertFalse(util.validateNewWorkTime(startTime, endTime));
	}

	// Working time must be at least 3 hours
	
	// Test for 3 hours, should return true
	@Test
	public void test5() {
		Utility util = new Utility();
		Business business = new Business();
		LocalTime startTime = LocalTime.now().withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().getMinute()).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(business.getOpeningTime().plusHours(3).getHour()).withMinute(business.getOpeningTime().getMinute()).withSecond(0).withNano(0);
		assertTrue(util.validateNewWorkTime(startTime, endTime));
	}

	// Test 2 hours 59 minutes, should return false
	@Test
	public void test6() {
		Utility util = new Utility();
		Business business = new Business();
		LocalTime startTime = LocalTime.now().withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().plusMinutes(1).getMinute()).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(business.getOpeningTime().plusHours(3).getHour()).withMinute(business.getOpeningTime().getMinute()).withSecond(0).withNano(0);
		assertFalse(util.validateNewWorkTime(startTime, endTime));
	}

	// Test for 3 hours 1 minute, should return true
	@Test
	public void test7() {
		Utility util = new Utility();
		Business business = new Business();
		LocalTime startTime = LocalTime.now().withHour(business.getOpeningTime().getHour()).withMinute(business.getOpeningTime().getMinute()).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(business.getOpeningTime().plusHours(3).getHour()).withMinute(business.getOpeningTime().plusMinutes(1).getMinute()).withSecond(0).withNano(0);
		assertTrue(util.validateNewWorkTime(startTime, endTime));
	}
}
