package application.view;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import application.MainApp;
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
 * @version 1.01 Last edited: 04/05/2017 Controls all functions of the employee
 *          dialog box which opens after new or edit is clicked on the Employee
 *          page
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

	private List<ComboBox<LocalTime>> startTimes = new ArrayList<ComboBox<LocalTime>>();
	private List<ComboBox<LocalTime>> endTimes = new ArrayList<ComboBox<LocalTime>>();

	private Stage employeeStage;
	private Employee employee;
	private boolean okClicked = false;
	private static final Logger LOGGER = Logger.getLogger("MyLog");

	@FXML
	private void initialize() {
		startTimes.add(mondayStart);
		startTimes.add(tuesdayStart);
		startTimes.add(wednesdayStart);
		startTimes.add(thursdayStart);
		startTimes.add(fridayStart);
		startTimes.add(saturdayStart);
		startTimes.add(sundayStart);
		endTimes.add(mondayEnd);
		endTimes.add(tuesdayEnd);
		endTimes.add(wednesdayEnd);
		endTimes.add(thursdayEnd);
		endTimes.add(fridayEnd);
		endTimes.add(saturdayEnd);
		endTimes.add(sundayEnd);
	}

	public void setEmployeeStage(Stage employeeStage) {
		this.employeeStage = employeeStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * @author David Ping Sets up dialog box for an existing employee
	 */
	public void setEmployee(Employee employee) {
		MainApp mainApp = new MainApp();
		Utility util = new Utility();
		LocalTime currentTime;
		this.employee = employee;
		firstNameField.setText(employee.getFirstName());
		lastNameField.setText(employee.getLastName());
		if (employee.getWorkTimes() != null) {
			for (int i = 0; i < employee.getWorkTimes().size(); i++) {
				if (employee.getWorkTimes().get(i) == null) {
					continue;
				}
				for (int j = 0; j < DayOfWeek.values().length; j++) {
					if (employee.getWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.of(j + 1))) {
						startTimes.get(j).setValue(employee.getWorkTimes().get(i).getStartTime());
						endTimes.get(j).setValue(employee.getWorkTimes().get(i).getEndTime());
					}
				}
			}
		}

		for (int i = 0; i < mainApp.getBusinessWorkTimes().size(); i++) {
			for (int j = 0; j < DayOfWeek.values().length; j++) {
				if (mainApp.getBusinessWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.of(j + 1))) {
					currentTime = mainApp.getBusinessWorkTimes().get(i).getStartTime();
					while (currentTime.isBefore(mainApp.getBusinessWorkTimes().get(i).getEndTime())
							|| currentTime.equals(mainApp.getBusinessWorkTimes().get(i).getEndTime())) {
						if (currentTime.isBefore(mainApp.getBusinessWorkTimes().get(i).getEndTime().minusMinutes(util.MIN_TOTAL_LENGTH))
								|| currentTime
										.equals(mainApp.getBusinessWorkTimes().get(i).getEndTime().minusMinutes(util.MIN_TOTAL_LENGTH))) {
							startTimes.get(j).getItems().add(currentTime);
						}
						if (startTimes.get(j).getValue() != null
								&& (currentTime.isAfter(startTimes.get(j).getValue().plusMinutes(util.MIN_TOTAL_LENGTH))
										|| currentTime.equals(startTimes.get(j).getValue().plusMinutes(util.MIN_TOTAL_LENGTH)))) {
							endTimes.get(j).getItems().add(currentTime);
						}
						currentTime = currentTime.plusMinutes(util.TIME_BLOCK);
					}
				}
			}
		}

		ObservableList<String> services = FXCollections.observableArrayList();
		for (int i = 0; i < mainApp.getServiceArray().size(); i++) {
			services.add(mainApp.getServiceArray().get(i).getServiceName());
		}
		servicesList.getItems().addAll(services);
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

	/**
	 * @author David Ping Sets up dialog box for a new employee
	 */
	public void setNewEmployee(Employee employee) {
		MainApp mainApp = new MainApp();
		Utility util = new Utility();
		this.employee = employee;
		employee.setId(createID());
		firstNameField.setText("");
		lastNameField.setText("");
		LocalTime currentTime;

		for (int i = 0; i < mainApp.getBusinessWorkTimes().size(); i++) {
			for (int j = 0; j < DayOfWeek.values().length; j++) {
				if (mainApp.getBusinessWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.of(j + 1))) {
					currentTime = mainApp.getBusinessWorkTimes().get(i).getStartTime();
					while (currentTime.isBefore(mainApp.getBusinessWorkTimes().get(i).getEndTime())
							|| currentTime.equals(mainApp.getBusinessWorkTimes().get(i).getEndTime())) {
						if (currentTime.isBefore(mainApp.getBusinessWorkTimes().get(i).getEndTime().minusMinutes(util.MIN_TOTAL_LENGTH))
								|| currentTime
										.equals(mainApp.getBusinessWorkTimes().get(i).getEndTime().minusMinutes(util.MIN_TOTAL_LENGTH))) {
							startTimes.get(j).getItems().add(currentTime);
						}
						currentTime = currentTime.plusMinutes(util.TIME_BLOCK);
					}
				}
			}
		}

		ObservableList<String> services = FXCollections.observableArrayList();
		for (int i = 0; i < mainApp.getServiceArray().size(); i++) {
			services.add(mainApp.getServiceArray().get(i).getServiceName());
		}
		servicesList.getItems().addAll(services);
		servicesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		LOGGER.info("New employee created.");

	}

	/**
	 * @author David Ping Function checks all the fields using validation and
	 *         sets employee details if all are passed Runs when the Ok Button
	 *         is clicked
	 */
	@FXML
	private void handleOk() {
		MainApp mainApp = new MainApp();

		// Checks the Services against employees existing appointments
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
			for (int i = 0; i < startTimes.size(); i++) {
				if (startTimes.get(i).getValue() != null) {
					WorkTime newTime = new WorkTime(DayOfWeek.of(i + 1), startTimes.get(i).getValue(),
							endTimes.get(i).getValue());
					workTimes.add(newTime);
				}
			}
			if (!isWorkTimesValid(workTimes, employee)) {
				errorMessage();
				return;
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

	/**
	 * @author David Ping This function will validate the first name and last
	 *         name fields
	 */
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

	/**
	 * @author David Ping This function checks the whether the new work times
	 *         being considered can be applied to the employee by checking if
	 *         the new list of work times conflict with any of the employees
	 *         existing bookings, it will return false if it does and true if it
	 *         does not
	 */
	public boolean isWorkTimesValid(List<WorkTime> workTimes, Employee employee) {
		MainApp mainApp = new MainApp();
		Service service = new Service();
		for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
			if (employee.getId().equals(mainApp.getAppointmentArray().get(i).getEmployeeId())) {
				if(workTimes.isEmpty()){
					return false;
				}
				for (int j = 0; j < workTimes.size(); j++) {
					if (workTimes.get(j).getDayOfWeek()
							.equals(mainApp.getAppointmentArray().get(i).getDateAndTime().getDayOfWeek())) {
						if (workTimes.get(j).getStartTime()
								.isBefore(mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime())
								|| workTimes.get(j).getStartTime()
										.equals(mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime())) {
							if (workTimes.get(j).getEndTime()
									.isAfter(mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime()
											.plusMinutes(service
													.getService(mainApp.getAppointmentArray().get(i).getServiceName())
													.getDuration()))
									|| workTimes.get(j).getEndTime()
											.equals(mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalTime()
													.plusMinutes(service.getService(
															mainApp.getAppointmentArray().get(i).getServiceName())
															.getDuration()))) {
								break;
							}
						}
					}
					if (j == workTimes.size() - 1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * @author David Ping This function clears all the working times in the
	 *         dialog page, this is done because as of now an error exists in
	 *         JRE where it will return a indexoutofbounds exception if a null
	 *         is selected from a combo box
	 */
	public void clearWorkingTimes() {
		for (int i = 0; i < startTimes.size(); i++) {
			startTimes.get(i).setValue(null);
			endTimes.get(i).setValue(null);
			endTimes.get(i).getItems().clear();
		}
		LOGGER.info("Working times cleared.");
	}

	/**
	 * @author David Ping This will refresh the end times in the dialog page
	 *         whenever a start time is changed, this prevents any possibility
	 *         of errors from users and supports the functionality of drop down
	 *         boxes
	 */
	public void changeEndTime() {
		MainApp mainApp = new MainApp();
		Utility util = new Utility();
		LocalTime currentTime;

		for (int i = 0; i < startTimes.size(); i++) {
			if (startTimes.get(i).getValue() != null) {
				LocalTime oldTime = endTimes.get(i).getValue();
				endTimes.get(i).getItems().clear();
				currentTime = startTimes.get(i).getValue();
				for (int j = 0; j < mainApp.getBusinessWorkTimes().size(); j++) {
					if (mainApp.getBusinessWorkTimes().get(j).getDayOfWeek().getValue() == i + 1) {
						while (currentTime.isBefore(mainApp.getBusinessWorkTimes().get(j).getEndTime())
								|| currentTime.equals(mainApp.getBusinessWorkTimes().get(j).getEndTime())) {
							if (currentTime.isAfter(startTimes.get(i).getValue().plusMinutes(util.MIN_TOTAL_LENGTH))
									|| currentTime.equals(startTimes.get(i).getValue().plusMinutes(util.MIN_TOTAL_LENGTH))) {
								endTimes.get(i).getItems().add(currentTime);
							}
							currentTime = currentTime.plusMinutes(util.TIME_BLOCK);
						}
					}
				}

				if (oldTime != null) {
					if (oldTime.isBefore(startTimes.get(i).getValue().plusMinutes(util.MIN_TOTAL_LENGTH))) {
						endTimes.get(i).setValue(startTimes.get(i).getValue().plusMinutes(util.MIN_TOTAL_LENGTH));
					} else {
						endTimes.get(i).setValue(oldTime);
					}
				} else {
					endTimes.get(i).setValue(startTimes.get(i).getValue().plusMinutes(util.MIN_TOTAL_LENGTH));
				}
			} else {
				endTimes.get(i).getItems().clear();
			}
		}
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
