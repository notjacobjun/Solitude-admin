package com.Solitude.controllers;

import com.Solitude.Entity.User;
import com.Solitude.RESTHelpers.BookingEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // make sure that users are created properly using this method
    @RequestMapping("/checkin")
    public boolean checkIn(@RequestBody User user, @RequestBody BookingEvent event) {
        try {
            // String calendarId = GoogleCalendar.getCalendarId();
            // Calendar service = GoogleCalendar.getService();

            // check if the user matches the event (using email for now but later use
            // generated code)
            if (user.getEmail().equalsIgnoreCase(event.getAttendeeEmail())) {
                // mark that the user is there right now (for checkout purposes and live
                // attendee count)
                event.getLocation().setCurrentNumberOfAttendees(
                        event.getLocation().getCurrentNumberOfAttendees() + event.getPartyNumber());
                // double check if we need this or if this is redundant
                event.setCheckedIn(true);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/checkout")
    public boolean checkOut(@RequestBody User user, @RequestBody BookingEvent event) {
        try {
            if (user.getEmail().equalsIgnoreCase(event.getAttendeeEmail()) && event.isCheckedIn()) {
                int currentNumberOfAttendees = event.getLocation().getCurrentNumberOfAttendees();
                event.getLocation().setCurrentNumberOfAttendees(currentNumberOfAttendees - event.getPartyNumber());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
