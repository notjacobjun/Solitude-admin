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
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // added these to avoid infinite recursion with Jackson
    @JsonBackReference
    private Set<BookingEvent> events;
}
