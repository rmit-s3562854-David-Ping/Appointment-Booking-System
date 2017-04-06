package JunitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Employee;
import main.Owner;

public class deleteEmployee_test {

	Owner owner = new Owner();
	Employee employee, employee2;
	
	@Before
	public void before_deleteEmployee_test(){
		employee = new Employee("firstName", "lastName", "e00001", null, null);
		employee2 = new Employee("firstName", "lastName", "valid", null, null);
		owner.getEmployeeArray().add(employee);
		owner.getEmployeeArray().add(employee2);
	}
	public Boolean deleteemployee(String id){
		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			if (owner.getEmployeeArray().get(i).getId().equals(id)){
					owner.getEmployeeArray().remove(i);
					return true;
			}			
		}
		return false;
	}
	
	@Test 
	public void delete_Employee_test(){
		assertTrue("delete employee", deleteemployee("e00001"));
	}
	
	@Test 
	public void delete_Employee_test2(){
		assertTrue("delete employee", deleteemployee("valid"));
	}
	
	@Test
	public void delete_Employee_test3(){
		assertFalse("Employee doesnt exist with id", deleteemployee("e0002"));
	}
	
	@Test
	public void delete_Employee_test4(){
		assertFalse("Employee doesnt exist with id", deleteemployee("invalid"));
	}
	
	@Test 
	public void delete_Employee_test5(){
		assertFalse("Employee doesnt exist with id", deleteemployee("id"));
	}
	
	@After
	public void after_deleteEmployee_test(){
		owner.getEmployeeArray().remove(employee);
		owner.getEmployeeArray().remove(employee2);
	}

}
