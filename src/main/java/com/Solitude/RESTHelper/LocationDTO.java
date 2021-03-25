package com.Solitude.RESTHelper;

import com.Solitude.Entity.BookingEvent;
import lombok.Data;

import java.util.Set;

// lombok doesn't work for me sometimes
@Data
public class LocationDTO {
    private Long locationID;
    private String locationName;
    private int maxCapacity;
    private int currentNumberOfAttendees;
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
