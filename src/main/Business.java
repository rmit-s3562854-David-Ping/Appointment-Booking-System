package main;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Business {

	/**
	 * Business class, contains attributes of a business, will undo hard coding
	 * when system supports multiple businesses
	 *
	 * @version 1.00 05 Mar 2017
	 * @author David Ping
	 */

	private DayOfWeek openingDays[] = { DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
			DayOfWeek.FRIDAY, DayOfWeek.SUNDAY };
	private LocalTime openingTime = LocalTime.of(8, 0);
	private LocalTime closingTime = LocalTime.of(18, 0);

	public Business() {
	}

	/**
	 * Will add more attributes to link business and owner when multiple
	 * businesses are supported
	 */
	public Business(DayOfWeek[] openingDays, LocalTime openingTime, LocalTime closingTime) {
		this.openingDays = openingDays;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
	}

	public DayOfWeek[] getOpeningDays() {
		return openingDays;
	}

	public LocalTime getOpeningTime() {
		return openingTime;
	}

	public LocalTime getClosingTime() {
		return closingTime;
	}

}
