package com.Solitude.controllers;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.RESTHelper.UserCheckInOut;
import com.Solitude.Service.UserServiceImplementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServiceImplementation userService;

    // user will give the ID for the event and provide their ID
    // later on add another option on top of this option to make the process faster
    // for users
    @PostMapping("/checkin")
    public ResponseBody checkIn(@RequestBody UserCheckInOut user, @RequestBody BookingEvent event) {
        userService.checkIn(user, event);
        return null;
    }

    @PostMapping("/checkout")
    public ResponseBody checkOut(@RequestBody UserCheckInOut user, @RequestBody BookingEvent event) {
        userService.checkOut(user, event);
        return null;
    }
}
