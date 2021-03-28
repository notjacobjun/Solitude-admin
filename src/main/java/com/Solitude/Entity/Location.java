package com.Solitude.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "location")
// constructor, getter, setter, toString EqualsAndHashCode
@Data
public class Location extends AuditModel{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    @Id
    private Long locationID;
    @Column(name = "location_name")
    private String locationName;
    @Column(name = "max_capacity")
    private int maxCapacity;
    @Column(name = "current_no_of_attendees")
    private int currentNumberOfAttendees;
    // TODO change cascade type
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // added these to avoid infinite recursion with Jackson
    @JsonBackReference
    private Set<BookingEvent> events;
    public Long getLocationID() {
        return locationID;
    }

    public void setLocationID(Long locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentNumberOfAttendees() {
        return currentNumberOfAttendees;
    }

    public void setCurrentNumberOfAttendees(int currentNumberOfAttendees) {
        this.currentNumberOfAttendees = currentNumberOfAttendees;
    }

    public Set<BookingEvent> getEvents() {
        return events;
    }

    public void setEvents(Set<BookingEvent> events) {
        this.events = events;
    }


}
