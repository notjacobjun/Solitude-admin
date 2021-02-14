package com.Solitude.Service;

import com.Solitude.DAO.UserDAOImplementation;
import com.Solitude.Entity.User;
import com.Solitude.RESTHelpers.BookingEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserDAOImplementation UserDAOImplementation;

    @Override
    // double-check to see if boolean is the right return type
    public boolean checkIn(User user, BookingEvent event) {
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

    @Override
    public boolean checkOut(User user, BookingEvent event) {
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

    public User getUser(int userId) {
        return UserDAOImplementation.getUser(userId);
    }

}
