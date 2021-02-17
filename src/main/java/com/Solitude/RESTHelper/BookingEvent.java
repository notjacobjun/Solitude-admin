package com.Solitude.RESTHelper;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class BookingEvent {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int ID;
	@Column(name = "eventName")
	private String name;
	@Column(name = "location")
	private String location;
	@Column(name = "description")
	private String description;
	@Column(name = "partyNumber")
	private int partyNumber;
	@Column(name = "checkIn")
	private boolean checkedIn;
	@Column(name = "startTime")
	private String startTime;
	@Column(name = "endTime")
	private String endTime;
	@Column(name = "attendeeEmail")
	private String attendeeEmail;

	public BookingEvent() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BookingEvent{" +
				"ID=" + ID +
				", eventName='" + name + '\'' +
				", location='" + location + '\'' +
				", description='" + description + '\'' +
				", partyNumber=" + partyNumber +
				", checkedIn=" + checkedIn +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				", attendeeEmail='" + attendeeEmail + '\'' +
				'}';
	}
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BookingEvent(int ID, String eventName, String location, String description, int partyNumber, boolean checkedIn, String startTime, String endTime, String attendeeEmail) {
		this.ID = ID;
		this.name = eventName;
		this.location = location;
		this.description = description;
		this.partyNumber = partyNumber;
		this.checkedIn = checkedIn;
		this.startTime = startTime;
		this.endTime = endTime;
		this.attendeeEmail = attendeeEmail;
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

	public boolean isCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}



	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


}
