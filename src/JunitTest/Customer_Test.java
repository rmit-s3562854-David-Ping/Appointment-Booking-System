package JunitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Customer;
import main.Main;


public class Customer_Test {

	Customer customer;
	Customer info;
	Main main;
	
	@Before
	public void before_login(){
		customer = new Customer("username","password","", "", "", "");
		
		main = new Main();
		main.getCustomerArray().add(customer);
	}
	
	@Test
	public void login_test() {
		
		assertTrue("valid username & password",customer.login("username", "password"));
		
	}
	
	@Test
	public void login_test2(){
		assertTrue("invalid username & password",customer.login("InvalidUsername", "password"));

	}
	
	@Test
	public void login_test3(){
		assertTrue("invalid username & password",customer.login("username", "InvalidPassword"));

	}
	
	@Test
	public void login_test4(){
		assertTrue("invalid username & password",customer.login("Invalidusername", "InvalidPassword"));

	}
	
	
	@Before
	public void before_getRegistrationinfo(){
		customer = new Customer(null,null, null, null, null, null);
		info = customer.getRegisterInformation();

	}
	
	@Test 
	public void customer_getRegistrationinfo_test(){
		//testing if a customer Object is created 
		assertTrue(info != null);
	}
	
	
	@Before
	public void before_customer_makeObject_test2(){
		info = customer.makeObj("","","","","","",true);
	}
	@Test
	public void customer_makeObject_test2(){
		assertTrue(info != null) ;
	}
	
	@Before
	public void before_customer_makeObject_test3(){
		info = customer.makeObj("","","","","","",false);
	}
	@Test
	public void customer_makeObject_test3(){
		info = customer.makeObj("","","","","","",false);
		assertTrue(info != null) ;
	}
	
	@Before
	public void before_addCustomer_test() {
		
		customer = new Customer(null,null, null, null, null, null);
	}
	
	@Test 
	public void customer_add_test()
	{
		assertTrue("working", customer.addCustomer("username","password", "jeff", "hong", "30 kambalda", "04350120"));

	}
	@Test 
	public void customer_add_test2()
	{
		assertFalse("working", customer.addCustomer("username","password", "jeff", "hong", "30 kambalda", "04350120"));

	}
	
	@Before
	public void before_customer_register(){
		
	}
	
	@Test
	public void customer_register_test(){
		assertTrue("working",customer.register());
	}
	
	@Test
	public void customer_register_test2(){
		assertTrue("working",customer.register());
	}
	

}
