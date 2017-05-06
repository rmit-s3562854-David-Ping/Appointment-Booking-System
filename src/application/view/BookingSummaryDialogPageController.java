package application.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.awt.Button;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;

import application.MainApp;
import application.main.Appointment;
import application.main.Customer;
import application.main.Employee;
import application.main.Service;
import application.main.Writer;

public class BookingSummaryDialogPageController {

	@FXML
	private TableView<Appointment> timeTable;
	@FXML
	private TableColumn<Appointment, LocalDateTime> dateColumn;
	@FXML
	private TableColumn<Appointment, LocalDateTime> timeColumn;
	@FXML
	private TableColumn<Appointment, String> durationColumn;
	@FXML
	private TableColumn<Appointment, String> serviceNameColumn;
	@FXML
	private TableColumn<Appointment, String> employeeNameColumn;
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mma");
	private ObservableList<Appointment> appointmentArray = FXCollections.observableArrayList();
	private String username;

	@FXML
	private void initialize() {
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());
		dateColumn.setCellFactory(cellData -> new TableCell<Appointment, LocalDateTime>() {
			@Override
			protected void updateItem(LocalDateTime item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
				} else {
					setText(String.format(item.format(dateFormatter)));
				}
			}
		});
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());
		timeColumn.setCellFactory(cellData -> new TableCell<Appointment, LocalDateTime>() {
			@Override
			protected void updateItem(LocalDateTime item, boolean empty) {

				super.updateItem(item, empty);
				if (empty) {
					setText(null);
				} else {
					setText(String.format(item.format(timeFormatter)));
				}
			}
		});
		durationColumn.setCellValueFactory(cellData -> cellData.getValue().getServiceNameProperty());
		durationColumn.setCellFactory(cellData -> new TableCell<Appointment, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				Service service = new Service();
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
				} else {
					setText(String.valueOf(service.getService(item).getDuration()) + " minutes");
				}
			}
		});
		serviceNameColumn.setCellValueFactory(cellData -> cellData.getValue().getServiceNameProperty());
		employeeNameColumn.setCellValueFactory(cellData -> cellData.getValue().getEmployeeIdProperty());
		employeeNameColumn.setCellFactory(cellData -> new TableCell<Appointment, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				Employee employee = new Employee();
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
				} else {
					setText(employee.getEmployee(item).getFirstName() + " " + employee.getEmployee(item).getLastName());
				}
			}
		});

	}

	public void loadTable() {
		ObservableList<Appointment> appointments = FXCollections.observableArrayList();
		LocalDateTime bookingDate;
		int i = 0;
		while (appointmentArray.size() > i) {
			bookingDate = appointmentArray.get(i).getDateAndTime();
			if (appointmentArray.get(i).getCustomerUsername().equals(username)
					&& bookingDate.isAfter(LocalDateTime.now())) {
				appointments.add(appointmentArray.get(i));
			}
			i++;
		}
		timeTable.setItems(appointments);
	}

	public void setAppointmentArray(ObservableList<Appointment> appointmentArray) {
		this.appointmentArray = appointmentArray;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@FXML
	private void handleDelete() {
		MainApp mainApp = new MainApp();
		Writer writer = new Writer();
		int selectedIndex = timeTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			if (mainApp.getAppointmentArray().contains(timeTable.getItems().get(selectedIndex))) {
				mainApp.getAppointmentArray().remove(timeTable.getItems().get(selectedIndex));
			}
			timeTable.getItems().remove(selectedIndex);
			try {
				writer.saveAppointments(mainApp.getAppointmentArray());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Appointment Selected");
			alert.setContentText("Please select an appointment from the table.");

			alert.showAndWait();
		}
	}

}
