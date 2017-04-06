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
