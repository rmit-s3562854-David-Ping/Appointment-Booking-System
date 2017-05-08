package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
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
    private static String businessName;
    
    private static ArrayList<Customer> customerArray = new ArrayList<Customer>();
	private static ArrayList<Owner> ownerArray = new ArrayList<Owner>();
	private static ArrayList<WorkTime> businessWorkTimes = new ArrayList<WorkTime>();
	private static ObservableList<Service> serviceArray = FXCollections.observableArrayList();
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
    	reader.readUsers();
        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("Appointment Booking System");
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
		AnchorPane header = (AnchorPane) scene.lookup("#header");
		Button employeeButton = (Button) scene.lookup("#employeeButton");
		Button servicesButton = (Button) scene.lookup("#servicesButton");
		Button bookingsButton = (Button) scene.lookup("#bookingsButton");
		Button businessButton = (Button) scene.lookup("#businessButton");
    	header.setVisible(true);
    	employeeButton.setVisible(true);
		servicesButton.setVisible(true);
		bookingsButton.setVisible(true);
		businessButton.setVisible(true);
    	
		Button homeBtn = (Button) scene.lookup("#businessNameButton");
		Label welcomeText = (Label)scene.lookup("#welcomeText");
		homeBtn.setText(businessName);
		
		for(int i=0;i<ownerArray.size();i++){
			if(ownerArray.get(i).getUsername().equals(username)){
				welcomeText.setText("Welcome "+ownerArray.get(i).getFirstName()+" "+ownerArray.get(i).getLastName());
			}
		}
		
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
    public void showNewBusinessPage(){
		Scene scene = rootLayout.getScene();
		AnchorPane header = (AnchorPane) scene.lookup("#header");
    	header.setVisible(true);
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/makeBusinessPage.fxml"));
            AnchorPane ownerPage = (AnchorPane) loader.load();

            rootLayout.setCenter(ownerPage);

            
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
    
    //show services available and add or edit or delete
    public void showServicesPage(){
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ServicesPage.fxml"));
            AnchorPane servicesPage = (AnchorPane) loader.load();

            rootLayout.setCenter(servicesPage);

            ServicePageController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows dialog box for new services, all fields are empty when initialised
     * */
    public boolean showNewServiceDialog(Service service){
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ServiceDialogPage.fxml"));
            AnchorPane dialogPage = (AnchorPane) loader.load();

            Stage serviceStage = new Stage();
            serviceStage.setTitle("Service");
            serviceStage.initModality(Modality.WINDOW_MODAL);
            serviceStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPage);
            serviceStage.setScene(scene);

            ServiceDialogPageController controller = loader.getController();
            controller.setServiceStage(serviceStage);
            controller.setNewService(service);
            
            serviceStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
    	
    	AnchorPane header = (AnchorPane) scene.lookup("#header");
    	header.setVisible(true);
    	header.setManaged(true);
    	
		Button homeBtn = (Button) scene.lookup("#businessNameButton");
		Label welcomeText = (Label)scene.lookup("#welcomeText");
		homeBtn.setText(businessName);
		
		for(int i=0;i<customerArray.size();i++){
			if(customerArray.get(i).getUsername().equals(username)){
				welcomeText.setText("Welcome "+customerArray.get(i).getFirstName()+" "+customerArray.get(i).getLastName());
			}
		}
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
    
    public void showChooseBusinessPage(){
		Scene scene = rootLayout.getScene();

		AnchorPane header = (AnchorPane) scene.lookup("#header");
    	header.setVisible(false);
    	header.setManaged(false);

    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/chooseBusinessPage.fxml"));
            scene.getStylesheets().add
            (MainApp.class.getResource("/styles/login.css").toExternalForm());
            AnchorPane ownerPage = (AnchorPane) loader.load();

            rootLayout.setCenter(ownerPage);

            
            LOGGER.info("Owner home page displayed.");
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

            Stage appointmentStage = new Stage();
            appointmentStage.setTitle("Appointment");
            appointmentStage.initModality(Modality.WINDOW_MODAL);
            appointmentStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPage);
            appointmentStage.setScene(scene);

            BookingsDialogPageController controller = loader.getController();
            controller.setAppointmentStage(appointmentStage);
            controller.setNewAppointment(appointment);

            appointmentStage.showAndWait();
            LOGGER.info("Customer booking dialog displayed.");
            return controller.isOkClicked();
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
            return false;
        }
	}
    
    public boolean showMyDetailsPage(Member member) {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/myDetailsPage.fxml"));
            AnchorPane detailsPage = (AnchorPane) loader.load();

            Stage memberStage = new Stage();
            memberStage.setTitle("My Details");
            memberStage.initModality(Modality.WINDOW_MODAL);
            memberStage.initOwner(primaryStage);
            Scene scene = new Scene(detailsPage);
            memberStage.setScene(scene);

            MyDetailsPageController controller = loader.getController();
            controller.setMemberStage(memberStage);
            controller.setMember(member);

            memberStage.showAndWait();
            return controller.isSaveClicked();
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
            return false;
        }
	}
    
    public void showBookingsSummaryPage()  {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BookingSummaryDialogPage.fxml"));
            AnchorPane dialogPage = (AnchorPane) loader.load();
            Stage apppointmentStage = new Stage();
            apppointmentStage.setTitle("My Appointments");
            apppointmentStage.initModality(Modality.WINDOW_MODAL);
            apppointmentStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPage);
            apppointmentStage.setScene(scene);
            BookingSummaryDialogPageController controller = loader.getController();
            controller.setAppointmentArray(getAppointmentArray());
            controller.setUsername(getUsername());
            controller.loadTable();
            apppointmentStage.show();
            LOGGER.info("Customer bookings displayed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    public void showBusinessHoursPage(){		
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BusinessPage.fxml"));
            AnchorPane businessPage = (AnchorPane) loader.load();

            rootLayout.setCenter(businessPage);

            BusinessPageController controller = loader.getController();
            controller.setMainApp(this);
            LOGGER.info("Customer home page displayed");
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
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
	
	public ArrayList<WorkTime> getBusinessWorkTimes(){
		return businessWorkTimes;
	}
	
	public ObservableList<Employee> getEmployeeData(){
		return employeeData;
	}

	public ObservableList<Appointment> getAppointmentArray() {
		return appointmentArray;
	}
	public ObservableList<Service> getServiceArray(){
		return serviceArray;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		MainApp.username=username;
	}
	public void setBusinessName(String businessName){
		MainApp.businessName=businessName;
	}
	public String getBusinessName(){
		return businessName;
	}
	public void setBusinessTimes(List<WorkTime> newBusinessTimes){
		this.getBusinessWorkTimes().addAll(newBusinessTimes);
	}
	public BorderPane getRootLayout(){
		return rootLayout;
	}
	
    

	
	
}