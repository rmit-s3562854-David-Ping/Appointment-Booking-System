package JunitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Employee;
import main.Main;
import main.Owner;

/**
 * Owner Junit tests
 * test the owner login method and that it accepts the right username and password
 * otherwise it will return false as non acceptance for entries 
 * 
 * @author hassen
 *
 */

public class Owner_loginTest {
	
	private Owner owner;
	private Main main;
	
	@Before
	public void before_login_test() {
		owner = new Owner("username","password","", "", "", "", "");
		main = new Main();
		main.getOwnerArray().add(owner);
	}
	@After
	public void after_login_test(){
		main.getOwnerArray().remove(owner);
	}
	
	
	@Test
	public void login_test2(){
		assertFalse("invalid username & password",owner.checkLogin("InvalidUsername", "password"));
	}
	
	@Test
	public void login_test3(){
		assertFalse("invalid username & password",owner.checkLogin("username", "InvalidPassword"));
	}
	
	@Test
	public void login_test4(){
		assertFalse("invalid username & password",owner.checkLogin("Invalidusername", "InvalidPassword"));
	}
	
	@Test
	public void login_test(){
		assertTrue("valid username & password",owner.checkLogin("username", "password"));
	}
	
}
