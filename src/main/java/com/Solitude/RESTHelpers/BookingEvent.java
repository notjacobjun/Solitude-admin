package com.Solitude.RESTHelpers;

public class BookingEvent {
	private String name;
	private String location;
	private String description;
	private String startTime;
	private String endTime;
	private String attendeeEmail;
	
	public BookingEvent() {
		
	}

	public BookingEvent(String name, String location, String description, String startTime, String endTime,
			String attendeeEmail) {
		super();
		this.name = name;
		this.location = location;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.attendeeEmail = attendeeEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAttendeeEmail() {
		return attendeeEmail;
	}

	public void setAttendeeEmail(String attendeeEmail) {
		this.attendeeEmail = attendeeEmail;
	}
		
}
