package com.Solitude.controllers;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Solitude.RESTHelpers.BookingEvent;
import com.Solitude.util.GoogleCalendar;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@RestController
@RequestMapping("/events")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    // TODO: Add firebase authentication
    @RequestMapping(value = "/upcoming/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> getEvents(@PathVariable("location") String location, @RequestParam(name = "size") Integer  size) {
        try {
//        	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//        	String uid = decodedToken.getUid();
        	
        	// TODO: Verify if the location belongs to the uid in Postgres
        	
        	List<Event> events = GoogleCalendar.getUpcomingEventsByLocation(location, size);
        	logger.debug("Found {} upcoming events", events.size());
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    // TODO: Add firebase authentication
    @RequestMapping(value = "/create", 
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> createUser(@RequestBody BookingEvent bookingEvent) {
    	try {
	//    	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
	//    	String uid = decodedToken.getUid();
	    	
	    	Event event = new Event()
			    .setSummary(bookingEvent.getName())
			    .setLocation(bookingEvent.getLocation())
			    .setDescription(bookingEvent.getDescription());
	
			DateTime startDateTime = new DateTime(bookingEvent.getStartTime());
			EventDateTime start = new EventDateTime()
			    .setDateTime(startDateTime)
			    .setTimeZone("America/Los_Angeles");
			event.setStart(start);
	
			DateTime endDateTime = new DateTime(bookingEvent.getEndTime());
			EventDateTime end = new EventDateTime()
			    .setDateTime(endDateTime)
			    .setTimeZone("America/Los_Angeles");
			event.setEnd(end);
			
			EventAttendee[] attendees = new EventAttendee[] {
			    new EventAttendee().setEmail(bookingEvent.getAttendeeEmail()),
			};
			event.setAttendees(Arrays.asList(attendees));
			
			Calendar service = GoogleCalendar.getService();
			
			event = service.events().insert(GoogleCalendar.getCalendarId(), event).execute();
	        return new ResponseEntity<>(event, HttpStatus.OK);
    	} catch(Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
