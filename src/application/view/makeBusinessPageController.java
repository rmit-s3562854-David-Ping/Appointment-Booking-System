package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import application.MainApp;
import application.main.Owner;
import application.main.Utility;
import application.main.Writer;

public class makeBusinessPageController {

	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField passwordField2;
	@FXML
	private TextField businessName;

	@FXML
	private void initialize() {

	}

	public void createBusiness() throws IOException {
		MainApp main = new MainApp();
		Writer writer = new Writer();
		File dir = new File("businesses/" + businessName.getText());
		if (isInputValid()) {
			if (!dir.exists()) {
				dir.mkdir();

				// Create the files

				File file = new File("businesses/" + businessName.getText() + "/appointmentinfo.txt");
				createFile(file);

				file = new File("businesses/" + businessName.getText() + "/employeeinfo.txt");
				createFile(file);

				file = new File("businesses/" + businessName.getText() + "/servicesinfo.txt");
				createFile(file);

				file = new File("businesses/" + businessName.getText() + "/worktimesinfo.txt");
				createFile(file);

				ArrayList<Owner> ownerArray = main.getOwnerArray();
				Owner newOwner = new Owner(usernameField.getText(), passwordField.getText(), businessName.getText());
				newOwner.setFirstName(" ");
				newOwner.setLastName(" ");
				newOwner.setAddress(" ");
				newOwner.setContactNumber(" ");
				ownerArray.add(newOwner);
				writer.saveOwner();

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Success");
				alert.setHeaderText("Registration complete");
				alert.setContentText("Business registration successful!");

				alert.showAndWait();
			}
		}
	}

	public void createFile(File file) throws IOException {
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}
	}

	private boolean isInputValid() {
		Utility util = new Utility();
		String errorMessage = "";

		if (util.validateBusinessName(businessName.getText()) == false) {
			errorMessage += "Invalid Business name, no special characters and must be between 1 and 20 in length\n";
		}
		if (util.businessExists(businessName.getText()) == true) {
			errorMessage += "Business name already exists\n";
		}
		if (util.usernameExists(usernameField.getText()) == true) {
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
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}