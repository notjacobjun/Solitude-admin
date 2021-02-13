package com.Solitude.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.Solitude.Entity.User;
import com.Solitude.RESTHelpers.BookingEvent;
import com.Solitude.util.GoogleCalendar;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	// TODO: Add firebase authentication
	@RequestMapping(value = "/upcoming/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Event>> getEvents(@PathVariable("location") String location,
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

	// TODO: Add firebase authentication
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE);
    public ResponseEntity<Event> createUser(@RequestBody BookingEvent bookingEvent) {
    	try {
	//    	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
	//    	String uid = decodedToken.getUid();
	    	
    		String calendarId = GoogleCalendar.getCalendarId();
    		Calendar service = GoogleCalendar.getService();
    		
    		DateTime startDateTime = new DateTime(bookingEvent.getStartTime());
    		DateTime endDateTime = new DateTime(bookingEvent.getEndTime());
    		
    		Events events = service.events().list(calendarId)
                    .setTimeMin(startDateTime)
                    .setTimeMax(endDateTime)
                    .setSingleEvents(true)
                    .execute();
    		// TODO: Filter the events using set() after service.events().list(calendarId)
    		
    		// Filter the events by the booking event's location
    		List<Event> filteredEvents = events.getItems().stream()
	    	        .filter(evnt -> 
	    	          evnt.getLocation().equals(bookingEvent.getLocation()))
	    	        .collect(Collectors.toList());
    		// Create the event only if there are previous events in that time frame for that location
    		if(filteredEvents.isEmpty()) {
    			Event event = new Event()
				    .setSummary(bookingEvent.getName())
				    .setLocation(bookingEvent.getLocation())
				    .setDescription(bookingEvent.getDescription());
		
				EventDateTime start = new EventDateTime()
				    .setDateTime(startDateTime)
				    .setTimeZone("America/Los_Angeles");
				event.setStart(start);
		
				EventDateTime end = new EventDateTime()
				    .setDateTime(endDateTime)
				    .setTimeZone("America/Los_Angeles");
				event.setEnd(end);
				
				EventAttendee[] attendees = new EventAttendee[] {
				    new EventAttendee().setEmail(bookingEvent.getAttendeeEmail()),
				};
				event.setAttendees(Arrays.asList(attendees));
				
				event = service.events().insert(calendarId, event).execute();
		        return new ResponseEntity<>(event, HttpStatus.OK);
    		} else {
    			// If an event already exists in that time range for that location
    			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
    		}
	    	
    	} catch(Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
