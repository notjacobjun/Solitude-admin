package com.Solitude.Repository;

import com.Solitude.Entity.BookingEvent;

import com.Solitude.Entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// exception translation enabled by this annotation
@Repository
public interface EventRepository extends JpaRepository<BookingEvent, String> {
    // configured using Spring JPA's automatic custom queries
    Page<BookingEvent> findByLocation(Optional<Location> location, Pageable pageable);

    Optional<BookingEvent> findByEventIdAndLocation(String eventId, Optional<Location> eventLocation);

    Page<BookingEvent> findByEventId(String eventId, Pageable pageable);

    BookingEvent findByEventId(String eventId);
}
