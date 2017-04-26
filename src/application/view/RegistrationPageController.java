package application.view;

import application.main.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author David Ping
 * @version 1.00
 * Last edited: 24/04/2017
 * */

public class RegistrationPageController {

	@FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField contactNumberField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordField2;
	

	private Stage registrationStage;
    private Customer customer;
    private boolean okClicked = false;
    
    @FXML
    private void initialize() {
    }
    
    public void setRegistrationStage(Stage registrationStage) {
        this.registrationStage = registrationStage;
    }
      
	public boolean isOkClicked() {
        return okClicked;
    }
	
	public void setCustomer(Customer customer) {
        this.customer = customer;

        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        contactNumberField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        passwordField2.setText("");
    }
	
	@FXML
    private void handleOk() {
        if (isInputValid()) {
            customer.setFirstName(firstNameField.getText());
            customer.setLastName(lastNameField.getText());
            customer.setAddress(addressField.getText());
            customer.setContactNumber(contactNumberField.getText());
            customer.setUsername(usernameField.getText());
            customer.setPassword(passwordField.getText());
            okClicked = true;
            registrationStage.close();
        }
    }
	
	@FXML
    private void handleCancel() {
        registrationStage.close();
    }
	
	private boolean isInputValid() {
		Utility util = new Utility();
		String errorMessage = "";

        if (util.validateName(firstNameField.getText()) == false) {
            errorMessage += "Invalid first name, must start with a capital letter\n"; 
        }
        if (util.validateName(lastNameField.getText()) == false) {
            errorMessage += "Invalid last name, must start with a capital letter\n"; 
        }
        if (util.validateAddress(addressField.getText()) == false) {
            errorMessage += "Invalid address\n"; 
        }
        if (util.validateContactNumber(contactNumberField.getText()) == false) {
            errorMessage += "Invalid contact number must match Australian format\n"; 
        } 
        if (util.usernameExists(usernameField.getText()) == true) {
            errorMessage += "Username already exists\n"; 
        }
        if (util.validateUsername(usernameField.getText()) == false){
        	errorMessage += "Invalid username, must be six or more in length and can only contain letters and numbers\n";
        }
        if (util.validatePassword(passwordField.getText()) == false) {
            errorMessage += "Invalid password, must be at least seven in length, "
            		+ "must contain at least one number, one upper case character and one special character\n";
        }
        if(!passwordField.getText().equals(passwordField2.getText())){
        	errorMessage+="Passwords entered do not match";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Alert users of the error
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(registrationStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
	}

}
