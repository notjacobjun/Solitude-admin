package com.Solitude.Service;

import com.Solitude.Entity.AppUser;
import com.Solitude.RESTHelper.BookingEventDTO;
import com.Solitude.RESTHelper.UserCheckInOut;
import com.Solitude.RESTHelper.UserDTO;
import com.Solitude.Repository.EventRepository;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final EventRepository eventRepository;
    private final EventServiceImplementation eventServiceImplementation;
    public final ModelMapper modelMapper = new ModelMapper();

    @Override
    public boolean checkIn(UserCheckInOut user, BookingEventDTO event) {
        if (user.getUserID().equals(event.getUserID())) {
            event.setCheckedIn(true);
            eventRepository.save(eventServiceImplementation.convertToEntity(event));
            return true;
        }
        return false;
    }

    @Override
    public boolean checkOut(UserCheckInOut user, BookingEventDTO event) {
        if (user.getUserID().equals(event.getUserID())) {
            event.setCheckedOut(true);
            eventRepository.save(eventServiceImplementation.convertToEntity(event));
            return true;
        }
        return false;
    }

    @Override
    public UserDTO convertToDTO(AppUser appUser) {
        UserDTO userDTO = modelMapper.map(appUser, UserDTO.class);
        userDTO.setUserId(appUser.getUserId());
        userDTO.setEmail(appUser.getEmail());
        userDTO.setDisplayName(appUser.getDisplayName());
        return userDTO;
    }

    @Override
    public AppUser convertToEntity(UserDTO userDTO) {
        AppUser appUser = modelMapper.map(userDTO, AppUser.class);
        appUser.setUserId(userDTO.getUserId());
        appUser.setEmail(userDTO.getEmail());
        appUser.setDisplayName(userDTO.getDisplayName());
        return appUser;
    }
}
