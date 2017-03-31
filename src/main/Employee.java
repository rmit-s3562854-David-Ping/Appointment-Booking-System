package main;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Employee {

	private ArrayList<LocalDateTime> startTimes = new ArrayList<LocalDateTime>();
	private ArrayList<LocalDateTime> endTimes = new ArrayList<LocalDateTime>();

	private String id;
	private String firstName;
	private String lastName;

	public Employee(String firstName, String lastName, ArrayList<LocalDateTime> startTimes, ArrayList<LocalDateTime> endTimes) {
		this.id = createID();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Employee() {
	}

	public ArrayList<LocalDateTime> getStartTimes() {
		return startTimes;
	}

	public ArrayList<LocalDateTime> getEndTimes() {
		return endTimes;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}


	public  String createID()
	{
		//Sets ID to 0 then searches array, if ID exists it iterates and repeats.
		Owner owner= new Owner();

		ArrayList<Employee> EmployeeArray = owner.getEmployeeArray();
		int IDCounter = 1;
		int index = 0;
		System.out.println(owner.getEmployeeArray().size());
		while(index < EmployeeArray.size()) {
			index=0;
			while (index < EmployeeArray.size()) {
				if (Integer.parseInt(String.format("%05d", IDCounter)) == Integer.parseInt((EmployeeArray.get(index).getId()).substring(1,6))) {
					IDCounter++;
					break;
				} else {
					index++;

				}
			}
		}
		String ID = "e" + String.format("%05d", IDCounter);
		return ID;
	}





}
