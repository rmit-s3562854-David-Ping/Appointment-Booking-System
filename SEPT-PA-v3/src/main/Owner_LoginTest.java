package main;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class Owner_LoginTest {
	
	private Owner owner;
	private Main main;
	
	@Before
	public void before_login_test() {
		owner = new Owner("valid","pass","", "", "", "", "");
		
		main = new Main();
		main.getOwnerArray().add(owner);
	}
	
	@Test
	public void login_test(){
		assertTrue("not working vars",owner.login("valid", "pass"));
	}

}
