package JunitTest;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import main.*;

public class ValidateBookingSummary_Test {
	
	/**
	 * Tests validateBookingSummaryDate(LocalDate date) to determine if the date is between the current date 
	 * and 1 month ago
	 * @author David Ping
	 * 
	 * Latest test date: 8/4/2017
	 * Status: Passed
	 * */

	//Tests if date being considered for booking summary is before the current date and up to 1 month behind
	//Used boundary checking
	
	//Checks if the current date returns a false
	@Test
	public void test() {
		Owner owner = new Owner();
		assertFalse(owner.validateBookingSummaryDate(LocalDate.now()));
	}
	
	//Checks if the day after the current date returns a false
	@Test
	public void test1() {
		Owner owner = new Owner();
		assertFalse(owner.validateBookingSummaryDate(LocalDate.now().plusDays(1)));
	}
	
	//Checks if the day before the current date is true (it is before current date)
	@Test
	public void test2() {
		Owner owner = new Owner();
		assertTrue(owner.validateBookingSummaryDate(LocalDate.now().minusDays(1)));
	}
	
	//Checks if the date 1 month ago is valid, should return true
	@Test
	public void test3() {
		Owner owner = new Owner();
		assertTrue(owner.validateBookingSummaryDate(LocalDate.now().minusMonths(1)));
	}
	
	//Checks if the date 1 month plus 1 day is true
	@Test
	public void test5() {
		Owner owner = new Owner();
		assertTrue(owner.validateBookingSummaryDate(LocalDate.now().minusMonths(1).plusDays(1)));
	}
	
	//Checks if the date 1 month minus 1 day is false
	@Test
	public void test6() {
		Owner owner = new Owner();
		assertFalse(owner.validateBookingSummaryDate(LocalDate.now().minusMonths(1).minusDays(1)));
	}

}
