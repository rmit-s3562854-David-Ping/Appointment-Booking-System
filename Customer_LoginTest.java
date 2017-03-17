package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class Customer_LoginTest {

	@Test
	public void login_test() {
		Customer customer = new Customer("has","pas","", "", "", "");
		
		Main main = new Main();
		main.getCustomerArray().add(customer);
		assertTrue("not working vars",customer.login("ha", "pa"));
		
	}
	
	@Test
	public void register_test(){
		
		Customer customer = null;
		
		assertTrue(customer.register());
	}

}
