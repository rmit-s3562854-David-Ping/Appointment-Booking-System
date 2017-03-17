package main;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class Owner_LoginTest {
	
	@Test
	public void login_test(){
		
		Owner owner = new Owner("has","pas","", "", "", "", "");
		
		Main main = new Main();
		main.getOwnerArray().add(owner);
		assertTrue("not working vars",owner.login("ha", "pa"));
		
	}

}
