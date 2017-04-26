package application.view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * @author David Ping
 * @version 1.00
 * Last edited: 24/04/2017
 * */

public class RootLayoutController {

	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	 
	@FXML
    private void initialize() {
    }
	
	@FXML
	public void handleHome(){
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
		mainApp.setUsername("");
		Scene scene = mainApp.getRootLayout().getScene();
		Button homeBtn = (Button) scene.lookup("#HomeButton");
		Button logoutBtn = (Button) scene.lookup("#LogoutButton");
		homeBtn.setVisible(false);
		logoutBtn.setVisible(false);
		mainApp.showLoginPage();
	}
	
}
