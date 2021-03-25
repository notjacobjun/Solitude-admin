package com.Solitude.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.type.Date;
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
//    @JsonManagedReference
    // make sure that this nullable value works when testing
    @JoinColumn(name = "location_id", nullable = false)
    // this is to avoid error with serializing a lazily fetched field
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
}
