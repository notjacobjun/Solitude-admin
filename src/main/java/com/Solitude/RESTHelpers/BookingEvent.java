package com.Solitude.RESTHelpers;

import com.Solitude.Entity.Location;

public class BookingEvent {
	private String name;
	private Location location;
	private String description;
	private int partyNumber;
	private boolean checkedIn;
	private String startTime;
	private String endTime;
	private String attendeeEmail;

	public BookingEvent() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
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

	public int getPartyNumber() {
		return partyNumber;
	}

	public void setPartyNumber(int partyNumber) {
		this.partyNumber = partyNumber;
	}

	public BookingEvent(String name, Location location, String description, int partyNumber, boolean checkedIn,
			String startTime, String endTime, String attendeeEmail) {
		this.name = name;
		this.location = location;
		this.description = description;
		this.partyNumber = partyNumber;
		this.checkedIn = checkedIn;
		this.startTime = startTime;
		this.endTime = endTime;
		this.attendeeEmail = attendeeEmail;
	}

	public boolean isCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}
}
