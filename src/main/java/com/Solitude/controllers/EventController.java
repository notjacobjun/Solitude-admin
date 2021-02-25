package com.Solitude.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.Solitude.Calendar.GoogleCalendar;
import com.Solitude.Entity.BookingEvent;
import com.Solitude.Entity.Location;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.LocationRepository;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private LocationRepository locationRepository;

//    // TODO: Add firebase authentication
//    @RequestMapping(value = "/upcoming/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Event>> getUpcomingEvents(@PathVariable("location") String location,
//                                                         @RequestParam(name = "size") Integer size) {
//        try {
//            // FirebaseToken decodedToken =
//            // FirebaseAuth.getInstance().verifyIdToken(idToken);
//            // String uid = decodedToken.getUid();
//
//            // TODO: Verify if the location belongs to the uid in Postgres
//
//            List<Event> events = GoogleCalendar.getUpcomingEventsByLocation(location, size);
//            logger.debug("Found {} upcoming events", events.size());
//            return new ResponseEntity<>(events, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Internal error {} ", e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @RequestMapping(value = "/history/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Event>> getHistory(@PathVariable("location") String location,
//                                                  @RequestParam(name = "size") Integer size) {
//        try {
//            // FirebaseToken decodedToken =
//            // FirebaseAuth.getInstance().verifyIdToken(idToken);
//            // String uid = decodedToken.getUid();
//
//            // TODO: Verify if the location belongs to the uid in Postgres
//
//            List<Event> events = GoogleCalendar.getPastEventsByLocation(location, size);
//            logger.debug("Found {} past events", events.size());
//            return new ResponseEntity<>(events, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Internal error {} ", e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/location/{locationId}/events")
    public Page<BookingEvent> getAllEventsByLocationId(@PathVariable(value = "locationId") Long locationId, Pageable pageable) {
        Optional<Location> location = locationRepository.findById(locationId);
        return eventRepository.findByLocation(location, pageable);
    }

    // creates a new event based on the event details provided
    @PostMapping("/location/{locationId}/events")
    public BookingEvent createBookingEvent(@RequestBody Event event) {
        BookingEvent bookingEvent = new BookingEvent();
        // creates a new authorized API client
        try {
            Calendar service = GoogleCalendar.getService();
            // creates the event within the Solitude admin calendar, then it adds the attendees to the event
            // TODO confirm that primary keyword is the right choice here, instead of explicitly using the solitude admin email
            // this line below requires that the start and end time for the event are instantiated, also that they
            // correspondent with both being an all-day event or both as a date-time event (part of the day)
            service.events().insert("primary", event).execute();

            // save the event into the postgres DB
            // TODO configure the event fields into BookingEvent
            bookingEvent.setEventId(event.getId());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return eventRepository.save(bookingEvent);
    }

    @PutMapping("/locations/{locationId}/events/{eventId}")
    public BookingEvent updateBookingEvent(@PathVariable(value = "eventId") Long eventId, @PathVariable(value = "locationId") Long locationid, @Valid @RequestBody BookingEvent eventRequest) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("EventId " + eventId + " not found");
        }

        return eventRepository.findById(eventId).map(event -> {
            event.setEventName(eventRequest.getEventName());
            if (eventRequest.getDescription() != null) {
                event.setDescription(eventRequest.getDescription());
            }
            event.setPartyNumber(eventRequest.getPartyNumber());
            event.setStartTime(eventRequest.getStartTime());
            event.setEndTime(eventRequest.getEndTime());
            event.setAttendeeEmail(eventRequest.getAttendeeEmail());
            return eventRepository.save(event);
        }).orElseThrow(() -> new ResourceNotFoundException("EventId " + eventId + " not found"));
    }

    @DeleteMapping("/locations/{locationId}/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "locationId") Long locationId,
                                         @PathVariable(value = "eventId") Long eventId) {
        Optional<Location> eventLocation = locationRepository.findById(locationId);
        return eventRepository.findByEventIdAndLocation(eventId, eventLocation).map(event -> {
            eventRepository.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId + " and locationId" + locationId));
    }
}
