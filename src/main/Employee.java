package main;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Employee {

	private ArrayList<LocalDateTime> startTimes = new ArrayList<LocalDateTime>();
	private ArrayList<LocalDateTime> endTimes = new ArrayList<LocalDateTime>();

	private String id;
	private String firstName;
	private String lastName;

	public Employee(String firstName, String lastName, String id, ArrayList<LocalDateTime> startTimes, ArrayList<LocalDateTime> endTimes) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
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
	
	public String toString() {
 		return this.getId()+":"+this.getFirstName()+":"+this.getLastName()+":"+ this.getStartTimes()+ this.getEndTimes();
 	}

}
