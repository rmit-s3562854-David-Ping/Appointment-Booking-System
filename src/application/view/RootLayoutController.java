package application.view;

import java.util.logging.Logger;

import application.MainApp;
import application.main.Reader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * @author David Ping
 * @version 1.00
 * Last edited: 24/04/2017
 * */

public class RootLayoutController {
	private static final Logger LOGGER = Logger.getLogger("MyLog");
	private MainApp mainApp;
	private Reader reader = new Reader();
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	 
	@FXML
    private void initialize() {
    }
	
	@FXML
	public void handleHome(){
		LOGGER.info("Selected HOME.");
		for(int i=0;i<mainApp.getOwnerArray().size();i++){
			if(mainApp.getUsername().equals(mainApp.getOwnerArray().get(i).getUsername())){
				mainApp.showOwnerHomePage();
			}
		}
		for(int i=0;i<mainApp.getCustomerArray().size();i++){
			if(mainApp.getUsername().equals(mainApp.getCustomerArray().get(i).getUsername())){
				mainApp.showCustomerHomePage();
			}
		}
		
	}
	
	@FXML
	public void handleLogout(){
		LOGGER.info("Selected LOGOUT.");
		mainApp.setUsername("");
		Scene scene = mainApp.getRootLayout().getScene();
		Button homeBtn = (Button) scene.lookup("#HomeButton");
		Button logoutBtn = (Button) scene.lookup("#LogoutButton");
		//Clear all new data and reload the values via readUsers and readBusiness (when called after login).
		mainApp.getEmployeeData().clear();
		mainApp.getServiceArray().clear();
		mainApp.getAppointmentArray().clear();
		mainApp.getOwnerArray().clear();
		mainApp.getCustomerArray().clear();
		reader.readUsers();
		homeBtn.setVisible(false);
		logoutBtn.setVisible(false);
		
		mainApp.showLoginPage();
	}
	
}
