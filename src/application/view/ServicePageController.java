package application.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.Optional;

import application.MainApp;
import application.main.Service;
import application.main.Writer;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ServicePageController {

	@FXML
	private TableView<Service> serviceTable;
	@FXML
	private TableColumn<Service, String> serviceNameColumn;
	@FXML
	private TableColumn<Service, Integer> durationColumn;
	@FXML
	private ListView<Service> servicesList;
	private ObservableList<ListView<Service>> obsSerList = FXCollections.observableArrayList(servicesList);
	MainApp mainApp = new MainApp();

	@FXML
	private void initialize() {
		// Initialize the service table with the two columns.
		serviceNameColumn.setCellValueFactory(cellData -> cellData.getValue().getServiceNameProperty());
		durationColumn.setCellValueFactory(cellData -> cellData.getValue().getDurationProperty().asObject());

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		// Add observable list data to the table
		serviceTable.setItems(mainApp.getServiceArray());
	}

	@FXML
	private void handleAdd() {
		Writer writer = new Writer();
		Service service = new Service("", 0);
		boolean okClicked = mainApp.showNewServiceDialog(service);
		if (okClicked) {
			mainApp.getServiceArray().add(service);
			try {
				writer.saveServices(mainApp.getServiceArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleDelete() {

		Writer writer = new Writer();
		boolean serviceUsed = false;
		Service service = serviceTable.getSelectionModel().getSelectedItem();
		int selectedIndex = serviceTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			for (int i = 0; i < mainApp.getAppointmentArray().size(); i++) {
				if (mainApp.getAppointmentArray().get(i).getServiceName()
						.equals(serviceTable.getItems().get(selectedIndex).getServiceName())) {
					serviceUsed = true;
				}
			}

			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Confirm delete");
			confirm.setHeaderText(null);
			confirm.setContentText("Are you sure you want to delete service?");
			Optional<ButtonType> action = confirm.showAndWait();

			if (serviceUsed == false) {
				if (action.get() == ButtonType.OK) {
					serviceTable.getItems().remove(selectedIndex);
					// service to be deleted for employees providing it
					for (int z = 0; z < mainApp.getEmployeeData().size(); z++) {
						for (int x = 0; x < mainApp.getEmployeeData().get(z).getEmployeeServices().size(); x++) {
							if (mainApp.getEmployeeData().get(z).getEmployeeServices().get(x)
									.equals(service.getServiceName())) {
								mainApp.getEmployeeData().get(z).getEmployeeServices().remove(x);
								try {
									writer.saveEmployees(mainApp.getEmployeeData());
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					try {
						writer.saveServices(mainApp.getServiceArray());
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			} else {
				// the service has been booked for an appointment
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("service has been booked for an appointments");
				alert.setHeaderText("Delete Failed");
				alert.setContentText("Please edit or delete appointments with this service in future");

				alert.showAndWait();
			}
		} else {
			// Nothing selected.
			Alert warn = new Alert(AlertType.WARNING);
			warn.initOwner(mainApp.getPrimaryStage());
			warn.setTitle("No Selection");
			warn.setHeaderText("No Service Selected");
			warn.setContentText("Please select a service from the table.");

			warn.showAndWait();
		}
	}

}
