package com.Solitude.Service;

import com.Solitude.DAO.UserDAOImplementation;
import com.Solitude.Entity.User;

import com.Solitude.Entity.BookingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserDAOImplementation UserDAOImplementation;

    // TODO implement the checkin API
    @Override
    // double-check to see if boolean is the right return type
    public boolean checkIn(User user, BookingEvent event) {
        try {
            // verify using the id instead of email
            // String calendarId = GoogleCalendar.getCalendarId();
            // Calendar service = GoogleCalendar.getService();

            // check if the user matches the event (using email for now but later use
            // generated code)
            if (user.getEmail().equalsIgnoreCase(event.getAttendeeEmail())) {
                event.setCheckedIn(true);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // TODO implement the checkout API
    @Override
    public boolean checkOut(User user, BookingEvent event) {
        try {
            if (user.getEmail().equalsIgnoreCase(event.getAttendeeEmail()) && event.isCheckedIn()) {
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
