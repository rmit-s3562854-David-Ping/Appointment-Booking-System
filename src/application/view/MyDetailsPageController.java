package application.view;

import application.MainApp;
import application.main.Customer;
import application.main.Member;
import application.main.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MyDetailsPageController {

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
	
	private Stage memberStage;
	private Member member;
	private boolean saveClicked = false;
	
	@FXML
	private void initialize() {
	}

	public void setMemberStage(Stage memberStage) {
		this.memberStage=memberStage;
	}

	public boolean isSaveClicked() {
		return saveClicked;
	}
	
	public void setMember(Member member) {
		this.member = member;

		firstNameField.setText(member.getFirstName());
		lastNameField.setText(member.getLastName());
		addressField.setText(member.getAddress());
		contactNumberField.setText(member.getContactNumber());
		usernameField.setText(member.getUsername());
		passwordField.setText(member.getPassword());
		passwordField2.setText(member.getPassword());
	}

	@FXML
	private void handleSave() {
		if (isInputValid()) {
			member.setFirstName(firstNameField.getText());
			member.setLastName(lastNameField.getText());
			member.setAddress(addressField.getText());
			member.setContactNumber(contactNumberField.getText());
			member.setUsername(usernameField.getText());
			member.setPassword(passwordField.getText());
			saveClicked = true;
			memberStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		memberStage.close();
	}
	
	private boolean isInputValid() {
		MainApp mainApp = new MainApp();
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
		if (util.usernameExists(usernameField.getText()) == true && !usernameField.getText().equals(mainApp.getUsername())) {
			errorMessage += "Username already exists\n";
		}
		if (util.validateUsername(usernameField.getText()) == false) {
			errorMessage += "Invalid username, must be six or more in length and can only contain letters and numbers\n";
		}
		if (util.validatePassword(passwordField.getText()) == false) {
			errorMessage += "Invalid password, must be at least seven in length, "
					+ "must contain at least one number, one upper case character and one special character\n";
		}
		if (!passwordField.getText().equals(passwordField2.getText())) {
			errorMessage += "Passwords entered do not match";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Alert users of the error
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(memberStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}
