package application.main;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class WorkTime {

	private DayOfWeek day;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	public WorkTime(DayOfWeek day, LocalTime startTime, LocalTime endTime){
		this.day=day;
		this.startTime=startTime;
		this.endTime=endTime;
	}
	
	public DayOfWeek getDayOfWeek(){
		return day;
	}
	
	public LocalTime getStartTime(){
		return startTime;
	}
	
	public LocalTime getEndTime(){
		return endTime;
	}
	
	public void setDayOfWeek(DayOfWeek day){
		this.day=day;
	}
	
	public void setStartTime(LocalTime startTime){
		this.startTime=startTime;
	}
	
	public void setEndTime(LocalTime endTime){
		this.endTime=endTime;
	}
		
}
