package com.Solitude.Service;

import com.Solitude.Entity.AppUser;
import com.Solitude.RESTHelper.BookingEventDTO;
import com.Solitude.RESTHelper.UserCheckInOut;
import com.Solitude.RESTHelper.UserDTO;

public interface UserService {

    public boolean checkIn(UserCheckInOut user, BookingEventDTO event);

    public boolean checkOut(UserCheckInOut user, BookingEventDTO event);

    public AppUser convertToEntity(UserDTO userDTO);

    public UserDTO convertToDTO(AppUser appUser);
}
