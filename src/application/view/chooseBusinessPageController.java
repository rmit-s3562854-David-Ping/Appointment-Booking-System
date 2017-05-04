package application.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import application.MainApp;
import application.main.Owner;
import application.main.Reader;

public class chooseBusinessPageController {
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private ListView<String> listView;
	@FXML
	private ImageView businessImage;

	private MainApp main = new MainApp();

	@FXML
	private void initialize() {
		ObservableList<String> data = FXCollections.observableArrayList();
		ArrayList<Owner> ownerArray = main.getOwnerArray();
		int i = 0;
		while (i < ownerArray.size()) {
			data.add(ownerArray.get(i).getBusinessName());
			i++;
		}
		listView.setItems(data);
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			// This is a listener. It waits for a new selection from the table.
			// How i change the image files.
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// The image value is set to change to the 'newValue' which is
				// the new businessName.
				// Clients must save their business image under their
				// businessName ie. 'Nike.png'
				Image img = new Image("file:images/" + newValue + ".png");
				businessImage.setImage(img);
			}
		});
	}

	public void loadCustomerSelection() {
		// When the user has selected a business, we call readBusiness and pass
		// the selected value... EASY!
		Reader reader = new Reader();
		reader.readBusiness(listView.getSelectionModel().selectedItemProperty().getValue());
		main.showCustomerHomePage();

	}

}