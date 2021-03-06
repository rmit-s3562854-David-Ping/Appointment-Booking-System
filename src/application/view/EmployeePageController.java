package application.view;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.MainApp;
import application.main.Employee;
import application.main.Writer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

/**
 * @author David Ping
 * @version 1.00 Last edited: 24/04/2017
 */

public class EmployeePageController {

	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee, String> firstNameColumn;
	@FXML
	private TableColumn<Employee, String> lastNameColumn;
	@FXML
	private ListView<String> servicesList;

	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label idLabel;
	@FXML
	private Label mondayLabel;
	@FXML
	private Label tuesdayLabel;
	@FXML
	private Label wednesdayLabel;
	@FXML
	private Label thursdayLabel;
	@FXML
	private Label fridayLabel;
	@FXML
	private Label saturdayLabel;
	@FXML
	private Label sundayLabel;

	private List<Label> labelList = new ArrayList<>();

	private static final Logger LOGGER = Logger.getLogger("MyLog");

	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());

		servicesList.setDisable(true);
		servicesList.setStyle("-fx-opacity: 1;");

		// Clear person details.
		showEmployeeDetails(null);
		// Listen for selection changes and show the person details when
		// changed.
		employeeTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showEmployeeDetails(newValue));
		labelList.add(mondayLabel);
		labelList.add(tuesdayLabel);
		labelList.add(wednesdayLabel);
		labelList.add(thursdayLabel);
		labelList.add(fridayLabel);
		labelList.add(saturdayLabel);
		labelList.add(sundayLabel);
	}

	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		employeeTable.setItems(mainApp.getEmployeeData());
	}

	private void showEmployeeDetails(Employee employee) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
		for (int i = 0; i < labelList.size(); i++) {
			labelList.get(i).setText("");
		}
		if (employee != null) {
			firstNameLabel.setText(employee.getFirstName());
			lastNameLabel.setText(employee.getLastName());
			idLabel.setText(employee.getId());

			if (employee.getEmployeeServices() != null) {
				servicesList.getItems().clear();
				servicesList.getItems().addAll(employee.getEmployeeServices());
			}

			if (employee.getWorkTimes() != null) {
				for (int i = 0; i < employee.getWorkTimes().size(); i++) {
					for (int j = 0; j < DayOfWeek.values().length; j++) {
						if (employee.getWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.of(j + 1))) {
							labelList.get(j).setText(formatter.format(employee.getWorkTimes().get(i).getStartTime())
									+ "-" + formatter.format(employee.getWorkTimes().get(i).getEndTime()));
						}
					}
				}
			}
		}
		LOGGER.info("Employee details shown.");
	}

	@FXML
	private void handleNew() {
		Writer writer = new Writer();
		Employee employee = new Employee("", "", "", null, null);
		boolean okClicked = mainApp.showEmployeeNewDialog(employee);
		if (okClicked) {
			mainApp.getEmployeeData().add(employee);
			try {
				writer.saveEmployees(mainApp.getEmployeeData());
				LOGGER.info("Employee saved.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.SEVERE, e.toString(), e);
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleEdit() {
		Writer writer = new Writer();
		Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
		if (selectedEmployee != null) {
			boolean okClicked = mainApp.showEmployeeEditDialog(selectedEmployee);
			if (okClicked) {
				showEmployeeDetails(selectedEmployee);
				try {
					writer.saveEmployees(mainApp.getEmployeeData());
					LOGGER.info("Employee edited.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.log(Level.SEVERE, e.toString(), e);
					e.printStackTrace();
				}
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Employee Selected");
			alert.setContentText("Please select an employee from the table.");

			alert.showAndWait();
		}

	}

	@FXML
	private void handleDelete() {
		Writer writer = new Writer();
		boolean hasAppointment = false;
		int selectedIndex = employeeTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
				if (mainApp.getAppointmentArray().get(i).getEmployeeId()
						.equals(employeeTable.getItems().get(selectedIndex).getId())) {
					if (mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalDate().isAfter(LocalDate.now())
							|| mainApp.getAppointmentArray().get(i).getDateAndTime().toLocalDate()
									.isEqual(LocalDate.now())) {
						hasAppointment = true;
					}
				}
			}

			if (hasAppointment == false) {
				employeeTable.getItems().remove(selectedIndex);
				LOGGER.info("Employee removed.");
				try {
					writer.saveEmployees(mainApp.getEmployeeData());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.log(Level.SEVERE, e.toString(), e);
					e.printStackTrace();
				}
			} else {
				// Employee has appointments in the future, please edit/delete
				// those appointments
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Employee has appointments");
				alert.setHeaderText("Delete Failed");
				alert.setContentText("Please edit or delete appointments with this employee in future");

				alert.showAndWait();
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Employee Selected");
			alert.setContentText("Please select an employee from the table.");

			alert.showAndWait();
		}
	}

}
