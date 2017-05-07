package application.view;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class BusinessPageController {

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
	
	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		Utility util = new Utility();
		LocalTime currentTime = LocalTime.MIN;
		for(int i=0;i<mainApp.getBusinessWorkTimes().size();i++){
			for (int j = 0; j < DayOfWeek.values().length; j++) {
				if (mainApp.getBusinessWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.of(j + 1))) {
					startTimes.get(j).setValue(mainApp.getBusinessWorkTimes().get(i).getStartTime());
					endTimes.get(j).setValue(mainApp.getBusinessWorkTimes().get(i).getEndTime());
				}
			}
		}
		while(currentTime.isBefore(LocalTime.MIDNIGHT.minusMinutes(util.)))
	}
	
	
	
	
	
	
	
	
}
