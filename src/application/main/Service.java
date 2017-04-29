package application.main;

import application.MainApp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Service {

	private StringProperty serviceName;
	private IntegerProperty duration;
	
	public Service(){}
	
	public Service(String serviceName, int duration){
		this.serviceName = new SimpleStringProperty(serviceName);
		this.duration= new SimpleIntegerProperty(duration);
	}
	
	public String getServiceName(){
		return serviceName.get();
	}
	
	public StringProperty getServiceNameProperty(){
		return serviceName;
	}
	
	public void setServiceName(String serviceName){
		this.serviceName.set(serviceName);
	}
	
	public int getDuration(){
		return duration.get();
	}
	
	public IntegerProperty getDurationProperty(){
		return duration;
	}
	
	public void setDuration(int duration){
		this.duration.set(duration);
	}
	
		
	public Service getService(String serviceName){
		MainApp mainApp = new MainApp();
		for(int i=0;i<mainApp.getServiceArray().size();i++){
			if(mainApp.getServiceArray().get(i).getServiceName().equals(serviceName)){
				return mainApp.getServiceArray().get(i);
			}
		}
		return null;
	}
	
}
