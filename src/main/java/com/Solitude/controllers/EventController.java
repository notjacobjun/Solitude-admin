package com.Solitude.controllers;

import java.util.List;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.LocationRepository;
import com.Solitude.util.GoogleCalendar;
import com.google.api.services.calendar.model.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
// TODO map all the endpoints properly
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private LocationRepository locationRepository;

    // TODO: Add firebase authentication
    @RequestMapping(value = "/upcoming/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> getUpcomingEvents(@PathVariable("location") String location,
                                                         @RequestParam(name = "size") Integer size) {
        try {
            // FirebaseToken decodedToken =
            // FirebaseAuth.getInstance().verifyIdToken(idToken);
            // String uid = decodedToken.getUid();

            // TODO: Verify if the location belongs to the uid in Postgres

            List<Event> events = GoogleCalendar.getUpcomingEventsByLocation(location, size);
            logger.debug("Found {} upcoming events", events.size());
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/history/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> getHistory(@PathVariable("location") String location,
                                                  @RequestParam(name = "size") Integer size) {
        try {
            // FirebaseToken decodedToken =
            // FirebaseAuth.getInstance().verifyIdToken(idToken);
            // String uid = decodedToken.getUid();

            // TODO: Verify if the location belongs to the uid in Postgres

            List<Event> events = GoogleCalendar.getPastEventsByLocation(location, size);
            logger.debug("Found {} past events", events.size());
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{eventId}/")
    // TODO change this method parameter to be linked to corresponding Location
    public Page<BookingEvent> getAllEvents(Pageable pageable) {
        return eventRepository.findByLocationId(pageable);
    }

    @PostMapping("/")
    // If you odn't add the javax annotations to the entities then get rid of this valid annotation
    public BookingEvent createBookingEvent(@PathVariable (value = "locationId") Long locationid, @Valid @RequestBody BookingEvent bookingEvent) {
        return locationRepository.findById(locationid).map(location -> {
            bookingEvent.setLocation(location);
            return eventRepository.save(bookingEvent);
        }).orElseThrow(() -> new ResourceNotFoundException("LocationId " + locationid + " not found"));
    }

    @PutMapping("/{eventId}")
    public BookingEvent updateBookingEvent(@PathVariable long eventId, @Valid @RequestBody BookingEvent bookingEvent) {
        return eventRepository.findById(eventId).map(event -> {
            // why aren't these working???
            event.setName(bookingEvent.getName());
            event.setDescription(bookingEvent.getDescription());
            event.setPartyNumber(bookingEvent.getPartyNumber());
            return eventRepository.save(event);
        }).orElseThrow(() -> new ResourceNotFoundException("EventId " + eventId + " not found"));
    }

    // TODO fix this method body
    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deletePost(@PathVariable Long eventId) {
        return eventRepository.findById(eventId).map(event -> {
            eventRepository.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("EventId " + eventId + " not found"));
    }

}
