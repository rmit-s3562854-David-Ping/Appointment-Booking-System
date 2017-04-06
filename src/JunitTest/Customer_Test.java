package JunitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Customer;
import main.Main;
import main.Utility;


public class Customer_Test {

	Customer customer, customer2, customer3, customer4, customer5;
	Customer info, info2, info3, info4;
	Main main;
	Utility util;
	
	@Before
	public void before_login(){
		customer = new Customer("username1","password1","", "", "", "");
		main = new Main();
		util = new Utility();
		main.getCustomerArray().add(customer);
		customer2 = new Customer(null,null, null, null, null, null);
		//info2 = customer2.getRegisterInformation();
		info3 = customer.makeObj("","","","","","",true);
		info4 = customer.makeObj("","","","","","",false);
		customer5 = new Customer(null,null, null, null, null, null);
	
	}
	public Boolean registerCustomer(String username, String password,String firstName,String lastName, String address, String contactNumber ){
		
			while (util.validateName(firstName) == false) {
					return false;			
			}
		
			while (util.validateName(lastName) == false) {
				return false;
			}
			
			while (util.validateAddress(address) == false) {
				return false;
			}
			
			while (util.validateContactNumber(contactNumber) == false) {
				return false;
			}
	
			while ((util.validateLogin(username) == false) || (util.customerUsernameIsDuplicate(username) == true)) {
				return false;
			}
	
			while (util.validateLogin(password) == false) {
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
		assertTrue("valid username & password",customer.login("username1", "password1"));
	}
	
	@Test
	public void login_test2(){
		assertFalse("invalid username & password",customer.login("InvalidUsername", "password"));
	}
	
	@Test
	public void login_test3(){
		assertFalse("invalid username & password",customer.login("username", "InvalidPassword"));
	}
	
	@Test
	public void login_test4(){
		assertFalse("invalid username & password",customer.login("Invalidusername", "InvalidPassword"));
	}	
	
	@Test
	public void customer_makeObject_test2(){
		assertTrue(info3 != null) ;
	}
	
	@Test
	public void customer_makeObject_test3(){
		assertFalse(info4 != null) ;
	}
	
	@Test 
	public void customer_add_test(){
		assertTrue("working", customer5.addCustomer("username","password", "jeff", "hong", "30 kambalda", "04350120"));
	}
	

	@Test
	public void reg_test(){
		assertTrue("valid", registerCustomer("username2","password2","Fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test2(){
		assertFalse("invalid entries", registerCustomer("un","pass","fn","ln","add","cn"));
	}	
	@Test
	public void reg_test3(){
		assertFalse("invalid username", registerCustomer("user","password","Fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test4(){
		assertFalse("invalid password", registerCustomer("username","pass","Fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test5(){
		assertFalse("invalid first name", registerCustomer("username","password","fname","Lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test6(){
		assertFalse("invalid last name", registerCustomer("username","pass","Fname","lname","30 swanston st ","0432363935"));
	}
	@Test
	public void reg_test7(){
		assertFalse("invalid address", registerCustomer("username","pass","Fname","Lname","swanston st ","0432363935"));
	}
	@Test
	public void reg_test8(){
		assertFalse("invalid phone number", registerCustomer("username","pass","Fname","Lname","30 swanston st ","043236"));
	}
	@Test
	public void reg_test9(){
		assertFalse("invalid phone number", registerCustomer("username","pass","Fname","Lname","30 swanston st ","phonenumber"));
	}

}
