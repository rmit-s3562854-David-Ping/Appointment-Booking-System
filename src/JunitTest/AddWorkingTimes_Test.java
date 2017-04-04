package JunitTest;

import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.*;


public class AddWorkingTimes_Test {

	@BeforeClass
	public static void setupEmployee() {
		Owner owner = new Owner();
		ArrayList<LocalDateTime> startTimes = new ArrayList<LocalDateTime>();
		ArrayList<LocalDateTime> endTimes = new ArrayList<LocalDateTime>();
		// Make an existing work time for the employee
		Employee dummyEmployee = new Employee("Fname", "Lname", "e00001", startTimes, endTimes);
		owner.getEmployeeArray().add(dummyEmployee);
	}

	@After
	public void clearWorkTimes() {
		Owner owner = new Owner();
		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			owner.getEmployeeArray().get(i).getStartTimes().clear();
			owner.getEmployeeArray().get(i).getEndTimes().clear();
		}
	}

	// Checks if a past date returns false (2016)
	@Test
	public void test() {
		Utility util = new Utility();
		LocalDateTime startTime = LocalDateTime.of(2016, 3, 28, 12, 0);
		LocalDateTime endTime = LocalDateTime.of(2016, 3, 28, 15, 0);
		assertFalse(util.validateNewWorkTime("e00001", startTime, endTime));
	}

	// Checks if valid times for working returns true (uses opening time and
	// closing time of business)
	// Also checks if boundary is valid (it is)
	@Test
	public void test2() {
		Business business = new Business();
		Utility util = new Utility();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(business.getOpeningTime().getHour())
				.withMinute(business.getOpeningTime().getMinute()).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1).withHour(business.getClosingTime().getHour())
				.withMinute(business.getClosingTime().getMinute()).withSecond(0).withNano(0);
		assertTrue(util.validateNewWorkTime("e00001", startTime, endTime));
		clearWorkTimes();
	}

	// Checks if the employees ending time is before the starting time, this
	// should return a false
	@Test
	public void test3() {
		Utility util = new Utility();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
		assertFalse(util.validateNewWorkTime("e00001", startTime, endTime));
	}

	// Tests if starting before the business is open is false
	@Test
	public void test4() {
		Utility util = new Utility();
		Business business = new Business();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1)
				.withHour(business.getOpeningTime().minusHours(4).getHour()).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1)
				.withHour(business.getOpeningTime().minusHours(1).getHour()).withMinute(0).withSecond(0).withNano(0);
		assertFalse(util.validateNewWorkTime("e00001", startTime, endTime));
	}

	// Owner cannot assign work times more than 1 month ahead (exactly one month
	// is ok)
	
	//Tests exactly 1 month ahead
	@Test
	public void test5() {
		Utility util = new Utility();
		LocalDateTime startTime = LocalDateTime.now().plusMonths(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusMonths(1).withHour(12).withMinute(0).withSecond(0).withNano(0);
		assertTrue(util.validateNewWorkTime("e00001", startTime, endTime));
	}

	// Tests for 1 month plus 1 day, should return false
	@Test
	public void test51() {
		Utility util = new Utility();
		LocalDateTime startTime = LocalDateTime.now().plusMonths(1).withHour(8).withMinute(0).withSecond(0).withNano(0)
				.plusDays(1);
		LocalDateTime endTime = LocalDateTime.now().plusMonths(1).withHour(12).withMinute(0).withSecond(0).withNano(0)
				.plusDays(1);
		assertFalse(util.validateNewWorkTime("e00001", startTime, endTime));
	}

	// Tests for 1 month minus 1 day, should return true
	@Test
	public void test52() {
		Utility util = new Utility();
		LocalDateTime startTime = LocalDateTime.now().plusMonths(1).withHour(8).withMinute(0).withSecond(0).withNano(0).minusDays(1);
		LocalDateTime endTime = LocalDateTime.now().plusMonths(1).withHour(12).withMinute(0).withSecond(0).withNano(0).minusDays(1);
		assertTrue(util.validateNewWorkTime("e00001", startTime, endTime));
	}

	// Working time must be at least 3 hours
	// Test for 3 hours
	@Test
	public void test6() {
		Utility util = new Utility();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
		assertTrue(util.validateNewWorkTime("e00001", startTime, endTime));
		clearWorkTimes();
	}

	// Test 2 hours 59 minutes, should return false
	@Test
	public void test61() {
		Utility util = new Utility();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(8).withMinute(1).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
		assertFalse(util.validateNewWorkTime("e00001", startTime, endTime));
	}

	// Test for 3 hours 1 minute, should return true
	@Test
	public void test62() {
		Utility util = new Utility();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1).withHour(11).withMinute(1).withSecond(0).withNano(0);
		assertTrue(util.validateNewWorkTime("e00001", startTime, endTime));
		clearWorkTimes();
	}

	// Tests for working time to an existing working day, should return false
	@Test
	public void test7() {
		Utility util = new Utility();
		Business business = new Business();

		LocalDateTime startTime = LocalDateTime.now().plusDays(3)
				.withHour(business.getOpeningTime().plusHours(1).getHour());
		LocalDateTime endTime = LocalDateTime.now().plusDays(3)
				.withHour(business.getOpeningTime().plusHours(4).getHour());
		util.validateNewWorkTime("e00001", startTime, endTime);
		assertFalse(util.validateNewWorkTime("e00001", startTime, endTime));
	}

}
