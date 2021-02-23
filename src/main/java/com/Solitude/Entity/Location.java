package com.Solitude.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Location")
// constructor, getter, setter, toString EqualsAndHashCode
@Data
public class Location extends AuditModel{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationId")
    @Id
    private Long locationID;
    @Column(name = "locationName")
    private String locationName;
    @Column(name = "maxCapacity")
    private int maxCapacity;
    @Column(name = "currentNumOfAttendees")
    private int currentNumberOfAttendees;
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BookingEvent> events;
}
