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


}
