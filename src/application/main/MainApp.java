package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.main.*;
import application.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author David Ping
 * @version 1.00
 * Last edited: 24/04/2017
 * */
public class MainApp extends Application {

    private static Stage primaryStage;
    private static BorderPane rootLayout;
    private static String username;

    private static ArrayList<Customer> customerArray = new ArrayList<Customer>();
	private static ArrayList<Owner> ownerArray = new ArrayList<Owner>();
	private static ArrayList<Service> serviceArray = new ArrayList<Service>();
	private static ObservableList<Employee> employeeData = FXCollections.observableArrayList();
	private static ObservableList<Appointment> appointmentArray = FXCollections.observableArrayList();
	private static final Logger LOGGER = Logger.getLogger("MyLog");
	public static void main(String[] args) {
        launch(args);
    }
	
	/**
	 * Start of the program, reads the files and stores data into lists, sets up the primary window and
	 * assigns the title. Initiates the root layout (home and logout)
	 * */
    @Override
    public void start(Stage primaryStage) {
    	LOGGER.info("Program started.");
    	Reader reader = new Reader();
    	reader.read();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Appointment Booking System");
        LOGGER.info("primary window initiated.");
        initRootLayout();

        showLoginPage();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the login page, assigns controller to the MainApp for login page
     */
    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginPage.fxml"));
            AnchorPane loginPage = (AnchorPane) loader.load();

            rootLayout.setCenter(loginPage);

            LoginPageController controller = loader.getController();
            controller.setMainApp(this);
            LOGGER.info("Login page displayed.");
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the registration page for customers and loads the controller
     * */
    public boolean showRegistrationPage(Customer customer) {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RegistrationPage.fxml"));
            AnchorPane registrationPage = (AnchorPane) loader.load();

            Stage registrationStage = new Stage();
            registrationStage.setTitle("Register");
            registrationStage.initModality(Modality.WINDOW_MODAL);
            registrationStage.initOwner(primaryStage);
            Scene scene = new Scene(registrationPage);
            registrationStage.setScene(scene);

            RegistrationPageController controller = loader.getController();
            controller.setRegistrationStage(registrationStage);
            controller.setCustomer(customer);

            registrationStage.showAndWait();
            LOGGER.info("Registation page displayed.");
            return controller.isOkClicked();
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Shows the owner's home page and makes home and logout buttons visible
     * */
    public void showOwnerHomePage(){
		Scene scene = rootLayout.getScene();
		Button homeBtn = (Button) scene.lookup("#HomeButton");
		Button logoutBtn = (Button) scene.lookup("#LogoutButton");
		homeBtn.setVisible(true);
		logoutBtn.setVisible(true);
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OwnerHomePage.fxml"));
            AnchorPane ownerPage = (AnchorPane) loader.load();

            rootLayout.setCenter(ownerPage);

            OwnerHomePageController controller = loader.getController();
            controller.setMainApp(this);
            LOGGER.info("Owner home page displayed.");
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }
    	
    }
    
    /**
     * Shows the employee page
     * */
    public void showEmployeePage(){
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EmployeePage.fxml"));
            AnchorPane employeePage = (AnchorPane) loader.load();

            rootLayout.setCenter(employeePage);

            EmployeePageController controller = loader.getController();
            controller.setMainApp(this);
            LOGGER.info("Employee page displayed.");
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }
    }
    
    /**
     * Shows dialog box for new employees, all fields are empty when initialised
     * */
    public boolean showEmployeeNewDialog(Employee employee){
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EmployeeDialogPage.fxml"));
            AnchorPane dialogPage = (AnchorPane) loader.load();

            Stage employeeStage = new Stage();
            employeeStage.setTitle("Employee");
            employeeStage.initModality(Modality.WINDOW_MODAL);
            employeeStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPage);
            employeeStage.setScene(scene);

            EmployeeDialogPageController controller = loader.getController();
            controller.setEmployeeStage(employeeStage);
            controller.setNewEmployee(employee);
            
            employeeStage.showAndWait();
            LOGGER.info("Employee dialog displayed");
            return controller.isOkClicked();
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Shows the employee dialog box for existing employees to edit
     * */
    public boolean showEmployeeEditDialog(Employee employee){
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EmployeeDialogPage.fxml"));
            AnchorPane dialogPage = (AnchorPane) loader.load();

            Stage employeeStage = new Stage();
            employeeStage.setTitle("Employee");
            employeeStage.initModality(Modality.WINDOW_MODAL);
            employeeStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPage);
            employeeStage.setScene(scene);

            EmployeeDialogPageController controller = loader.getController();
            controller.setEmployeeStage(employeeStage);
            controller.setEmployee(employee);

            employeeStage.showAndWait();
            LOGGER.info("Employee edit dialog displayed.");
            return controller.isOkClicked();
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Show bookings page for owner
     * */
    public void showOwnerBookingsPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OwnerBookingsPage.fxml"));
            AnchorPane ownerBookingsPage = (AnchorPane) loader.load();
            
            rootLayout.setCenter(ownerBookingsPage);

            OwnerBookingsPageController controller = loader.getController();
            controller.setMainApp(this);
            LOGGER.info("Owner booking page displayed.");
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }
	}
    
    /**
     * Show dialog box for new bookings
     * */
    public boolean showBookingsDialogOwnerNew(Appointment appointment){
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OwnerBookingsDialogPage.fxml"));
            AnchorPane dialogPage = (AnchorPane) loader.load();

            Stage appointmentStage = new Stage();
            appointmentStage.setTitle("Appointment");
            appointmentStage.initModality(Modality.WINDOW_MODAL);
            appointmentStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPage);
            appointmentStage.setScene(scene);

            OwnerBookingsDialogPageController controller = loader.getController();
            controller.setAppointmentStage(appointmentStage);
            controller.setNewAppointment(appointment);
            
            appointmentStage.showAndWait();
            LOGGER.info("Booking dialog displayed.");
            return controller.isOkClicked();
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Show dialog box for editing bookings
     * */
    public boolean showBookingsDialogOwnerEdit(Appointment appointment){
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OwnerBookingsDialogPage.fxml"));
            AnchorPane dialogPage = (AnchorPane) loader.load();

            Stage apppointmentStage = new Stage();
            apppointmentStage.setTitle("Appointment");
            apppointmentStage.initModality(Modality.WINDOW_MODAL);
            apppointmentStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPage);
            apppointmentStage.setScene(scene);

            OwnerBookingsDialogPageController controller = loader.getController();
            controller.setAppointmentStage(apppointmentStage);
            controller.setAppointment(appointment);

            apppointmentStage.showAndWait();
            LOGGER.info("Edit bookings dialog displayed.");
            return controller.isOkClicked();
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Shows customer home page
     * */
    public void showCustomerHomePage(){
    	Scene scene = rootLayout.getScene();
		Button homeBtn = (Button) scene.lookup("#HomeButton");
		Button logoutBtn = (Button) scene.lookup("#LogoutButton");
		homeBtn.setVisible(true);
		logoutBtn.setVisible(true);
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CustomerHomePage.fxml"));
            AnchorPane customerPage = (AnchorPane) loader.load();

            rootLayout.setCenter(customerPage);

            CustomerHomePageController controller = loader.getController();
            controller.setMainApp(this);
            LOGGER.info("Customer home page displayed");
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }
    }
    
    /**
     * Shows dialog box for making a booking as a customer
     * */
    public boolean showBookingsDialogPage(Appointment appointment) {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BookingsDialogPage.fxml"));
            AnchorPane dialogPage = (AnchorPane) loader.load();

            Stage apppointmentStage = new Stage();
            apppointmentStage.setTitle("Appointment");
            apppointmentStage.initModality(Modality.WINDOW_MODAL);
            apppointmentStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPage);
            apppointmentStage.setScene(scene);

            BookingsDialogPageController controller = loader.getController();
            controller.setAppointmentStage(apppointmentStage);
            controller.setNewAppointment(appointment);

            apppointmentStage.showAndWait();
            LOGGER.info("Customer booking dialog displayed.");
            return controller.isOkClicked();
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
            return false;
        }
	}

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public ArrayList<Customer> getCustomerArray() {
		return customerArray;
	}

	public ArrayList<Owner> getOwnerArray() {
		return ownerArray;
	}
	
	public ObservableList<Employee> getEmployeeData(){
		return employeeData;
	}

	public ObservableList<Appointment> getAppointmentArray() {
		return appointmentArray;
	}
	
	public ArrayList<Service> getServiceArray(){
		return serviceArray;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username=username;
	}
	
	public BorderPane getRootLayout(){
		return rootLayout;
	}
	
    

	
	
}