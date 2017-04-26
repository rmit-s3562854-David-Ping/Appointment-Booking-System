package application.main;

import java.util.List;

import application.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Employee {

	/**
	 * Employee class, contains attributes of employees, will add
	 * profession/services offered when services are supported
	 *
	 * @version 1.00 05 Mar 2017
	 * @author David Ping
	 */

	private StringProperty id;
	private StringProperty firstName;
	private StringProperty lastName;
	private List<WorkTime> workTimes;
	private ObservableList<String> services;

	public Employee(String firstName, String lastName, String id, List<WorkTime> workTimes, ObservableList<String> services) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.id = new SimpleStringProperty(id);
		this.workTimes = workTimes;
		this.services=services;
	}

	public Employee() {
	}

	public List<WorkTime> getWorkTimes(){
		return workTimes;
	}
	
	public ObservableList<String> getEmployeeServices(){
		return services;
	}

	public String getId() {
		return id.get();
	}

	public String getFirstName() {
		return firstName.get();
	}

	public String getLastName() {
		return lastName.get();
	}
	
	public StringProperty getFirstNameProperty(){
		return firstName;
	}
	
	public StringProperty getLastNameProperty(){
		return lastName;
	}
	
	public StringProperty getIdProperty(){
		return id;
	}
	
	public void setId(String id){
		this.id.set(id);
	}
	
	public void setFirstName(String firstName){
		this.firstName.set(firstName);
	}
	
	public void setLastName(String lastName){
		this.lastName.set(lastName);
	}
	
	public void setWorkTimes(List<WorkTime> workTimes){
		this.workTimes = workTimes;
	}
	
	public void setServices(ObservableList<String> services){
		this.services=services;
	}
	
	public Employee getEmployee(String employeeId){
		MainApp mainApp = new MainApp();
		for(int i=0;i<mainApp.getEmployeeData().size();i++){
			if(mainApp.getEmployeeData().get(i).getId().equals(employeeId)){
				return mainApp.getEmployeeData().get(i);
			}
		}
		return null;
	}
	
	public String toString() {
		return this.getId() + ":" + this.getFirstName() + ":" + this.getLastName() + ":" + this.getWorkTimes();
	}

}
