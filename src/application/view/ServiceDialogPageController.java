package application.view;

import application.MainApp;
import application.main.Service;
import application.main.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ServiceDialogPageController {
	
	@FXML
	private TextField serviceNameF;
	@FXML
	private ComboBox<String> duration;
	
	private Stage serviceStage;
	private Service service;
	private MainApp mainApp;
	private boolean okClicked = false;
	
	ObservableList<String> options = 
		FXCollections.observableArrayList(
				"30mins",
				"1hr",
				"1hr 30mins",
				"2hr",
				"2hr 30mins",
				"3hr"				
			);
	
	
	@FXML
	private void initialize() {
		duration.setItems(options);
	}

	public void setServiceStage(Stage serviceStage) {
		this.serviceStage = serviceStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}
	
	public void setNewService(Service service){
		MainApp mainApp = new MainApp();
		this.service = service;
		duration.setItems(options);
		
		}
	@FXML
	public void handleOk(){
		
		String half = "30mins";
		String hr = "1hr";
		String hr1min30 = "1hr 30mins";
		String hr2 = "2hr";
		String hr2min30 = "2hr 30mins";
		String hr3 = "3hr";
		int i = 30;
		int x = 0;
		String opt = duration.getValue();
		if(isInputValid()){
			
			if(duration.getValue() != null){
				for (String option : options){
					if (opt.equalsIgnoreCase(half)){
						x = i*1;
					}
					if (opt.equalsIgnoreCase(hr)){
						x = i*2;
					}
					if (opt.equalsIgnoreCase(hr1min30)){
						x = i*3;
					}
					if (opt.equalsIgnoreCase(hr2)){
						x = i*4;
					}
					if (opt.equalsIgnoreCase(hr2min30)){
						x = i*5;
					}
					if (opt.equalsIgnoreCase(hr3)){
						x = i*6;
					}
				}
				service.setDuration(x);
			}
			service.setServiceName(serviceNameF.getText());
			okClicked = true;
			serviceStage.close();
		}
	}
	
	@FXML
	private void handleCancel(){
		serviceStage.close();
	}
	
	private boolean isInputValid(){
		Utility util = new Utility();
		String errorMsg = "";
		boolean optionIsEmpty = (duration.getValue() == null);
		
		if(util.checkString(serviceNameF.getText()) == false){
			errorMsg += "Invalid service name, must start with a capital letter\n";
		}
		if (optionIsEmpty == true){
			errorMsg += "must select a duration\n";
		}
		if(errorMsg.length() == 0){
			return true;
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(serviceStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMsg);

			alert.showAndWait();

			return false;
		}
	}
	
}
