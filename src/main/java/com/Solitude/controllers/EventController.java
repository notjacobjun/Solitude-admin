package com.Solitude.controllers;

import java.util.List;

<<<<<<< HEAD
import com.Solitude.Entity.BookingEvent;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.LocationRepository;
=======
import com.Solitude.RESTHelper.BookingEvent;
>>>>>>> 66263f063fd52381b289742d6cf92325e093fec2
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

<<<<<<< HEAD
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

=======
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);

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

	// TODO: Add firebase authentication
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createEvent(@RequestBody BookingEvent bookingEvent) {
		try {
			// FirebaseToken decodedToken =
			// FirebaseAuth.getInstance().verifyIdToken(idToken);
			// String uid = decodedToken.getUid();

			String calendarId = GoogleCalendar.getCalendarId();
			Calendar service = GoogleCalendar.getService();

			DateTime startDateTime = new DateTime(bookingEvent.getStartTime());
			DateTime endDateTime = new DateTime(bookingEvent.getEndTime());

			// Check if start time is after the current time
			if (startDateTime.getValue() >= System.currentTimeMillis()) {

				Events events = service.events().list(calendarId).setTimeMin(startDateTime).setTimeMax(endDateTime)
						.setSingleEvents(true).execute();
				// TODO: Filter the events using set() after service.events().list(calendarId)

				// Filter the events by the booking event's location
				List<Event> filteredEvents = events.getItems().stream()
						.filter(evnt -> evnt.getAttendees().stream()
								.filter(o -> o.getEmail().equals(bookingEvent.getAttendeeEmail())).findFirst()
								.isPresent())
						.collect(Collectors.toList());
				// Create the event only if there are no previous events in that time frame for
				// that user
				if (filteredEvents.isEmpty()) {
					Event event = new Event().setSummary(bookingEvent.getName())
							.setLocation(bookingEvent.getLocation())
							.setDescription(bookingEvent.getDescription());

					EventDateTime start = new EventDateTime().setDateTime(startDateTime)
							.setTimeZone("America/Los_Angeles");
					event.setStart(start);

					EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("America/Los_Angeles");
					event.setEnd(end);

					EventAttendee[] attendees = new EventAttendee[] {
							new EventAttendee().setEmail(bookingEvent.getAttendeeEmail()), };
					event.setAttendees(Arrays.asList(attendees));

					event = service.events().insert(calendarId, event).execute();
					return new ResponseEntity<>(event, HttpStatus.OK);
				} else {
					// If an event already exists in that time range for that location
					// TODO fix this equals statement
					if (filteredEvents.stream().filter(o -> o.getLocation().equals(bookingEvent.getLocation()))
							.findFirst().isPresent()) {
						return new ResponseEntity<>(null, HttpStatus.valueOf(420));
					} else {
						// If an event exists for that user elsewhere
						return new ResponseEntity<>(null, HttpStatus.valueOf(421));
					}

				}
			} else {
				// If start time is before the current time
				return new ResponseEntity<>(null, HttpStatus.valueOf(419));
			}

		} catch (Exception e) {
			logger.error("Internal error {} ", e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
>>>>>>> 66263f063fd52381b289742d6cf92325e093fec2
}
