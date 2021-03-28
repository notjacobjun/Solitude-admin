package com.Solitude.Controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

import com.Solitude.Calendar.GoogleCalendar;
import com.Solitude.Entity.BookingEvent;
import com.Solitude.Entity.Location;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.RESTHelper.BookingEventDTO;
import com.Solitude.RESTHelper.GoogleCalendarEventCombinedSerializer;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.LocationRepository;

import com.Solitude.Service.EventServiceImplementation;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EventController {

    //    private Logger logger = Logger.getLogger(EventController.class.getName());
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final EventServiceImplementation eventServiceImplementation;

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

    @GetMapping("/location/{locationId}/event")
    public Page<BookingEvent> getAllEventsByLocationId(@PathVariable(value = "locationId") Long locationId, Pageable pageable) {
        Optional<Location> location = locationRepository.findById(locationId);
        return eventRepository.findByLocation(location, pageable);
    }

    @GetMapping(value = "/event/{eventId}")
    public Page<BookingEvent> getEventById(@PathVariable(value = "eventId") final String eventId, Pageable pageable) {
        return eventRepository.findByEventId(eventId, pageable);
    }

    @GetMapping(value = "/event")
    public List<BookingEvent> getAllEvents() {
//        BookingEvent event1 = new BookingEvent();
//        System.out.println(event1);
//        BookingEvent event = new BookingEvent("testEvent", "testEventname", null, "testDescription", "tester@gmail.com", 5, "startingTime", "endingTime", 123L, false, false);
        return eventRepository.findAll();
    }

    // saves BookingEvent JSON data into postgres DB
    @PostMapping("/event")
    public void saveEventIntoPostgres(@RequestBody BookingEventDTO event) throws ParseException {
        BookingEvent newEvent = eventServiceImplementation.convertToEntity(event);
        eventRepository.save(newEvent);
//        Event GCEvent = eventServiceImplementation.convertToGCEvent(newEvent);
//        // TODO update the GCEvent with email, attendees, start, and end time.
//        try {
//            Calendar service = GoogleCalendar.getService();
//            service.events().insert(event.getCreatorEmail(), GCEvent).execute();
//        } catch (IOException | GeneralSecurityException e) {
//            e.printStackTrace();
//        }
    }

    // buggy post mapping
//    @PostMapping("/event/{userId}")
////    @JsonSerialize(using = GoogleCalendarEventCombinedSerializer.EventJsonSerializer.class)
//    public BookingEvent saveEventIntoPostgres(@PathVariable Long userId, @RequestBody Event event) {
//        Objects.requireNonNull(event, "event and its fields must not be null");
//        System.out.println("jacob's debug stuff: " + event);
////        logger.debug(event);
////        Gson gson = new Gson();
////        String eventJson = gson.toJson(event);
////        Event calendarEvent = gson.fromJson(eventJson, Event.class);
//        BookingEvent bookingEvent = new BookingEvent(event.getId(), event.getSummary(), null,
//                event.getDescription(), event.getCreator().getEmail(), event.getAttendees().size(),
//                event.getStart().getDate().toString(), event.getEnd().getDate().toString(), userId,
//                false, false);
//
//        return eventRepository.save(bookingEvent);
//    }

    @PutMapping(value = "/event/{eventId}", consumes = {"application/json"})
    public BookingEvent updateBookingEvent(@PathVariable String eventId, @RequestBody BookingEventDTO newEvent) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    event.setEventId(newEvent.getEventId());
                    event.setEventName(newEvent.getEventName());
                    event.setDescription(newEvent.getDescription());
                    event.setCreatorEmail(newEvent.getCreatorEmail());
                    event.setStartTime(newEvent.getStartTime());
                    event.setEndTime(newEvent.getEndTime());
                    event.setLocation(newEvent.getLocation());
                    event.setPartyNumber(newEvent.getPartyNumber());
                    event.setUserID(newEvent.getUserID());
                    event.setCheckedIn(newEvent.isCheckedIn());
                    event.setCheckedOut(newEvent.isCheckedOut());
                    return eventRepository.save(event);
                })
                .orElseGet(() -> {
                    newEvent.setEventId(eventId);
                    BookingEvent modifiedEvent = eventServiceImplementation.convertToEntity(newEvent);
                    return eventRepository.save(modifiedEvent);
                });
    }

    // TODO configure to not delete location as well (not sure if it does now)
    @DeleteMapping("/location/{locationId}/event/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "locationId") Long locationId,
                                         @PathVariable(value = "eventId") String eventId) {
        Optional<Location> eventLocation = locationRepository.findById(locationId);
        return eventRepository.findByEventIdAndLocation(eventId, eventLocation).map(event -> {
            eventRepository.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId + " and locationId" + locationId));
    }
}
