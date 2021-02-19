package com.Solitude.Repository;

<<<<<<< HEAD
=======
import com.Solitude.Entity.BookingEvent;
>>>>>>> 66263f063fd52381b289742d6cf92325e093fec2

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
