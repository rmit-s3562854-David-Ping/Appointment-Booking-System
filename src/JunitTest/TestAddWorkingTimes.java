package junit;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.*;

public class TestAddWorkingTimes {

	@BeforeClass
	public static void setupEmployee(){
		Owner owner = new Owner();
		Employee dummyEmployee = new Employee("Fname", "Lname", "e00001", null, null);
		owner.getEmployeeArray().add(dummyEmployee);
	}
	
	@After
	public void clearWorkTimes(){
		Owner owner = new Owner();
		for(int i=0;i<owner.getEmployeeArray().size();i++){
			owner.getEmployeeArray().get(i).getStartTimes().clear();
			owner.getEmployeeArray().get(i).getEndTimes().clear();
		}
	}
	
	//Checks if a past date is false
	@Test
	public void testValidateNewWorkTime() {
		Owner owner = new Owner();
		LocalDateTime startTime = LocalDateTime.of(2016, 3, 28, 12, 0);//year, month, dayOfMonth, hour, minute
		LocalDateTime endTime = LocalDateTime.of(2016, 3, 28, 15, 0);//Past date (2016)
		assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
	}
	
	
	//checks if valid times if true (also checks day of week is opening day)
	@Test 
	public void testValidateNewWorkTime2() {
		Owner owner = new Owner();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);
		if(owner.validateDayOfWeek(startTime)){
			assertTrue(owner.validateNewWorkTime("e00001", startTime, endTime));
		}else{
			System.out.println("business not open on this day");
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}
	}
	
	
	//Checks if end time before start time returns a false
	@Test 
	public void testValidateNewWorkTime3() {
		Owner owner = new Owner();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
		if(owner.validateDayOfWeek(startTime)){
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}else{
			System.out.println("business not open on this day");
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}
	}
	
	
	//Tests if starting before the business is open is false
	@Test 
	public void testValidateNewWorkTime4() {
		Owner owner = new Owner();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(5).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0);
		if(owner.validateDayOfWeek(startTime)){
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}else{
			System.out.println("business not open on this day");
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}
	}
	
	//Tests if the month input is more than 1 month from current date is false
	@Test 
	public void testValidateNewWorkTime5() {
		Owner owner = new Owner();
		LocalDateTime startTime = LocalDateTime.now().plusMonths(2).withHour(8).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusMonths(2).withHour(12).withMinute(0).withSecond(0).withNano(0);
		if(owner.validateDayOfWeek(startTime)){
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}else{
			System.out.println("business not open on this day");
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}
	}
	
	//Tests if less than 3 hours of working time is false
	@Test 
	public void testValidateNewWorkTime6() {
		Owner owner = new Owner();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);
		if(owner.validateDayOfWeek(startTime)){
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}else{
			System.out.println("business not open on this day");
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}
	}
	
	//Tests if working on different days gives a false
	@Test
	public void testValidateNewWorkTime7() {
		Owner owner = new Owner();
		LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endTime = LocalDateTime.now().plusDays(2).withHour(12).withMinute(0).withSecond(0).withNano(0);
		if(owner.validateDayOfWeek(startTime)){
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}else{
			System.out.println("business not open on this day");
			assertFalse(owner.validateNewWorkTime("e00001", startTime, endTime));
		}
	}
	
	
	
	

}
