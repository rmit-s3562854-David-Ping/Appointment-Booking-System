package JunitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Owner;
import main.Utility;

/**
 * test checks that the data passed for employee is valid
 * if any of the data is not valid employee isn't created 
 * validation tests are in a different class
 * 
 * @author hassen
 *
 */

public class createEmployee_test {
	private Owner owner;
	private Utility util;
	private String createId;

	@Before
	public void before_createEmplyee_test(){
		owner = new Owner("username","password","", "", "", "", "");
		util = new Utility();
		createId = null;
	}
	public Boolean createEmployee(String firstname,String lastname,String id){
		/*
		System.out.println(createId);*/
		createId = util.createID();
		while(util.validateName(firstname) == false){
			return false;
		}
		while(util.validateName(lastname)== false){
			return false;
		}
		if(id.equals(createId)){
			return true;
		}
		else{
			return false;
		}			
	}
	
	@Test
	public void makeEmployee_test(){
		assertTrue("", createEmployee("Fname", "Lname","e00001"));
	}
	@Test
	public void makeEmployee_test2(){
		assertFalse("", createEmployee("Fname", "Lname","b00001"));
	}
	@Test
	public void makeEmployee_test3(){
		assertFalse("", createEmployee("fname", "Lname","e00001"));
	}
	@Test
	public void makeEmployee_test4(){
		assertFalse("", createEmployee("Fname", "lname","e00001"));
	}

}
