package application.main;

import java.time.*;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Appointment {

	/**
	 * Appointment class contains attributes of appointment
	 *
	 * @version 1.00 05 Mar 2017
	 * @author David Ping
	 */

	private ObservableValue<LocalDateTime> dateAndTime;
	private StringProperty customerUsername;
	private StringProperty employeeId;
	private StringProperty serviceName;
	
	public Appointment() {
	}

	public Appointment(LocalDateTime dateAndTime, String customerUsername, String employeeId, String serviceName) {
		this.dateAndTime = new SimpleObjectProperty<LocalDateTime>(dateAndTime);
		this.customerUsername = new SimpleStringProperty(customerUsername);
		this.employeeId = new SimpleStringProperty(employeeId);
		this.serviceName = new SimpleStringProperty(serviceName);
	}

	public LocalDateTime getDateAndTime() {
		return dateAndTime.getValue();
	}

	public String getCustomerUsername() {
		return customerUsername.get();
	}

	public String getEmployeeId() {
		return employeeId.get();
	}
	
	public String getServiceName(){
		return serviceName.get();
	}

	public void setDateAndTime(ObservableValue<LocalDateTime> dateAndTime) {
		this.dateAndTime=dateAndTime;
	}
	
	public void setCustomerUsername(String customerUsername){
		this.customerUsername.set(customerUsername);
	}

	public void setEmployeeId(String employeeId){
		this.employeeId.set(employeeId);
	}
	
	public void setServiceName(String serviceName){
		this.serviceName.set(serviceName);
	}
	
	public ObservableValue<LocalDateTime> getDateTimeProperty(){
		return dateAndTime;
	}
	
	public StringProperty getCustomerUsernameProperty(){
		return customerUsername;
	}
	
	public StringProperty getEmployeeIdProperty(){
		return employeeId;
	}
	
	public StringProperty getServiceNameProperty(){
		return serviceName;
	}
	
	public String toString() {
		return this.getCustomerUsername() + "|" + this.getEmployeeId() + "|" + this.getDateAndTime()+"|"+this.getServiceName();
	}
}
