package application.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import application.MainApp;
import application.main.Service;
import application.main.Appointment;
import application.main.Business;
import application.main.Customer;
import application.main.Employee;
import application.main.Utility;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author David Ping
 * @version 1.00
 * Last edited: 24/04/2017
 * */

public class OwnerBookingsDialogPageController {

	@FXML
	private TextField customerUsernameField;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox<String> serviceBox;
	@FXML
	private ComboBox<String> employeeIdBox;
	@FXML
	private ComboBox<LocalTime> timeBox;
	@FXML
	private Label durationLabel;
	@FXML
	private Label customerNameLabel;
	@FXML 
	private Label customerContactLabel;
	@FXML
	private Label customerAddressLabel;
	@FXML
	private Label employeeNameLabel;

	private Stage appointmentStage;
	private Appointment appointment;
	private boolean okClicked = false;

	@FXML
	private void initialize() {

		Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				MainApp mainApp = new MainApp();
				if (item.isBefore(LocalDate.now().plusDays(1)) || item.isAfter(LocalDate.now().plusMonths(1))) {
					setStyle("-fx-background-color: #FF716D; -fx-text-fill: gray;");
					setDisable(true);
				}
				List<DayOfWeek> availableDays = new ArrayList<DayOfWeek>();
				for (int i = 0; i < mainApp.getEmployeeData().size(); i++) {
					for (int j = 0; j < mainApp.getEmployeeData().get(i).getWorkTimes().size(); j++) {
						if (!availableDays
								.contains(mainApp.getEmployeeData().get(i).getWorkTimes().get(j).getDayOfWeek())) {
							availableDays.add(mainApp.getEmployeeData().get(i).getWorkTimes().get(j).getDayOfWeek());
						}
					}
				}
				if (!availableDays.contains(item.getDayOfWeek())) {
					setStyle("-fx-background-color: #FF716D; -fx-text-fill: gray;");
					setDisable(true);
				}
			}
		};
		datePicker.setDayCellFactory(dayCellFactory);
		datePicker.setValue(LocalDate.now().plusDays(1));

	}

	public void setAppointmentStage(Stage appointmentStage) {
		this.appointmentStage = appointmentStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void setAppointment(Appointment appointment) {
		MainApp mainApp = new MainApp();
		Service service = new Service();
		Business business = new Business();
		Employee employee = new Employee();
		Customer customer = new Customer();
		this.appointment = appointment;
		
		customerUsernameField.setText(appointment.getCustomerUsername());
		customerNameLabel.setText(customer.getCustomer(appointment.getCustomerUsername()).getFirstName()+" "+
				customer.getCustomer(appointment.getCustomerUsername()).getLastName());
		customerContactLabel.setText(customer.getCustomer(appointment.getCustomerUsername()).getContactNumber());
		customerAddressLabel.setText(customer.getCustomer(appointment.getCustomerUsername()).getAddress());
		datePicker.setValue(appointment.getDateAndTime().toLocalDate());
		timeBox.setValue(appointment.getDateAndTime().toLocalTime());
		serviceBox.setValue(appointment.getServiceName());
		durationLabel
				.setText(String.valueOf(service.getService(appointment.getServiceName()).getDuration()) + " minutes");
		employeeIdBox.setValue(appointment.getEmployeeId());
		employeeNameLabel.setText(employee.getEmployee(appointment.getEmployeeId()).getFirstName()+" "+
		employee.getEmployee(appointment.getEmployeeId()).getLastName());
		
		for (int i = 0; i < mainApp.getEmployeeData().size(); i++) {
			boolean employeeAvailable = false;
			for (int j = 0; j < mainApp.getEmployeeData().get(i).getWorkTimes().size(); j++) {
				if (mainApp.getEmployeeData().get(i).getWorkTimes().get(j).getDayOfWeek()
						.equals(datePicker.getValue().getDayOfWeek())) {
					employeeAvailable = true;
				}
			}
			if (employeeAvailable == true) {
				for (int j = 0; j < mainApp.getEmployeeData().get(i).getEmployeeServices().size(); j++) {
					if (!serviceBox.getItems()
							.contains(mainApp.getEmployeeData().get(i).getEmployeeServices().get(j))) {
						serviceBox.getItems().add(mainApp.getEmployeeData().get(i).getEmployeeServices().get(j));
					}
				}
			}
		}

		for (int i = 0; i < mainApp.getEmployeeData().size(); i++) {
			boolean employeeAvailable = false;
			// if the employee is working on the date specified
			for (int j = 0; j < mainApp.getEmployeeData().get(i).getWorkTimes().size(); j++) {
				if (mainApp.getEmployeeData().get(i).getWorkTimes().get(j).getDayOfWeek()
						.equals(datePicker.getValue().getDayOfWeek())) {
					employeeAvailable = true;
				}
			}
			if (employeeAvailable == true) {
				for (int j = 0; j < mainApp.getEmployeeData().get(i).getEmployeeServices().size(); j++) {
					if (mainApp.getEmployeeData().get(i).getEmployeeServices().get(j).equals(serviceBox.getValue())) {
						employeeIdBox.getItems().add(mainApp.getEmployeeData().get(i).getId());
					}
				}
			}
		}

		int appointmentDuration = service.getService(serviceBox.getValue()).getDuration();
		LocalTime currentTime = business.getOpeningTime();
		int counter = 0;
		for (int i = 0; i < employee.getEmployee(employeeIdBox.getValue()).getWorkTimes().size(); i++) {
			if (employee.getEmployee(employeeIdBox.getValue()).getWorkTimes().get(i).getDayOfWeek()
					.equals(datePicker.getValue().getDayOfWeek())) {
				counter = i;
			}
		}
		List<LocalTime> unavailableTimeBlocks = new ArrayList<LocalTime>();
		for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
			if (mainApp.getAppointmentArray().get(i).getEmployeeId().equals(employeeIdBox.getValue()) && mainApp
					.getAppointmentArray().get(i).getDateAndTime().toLocalDate().equals(datePicker.getValue())) {
				int thisDuration = service.getService(mainApp.getAppointmentArray().get(i).getServiceName())
						.getDuration();

				currentTime = mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime();
				unavailableTimeBlocks.add(currentTime);
				LocalTime endTime = currentTime.plusMinutes(thisDuration);
				while (currentTime.isBefore(endTime)) {
					unavailableTimeBlocks.add(currentTime);
					currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
				}
			}
		}
		currentTime = employee.getEmployee(employeeIdBox.getValue()).getWorkTimes().get(counter).getStartTime();
		while (currentTime.isBefore(employee.getEmployee(employeeIdBox.getValue()).getWorkTimes().get(counter)
				.getEndTime().minusMinutes(appointmentDuration).plusMinutes(business.TIME_BLOCK))) {
			boolean valid = true;
			LocalTime endTime = currentTime.plusMinutes(appointmentDuration);
			while (currentTime.isBefore(endTime)) {
				if (unavailableTimeBlocks.contains(currentTime)) {
					valid = false;
				}
				currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
			}
			currentTime = currentTime.minusMinutes(appointmentDuration);
			if (valid == true) {
				timeBox.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
	}

	public void setNewAppointment(Appointment appointment) {
		this.appointment = appointment;
		customerUsernameField.setText("");
		customerNameLabel.setText("");
		customerContactLabel.setText("");
		customerAddressLabel.setText("");
		datePicker.setValue(null);
		timeBox.setValue(null);
		serviceBox.setValue(null);
		durationLabel.setText("");
		employeeIdBox.setValue(null);
		employeeNameLabel.setText("");
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			LocalDateTime dateTime = LocalDateTime.of(datePicker.getValue(), timeBox.getValue());
			SimpleObjectProperty<LocalDateTime> dateAndTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
			appointment.setCustomerUsername(customerUsernameField.getText());
			appointment.setDateAndTime(dateAndTime);
			appointment.setEmployeeId(employeeIdBox.getValue());
			appointment.setServiceName(serviceBox.getValue());
			okClicked = true;
			appointmentStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		appointmentStage.close();
	}

	private boolean isInputValid() {
		Utility util = new Utility();
		String errorMessage = "";

		if (util.usernameExists(customerUsernameField.getText()) == false) {
			errorMessage += "Customer username does not exist\n";
		}
		if (datePicker.getValue() == null) {
			errorMessage += "No date has been selected\n";
		}
		if (serviceBox.getValue() == null) {
			errorMessage += "No service has been selected\n";
		}
		if (employeeIdBox.getValue() == null) {
			errorMessage += "No employee has been selected\n";
		}
		if (timeBox.getValue() == null) {
			errorMessage += "No time has been selected\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(appointmentStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();

			return false;
		}
	}
	
	public void handleUsernameEntered(){
		MainApp mainApp = new MainApp();
		Customer customer = new Customer();
		for(int i=0;i<mainApp.getCustomerArray().size();i++){
			if(mainApp.getCustomerArray().get(i).getUsername().equals(customerUsernameField.getText())){
				customerNameLabel.setText(customer.getCustomer(customerUsernameField.getText()).getFirstName()+" "+
						customer.getCustomer(customerUsernameField.getText()).getLastName());
				customerContactLabel.setText(customer.getCustomer(customerUsernameField.getText()).getContactNumber());
				customerAddressLabel.setText(customer.getCustomer(customerUsernameField.getText()).getAddress());
				break;
			}else if(i==mainApp.getCustomerArray().size()-1){
				customerNameLabel.setText("");
				customerContactLabel.setText("");
				customerAddressLabel.setText("");
			}
		}
	}

	public void handleDateSelected() {
		MainApp mainApp = new MainApp();
		serviceBox.getItems().clear();
		employeeIdBox.getItems().clear();
		timeBox.getItems().clear();
		serviceBox.setValue(null);
		employeeIdBox.setValue(null);
		timeBox.setValue(null);	
		if (datePicker.getValue().isBefore(LocalDate.now().plusDays(1))
				|| datePicker.getValue().isAfter(LocalDate.now().plusMonths(1))) {
			return;
		}
		employeeNameLabel.setText("");
		for (int i = 0; i < mainApp.getEmployeeData().size(); i++) {
			boolean employeeAvailable = false;
			for (int j = 0; j < mainApp.getEmployeeData().get(i).getWorkTimes().size(); j++) {
				if (mainApp.getEmployeeData().get(i).getWorkTimes().get(j).getDayOfWeek()
						.equals(datePicker.getValue().getDayOfWeek())) {
					employeeAvailable = true;
				}
			}
			if (employeeAvailable == true) {
				for (int j = 0; j < mainApp.getEmployeeData().get(i).getEmployeeServices().size(); j++) {
					if (!serviceBox.getItems()
							.contains(mainApp.getEmployeeData().get(i).getEmployeeServices().get(j))) {
						serviceBox.getItems().add(mainApp.getEmployeeData().get(i).getEmployeeServices().get(j));
					}
				}
			}
		}
	}

	public void handleServiceSelected() {
		MainApp mainApp = new MainApp();
		Service service = new Service();
		employeeIdBox.getItems().clear();
		timeBox.getItems().clear();
		employeeIdBox.setValue(null);
		timeBox.setValue(null);
		if(serviceBox.getValue()==null){
			return;
		}
		employeeNameLabel.setText("");
		durationLabel.setText(String.valueOf(service.getService(serviceBox.getValue()).getDuration()) + " minutes");
		for (int i = 0; i < mainApp.getEmployeeData().size(); i++) {
			boolean employeeAvailable = false;
			// if the employee is working on the date specified
			for (int j = 0; j < mainApp.getEmployeeData().get(i).getWorkTimes().size(); j++) {
				if (mainApp.getEmployeeData().get(i).getWorkTimes().get(j).getDayOfWeek()
						.equals(datePicker.getValue().getDayOfWeek())) {
					employeeAvailable = true;
				}
			}
			if (employeeAvailable == true) {
				for (int j = 0; j < mainApp.getEmployeeData().get(i).getEmployeeServices().size(); j++) {
					if (mainApp.getEmployeeData().get(i).getEmployeeServices().get(j).equals(serviceBox.getValue())) {
						employeeIdBox.getItems().add(mainApp.getEmployeeData().get(i).getId());
					}
				}
			}
		}
	}

	public void handleEmployeeSelected() {
		MainApp mainApp = new MainApp();
		Service service = new Service();
		Business business = new Business();
		Employee employee = new Employee();
		timeBox.getItems().clear();
		timeBox.setValue(null);
		if(employeeIdBox.getValue()==null){
			return;
		}
		employeeNameLabel.setText(employee.getEmployee(employeeIdBox.getValue()).getFirstName()+" "+
				employee.getEmployee(employeeIdBox.getValue()).getLastName());
		int appointmentDuration = service.getService(serviceBox.getValue()).getDuration();
		LocalTime currentTime = business.getOpeningTime();
		int counter = 0;
		for (int i = 0; i < employee.getEmployee(employeeIdBox.getValue()).getWorkTimes().size(); i++) {
			if (employee.getEmployee(employeeIdBox.getValue()).getWorkTimes().get(i).getDayOfWeek()
					.equals(datePicker.getValue().getDayOfWeek())) {
				counter = i;
			}
		}
		List<LocalTime> unavailableTimeBlocks = new ArrayList<LocalTime>();
		for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
			if (mainApp.getAppointmentArray().get(i).getEmployeeId().equals(employeeIdBox.getValue()) && mainApp
					.getAppointmentArray().get(i).getDateAndTime().toLocalDate().equals(datePicker.getValue())) {
				int thisDuration = service.getService(mainApp.getAppointmentArray().get(i).getServiceName())
						.getDuration();

				currentTime = mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime();
				unavailableTimeBlocks.add(currentTime);
				LocalTime endTime = currentTime.plusMinutes(thisDuration);
				while (currentTime.isBefore(endTime)) {
					unavailableTimeBlocks.add(currentTime);
					currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
				}
			}
		}
		currentTime = employee.getEmployee(employeeIdBox.getValue()).getWorkTimes().get(counter).getStartTime();
		while (currentTime.isBefore(employee.getEmployee(employeeIdBox.getValue()).getWorkTimes().get(counter)
				.getEndTime().minusMinutes(appointmentDuration).plusMinutes(business.TIME_BLOCK))) {
			boolean valid = true;
			LocalTime endTime = currentTime.plusMinutes(appointmentDuration);
			while (currentTime.isBefore(endTime)) {
				if (unavailableTimeBlocks.contains(currentTime)) {
					valid = false;
				}
				currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
			}
			currentTime = currentTime.minusMinutes(appointmentDuration);
			if (valid == true) {
				timeBox.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
	}
}
