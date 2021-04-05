package com.Solitude.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// TODO consider adding javax validation annotations for each field if not already done so on the frontend
@Entity
@Table(name = "booking_event")
// constructor, getter, setter, toString EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEvent extends AuditModel {
    // note that in google calendar the id of each calendar is the email of the user
    @Id
    @Column(name = "event_id")
    private String eventId;
    @Column(name = "event_name")
    private String eventName;
    @ManyToOne(fetch = FetchType.LAZY)
    // added these to avoid infinite recursion with Jackson
    // @JsonManagedReference
    // make sure that this nullable value works when testing
    @JoinColumn(name = "location_id", nullable = false)
    // this is to avoid error with serializing a lazily fetched field
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Location location;
    @Column(name = "description")
    private String description;
    @Column(name = "creator_email")
    private String creatorEmail;
    @Column(name = "party_number")
    private int partyNumber;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;
    @Column(name = "user_Id")
    private Long userID;
    @Column(name = "check_in")
    private boolean checkedIn;
    @Column(name = "check_out")
    private boolean checkedOut;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public int getPartyNumber() {
        return partyNumber;
    }

    public void setPartyNumber(int partyNumber) {
        this.partyNumber = partyNumber;
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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

//    public List<User> getAttendees() {
//        return attendees;
//    }
//
//    public void setAttendees(List<User> attendees) {
//        this.attendees = attendees;
//    }
}
