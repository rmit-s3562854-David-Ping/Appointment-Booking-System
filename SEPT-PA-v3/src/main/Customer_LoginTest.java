package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class Customer_LoginTest {

	Customer customer;
	Main main;
	
	@Before
	public void before_login(){
		customer = new Customer("has","pas","", "", "", "");
		
		main = new Main();
		main.getCustomerArray().add(customer);
	}
	
	@Test
	public void login_test() {
		
		assertTrue("not working vars",customer.login("has", "pas"));
		
	}
	
	@Before
	public void before_getRegistrationinfo(){
		customer = new Customer(null,null, null, null, null, null);
		
	}
	
	@Test 
	public void customer_getRegistrationinfo_test(){
		System.out.println("");
		System.out.println("get Registration info test");
		RegistrationInformation info = customer.getRegisterInformation("","","","","","",true);
		assertTrue(info != null);
	}
	@Before
	public void before_customer_makeObject(){
		
	}
	
	@Test
	public void customer_makeObject_test(){
		RegistrationInformation info = customer.makeObj("","","","","","",true);
		assertTrue(info != null) ;
	}
	
	@Before
	public void before_addCustomer() {
		
		customer = new Customer(null,null, null, null, null, null);
	}
	
	@Test 
	public void customer_add_test()
	{
		assertTrue("working", customer.addCustomer("invalid","pass", "djlf", "df", "df", "df"));

	}
	
	@Before
	public void before_customer_register(){
		
	}
	
	@Test
	public void customer_register_test(){
		System.out.println("");
		System.out.println("customer register test");
		assertTrue("wewwe",customer.register());
	}
	

}
