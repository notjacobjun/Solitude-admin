//package com.Solitude.Service;
//
//import com.Solitude.Entity.BookingEvent;
//import com.Solitude.RESTHelper.UserCheckInOut;
//import com.Solitude.Repository.EventRepository;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class UserServiceImplementation implements UserService {
//
//    private final EventRepository eventRepository;
//
//    @Override
//    public void checkIn(UserCheckInOut user, BookingEvent event) {
//        try {
//            event.setCheckedIn(user.getUserID().equals(event.getUserID()) && user.getEmail().equalsIgnoreCase(event.getCreatorEmail()));
//            eventRepository.save(event);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void checkOut(UserCheckInOut user, BookingEvent event) {
//        try {
//            // changed to .equals because we are trying to compare the values, not the memory addresses of the objects
//        	event.setCheckedOut(user.getUserID().equals(event.getUserID()) && user.getEmail().equalsIgnoreCase(event.getEventId()));
//        	eventRepository.save(event);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
