package JunitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Customer;
import main.Main;
import main.Utility;

public class validationTest {

	private Utility util;
	private Main main;
	private Customer customer;
	
	@Before
	public void before_checkDuplicate_test(){
		customer = new Customer("username","password", null, null, null, null);
		main = new Main();
		main.getCustomerArray().add(customer);
	}
	@Test
	public void checkDuplicateUsername_test(){
		assertFalse("valid username",util.customerUsernameIsDuplicate("nameuser"));
	}
	
	@Test
	public void checkDuplicateUsername_test2(){
		assertFalse("already in the System username",util.customerUsernameIsDuplicate("username"));
	}
	
	@Before
	public void before_test(){
		util = new Utility();
		
	}
	
	@Test
	public void validateName_test() {		
		assertTrue("valid input",util.validateName("Username"));
	}
	
	@Test
	public void validateName_test2() {		
		assertTrue("invalid input",util.validateName("username"));
	}
	
	@Test
	public void validateName_test3() {		
		assertTrue("less than Min",util.validateName(""));
	}
	
	@Test
	public void validateName_test4() {		
		assertTrue("more than Max",util.validateName("usernameandpasswordalltogether"));
	}
	
	@Test
	public void validatelogin_username_test(){
		assertTrue("valid username",util.validateLogin("username"));
	}
	
	@Test
	public void validatelogin_username_test2(){
		assertTrue("less than required length",util.validateLogin("user"));
	}
	
	@Test
	public void validatelogin_username_test3(){
		assertTrue("more than allowed length",util.validateLogin("usernameismorethantwinty"));
	}
	
	@Test
	public void validatelogin_password_test(){
		assertTrue("valid username",util.validateLogin("password"));
	}
	
	@Test
	public void validatelogin_password_test2(){
		assertTrue("less than required length",util.validateLogin("pass"));
	}
	
	@Test
	public void validatelogin_password_test3(){
		assertTrue("more than allowed length",util.validateLogin("passwordismorethantwinty"));
	}
	
	@Test
	public void validateContactNumber_test(){
		assertTrue("valid number",util.validateContactNumber("0412345678"));
	}
	
	@Test
	public void validateContactNumber_test2(){
		assertTrue("invalid number",util.validateContactNumber("0412345678910"));
	}
	
	@Test
	public void validateContactNumber_test3(){
		assertTrue("invalid number",util.validateContactNumber("1234567890"));
	}
	
	@Test
	public void validateContactNumber_test4(){
		assertTrue("invalid number",util.validateContactNumber("phoneNum"));
	}
	
	@Test
	public void validateAddress_test(){
		assertTrue("valid Address",util.validateAddress("14 swanston st Vic"));
	}
	
	@Test
	public void validateAddress_test2(){
		assertTrue("invalid Address length less than allowed",util.validateAddress("1 st"));
	}
	
	@Test
	public void validateAddress_test3(){
		assertTrue("invalid Address length more than allowed",util.validateAddress("80 RMIT swanston st Melbourne Victoria 3000 Australia the Earth"));
	}

}
