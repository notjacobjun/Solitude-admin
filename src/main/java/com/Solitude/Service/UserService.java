package com.Solitude.Service;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.RESTHelper.UserCheckInOut;

public interface UserService {

    public void checkIn(UserCheckInOut user, BookingEvent event);

    public void checkOut(UserCheckInOut user, BookingEvent event);
}
