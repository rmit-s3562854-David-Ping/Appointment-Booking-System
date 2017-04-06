package JunitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.Employee;
import main.Main;
import main.Owner;

public class Owner_loginTest {
	
	private Owner owner;
	private Main main;
	
	@Before
	public void before_login_test() {
		owner = new Owner("username","password","", "", "", "", "");
		
		main = new Main();
		main.getOwnerArray().add(owner);
	}
	
	
	@Test
	public void login_test2(){
		assertFalse("invalid username & password",owner.login("InvalidUsername", "password"));
	}
	
	@Test
	public void login_test3(){
		assertFalse("invalid username & password",owner.login("username", "InvalidPassword"));
	}
	
	@Test
	public void login_test4(){
		assertFalse("invalid username & password",owner.login("Invalidusername", "InvalidPassword"));
	}
	
	@Test
	public void login_test(){
		assertTrue("valid username & password",owner.login("username", "password"));
	}
	
}
