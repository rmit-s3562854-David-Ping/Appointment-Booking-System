package JunitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.main.Employee;
import application.MainApp;
import application.main.Owner;
import application.main.Utility;
import application.view.EmployeeDialogPageController;

/**
 * checked that the data passed to create an employee is valid
 * according to that object is added to list or not 
 * newEmployee returns false if any of the data provided is invalid
 * otherwise it returns true
 * Note ID is auto generated   
 * @author hassen
 *
 */

public class Employee_Test {

	private Owner owner;
	private MainApp main;
	private Utility util;
	private EmployeeDialogPageController employee;
	
	
	
	@Before
	public void before_createEmplyee_test(){
		owner = new Owner("username","password","", "", "", "", "");
		util = new Utility();
		employee = new EmployeeDialogPageController(); 
	}
	
	public boolean  newEmployee (String firstName, String lastName, String id){
		
		if (util.validateName(firstName) == false){
			return false;
		}
		if (util.validateName(lastName) == false){
			return false;
		}
		return true;
	}

	@Test
	public void makeEmployeeObject_test(){
		String id = employee.createID();
		assertTrue("valid Employee",newEmployee("Fname", "Lname", id));
	}
	
	@Test
	public void makeEmployeeObject_test2(){
		String id = employee.createID();
		assertFalse("invalid Employee first name",newEmployee("fname", "Lname", id));
	}
	
	@Test
	public void makeEmployeeObject_test3(){
		String id = employee.createID();
		assertFalse("invalid Employee last name",newEmployee("Fname", "lname", id));
	}
	
	@Test
	public void makeEmployeeObject_test4(){
		String id = employee.createID();
		assertFalse("invalid Employee first/last name",newEmployee("fname", "lname", id));
	}
	
}
