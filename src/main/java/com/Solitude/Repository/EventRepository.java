package com.Solitude.Repository;

import com.Solitude.Entity.BookingEvent;

import com.Solitude.Entity.BookingEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EventRepository extends JpaRepository<BookingEvent, Long> {
    Page<BookingEvent> findByLocationId(Long locationId, Pageable pageable);
    Optional<BookingEvent> findByIdAndLocationId(Long id, Long locationId);
}
