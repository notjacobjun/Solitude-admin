package com.Solitude.controllers;

import com.Solitude.Entity.User;
import com.Solitude.RESTHelper.BookingEvent;
import com.Solitude.Service.UserServiceImplementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserServiceImplementation userService;

    // user will give the ID for the event and provide their ID
    // later on add another option on top of this option to make the process faster
    // for users
    @GetMapping("/checkin")
    public ResponseBody checkIn(@RequestBody User user, @RequestBody BookingEvent event) {
        userService.checkIn(user, event);
        return null;
    }

    @GetMapping("/checkout")
    public ResponseBody checkOut(@RequestBody User user, @RequestBody BookingEvent event) {
        userService.checkOut(user, event);
        return null;
    }
}
