package main;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Employee {
	
	private static ArrayList<LocalDateTime> startTimes = new ArrayList<LocalDateTime>();//Change to next month
	private static ArrayList<LocalDateTime> endTimes = new ArrayList<LocalDateTime>();
	
	private String id;
	private String firstName;
	private String lastName;
	
	public Employee(String firstName, String lastName, String id) {
		this.id=id;
		this.firstName=firstName;
		this.lastName=lastName;
	}
	
	public Employee(){}
	
	public ArrayList<LocalDateTime> getStartTimes(){
		return startTimes;
	}
	
	public ArrayList<LocalDateTime> getEndTimes(){
		return endTimes;
	}
	
	public String getId(){
		return id;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}

	public  String createID()
	{
		//Sets ID to 0 then searches array, if ID exists it iterates and repeats.
		Owner owner= new Owner();

		ArrayList<Employee> EmployeeArray = owner.getEmployeeArray();
		int IDCounter = 0;
		int index = 0;
		System.out.println(owner.getEmployeeArray().size());
		while(index < EmployeeArray.size()) {
			index=0;
			while (index < EmployeeArray.size()) {
				if (IDCounter == Integer.parseInt(EmployeeArray.get(index).getId())) {
					System.out.println(Integer.parseInt(EmployeeArray.get(index).getId()) + " is  " + IDCounter);
					System.out.println("Changing ID");
					IDCounter++;
					break;
				} else {
					System.out.println(Integer.parseInt(EmployeeArray.get(index).getId()) + " is not " + IDCounter);
					//search=false;
					index++;

				}
			}
		}
		System.out.println("Assigning ID: " + IDCounter);
		return String.valueOf(IDCounter);
	}

}