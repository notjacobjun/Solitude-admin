package com.Solitude.Entity;

import lombok.Data;

import javax.persistence.*;

// TODO consider adding javax validation annotations for each field if not already done so on the frontend
@Entity
@Table(name = "BookingEvent")
// constructor, getter, setter, toString EqualsAndHashCode
@Data
public class BookingEvent extends AuditModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long eventId;
    @Column(name = "eventName")
    private String eventName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationId", nullable = false)
    // make sure that this is the right type for this field, maybe we have to set to Long, instead of Location
    private Location location;
    @Column(name = "description")
    private String description;
    @Column(name = "partyNumber")
    private int partyNumber;
    @Column(name = "startTime")
    private String startTime;
    @Column(name = "endTime")
    private String endTime;
    @Column(name = "attendeeEmail")
    private String attendeeEmail;
    @Column(name = "userID")
    private Long userID;
    @Column(name = "checkedIn")
    private boolean checkedIn;
    @Column(name = "checkedOut")
    private boolean checkedOut;
}
