package com.Solitude.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Solitude.util.GoogleCalendar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@RequestHeader("token") String idToken) throws FirebaseAuthException {
        try {
//        	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//            String uid = decodedToken.getUid();
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEvents() {
        try {
        	GoogleCalendar.getEvents();
        	logger.info("Inside events");
            return new ResponseEntity<>("ALL OK", HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
