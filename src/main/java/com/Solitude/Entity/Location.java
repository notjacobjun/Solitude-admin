package com.Solitude.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// double-check that this would work in postgres
@Entity
@Table(name = "Location")
public class Location {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int locationID;
    @Column(name = "locationName")
    private String locationName;
    @Column(name = "maxCapacity")
    private int maxCapacity;

    public Location() {
    }

    @Column(name = "current#OfAttendees")
    private int currentNumberOfAttendees;

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

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public Location(int locationID, String locationName, int maxCapacity, int currentNumberOfAttendees) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.maxCapacity = maxCapacity;
        this.currentNumberOfAttendees = currentNumberOfAttendees;
    }

    public String getName() {
        return locationName;
    }

    public void setName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return "Location [currentNumberOfAttendees=" + currentNumberOfAttendees + ", locationID=" + locationID
                + ", locationName=" + locationName + ", maxCapacity=" + maxCapacity + "]";
    }

}
