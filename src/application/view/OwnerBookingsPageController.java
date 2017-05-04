package application.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.MainApp;
import application.main.Service;
import application.main.Appointment;
import application.main.Customer;
import application.main.Employee;
import application.main.Writer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

/**
 * @author David Ping
 * @version 1.00 Last edited: 24/04/2017
 */

public class OwnerBookingsPageController {

	@FXML
	private TableView<Appointment> timeTable;
	@FXML
	private TableColumn<Appointment, LocalDateTime> dateColumn;
	@FXML
	private TableColumn<Appointment, LocalDateTime> timeColumn;
	@FXML
	private TableColumn<Appointment, String> durationColumn;
	@FXML
	private TableColumn<Appointment, String> customerUsernameColumn;
	@FXML
	private TableColumn<Appointment, String> employeeIdColumn;
	@FXML
	private TableColumn<Appointment, String> serviceNameColumn;
	@FXML
	private TableColumn<Appointment, String> customerNameColumn;
	@FXML
	private TableColumn<Appointment, String> employeeNameColumn;

	@FXML
	private Label dateRangeLabel;

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mma");

	private static LocalDate date = LocalDate.now();
	private static final Logger LOGGER = Logger.getLogger("MyLog");

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
					LOGGER.info("Item updated.");
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
					LOGGER.info("Item updated.");
				}
			}
		});
		customerUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerUsernameProperty());
		employeeIdColumn.setCellValueFactory(cellData -> cellData.getValue().getEmployeeIdProperty());
		serviceNameColumn.setCellValueFactory(cellData -> cellData.getValue().getServiceNameProperty());
		customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerUsernameProperty());
		customerNameColumn.setCellFactory(cellData -> new TableCell<Appointment, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				Customer customer = new Customer();
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
				} else {
					setText(customer.getCustomer(item).getFirstName() + " " + customer.getCustomer(item).getLastName());
					LOGGER.info("Item updated.");
				}
			}
		});
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
					LOGGER.info("Item updated.");
				}
			}
		});

		dateRangeLabel.setText(date.format(dateFormatter) + "-" + date.plusWeeks(1).minusDays(1).format(dateFormatter));
	}

	private MainApp mainApp;

	private ObservableList<Appointment> tempList = FXCollections.observableArrayList();

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
			tempList.add(mainApp.getAppointmentArray().get(i));
		}
		timeTable.setItems(tempList);
		for (int i = 0; i < timeTable.getItems().size(); i++) {
			if (timeTable.getItems().get(i).getDateAndTime().toLocalDate().isBefore(date)
					|| timeTable.getItems().get(i).getDateAndTime().toLocalDate().isAfter(date.plusWeeks(1))
					|| timeTable.getItems().get(i).getDateAndTime().toLocalDate().isEqual(date.plusWeeks(1))) {
				timeTable.getItems().remove(i);
				i--;
			}
		}
	}

	public void handleNextWeekClicked() {
		date = date.plusWeeks(1);
		timeTable.getItems().clear();
		ObservableList<Appointment> tempList = FXCollections.observableArrayList();
		for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
			tempList.add(mainApp.getAppointmentArray().get(i));
		}
		timeTable.setItems(tempList);
		for (int i = 0; i < timeTable.getItems().size(); i++) {
			if (timeTable.getItems().get(i).getDateAndTime().toLocalDate().isBefore(date)
					|| timeTable.getItems().get(i).getDateAndTime().toLocalDate().isAfter(date.plusWeeks(1))
					|| timeTable.getItems().get(i).getDateAndTime().toLocalDate().isEqual(date.plusWeeks(1))) {
				timeTable.getItems().remove(i);
				i--;
			}
		}
		dateRangeLabel.setText(date.format(dateFormatter) + "-" + date.plusWeeks(1).minusDays(1).format(dateFormatter));
		LOGGER.info("Next week clicked.");
	}

	public void handleLastWeekClicked() {
		date = date.minusWeeks(1);
		timeTable.getItems().clear();
		ObservableList<Appointment> tempList = FXCollections.observableArrayList();
		for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
			tempList.add(mainApp.getAppointmentArray().get(i));
		}
		timeTable.setItems(tempList);
		for (int i = 0; i < timeTable.getItems().size(); i++) {
			if (timeTable.getItems().get(i).getDateAndTime().toLocalDate().isBefore(date)
					|| timeTable.getItems().get(i).getDateAndTime().toLocalDate().isAfter(date.plusWeeks(1))
					|| timeTable.getItems().get(i).getDateAndTime().toLocalDate().isEqual(date.plusWeeks(1))) {
				timeTable.getItems().remove(i);
				i--;
			}
		}
		dateRangeLabel.setText(date.format(dateFormatter) + "-" + date.plusWeeks(1).minusDays(1).format(dateFormatter));
		LOGGER.info("Previous week selected.");
	}

	@FXML
	private void handleNew() {
		Writer writer = new Writer();
		Appointment appointment = new Appointment(null, "", "", "");
		boolean okClicked = mainApp.showBookingsDialogOwnerNew(appointment);
		if (okClicked) {
			mainApp.getAppointmentArray().add(appointment);
			tempList.add(appointment);
			try {
				writer.saveAppointments(mainApp.getAppointmentArray());
				refreshTable();
				LOGGER.info("Appointment saved.");
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
		int counter = timeTable.getSelectionModel().getSelectedIndex();
		Appointment selectedAppointment = timeTable.getSelectionModel().getSelectedItem();

		if (selectedAppointment != null) {
			for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
				if (selectedAppointment.equals(mainApp.getAppointmentArray().get(i))) {
					selectedAppointment = mainApp.getAppointmentArray().get(i);
				}
			}
			if (selectedAppointment.getDateAndTime().toLocalDate().isBefore(LocalDate.now().plusDays(1))) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Invalid edit");
				alert.setHeaderText("Cannot edit");
				alert.setContentText("Cannot edit past appointments");
				LOGGER.info("Cannot edit appointmnet");
				alert.showAndWait();
				return;
			}
			boolean okClicked = mainApp.showBookingsDialogOwnerEdit(selectedAppointment);
			timeTable.getItems().set(counter, selectedAppointment);
			if (okClicked) {
				try {
					writer.saveAppointments(mainApp.getAppointmentArray());
					refreshTable();
					LOGGER.info("Appointment edited.");
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
					e.printStackTrace();
				}
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

	@FXML
	private void handleDelete() {
		Writer writer = new Writer();
		int selectedIndex = timeTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			if (mainApp.getAppointmentArray().contains(timeTable.getItems().get(selectedIndex))) {
				mainApp.getAppointmentArray().remove(timeTable.getItems().get(selectedIndex));
				LOGGER.info("Appointment delted.");
			}
			timeTable.getItems().remove(selectedIndex);
			try {
				writer.saveAppointments(mainApp.getAppointmentArray());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.SEVERE, e.toString(), e);
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

	public void refreshTable() {
		timeTable.getItems().clear();
		ObservableList<Appointment> tempList = FXCollections.observableArrayList();
		for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
			tempList.add(mainApp.getAppointmentArray().get(i));
		}
		timeTable.setItems(tempList);
		for (int i = 0; i < timeTable.getItems().size(); i++) {
			if (timeTable.getItems().get(i).getDateAndTime().toLocalDate().isBefore(date)
					|| timeTable.getItems().get(i).getDateAndTime().toLocalDate().isAfter(date.plusWeeks(1))
					|| timeTable.getItems().get(i).getDateAndTime().toLocalDate().isEqual(date.plusWeeks(1))) {
				timeTable.getItems().remove(i);
				i--;
			}
		}
	}

}
