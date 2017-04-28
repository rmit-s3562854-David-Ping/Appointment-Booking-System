package application.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.MainApp;
import application.main.Employee;
import application.main.Service;
import application.main.Writer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
		// Add observable list data to the table
		serviceTable.setItems(mainApp.getServiceArray());
	}
	
	
	@FXML
	private void handleAdd(){
		Writer writer = new Writer();
		Service service = new Service ("" , 0);
		boolean okClicked = mainApp.showNewServiceDialog(service);
		if (okClicked){
			mainApp.getServiceArray().add(service);	
			try {
				writer.saveServices(mainApp.getServiceArray());
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	@FXML
	private void handleDelete(){
		
		Writer writer = new Writer();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm delete");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete service?");
		Optional <ButtonType> action = alert.showAndWait();
		
		int selectedIndex = serviceTable.getSelectionModel().getSelectedIndex();
		
		if (action.get() == ButtonType.OK){
			serviceTable.getItems().remove(selectedIndex);
			try {
				writer.saveServices(mainApp.getServiceArray());
			} catch (IOException e){
				
				e.printStackTrace();
			}
		}else {
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
