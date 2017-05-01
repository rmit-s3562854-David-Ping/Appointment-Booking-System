package application.view;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import application.MainApp;
import application.main.Business;
import application.main.Employee;
import application.main.Service;
import application.main.Utility;
import application.main.WorkTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author David Ping
 * @version 1.00 Last edited: 24/04/2017
 */

public class EmployeeDialogPageController {

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private ListView<String> servicesList;
	@FXML
	private ComboBox<LocalTime> mondayStart;
	@FXML
	private ComboBox<LocalTime> mondayEnd;
	@FXML
	private ComboBox<LocalTime> tuesdayStart;
	@FXML
	private ComboBox<LocalTime> tuesdayEnd;
	@FXML
	private ComboBox<LocalTime> wednesdayStart;
	@FXML
	private ComboBox<LocalTime> wednesdayEnd;
	@FXML
	private ComboBox<LocalTime> thursdayStart;
	@FXML
	private ComboBox<LocalTime> thursdayEnd;
	@FXML
	private ComboBox<LocalTime> fridayStart;
	@FXML
	private ComboBox<LocalTime> fridayEnd;
	@FXML
	private ComboBox<LocalTime> saturdayStart;
	@FXML
	private ComboBox<LocalTime> saturdayEnd;
	@FXML
	private ComboBox<LocalTime> sundayStart;
	@FXML
	private ComboBox<LocalTime> sundayEnd;

	private Stage employeeStage;
	private Employee employee;
	private boolean okClicked = false;
	private static final Logger LOGGER = Logger.getLogger("MyLog");

	@FXML
	private void initialize() {
	}

	public void setEmployeeStage(Stage employeeStage) {
		this.employeeStage = employeeStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void setEmployee(Employee employee) {
		MainApp mainApp = new MainApp();
		Business business = new Business();
		this.employee = employee;
		firstNameField.setText(employee.getFirstName());
		lastNameField.setText(employee.getLastName());
		if (employee.getWorkTimes() != null) {
			for (int i = 0; i < employee.getWorkTimes().size(); i++) {
				if (employee.getWorkTimes().get(i) == null) {
					continue;
				}
				if (employee.getWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.MONDAY)) {
					mondayStart.setValue(employee.getWorkTimes().get(i).getStartTime());
					mondayEnd.setValue(employee.getWorkTimes().get(i).getEndTime());
				} else if (employee.getWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.TUESDAY)) {
					tuesdayStart.setValue(employee.getWorkTimes().get(i).getStartTime());
					tuesdayEnd.setValue(employee.getWorkTimes().get(i).getEndTime());
				} else if (employee.getWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.WEDNESDAY)) {
					wednesdayStart.setValue(employee.getWorkTimes().get(i).getStartTime());
					wednesdayEnd.setValue(employee.getWorkTimes().get(i).getEndTime());
				} else if (employee.getWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
					thursdayStart.setValue(employee.getWorkTimes().get(i).getStartTime());
					thursdayEnd.setValue(employee.getWorkTimes().get(i).getEndTime());
				} else if (employee.getWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
					fridayStart.setValue(employee.getWorkTimes().get(i).getStartTime());
					fridayEnd.setValue(employee.getWorkTimes().get(i).getEndTime());
				} else if (employee.getWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
					saturdayStart.setValue(employee.getWorkTimes().get(i).getStartTime());
					saturdayEnd.setValue(employee.getWorkTimes().get(i).getEndTime());
				} else if (employee.getWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
					sundayStart.setValue(employee.getWorkTimes().get(i).getStartTime());
					sundayEnd.setValue(employee.getWorkTimes().get(i).getEndTime());
				}
			}

		}
		LocalTime currentTime = business.getOpeningTime();

		while (currentTime.isBefore(business.getClosingTime()) || currentTime.equals(business.getClosingTime())) {
			if (currentTime.isBefore(business.getClosingTime().minusHours(3))
					|| currentTime.equals(business.getClosingTime().minusHours(3))) {
				mondayStart.getItems().add(currentTime);
				tuesdayStart.getItems().add(currentTime);
				wednesdayStart.getItems().add(currentTime);
				thursdayStart.getItems().add(currentTime);
				fridayStart.getItems().add(currentTime);
				saturdayStart.getItems().add(currentTime);
				sundayStart.getItems().add(currentTime);
			}

			if (mondayStart.getValue() != null && (currentTime.isAfter(mondayStart.getValue().plusHours(3))
					|| currentTime.equals(mondayStart.getValue().plusHours(3)))) {
				mondayEnd.getItems().add(currentTime);
			}
			if (tuesdayStart.getValue() != null && (currentTime.isAfter(tuesdayStart.getValue().plusHours(3))
					|| currentTime.equals(tuesdayStart.getValue().plusHours(3)))) {
				tuesdayEnd.getItems().add(currentTime);
			}
			if (wednesdayStart.getValue() != null && (currentTime.isAfter(wednesdayStart.getValue().plusHours(3))
					|| currentTime.equals(wednesdayStart.getValue().plusHours(3)))) {
				wednesdayEnd.getItems().add(currentTime);
			}
			if (thursdayStart.getValue() != null && (currentTime.isAfter(thursdayStart.getValue().plusHours(3))
					|| currentTime.equals(thursdayStart.getValue().plusHours(3)))) {
				thursdayEnd.getItems().add(currentTime);
			}
			if (fridayStart.getValue() != null && (currentTime.isAfter(fridayStart.getValue().plusHours(3))
					|| currentTime.equals(fridayStart.getValue().plusHours(3)))) {
				fridayEnd.getItems().add(currentTime);
			}
			if (saturdayStart.getValue() != null && (currentTime.isAfter(saturdayStart.getValue().plusHours(3))
					|| currentTime.equals(saturdayStart.getValue().plusHours(3)))) {
				saturdayEnd.getItems().add(currentTime);
			}
			if (sundayStart.getValue() != null && (currentTime.isAfter(sundayStart.getValue().plusHours(3))
					|| currentTime.equals(sundayStart.getValue().plusHours(3)))) {
				sundayEnd.getItems().add(currentTime);
			}

			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
		ObservableList<String> qualifications = FXCollections.observableArrayList();
		for (int i = 0; i < mainApp.getServiceArray().size(); i++) {
			qualifications.add(mainApp.getServiceArray().get(i).getServiceName());
		}
		servicesList.getItems().addAll(qualifications);
		servicesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		for (int i = 0; i < employee.getEmployeeServices().size(); i++) {
			for (int j = 0; j < servicesList.getItems().size(); j++) {
				if (employee.getEmployeeServices().get(i).equals(servicesList.getItems().get(j))) {
					servicesList.getSelectionModel().select(servicesList.getItems().get(j));
				}
			}
		}
		LOGGER.info("Employee set.");
	}

	public void setNewEmployee(Employee employee) {
		MainApp mainApp = new MainApp();
		Business business = new Business();
		this.employee = employee;
		employee.setId(createID());
		firstNameField.setText("");
		lastNameField.setText("");
		LocalTime currentTime = business.getOpeningTime();
		while (currentTime.isBefore(business.getClosingTime())) {
			if (currentTime.isBefore(business.getClosingTime().minusHours(3))
					|| currentTime.equals(business.getClosingTime().minusHours(3))) {
				mondayStart.getItems().add(currentTime);
				tuesdayStart.getItems().add(currentTime);
				wednesdayStart.getItems().add(currentTime);
				thursdayStart.getItems().add(currentTime);
				fridayStart.getItems().add(currentTime);
				saturdayStart.getItems().add(currentTime);
				sundayStart.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
		ObservableList<String> qualifications = FXCollections.observableArrayList();
		for (int i = 0; i < mainApp.getServiceArray().size(); i++) {
			qualifications.add(mainApp.getServiceArray().get(i).getServiceName());
		}
		servicesList.getItems().addAll(qualifications);
		servicesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		LOGGER.info("New employee created.");

	}

	@FXML
	private void handleOk() {
		MainApp mainApp = new MainApp();
		Service service = new Service();

		for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
			if (employee.getId().equals(mainApp.getAppointmentArray().get(i).getEmployeeId())) {
				if (!servicesList.getSelectionModel().getSelectedItems()
						.contains(mainApp.getAppointmentArray().get(i).getServiceName())) {
					errorMessage();
					return;
				}
			}
		}

		if (isInputValid()) {
			ObservableList<String> services = FXCollections.observableArrayList();
			List<WorkTime> workTimes = new ArrayList<WorkTime>();
			if (mondayStart.getValue() != null) {
				WorkTime monday = new WorkTime(DayOfWeek.MONDAY, mondayStart.getValue(), mondayEnd.getValue());
				workTimes.add(monday);
			}
			if (tuesdayStart.getValue() != null) {
				WorkTime tuesday = new WorkTime(DayOfWeek.TUESDAY, tuesdayStart.getValue(), tuesdayEnd.getValue());
				workTimes.add(tuesday);
			}
			if (wednesdayStart.getValue() != null) {
				WorkTime wednesday = new WorkTime(DayOfWeek.WEDNESDAY, wednesdayStart.getValue(),
						wednesdayEnd.getValue());
				workTimes.add(wednesday);
			}
			if (thursdayStart.getValue() != null) {
				WorkTime thursday = new WorkTime(DayOfWeek.THURSDAY, thursdayStart.getValue(), thursdayEnd.getValue());
				workTimes.add(thursday);
			}
			if (fridayStart.getValue() != null) {
				WorkTime friday = new WorkTime(DayOfWeek.FRIDAY, fridayStart.getValue(), fridayEnd.getValue());
				workTimes.add(friday);
			}
			if (saturdayStart.getValue() != null) {
				WorkTime saturday = new WorkTime(DayOfWeek.SATURDAY, saturdayStart.getValue(), saturdayEnd.getValue());
				workTimes.add(saturday);
			}
			if (sundayStart.getValue() != null) {
				WorkTime sunday = new WorkTime(DayOfWeek.SUNDAY, sundayStart.getValue(), sundayEnd.getValue());
				workTimes.add(sunday);
			}

			for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
				if (employee.getId().equals(mainApp.getAppointmentArray().get(i).getEmployeeId())) {
					for (int j = 0; j < workTimes.size(); j++) {
						if (workTimes.get(j).getDayOfWeek()
								.equals(mainApp.getAppointmentArray().get(i).getDateAndTime().getDayOfWeek())) {

							if (!workTimes.get(j).getStartTime()
									.isBefore(mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime())
									&& !workTimes.get(j).getStartTime().equals(
											mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime())) {
								errorMessage();
								return;
							}
							if (!workTimes.get(j).getEndTime()
									.isAfter(mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime()
											.plusMinutes(service
													.getService(mainApp.getAppointmentArray().get(i).getServiceName())
													.getDuration()))
									&& !workTimes.get(j).getEndTime()
											.equals(mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime()
													.plusMinutes(service.getService(
															mainApp.getAppointmentArray().get(i).getServiceName())
															.getDuration()))) {
								errorMessage();
								return;
							}
						}
					}
				}
			}

			services.addAll(servicesList.getSelectionModel().getSelectedItems());

			employee.setFirstName(firstNameField.getText());
			employee.setLastName(lastNameField.getText());
			employee.setWorkTimes(workTimes);
			employee.setServices(services);
			okClicked = true;
			employeeStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		LOGGER.info("User cancelled.");
		employeeStage.close();
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
		if (errorMessage.length() == 0) {
			LOGGER.info("User input valid.");
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(employeeStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();
			LOGGER.info(errorMessage);
			return false;
		}
	}

	public void clearWorkingTimes() {
		mondayStart.setValue(null);
		mondayEnd.setValue(null);
		tuesdayStart.setValue(null);
		tuesdayEnd.setValue(null);
		wednesdayStart.setValue(null);
		wednesdayEnd.setValue(null);
		thursdayStart.setValue(null);
		thursdayEnd.setValue(null);
		fridayStart.setValue(null);
		fridayEnd.setValue(null);
		saturdayStart.setValue(null);
		saturdayEnd.setValue(null);
		sundayStart.setValue(null);
		sundayEnd.setValue(null);
		mondayEnd.getItems().clear();
		tuesdayEnd.getItems().clear();
		wednesdayEnd.getItems().clear();
		thursdayEnd.getItems().clear();
		fridayEnd.getItems().clear();
		saturdayEnd.getItems().clear();
		sundayEnd.getItems().clear();
		LOGGER.info("Working times cleared.");
	}

	public void changeEndTimeMonday() {
		if (mondayStart.getValue() == null) {
			return;
		}
		mondayEnd.getItems().clear();
		Business business = new Business();
		LocalTime currentTime = mondayStart.getValue();
		while (currentTime.isBefore(business.getClosingTime()) || currentTime.equals(business.getClosingTime())) {
			if (currentTime.isAfter(mondayStart.getValue().plusHours(3))
					|| currentTime.equals(mondayStart.getValue().plusHours(3))) {
				mondayEnd.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
		mondayEnd.setValue(mondayStart.getValue().plusHours(3));
		LOGGER.info("Monday end time changed.");
	}

	public void changeEndTimeTuesday() {
		if (tuesdayStart.getValue() == null) {
			return;
		}
		tuesdayEnd.getItems().clear();
		Business business = new Business();
		LocalTime currentTime = tuesdayStart.getValue();
		while (currentTime.isBefore(business.getClosingTime()) || currentTime.equals(business.getClosingTime())) {
			if (currentTime.isAfter(tuesdayStart.getValue().plusHours(3))
					|| currentTime.equals(tuesdayStart.getValue().plusHours(3))) {
				tuesdayEnd.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
		tuesdayEnd.setValue(tuesdayStart.getValue().plusHours(3));
		LOGGER.info("Tuesday end time changed.");
	}

	public void changeEndTimeWednesday() {
		if (wednesdayStart.getValue() == null) {
			return;
		}
		wednesdayEnd.getItems().clear();
		Business business = new Business();
		LocalTime currentTime = wednesdayStart.getValue();
		while (currentTime.isBefore(business.getClosingTime()) || currentTime.equals(business.getClosingTime())) {
			if (currentTime.isAfter(wednesdayStart.getValue().plusHours(3))
					|| currentTime.equals(wednesdayStart.getValue().plusHours(3))) {
				wednesdayEnd.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
		wednesdayEnd.setValue(wednesdayStart.getValue().plusHours(3));
		LOGGER.info("Wednesday end time changed.");
	}

	public void changeEndTimeThursday() {
		if (thursdayStart.getValue() == null) {
			return;
		}
		thursdayEnd.getItems().clear();
		Business business = new Business();
		LocalTime currentTime = thursdayStart.getValue();
		while (currentTime.isBefore(business.getClosingTime()) || currentTime.equals(business.getClosingTime())) {
			if (currentTime.isAfter(thursdayStart.getValue().plusHours(3))
					|| currentTime.equals(thursdayStart.getValue().plusHours(3))) {
				thursdayEnd.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
		thursdayEnd.setValue(thursdayStart.getValue().plusHours(3));
		LOGGER.info("Thursday end time changed.");
	}

	public void changeEndTimeFriday() {
		if (fridayStart.getValue() == null) {
			return;
		}
		fridayEnd.getItems().clear();
		Business business = new Business();
		LocalTime currentTime = fridayStart.getValue();
		while (currentTime.isBefore(business.getClosingTime()) || currentTime.equals(business.getClosingTime())) {
			if (currentTime.isAfter(fridayStart.getValue().plusHours(3))
					|| currentTime.equals(fridayStart.getValue().plusHours(3))) {
				fridayEnd.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
		fridayEnd.setValue(fridayStart.getValue().plusHours(3));
		LOGGER.info("Friday end time changed.");
	}

	public void changeEndTimeSaturday() {
		if (saturdayStart.getValue() == null) {
			return;
		}
		saturdayEnd.getItems().clear();
		Business business = new Business();
		LocalTime currentTime = saturdayStart.getValue();
		while (currentTime.isBefore(business.getClosingTime()) || currentTime.equals(business.getClosingTime())) {
			if (currentTime.isAfter(saturdayStart.getValue().plusHours(3))
					|| currentTime.equals(saturdayStart.getValue().plusHours(3))) {
				saturdayEnd.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
		saturdayEnd.setValue(saturdayStart.getValue().plusHours(3));
		LOGGER.info("Saturday end time changed.");
	}

	public void changeEndTimeSunday() {
		if (sundayStart.getValue() == null) {
			return;
		}
		sundayEnd.getItems().clear();
		Business business = new Business();
		LocalTime currentTime = sundayStart.getValue();
		while (currentTime.isBefore(business.getClosingTime()) || currentTime.equals(business.getClosingTime())) {
			if (currentTime.isAfter(sundayStart.getValue().plusHours(3))
					|| currentTime.equals(sundayStart.getValue().plusHours(3))) {
				sundayEnd.getItems().add(currentTime);
			}
			currentTime = currentTime.plusMinutes(business.TIME_BLOCK);
		}
		sundayEnd.setValue(sundayStart.getValue().plusHours(3));
		LOGGER.info("Sunday end time changed.");
	}

	/**
	 * This method creates a unique ID in the format eXXXXX (X being a number).
	 * 
	 * @author Luke Waldren
	 */
	public String createID() {
		MainApp mainApp = new MainApp();
		int IDCounter = 1;
		int index = 0;
		while (index < mainApp.getEmployeeData().size()) {
			index = 0;
			while (index < mainApp.getEmployeeData().size()) {
				if (Integer.parseInt(String.format("%05d", IDCounter)) == Integer
						.parseInt((mainApp.getEmployeeData().get(index).getId()).substring(1, 6))) {
					IDCounter++;
					break;
				} else {
					index++;
				}
			}
		}
		String ID = "e" + String.format("%05d", IDCounter);
		LOGGER.info("ID generated.");
		return ID;
	}

	public void errorMessage() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(employeeStage);
		alert.setTitle("Invalid Edit");
		alert.setHeaderText("Invalid Edit");
		alert.setContentText("New employee attributes conflict with existing bookings");

		alert.showAndWait();
	}
}
