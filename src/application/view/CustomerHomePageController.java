package application.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.MainApp;
import application.main.Appointment;
import application.main.Writer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author David Ping
 * @version 1.00 Last edited: 24/04/2017
 */

public class CustomerHomePageController {

	@FXML
	private Label businessName;
	@FXML
	private Label address;
	@FXML
	private Label contactNumber;
	
	public CustomerHomePageController() {
	}

	@FXML
	private void initialize() {
	}

	private MainApp mainApp;
	private static final Logger LOGGER = Logger.getLogger("MyLog");

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		businessName.setText(mainApp.getBusinessName());
		for(int i=0;i<mainApp.getOwnerArray().size();i++){
			if(mainApp.getOwnerArray().get(i).getBusinessName().equals(mainApp.getBusinessName())){
				address.setText(mainApp.getOwnerArray().get(i).getAddress());
				contactNumber.setText("Tel: "+mainApp.getOwnerArray().get(i).getContactNumber());
			}
		}
	}

	public void handleMakeAppointmentClicked() {
		Writer writer = new Writer();
		Appointment appointment = new Appointment(null, "", "", "");
		boolean okClicked = mainApp.showBookingsDialogPage(appointment);
		if (okClicked) {
			mainApp.getAppointmentArray().add(appointment);
			LOGGER.info("Make appointment clicked.");
			try {
				writer.saveAppointments(mainApp.getAppointmentArray());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.SEVERE, e.toString(), e);
				e.printStackTrace();
			}
		}
	}

	public void handleViewAppointmentsClicked() {
		LOGGER.info("View appointments clicked.");
		mainApp.showBookingsSummaryPage();
	}
}
