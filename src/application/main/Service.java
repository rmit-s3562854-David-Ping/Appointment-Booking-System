package application.main;

import application.MainApp;

public class Service {

	private String serviceName;
	private int duration;
	
	public Service(){}
	
	public Service(String serviceName, int duration){
		this.serviceName=serviceName;
		this.duration=duration;
	}
	
	public String getServiceName(){
		return serviceName;
	}
	
	public int getDuration(){
		return duration;
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
