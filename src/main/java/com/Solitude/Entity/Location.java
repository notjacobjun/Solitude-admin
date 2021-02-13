package com.Solitude.Entity;

public class Location {
    private String name;
    private int maxCapacity;
    private int currentNumberOfAttendees;
    private int id;

    public Location(String name, int maxCapacity, int currentNumberOfAttendees, int id) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentNumberOfAttendees = currentNumberOfAttendees;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
