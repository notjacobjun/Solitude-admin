package com.Solitude.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.Solitude.Calendar.GoogleCalendar;
import com.Solitude.Entity.BookingEvent;
import com.Solitude.Entity.Location;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.RESTHelper.BookingEventDTO;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.LocationRepository;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// using constructor injection for unit testing compatibility
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

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
        return eventRepository.findAll();
    }

    // API call to create a Google Calendar event based on the JSON parameters within event given
    @PostMapping("/event")
    public void createGoogleCalendarEvent(@RequestBody Event event) {
        // creates a new authorized API client
        try {
            Calendar service = GoogleCalendar.getService();
            service.events().insert(event.getCreator().getEmail(), event).execute();

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    // TODO test post mapping with solitude admin email and a proper Google calendar event created (now just getting HTTP status 400)
    // specify the calendar that you want to add this event into with the email
    // also have to specify the userId
    @PostMapping("/event/{userId}")
//    @JsonSerialize(using = GoogleCalendarEventCombinedSerializer.EventJsonSerializer.class)
    public BookingEvent saveEventIntoPostgres(@PathVariable Long userId, @RequestBody Event event) {
        Objects.requireNonNull(event, "event and its fields must not be null");
//        Gson gson = new Gson();
//        String eventJson = gson.toJson(event);
//        Event calendarEvent = gson.fromJson(eventJson, Event.class);
        BookingEvent bookingEvent = new BookingEvent(event.getId(), event.getSummary(), null,
                event.getDescription(), event.getCreator().getEmail(), event.getAttendees().size(),
                event.getStart().getDate().toString(), event.getEnd().getDate().toString(), userId,
                false, false);

        return eventRepository.save(bookingEvent);
    }

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
                    BookingEvent modifiedEvent = convertToEntity(newEvent);
                    return eventRepository.save(modifiedEvent);
                });
    }

    @DeleteMapping("/location/{locationId}/event/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "locationId") Long locationId,
                                         @PathVariable(value = "eventId") String eventId) {
        Optional<Location> eventLocation = locationRepository.findById(locationId);
        return eventRepository.findByEventIdAndLocation(eventId, eventLocation).map(event -> {
            eventRepository.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId + " and locationId" + locationId));
    }

    private BookingEventDTO convertToDto(BookingEvent event) {
        BookingEventDTO bookingEventDTO = modelMapper.map(event, BookingEventDTO.class);
        bookingEventDTO.setEventId(event.getEventId());
        bookingEventDTO.setEventName(event.getEventName());
        bookingEventDTO.setDescription(event.getDescription());
        bookingEventDTO.setCreatorEmail(event.getCreatorEmail());
        bookingEventDTO.setStartTime(event.getStartTime());
        bookingEventDTO.setEndTime(event.getEndTime());
        bookingEventDTO.setLocation(event.getLocation());
        bookingEventDTO.setPartyNumber(event.getPartyNumber());
        bookingEventDTO.setUserID(event.getUserID());
        bookingEventDTO.setCheckedIn(event.isCheckedIn());
        bookingEventDTO.setCheckedOut(event.isCheckedOut());
        return bookingEventDTO;
    }

    private BookingEvent convertToEntity(BookingEventDTO eventDTO) {
        BookingEvent bookingEvent = modelMapper.map(eventDTO, BookingEvent.class);
        bookingEvent.setEventId(eventDTO.getEventId());
        bookingEvent.setEventName(eventDTO.getEventName());
        bookingEvent.setDescription(eventDTO.getDescription());
        bookingEvent.setCreatorEmail(eventDTO.getCreatorEmail());
        bookingEvent.setStartTime(eventDTO.getStartTime());
        bookingEvent.setEndTime(eventDTO.getEndTime());
        bookingEvent.setLocation(eventDTO.getLocation());
        bookingEvent.setPartyNumber(eventDTO.getPartyNumber());
        bookingEvent.setUserID(eventDTO.getUserID());
        bookingEvent.setCheckedIn(eventDTO.isCheckedIn());
        bookingEvent.setCheckedOut(eventDTO.isCheckedOut());
        return bookingEvent;
    }
}
