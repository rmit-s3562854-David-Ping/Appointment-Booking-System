package main;

import java.time.LocalTime;
import java.util.ArrayList;

public class Employee {

	/**
	 * Employee class, contains attributes of employees, will add
	 * profession/services offered when services are supported
	 *
	 * @version 1.00 05 Mar 2017
	 * @author David Ping
	 */

	private ArrayList<LocalTime> startTimes = new ArrayList<LocalTime>();
	private ArrayList<LocalTime> endTimes = new ArrayList<LocalTime>();
	private String id;
	private String firstName;
	private String lastName;

	public Employee(String firstName, String lastName, String id, ArrayList<LocalTime> startTimes,
			ArrayList<LocalTime> endTimes) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.startTimes = startTimes;
		this.endTimes = endTimes;
	}

	public Employee() {
	}

	public ArrayList<LocalTime> getStartTimes() {
		return startTimes;
	}

	public ArrayList<LocalTime> getEndTimes() {
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
		return this.getId() + ":" + this.getFirstName() + ":" + this.getLastName() + ":" + this.getStartTimes()
				+ this.getEndTimes();
	}

}
