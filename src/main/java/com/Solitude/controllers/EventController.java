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
        return eventRepository.findByLocationId(locationId, pageable);
    }

    // creates new event based on @RequestBody event parameters given
    @PostMapping("/location/{locationId}/events")
    public BookingEvent createBookingEvent(@PathVariable(value = "locationId") Long locationid, @Valid @RequestBody BookingEvent bookingEvent) {
        return locationRepository.findById(locationid).map(location -> {
            bookingEvent.setLocation(location);
            return eventRepository.save(bookingEvent);
        }).orElseThrow(() -> new ResourceNotFoundException("LocationId " + locationid + " not found"));
    }

    // updates the event baesd the @RequestBody event parameters given
    @PutMapping("/locations/{locationId}/events/{eventId}")
    public BookingEvent updateBookingEvent(@PathVariable (value = "eventId") Long eventId, @PathVariable (value = "locationId") Long locationid, @Valid @RequestBody BookingEvent eventRequest) {
        if(!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("EventId " + eventId + " not found");
        }

        return eventRepository.findById(eventId).map(event -> {
            event.setName(eventRequest.getName());
            if(eventRequest.getDescription() != null) {
                event.setDescription(eventRequest.getDescription());
            }
            event.setPartyNumber(eventRequest.getPartyNumber());
            event.setStartTime(eventRequest.getStartTime());
            event.setEndTime(eventRequest.getEndTime());
            event.setAttendeeEmail(eventRequest.getAttendeeEmail());
            return eventRepository.save(event);
        }).orElseThrow(() -> new ResourceNotFoundException("EventId " + eventId + " not found"));
    }

    // TODO configure the optimal cascade settings for this deletion
    @DeleteMapping("/locations/{locationId}/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "locationId") Long locationId,
                                         @PathVariable(value = "eventId") Long eventId) {
        return eventRepository.findByIdAndLocationId(locationId, eventId).map(event -> {
            eventRepository.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId + " and locationId" + locationId));
    }
}
