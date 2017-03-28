package JunitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Employee;
import main.Main;
import main.Owner;

public class Employee_Test {

	private Owner owner;
	private Employee employee;
	private Main main;

	@Before
	public void before_createEmplyee_test(){
		owner = new Owner("username","password","", "", "", "", "");
	}
	
	@Test
	public void makeEmployeeObject_test(){
		employee = owner.makeEmployeeObj("firstName", "lastName", "id");
		assertTrue("valid Employee",employee != null);
	}
	
	@Test
	public void makeEmployeeObject_test2(){
		employee = owner.makeEmployeeObj(null,null, null);
		assertTrue("invalid Employee",employee != null);
	}
	
	@Test
	public void makeEmployeeObject_test3(){
		employee = owner.makeEmployeeObj(null,"lastName", "id");
		assertTrue("invalid Employee",employee != null);
	}
	
	@Test
	public void makeEmployeeObject_test4(){
		employee = owner.makeEmployeeObj("firstName",null, "id");
		assertTrue("invalid Employee",employee != null);
	}
	
	@Test
	public void makeEmployeeObject_test5(){
		employee = owner.makeEmployeeObj("firstName","lastName", null);
		assertTrue("invalid Employee",employee != null);
	}
	
	@Test 
	public void makeEmployeeObject_test6(){
		employee = owner.makeEmployeeObj("","","");
		assertTrue("invalid Employee",employee != null);
	}
	
	@Test
	public void addEmployee_test(){
		employee = new Employee("last","user","id",null,null);
		assertTrue("accepted Employee",owner.addEmployee(employee));
	}
	
	@Test
	public void addEmployee_test2(){
		employee = null;
		assertTrue("Employee is null",owner.addEmployee(employee));
	}
	
	@Test
	public void getEmployeeInfo_test(){
		Employee employeeInfo = owner.getEmployeeInfo();
		assertTrue(employeeInfo != null);
	}
	
	@Test
	public void createEmployee_test(){
		assertTrue("working", owner.createEmployee());
	}
	
	/*@Test
	public void validateNewWorkTime_test(){
		assertTrue("",owner.validateNewWorkTime("e12345", 10:30, 11:30));
	}*/

}
