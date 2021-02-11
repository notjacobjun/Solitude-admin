package com.Solitude.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Solitude.util.GoogleCalendar;
import com.google.api.services.calendar.model.Event;
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
}
