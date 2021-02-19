package com.Solitude.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.Solitude.Entity.AuditModel;
import com.Solitude.Entity.Location;

// TODO consider adding javax validation annotaions for each field if not already done so on the frontend
@Entity
@Table(name = "BookingEvent")
public class BookingEvent extends AuditModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int ID;
    @Column(name = "eventName")
    private String eventName;
    @ManyToOne
    // double check that this is the right column name
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
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

    public String getEventName() {
        return eventName;
    }

    public Location getLocation() {
        return location;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public BookingEvent(int ID, String eventName, Location location, String description, int partyNumber, boolean checkedIn, String startTime, String endTime, String attendeeEmail) {
        this.ID = ID;
        this.eventName = eventName;
        this.location = location;
        this.description = description;
        this.partyNumber = partyNumber;
        this.checkedIn = checkedIn;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendeeEmail = attendeeEmail;
    }

    @Override
    public String toString() {
        return "BookingEvent{" +
                "ID=" + ID +
                ", eventName='" + eventName + '\'' +
                ", location=" + location +
                ", description='" + description + '\'' +
                ", partyNumber=" + partyNumber +
                ", checkedIn=" + checkedIn +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", attendeeEmail='" + attendeeEmail + '\'' +
                '}';
    }

}
