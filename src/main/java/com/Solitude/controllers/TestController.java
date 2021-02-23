package com.Solitude.controllers;

import java.util.List;
import java.util.Set;

import com.Solitude.Entity.User;
import com.Solitude.Repository.UserRepository;
import com.Solitude.Service.UserServiceImplementation;
import com.Solitude.util.GoogleCalendar;
import com.google.api.services.calendar.model.Event;
import com.google.firebase.auth.FirebaseAuthException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    UserServiceImplementation UserServiceImplementation;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@RequestHeader("token") String idToken) throws FirebaseAuthException {
        try {
            // FirebaseToken decodedToken =
            // FirebaseAuth.getInstance().verifyIdToken(idToken);
            // String uid = decodedToken.getUid();
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> getEvents() {
        try {
            List<Event> events = GoogleCalendar.getUpcomingEventsByLocation("", 10);
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal error {} ", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/users")
    public Model getAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute(users);
        return model;
    }
}
