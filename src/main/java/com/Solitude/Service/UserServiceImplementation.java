package com.Solitude.Service;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.RESTHelper.UserCheckInOut;
import com.Solitude.Repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	EventRepository eventRepository;
    // TODO implement the checkin API
    @Override
    // double-check to see if boolean is the right return type
    public void checkIn(UserCheckInOut user, BookingEvent event) {
        try {
            // verify using the id instead of email
            // String calendarId = GoogleCalendar.getCalendarId();
            // Calendar service = GoogleCalendar.getService();

            // check if the user matches the event (using email for now but later use
            // generated code)
            event.setCheckedIn(user.getUserID() == event.getUserID() && user.getEmail().equalsIgnoreCase(event.getAttendeeEmail()));
            eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO implement the checkout API
    @Override
    public void checkOut(UserCheckInOut user, BookingEvent event) {
        try {
        	event.setCheckedOut(user.getUserID() == event.getUserID() && user.getEmail().equalsIgnoreCase(event.getAttendeeEmail()));
        	eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
