package application.view;

import application.MainApp;
import javafx.fxml.FXML;

/**
 * @author David Ping
 * @version 1.00
 * Last edited: 24/04/2017
 * */

public class OwnerHomePageController {

	public OwnerHomePageController(){}
	
	@FXML
    private void initialize() {
    }
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void handleManageEmployeesClicked(){
		mainApp.showEmployeePage();
    }
	
	public void handleManageBookingsClicked(){
		mainApp.showOwnerBookingsPage();
	}

}
