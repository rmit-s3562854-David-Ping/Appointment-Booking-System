package JunitTest;

import static org.junit.Assert.*;
import java.time.LocalTime;

import org.junit.After;
import org.junit.Test;

import main.*;


public class AddWorkingTimes_Test {

	@After
	public void clearWorkTimes() {
		Owner owner = new Owner();
		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			owner.getEmployeeArray().get(i).getStartTimes().clear();
			owner.getEmployeeArray().get(i).getEndTimes().clear();
		}
	}

	// Checks if valid times for working returns true (uses opening time and
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

	// Checks if the employees ending time is before the starting time, this
	// should return a false
	@Test
	public void test2() {
		Utility util = new Utility();
		LocalTime startTime = LocalTime.now().withHour(18).withMinute(0).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(8).withMinute(0).withSecond(0).withNano(0);
		assertFalse(util.validateNewWorkTime(startTime, endTime));
	}

	// Tests if starting before the business is open is false
	@Test
	public void test3() {
		Utility util = new Utility();
		Business business = new Business();
		LocalTime startTime = LocalTime.now()
				.withHour(business.getOpeningTime().minusHours(4).getHour()).withMinute(0).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now()
				.withHour(business.getOpeningTime().minusHours(1).getHour()).withMinute(0).withSecond(0).withNano(0);
		assertFalse(util.validateNewWorkTime(startTime, endTime));
	}

	// Owner cannot assign work times more than 1 month ahead (exactly one month
	// is ok)

	// Working time must be at least 3 hours
	// Test for 3 hours
	@Test
	public void test4() {
		Utility util = new Utility();
		LocalTime startTime = LocalTime.now().withHour(8).withMinute(0).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(11).withMinute(0).withSecond(0).withNano(0);
		assertTrue(util.validateNewWorkTime(startTime, endTime));
	}

	// Test 2 hours 59 minutes, should return false
	@Test
	public void test5() {
		Utility util = new Utility();
		LocalTime startTime = LocalTime.now().withHour(8).withMinute(1).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(11).withMinute(0).withSecond(0).withNano(0);
		assertFalse(util.validateNewWorkTime(startTime, endTime));
	}

	// Test for 3 hours 1 minute, should return true
	@Test
	public void test6() {
		Utility util = new Utility();
		LocalTime startTime = LocalTime.now().withHour(8).withMinute(0).withSecond(0).withNano(0);
		LocalTime endTime = LocalTime.now().withHour(11).withMinute(1).withSecond(0).withNano(0);
		assertTrue(util.validateNewWorkTime(startTime, endTime));
	}
}
