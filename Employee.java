package main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Employee {
	
	private static ArrayList<LocalDateTime> startTimes = new ArrayList<LocalDateTime>();//Change to next month
	private static ArrayList<LocalDateTime> endTimes = new ArrayList<LocalDateTime>();
	
	private String id;
	private String name;
	
	public Employee(String name, String id) {
		this.id=createID();
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

	public  String createID()
	{
		//Sets ID to 0 then searches array, if ID exists it iterates and repeats.
		Main driver = new Main();
		ArrayList<Customer> customerArray = driver.getCustomerArray();
		int IDCounter = 0;
		int index = 0;
		while(index < customerArray.size()) {
			index=0;
			while (index < customerArray.size()) {
				if (IDCounter == Integer.parseInt(customerArray.get(index).getID())) {
					System.out.println(Integer.parseInt(customerArray.get(index).getID()) + " is  " + IDCounter);
					System.out.println("Changing ID");
					IDCounter++;
					break;
				} else {
					System.out.println(Integer.parseInt(customerArray.get(index).getID()) + " is not " + IDCounter);
					//search=false;
					index++;

				}
			}
		}
		return String.valueOf(IDCounter);
	}
}
