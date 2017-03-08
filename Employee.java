package main;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Employee {
	
	private static ArrayList<LocalDateTime> startTimes = new ArrayList<LocalDateTime>();//Change to next month
	private static ArrayList<LocalDateTime> endTimes = new ArrayList<LocalDateTime>();
	
	private String id;
	private String name;
	
	public Employee(String name, String id) {
		this.id=id;
		this.name=name;
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
	
	public String getName(){
		return name;
	}


}
