package application.view;

import java.io.IOException;

import application.MainApp;
import application.main.Appointment;
import application.main.Writer;
import javafx.fxml.FXML;

/**
 * @author David Ping
 * @version 1.00
 * Last edited: 24/04/2017
 * */

public class CustomerHomePageController {

	public CustomerHomePageController(){}
	
	@FXML
    private void initialize() {
    }
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void handleMakeAppointmentClicked(){
		Writer writer = new Writer();
		Appointment appointment = new Appointment(null, "", "", "");
		boolean okClicked = mainApp.showBookingsDialogPage(appointment);
		if (okClicked) {
			mainApp.getAppointmentArray().add(appointment);
			try {
				writer.saveAppointments(mainApp.getAppointmentArray());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleViewAppointmentsClicked(){
		
	}
}
