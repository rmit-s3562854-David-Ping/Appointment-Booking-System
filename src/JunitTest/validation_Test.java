package JunitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.main.Customer;
import application.main.Employee;
import application.MainApp;
import application.main.Owner;
import application.main.Utility;

/**
 * validation Junit tests
 * tests the validation for all entries
 * if entry matches the pattern tests passes success
 * else returns false
 * 
 * also tests the check for duplicate username if customer wanted to register 
 * and the username is already used by another customer
 * 
 * @author hassen
 *
 */

public class validation_Test {

	private Utility util;
	private MainApp main;
	private Customer customer;
	private Employee employee;
	private Owner owner;
	
	@Before
	public void before_checkDuplicate_test(){
		util = new Utility();
		customer = new Customer("username","password", null, null, null, null);
		main = new MainApp();
		main.getCustomerArray().add(customer);
		employee = new Employee("employeeName","lastName", "e12345", null, null);
		owner = new Owner();
		main.getEmployeeData().add(employee);
		
	}
	@After
	public void after_validation_test(){
		main.getCustomerArray().remove(customer);
		main.getEmployeeData().remove(employee);

	}
	@Test
	public void checkDuplicateUsername_test(){
		assertFalse("valid username",util.usernameExists("nameuser"));
	}
	
	@Test
	public void checkDuplicateUsername_test2(){
		assertTrue("already in the System username",util.usernameExists("username"));
	}	
	
	@Test
	public void validateName_test() {		
		assertTrue("valid input",util.validateName("Username"));
	}
	
	@Test
	public void validateName_test2() {		
		assertFalse("invalid input",util.validateName("username"));
	}
	
	@Test
	public void validateName_test3() {		
		assertFalse("less than Min",util.validateName(""));
	}
	
	@Test
	public void validateName_test4() {		
		assertFalse("more than Max",util.validateName("usernameandpasswordalltogether"));
	}
	
	@Test
	public void validatelogin_username_test(){
		assertTrue("valid username",util.validateUsername("username"));
	}
	
	@Test
	public void validatelogin_username_test2(){
		assertFalse("less than required length",util.validateUsername("user"));
	}
	
	@Test
	public void validatelogin_username_test3(){
		assertFalse("more than allowed length",util.validateUsername("usernameismorethantwinty"));
	}
	
	//password has to contain : Capital letter + small letter + number + special character 
	@Test
	public void validatelogin_password_test(){
		assertTrue("valid password",util.validatePassword("Password-12"));
	}
	
	@Test
	public void validatelogin_password_test2(){
		assertFalse("less than required length",util.validatePassword("pass"));
	}
	
	@Test
	public void validatelogin_password_test3(){
		assertFalse("more than allowed length",util.validatePassword("passwordismorethantwinty"));
	}
	
	@Test
	public void validatelogin_password_test4(){
		assertFalse("doesnt contain number/special character",util.validatePassword("password"));
	}
	
	@Test
	public void validatelogin_password_test5(){
		assertFalse("doesnt contain special character",util.validatePassword("password12"));
	}
	
	@Test
	public void validatelogin_password_test6(){
		assertFalse("doesnt contain number",util.validatePassword("password$%"));
	}
	
	@Test
	public void validatelogin_password_test7(){
		assertFalse("doesnt contain capital letter",util.validatePassword("password12$$"));
	}
	
	@Test
	public void validatelogin_password_test8(){
		assertFalse("doesnt contain capital letter",util.validatePassword("PASSWORD12&#"));
	}
	
	@Test
	public void validateContactNumber_test(){
		assertTrue("valid number",util.validateContactNumber("0412345678"));
	}
	
	@Test
	public void validateContactNumber_test2(){
		assertFalse("invalid number",util.validateContactNumber("0412345678910"));
	}
	
	@Test
	public void validateContactNumber_test3(){
		assertFalse("invalid number",util.validateContactNumber("1234567890"));
	}
	
	@Test
	public void validateContactNumber_test4(){
		assertFalse("invalid number",util.validateContactNumber("phoneNum"));
	}
	
	@Test
	public void validateAddress_test(){
		assertTrue("valid Address",util.validateAddress("14 swanston st Vic"));
	}
	
	@Test
	public void validateAddress_test2(){
		assertFalse("invalid Address length less than allowed",util.validateAddress("1 st"));
	}
	
	@Test
	public void validateAddress_test3(){
		assertFalse("invalid Address length more than allowed",util.validateAddress("80 RMIT swanston st Melbourne Victoria 3000 Australia the Earth"));
	}
	
	@Test
	public void validateDate_test(){
		assertTrue("valid date", util.validateDate("25/03/2017"));
	}
	
	@Test
	public void validateDate_test2(){
		assertFalse("invalid date", util.validateDate("3/25/2017"));
	}
	
	@Test
	public void validateDate_test3(){
		assertFalse("invalid date", util.validateDate("25/2017/03"));
	}
	
	@Test
	public void validateDate_test4(){
		assertFalse("invalid date", util.validateDate("2017/03/25"));
	}
	
	@Test
	public void validateDate_test5(){
		assertFalse("invalid date", util.validateDate("3/2017/25"));
	}
	
	@Test
	public void validateDate_test6(){
		assertFalse("invalid date", util.validateDate("2017/25/03"));
	}
	
	@Test
	public void validateDate_test7(){
		assertFalse("invalid date", util.validateDate("3/25/17"));
	}
	
	@Test
	public void validateDate_test8(){
		assertFalse("invalid date", util.validateDate("3-25-2017"));
	}
	
	@Test
	public void validateDate_test9(){
		assertFalse("invalid date", util.validateDate(""));
	}
	
	@Test
	public void validateTime_test(){
		assertTrue("valid time", util.validateTime("10:30"));
	}
	
	@Test
	public void validateTime_test2(){
		assertTrue("valid time", util.validateTime("10:30AM"));
	}
	
	@Test
	public void validateTime_test3(){
		assertTrue("valid time", util.validateTime("10:30PM"));
	}
	
	@Test
	public void validateTime_test4(){
		assertTrue("valid time", util.validateTime("15:30"));
	}
	
	@Test
	public void validateTime_test5(){
		assertFalse("invalid time", util.validateTime("15:30AM"));
	}
	
	@Test
	public void validateTime_test6(){
		assertFalse("invalid time", util.validateTime("15:30PM"));
	}
	
	@Test
	public void validateTime_test7(){
		assertFalse("invalid time", util.validateTime("10:30am"));
	}
	
	@Test
	public void validateTime_test8(){
		assertFalse("invalid time", util.validateTime("10:30pm"));
	}
	
	@Test
	public void validateTime_test9(){
		assertFalse("invalid time", util.validateTime("30:30"));
	}
	
	@Test
	public void validateTime_test10(){
		assertFalse("invalid time", util.validateTime("24:30"));
	}
	
	@Test
	public void validateTime_test11(){
		assertFalse("invalid time", util.validateTime("1030"));
	}
	
	@Test
	public void validateTime_test12(){
		assertFalse("invalid time", util.validateTime("10-30"));
	}
		
	@Test
	public void validateMakeEmployeeId_test(){
		assertTrue("valid Emplyee ID", util.validateEmployeeId("e12345"));
	}
	
	@Test
	public void validateMakeEmployeeId_test2(){
		assertFalse("invalid Emplyee ID", util.validateEmployeeId("e1234"));
	}
	
	@Test
	public void validateMakeEmployeeId_test3(){
		assertFalse("invalid Emplyee ID", util.validateEmployeeId("e1234567"));
	}
	
	@Test
	public void validateMakeEmployeeId_test4(){
		assertFalse("invalid Emplyee ID", util.validateEmployeeId("eqazwsx"));
	}
	
	@Test
	public void validateMakeEmployeeId_test5(){
		assertFalse("invalid Emplyee ID", util.validateEmployeeId("12345"));
	}
	
	@Test
	public void validateMakeEmployeeId_test6(){
		assertFalse("invalid Emplyee ID", util.validateEmployeeId("b1234"));
	}
	
	@Test
	public void checkString_test(){
		assertTrue("valid string", util.checkString("service"));
	}
	@Test
	public void checkString_test2(){
		assertFalse("invalid string", util.checkString(""));
	}
	

}
