package JunitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Employee;
import main.Main;
import main.Owner;
import main.Utility;

/**
 * checked that the data passed to create an employee isn't null or empty string
 * the add employee method checks if the object passed is null or not
 * according to that object is added to list or not 
 * makeEmployee returns null if any of the data provided is empty
 * and the add employee returns false if object is null
 * otherwise it returns true
 *  
 * @author hassen
 *
 */

public class Employee_Test {

	private Owner owner;
	private Employee employee;
	private Main main;
	private Utility util;
	
	@Before
	public void before_createEmplyee_test(){
		owner = new Owner("username","password","", "", "", "", "");
		util = new Utility();
	}

	@Test
	public void makeEmployeeObject_test(){
		employee = owner.makeEmployeeObj("firstName", "lastName", "id");
		assertTrue("valid Employee",employee != null);
	}
	
	@Test
	public void makeEmployeeObject_test2(){
		employee = owner.makeEmployeeObj(null,null, null);
		assertFalse("invalid Employee",employee != null);
	}
	
	@Test
	public void makeEmployeeObject_test3(){
		employee = owner.makeEmployeeObj(null,"lastName", "id");
		assertFalse("invalid Employee",employee != null);
	}
	
	@Test
	public void makeEmployeeObject_test4(){
		employee = owner.makeEmployeeObj("firstName",null, "id");
		assertFalse("invalid Employee",employee != null);
	}
	
	@Test
	public void makeEmployeeObject_test5(){
		employee = owner.makeEmployeeObj("firstName","lastName", null);
		assertFalse("invalid Employee",employee != null);
	}
	
	@Test 
	public void makeEmployeeObject_test6(){
		employee = owner.makeEmployeeObj("","","");
		assertFalse("invalid Employee",employee != null);
	}
	
	@Test
	public void addEmployee_test(){
		employee = new Employee("last","user","id",null,null);
		assertTrue("accepted Employee",owner.addEmployee(employee));
	}
	
	@Test
	public void addEmployee_test2(){
		employee = null;
		assertFalse("Employee is null",owner.addEmployee(employee));
	}
	
}
