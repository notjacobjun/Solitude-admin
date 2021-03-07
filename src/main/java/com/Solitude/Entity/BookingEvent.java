package com.Solitude.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

// TODO consider adding javax validation annotations for each field if not already done so on the frontend
@Entity
@Table(name = "booking_event")
// constructor, getter, setter, toString EqualsAndHashCode
@Data
public class BookingEvent extends AuditModel {
    // note that in google calendar the id of each calendar is the email of the user
    @Id
    @Column(name = "attendee_email")
    private String eventId;
    @Column(name = "event_name")
    private String eventName;
    @ManyToOne(fetch = FetchType.LAZY)
    // added these to avoid infinite recursion with Jackson
    @JsonManagedReference
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    @Column(name = "description")
    private String description;
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
}
