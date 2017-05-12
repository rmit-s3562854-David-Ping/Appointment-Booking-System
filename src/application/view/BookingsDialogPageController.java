package application.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import application.MainApp;
import application.main.Appointment;
import application.main.Employee;
import application.main.Service;
import application.main.Utility;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Ping
 * @version 1.00 Last edited: 24/04/2017
 */

public class BookingsDialogPageController {

	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox<String> serviceBox;
	@FXML
	private ComboBox<String> employeeNameBox;
	@FXML
	private Label durationLabel;
	@FXML
	private ComboBox<LocalTime> timeBox;

	private Stage appointmentStage;
	private Appointment appointment;
	private boolean okClicked = false;
	private static final Logger LOGGER = Logger.getLogger("MyLog");

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
		LOGGER.info("User selected ok.");
		return okClicked;
	}

	public void setNewAppointment(Appointment appointment) {
		this.appointment = appointment;
		datePicker.setValue(null);
		timeBox.setValue(null);
		serviceBox.setValue(null);
		durationLabel.setText(null);
		employeeNameBox.setValue(null);
	}

	@FXML
	private void handleOk() {
		MainApp mainApp = new MainApp();
		if (isInputValid()) {
			LocalDateTime dateTime = LocalDateTime.of(datePicker.getValue(), timeBox.getValue());
			SimpleObjectProperty<LocalDateTime> dateAndTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
			appointment.setCustomerUsername(mainApp.getUsername());
			appointment.setDateAndTime(dateAndTime);
			for (int i = 0; i < mainApp.getEmployeeData().size(); i++) {
				if (employeeNameBox.getValue().equals(mainApp.getEmployeeData().get(i).getFirstName() + " "
						+ mainApp.getEmployeeData().get(i).getLastName())) {
					appointment.setEmployeeId(mainApp.getEmployeeData().get(i).getId());
				}
			}
			appointment.setServiceName(serviceBox.getValue());
			okClicked = true;
			appointmentStage.close();
			LOGGER.info("Appointment set.");
		}
	}

	@FXML
	private void handleCancel() {
		LOGGER.info("Appointment setup cancelled.");
		appointmentStage.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (datePicker.getValue() == null) {
			errorMessage += "No date has been selected\n";
		}
		if (serviceBox.getValue() == null) {
			errorMessage += "No service has been selected\n";
		}
		if (employeeNameBox.getValue() == null) {
			errorMessage += "No employee has been selected\n";
		}
		if (timeBox.getValue() == null) {
			errorMessage += "No time has been selected\n";
		}
		if (errorMessage.length() == 0) {
			LOGGER.info("User input valdiadted.");
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(appointmentStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			LOGGER.info(errorMessage);
			return false;
		}
	}

	public void handleDateSelected() {
		MainApp mainApp = new MainApp();
		serviceBox.getItems().clear();
		employeeNameBox.getItems().clear();
		timeBox.getItems().clear();
		serviceBox.setValue(null);
		employeeNameBox.setValue(null);
		timeBox.setValue(null);
		if (datePicker.getValue().isBefore(LocalDate.now().plusDays(1))
				|| datePicker.getValue().isAfter(LocalDate.now().plusMonths(1))) {
			return;
		}
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
		employeeNameBox.getItems().clear();
		timeBox.getItems().clear();
		employeeNameBox.setValue(null);
		timeBox.setValue(null);
		if (serviceBox.getValue() == null) {
			return;
		}
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
						employeeNameBox.getItems().add(mainApp.getEmployeeData().get(i).getFirstName() + " "
								+ mainApp.getEmployeeData().get(i).getLastName());
					}
				}
			}
		}
		LOGGER.info("Service selected.");
	}

	public void handleEmployeeSelected() {
		MainApp mainApp = new MainApp();
		Service service = new Service();
		Utility util = new Utility();
		Employee employee = new Employee();
		LocalTime currentTime;
		timeBox.getItems().clear();
		timeBox.setValue(null);
		if (employeeNameBox.getValue() == null) {
			return;
		}
		String employeeId = null;
		for (int i = 0; i < mainApp.getEmployeeData().size(); i++) {
			if (employeeNameBox.getValue().equals(mainApp.getEmployeeData().get(i).getFirstName() + " "
					+ mainApp.getEmployeeData().get(i).getLastName())) {
				employeeId = mainApp.getEmployeeData().get(i).getId();
			}
		}

		int appointmentDuration = service.getService(serviceBox.getValue()).getDuration();
		for (int i = 0; i < mainApp.getBusinessWorkTimes().size(); i++) {
			if (mainApp.getBusinessWorkTimes().get(i).getDayOfWeek().equals(datePicker.getValue().getDayOfWeek())) {
				currentTime = mainApp.getBusinessWorkTimes().get(i).getStartTime();
			}
		}
		int counter = 0;
		for (int i = 0; i < employee.getEmployee(employeeId).getWorkTimes().size(); i++) {
			if (employee.getEmployee(employeeId).getWorkTimes().get(i).getDayOfWeek()
					.equals(datePicker.getValue().getDayOfWeek())) {
				counter = i;
			}
		}
		List<LocalTime> unavailableTimeBlocks = new ArrayList<LocalTime>();
		for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
			if (mainApp.getAppointmentArray().get(i).getEmployeeId().equals(employee.getEmployee(employeeId).getId()) && mainApp.getAppointmentArray()
					.get(i).getDateAndTime().toLocalDate().equals(datePicker.getValue())) {
				int thisDuration = service.getService(mainApp.getAppointmentArray().get(i).getServiceName())
						.getDuration();

				currentTime = mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime();
				unavailableTimeBlocks.add(currentTime);
				LocalTime endTime = currentTime.plusMinutes(thisDuration);
				while (currentTime.isBefore(endTime)) {
					unavailableTimeBlocks.add(currentTime);
					currentTime = currentTime.plusMinutes(util.TIME_BLOCK);
				}
			}
		}
		currentTime = employee.getEmployee(employeeId).getWorkTimes().get(counter).getStartTime();
		while (currentTime.isBefore(employee.getEmployee(employeeId).getWorkTimes().get(counter).getEndTime()
				.minusMinutes(appointmentDuration).plusMinutes(util.TIME_BLOCK))) {
			boolean valid = true;
			LocalTime endTime = currentTime.plusMinutes(appointmentDuration);
			while (currentTime.isBefore(endTime)) {
				if (unavailableTimeBlocks.contains(currentTime)) {
					valid = false;
				}
				currentTime = currentTime.plusMinutes(util.TIME_BLOCK);
			}
			currentTime = currentTime.minusMinutes(appointmentDuration);
			if (valid == true) {
				timeBox.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(util.TIME_BLOCK);
		}
		LOGGER.info("Employee selected.");
	}

}
