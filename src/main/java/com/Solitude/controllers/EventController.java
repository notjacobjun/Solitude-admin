package com.Solitude.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.google.api.services.calendar.model.Events;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@RestController
@RequestMapping("/events")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    
    // TODO: Add firebase authentication
    @RequestMapping(value = "/upcoming/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> getUpcomingEvents(@PathVariable("location") String location, @RequestParam(name = "size") Integer  size) {
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
    public ResponseEntity<Object> createEvent(@RequestBody BookingEvent bookingEvent) {
    	try {
	//    	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
	//    	String uid = decodedToken.getUid();
	    	
    		String calendarId = GoogleCalendar.getCalendarId();
    		Calendar service = GoogleCalendar.getService();
    		
    		DateTime startDateTime = new DateTime(bookingEvent.getStartTime());
    		DateTime endDateTime = new DateTime(bookingEvent.getEndTime());

    		// Check if start time is after the current time
    		if(startDateTime.getValue()>=System.currentTimeMillis()) {
    		
	    		Events events = service.events().list(calendarId)
	                    .setTimeMin(startDateTime)
	                    .setTimeMax(endDateTime)
	                    .setSingleEvents(true)
	                    .execute();
	    		// TODO: Filter the events using set() after service.events().list(calendarId)
	    		
	    		// Filter the events by the booking event's location
	    		List<Event> filteredEvents = events.getItems().stream()
		    	        .filter(evnt -> 
		    	          evnt.getLocation().equals(bookingEvent.getLocation())
		    	          || evnt.getAttendees().stream().filter(o -> o.getEmail().equals(bookingEvent.getAttendeeEmail())).findFirst().isPresent() 
		    	        )
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
	    			if(filteredEvents.stream().filter(o -> o.getLocation().equals(bookingEvent.getLocation())).findFirst().isPresent()) {
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
	    	
    	} catch(Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
