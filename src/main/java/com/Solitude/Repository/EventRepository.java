package com.Solitude.Repository;

import com.Solitude.RESTHelpers.BookingEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// should come with CRUD functions
public interface EventRepository extends JpaRepository<BookingEvent, Long> {
}
