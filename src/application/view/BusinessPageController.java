package application.view;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import application.MainApp;
import application.main.Employee;
import application.main.Utility;
import application.main.WorkTime;
import application.main.Writer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

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
		for (int i = 0; i < mainApp.getBusinessWorkTimes().size(); i++) {
			for (int j = 0; j < DayOfWeek.values().length; j++) {
				if (mainApp.getBusinessWorkTimes().get(i).getDayOfWeek().equals(DayOfWeek.of(j + 1))) {
					startTimes.get(j).setValue(mainApp.getBusinessWorkTimes().get(i).getStartTime());
					endTimes.get(j).setValue(mainApp.getBusinessWorkTimes().get(i).getEndTime());
				}
			}
		}
		while (currentTime.isBefore(LocalTime.MIDNIGHT.minusMinutes(util.MIN_TOTAL_LENGTH))
				|| currentTime.equals(LocalTime.MIDNIGHT.minusMinutes(util.MIN_TOTAL_LENGTH))) {
			for (int i = 0; i < startTimes.size(); i++) {
				startTimes.get(i).getItems().add(currentTime);
				System.out.println(startTimes.get(i).getValue());
				if (startTimes.get(i).getValue() != null) {
					if (currentTime.isAfter(startTimes.get(i).getValue().plusMinutes(util.MIN_TOTAL_LENGTH))
							|| currentTime.equals(startTimes.get(i).getValue().plusMinutes(util.MIN_TOTAL_LENGTH))) {
						endTimes.get(i).getItems().add(currentTime);
					}
				}
			}
			currentTime = currentTime.plusMinutes(util.TIME_BLOCK);
		}
	}

	@FXML
	private void handleSave() {
		MainApp mainApp = new MainApp();
		Writer writer = new Writer();
		List<WorkTime> businessTimes = new ArrayList<WorkTime>();

		for (int i = 0; i < startTimes.size(); i++) {
			if (startTimes.get(i).getValue() != null) {
				WorkTime newTime = new WorkTime(DayOfWeek.of(i + 1), startTimes.get(i).getValue(),
						endTimes.get(i).getValue());
				businessTimes.add(newTime);
			}
		}

		if (isBusinessTimesValid(businessTimes, mainApp.getEmployeeData())) {
			mainApp.getBusinessWorkTimes().clear();
			mainApp.setBusinessTimes(businessTimes);
		} else {
			errorMessage();
			return;
		}

		try {
			writer.saveWorkTimes(mainApp.getBusinessWorkTimes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Successful Save");
		alert.setHeaderText("Success");
		alert.setContentText("Business work hours saved.");
		alert.showAndWait();
	}

	public void changeEndTime() {
		Utility util = new Utility();
		LocalTime currentTime;

		for (int i = 0; i < startTimes.size(); i++) {
			if (startTimes.get(i).getValue() != null) {
				LocalTime oldTime = endTimes.get(i).getValue();
				endTimes.get(i).getItems().clear();
				currentTime = startTimes.get(i).getValue();

				while (currentTime.isBefore(LocalTime.MIDNIGHT.minusMinutes(util.TIME_BLOCK))) {
					if (currentTime.isAfter(startTimes.get(i).getValue().plusMinutes(util.MIN_TOTAL_LENGTH))
							|| currentTime.equals(startTimes.get(i).getValue().plusMinutes(util.MIN_TOTAL_LENGTH))) {
						endTimes.get(i).getItems().add(currentTime);
					}
					currentTime = currentTime.plusMinutes(util.TIME_BLOCK);
				}

				if (oldTime != null) {
					if (oldTime.isBefore(startTimes.get(i).getValue().plusHours(3))) {
						endTimes.get(i).setValue(startTimes.get(i).getValue().plusHours(3));
					} else {
						endTimes.get(i).setValue(oldTime);
					}
				} else {
					endTimes.get(i).setValue(startTimes.get(i).getValue().plusHours(3));
				}
			} else {
				endTimes.get(i).getItems().clear();
			}
		}
	}

	public boolean isBusinessTimesValid(List<WorkTime> businessTimes, List<Employee> employees) {
		// for every employee, they must start at or before the start time for
		// the corresponding day in businessTimes
		// and end at or before the closing time
		// if the day does not exist in business times, then return false
		boolean validDay;
		for (int i = 0; i < employees.size(); i++) {
			for (int j = 0; j < employees.get(i).getWorkTimes().size(); j++) {
				validDay = false;
				for (int k = 0; k < businessTimes.size(); k++) {
					if (employees.get(i).getWorkTimes().get(j).getDayOfWeek()
							.equals(businessTimes.get(k).getDayOfWeek())) {
						validDay = true;
						// found the day they work on, now check if the work
						// hours are valid
						if (employees.get(i).getWorkTimes().get(j).getStartTime()
								.isBefore(businessTimes.get(k).getStartTime())) {
							return false;
						}
						if (employees.get(i).getWorkTimes().get(j).getEndTime()
								.isAfter(businessTimes.get(k).getEndTime())) {
							return false;
						}
					}
				}
				if (validDay == false) {
					return false;
				}
			}
		}
		return true;
	}

	public void clearWorkingTimes() {
		for (int i = 0; i < startTimes.size(); i++) {
			startTimes.get(i).setValue(null);
			endTimes.get(i).setValue(null);
			endTimes.get(i).getItems().clear();
		}
	}

	public void errorMessage() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Edit");
		alert.setHeaderText("Invalid business times");
		alert.setContentText("New business times conflict with existing employee work times");
		alert.showAndWait();
	}

}
