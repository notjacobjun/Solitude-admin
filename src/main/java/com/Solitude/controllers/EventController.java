package com.Solitude.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

import com.Solitude.Calendar.GoogleCalendar;
import com.Solitude.Entity.BookingEvent;
import com.Solitude.Entity.Location;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.LocationRepository;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
// using constructor injection for unit testing compatibility
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    // TODO: Add firebase authentication
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
    public Page<BookingEvent> getEventByLocationId(@PathVariable(value = "locationId") Long locationId, Pageable pageable) {
        Optional<Location> location = locationRepository.findById(locationId);
        return eventRepository.findByLocation(location, pageable);
    }

    // TODO test post mapping with solitude admin email
    // specify the calendar that you want to add this event into with the email
    // also have to specify the userId
    @PostMapping("/location/{locationId}/{email}/{userId}/events")
    public BookingEvent addBookingEvent(@PathVariable String email, @PathVariable Long userId, @RequestBody Event event) {
        // save the event into the postgres DB
        BookingEvent bookingEvent = new BookingEvent(event.getId(), event.getSummary(), null, event.getDescription(),
                event.getAttendees().size(), event.getStart().toString(), event.getEnd().toString(), userId,
                false, false);
        // creates a new authorized API client
        try {
            Calendar service = GoogleCalendar.getService();
            // creates the event within the Solitude admin calendar, then it adds the attendees to the event
            // this line below requires that the start and end time for the event are instantiated, also that they
            // correspondent with both being an all-day event or both as a date-time event (part of the day)
            service.events().insert(email, event).execute();


        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return eventRepository.save(bookingEvent);
    }

    @PutMapping("/locations/{locationId}/events/{eventId}")
    public BookingEvent updateBookingEvent(@PathVariable(value = "eventId") String eventId, @PathVariable(value = "locationId") Long locationid, @Valid @RequestBody BookingEvent eventRequest) {
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
            return eventRepository.save(event);
        }).orElseThrow(() -> new ResourceNotFoundException("EventId " + eventId + " not found"));
    }

    @DeleteMapping("/locations/{locationId}/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "locationId") Long locationId,
                                         @PathVariable(value = "eventId") String eventId) {
        Optional<Location> eventLocation = locationRepository.findById(locationId);
        return eventRepository.findByEventIdAndLocation(eventId, eventLocation).map(event -> {
            eventRepository.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId + " and locationId" + locationId));
    }
}
