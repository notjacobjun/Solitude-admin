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

    @Override
    public void checkIn(UserCheckInOut user, BookingEvent event) {
        try {
            // verify using the id instead of email
            // String calendarId = GoogleCalendar.getCalendarId();
            // Calendar service = GoogleCalendar.getService();

            // changed to .equals because we are trying to compare the values, not the memory addresses of the objects
            // also the eventId is the attendee email beacuse this is how it's done in Google Calendar API so I changed it for clarity
            event.setCheckedIn(user.getUserID().equals(event.getUserID()) && user.getEmail().equalsIgnoreCase(event.getEventId()));
            eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkOut(UserCheckInOut user, BookingEvent event) {
        try {
            // changed to .equals because we are trying to compare the values, not the memory addresses of the objects
        	event.setCheckedOut(user.getUserID().equals(event.getUserID()) && user.getEmail().equalsIgnoreCase(event.getEventId()));
        	eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
