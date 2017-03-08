package main;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Business {

	private DayOfWeek openingDays[] = { DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, 
			DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY};
	private LocalTime openingTime = LocalTime.of(8, 0);
	private LocalTime closingTime = LocalTime.of(18, 0);
	
	public Business(){}
	
	public Business (DayOfWeek[] openingDays, LocalTime openingTime, LocalTime closingTime){
		this.openingDays=openingDays;
		this.openingTime=openingTime;
		this.closingTime=closingTime;
	}
	
	public DayOfWeek[] getOpeningDays(){
		return openingDays;
	}
	
	public LocalTime getOpeningTime(){
		return openingTime;
	}
	
	public LocalTime getClosingTime(){
		return closingTime;
	}
	
}
