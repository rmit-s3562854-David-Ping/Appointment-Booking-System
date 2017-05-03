package application.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.awt.Button;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import application.MainApp;
import application.main.Appointment;
import application.main.Customer;
import application.main.Employee;
import application.main.Owner;
import application.main.Service;
import application.main.Writer;
public class makeBusinessPageController {
	@FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField businessName; 
	
	@FXML
	private void initialize() {
		
	}
	

public void createBusiness() throws IOException {
	MainApp main = new MainApp();
	Writer writer = new Writer();
	File dir = new File("businesses/"+businessName.getText());
	 if (! dir.exists()){
	        dir.mkdir();
	        
	      //Create the files
	      
	      File file = new File("businesses/" + businessName.getText() + "/appointmentinfo.txt");
	      createFile(file);

	      
	       file =new File("businesses/"+ businessName.getText() +"/employeeinfo.txt");
	       createFile(file);
	       ArrayList<Owner> ownerArray = main.getOwnerArray();
	       Owner newOwner = new Owner(usernameField.getText(), passwordField.getText(),businessName.getText());
	       ownerArray.add(newOwner);
	       writer.saveOwner();
	       System.out.println("Business Created");
	    }
	 else {
		 System.out.println("Business already exists");
		 return;
	 }
	}
public void createFile(File file) throws IOException {
	if (file.createNewFile()){
	      System.out.println("File is created!");
	      }else{
	      System.out.println("File already exists.");
	      }
}
}