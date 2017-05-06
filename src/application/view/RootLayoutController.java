package application.view;

import java.io.IOException;
import java.util.logging.Logger;

import application.MainApp;
import application.main.Reader;
import application.main.Writer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * @author David Ping
 * @version 1.00 Last edited: 24/04/2017
 */

public class RootLayoutController {
	@FXML
	private Label welcomeText;
	@FXML
	private Button businessNameButton;
	@FXML
	private Button homeButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Button myDetailsButton;
	@FXML
	private AnchorPane header;

	
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
	public void handleHome() {
		LOGGER.info("Selected HOME.");
		for (int i = 0; i < mainApp.getOwnerArray().size(); i++) {
			if (mainApp.getUsername().equals(mainApp.getOwnerArray().get(i).getUsername())) {
				mainApp.showOwnerHomePage();
			}
		}
		for (int i = 0; i < mainApp.getCustomerArray().size(); i++) {
			if (mainApp.getUsername().equals(mainApp.getCustomerArray().get(i).getUsername())) {
				mainApp.showCustomerHomePage();
			}
		}
	}

	@FXML
	public void handleLogout() {
		LOGGER.info("Selected LOGOUT.");
		mainApp.setUsername("");
		// Clear all new data and reload the values via readUsers and
		// readBusiness (when called after login).
		mainApp.getEmployeeData().clear();
		mainApp.getServiceArray().clear();
		mainApp.getAppointmentArray().clear();
		mainApp.getOwnerArray().clear();
		mainApp.getCustomerArray().clear();
		reader.readUsers();
		header.setVisible(false);
		mainApp.showLoginPage();
	}

	@FXML
	public void handleMyDetails(){
		Writer writer = new Writer();
		boolean saveClicked = false;
		for(int i=0;i<mainApp.getCustomerArray().size();i++){
			if(mainApp.getCustomerArray().get(i).getUsername().equals(mainApp.getUsername())){
				saveClicked=mainApp.showMyDetailsPage(mainApp.getCustomerArray().get(i));
				welcomeText.setText("Welcome "+mainApp.getCustomerArray().get(i).getFirstName()+" "+mainApp.getCustomerArray().get(i).getLastName());
				mainApp.setUsername(mainApp.getCustomerArray().get(i).getUsername());
				if(saveClicked==true){
					break;
				}
			}
		}

		for(int i=0;i<mainApp.getOwnerArray().size();i++){
			if(mainApp.getOwnerArray().get(i).getUsername().equals(mainApp.getUsername())){
				saveClicked=mainApp.showMyDetailsPage(mainApp.getOwnerArray().get(i));
				welcomeText.setText("Welcome "+mainApp.getOwnerArray().get(i).getFirstName()+" "+mainApp.getOwnerArray().get(i).getLastName());
				mainApp.setUsername(mainApp.getOwnerArray().get(i).getUsername());
				if(saveClicked==true){
					break;
				}
			}
		}
		if (saveClicked) {
			try {
				writer.save(mainApp.getCustomerArray());
				writer.saveOwner();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
