package JunitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.main.Customer;
import application.MainApp;
import application.main.Utility;

/**
 * customer Junit tests
 * test the customer login method and that it accepts the right username and password
 * otherwise it will return false as non acceptance for entries 
 * 
 * test the registeration method 
 * the individual validation tests are done separately in a different Junit class
 * registerCustomer method checks that the registration fields are all valid to create a customer
 * if any of the fileds is invalid the registration isn't successfull
 * @author Hassen Mender
 */

public class Customer_Test {

	Customer customer, customer2, customer3, customer4, customer5;
	Customer info, info2, info3, info4;
	MainApp main;
	Utility util;
	
	@Before
	public void before_login(){
		customer = new Customer("username1","password1","", "", "", "");
		main = new MainApp();
		util = new Utility();
		main.getCustomerArray().add(customer);
		customer2 = new Customer(null,null, null, null, null, null);
		customer5 = new Customer(null,null, null, null, null, null);
	
	}
	public Boolean registerCustomer(String username, String password, String password2, String firstName,String lastName, String address, String contactNumber ){
		
			if(util.validateName(firstName) == false) {
					return false;			
			}
		
			if(util.validateName(lastName) == false) {
				return false;
			}
			
			if(util.validateAddress(address) == false) {
				return false;
			}
			
			if (util.validateContactNumber(contactNumber) == false) {
				return false;
			}
	
			if ((util.validateUsername(username) == false) || (util.usernameExists(username) == true)) {
				return false;
			}
	
			if (util.validatePassword(password) == false) {
				return false;
			}
			
			if (password.equals(password2) == false){
				return false;
			}
			
		return true;		
	}
	
	@After
	public void after_test(){
		main.getCustomerArray().remove(customer);

	}
		
	@Test
	public void login_test() {		
		assertTrue("valid username & password",customer.checkLogin("username1", "password1"));
	}
	
	@Test
	public void login_test2(){
		assertFalse("invalid username & password",customer.checkLogin("InvalidUsername", "password"));
	}
	
	@Test
	public void login_test3(){
		assertFalse("invalid username & password",customer.checkLogin("username", "InvalidPassword"));
	}
	
	@Test
	public void login_test4(){
		assertFalse("invalid username & password",customer.checkLogin("Invalidusername", "InvalidPassword"));
	}	
	
	@Test
	public void reg_test(){
		assertTrue("valid", registerCustomer("Username2","Password2$", "Password2$" ,"Fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test2(){
		assertFalse("invalid entries", registerCustomer("un","pass","pass","fn","ln","add","cn"));
	}	
	@Test
	public void reg_test3(){
		assertFalse("invalid username", registerCustomer("user","password","password","Fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test4(){
		assertFalse("invalid password", registerCustomer("username","pass","pass","Fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test10(){
		assertFalse("invalid second password", registerCustomer("username","password","password2","Fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test11(){
		assertFalse("invalid second password", registerCustomer("Username","Password1%","Password2%","Fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test5(){
		assertFalse("invalid first name", registerCustomer("username","password","password","fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test6(){
		assertFalse("invalid last name", registerCustomer("username","pass","pass","Fname","lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test7(){
		assertFalse("invalid address", registerCustomer("username","pass","pass","Fname","Lname","swanston st ","0432363935"));
	}
	@Test
	public void reg_test8(){
		assertFalse("invalid phone number", registerCustomer("username","pass","pass","Fname","Lname","30 swanston st ","043236"));
	}
	@Test
	public void reg_test9(){
		assertFalse("invalid phone number", registerCustomer("username","pass","pass","Fname","Lname","30 swanston st ","phonenumber"));
	}
	
	

}
