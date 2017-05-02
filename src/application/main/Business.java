package application.main;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Business {

	/**
	 * Business class, contains attributes of a business
	 *
	 * @version 1.00 05 Mar 2017
	 * @author David Ping
	 */

	private static ArrayList<DayOfWeek> openingDays;
	private static LocalTime openingTime = LocalTime.of(8, 0);
	private static LocalTime closingTime = LocalTime.of(18, 0);

	public final int TIME_BLOCK = 30;
	
	public Business() {
	}

	/**
	 * Will add more attributes to link business and owner when multiple
	 * businesses are supported
	 */
	public Business(ArrayList<DayOfWeek> openingDays, LocalTime openingTime, LocalTime closingTime) {
		this.openingDays = openingDays;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
	}

	public ArrayList<DayOfWeek> getOpeningDays() {
		return openingDays;
	}

	public LocalTime getOpeningTime() {
		return openingTime;
	}

	public LocalTime getClosingTime() {
		return closingTime;
	}

}
