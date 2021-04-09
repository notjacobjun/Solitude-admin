package com.Solitude.Controllers;

import com.Solitude.Entity.AppUser;
import com.Solitude.Entity.BookingEvent;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.RESTHelper.UserDTO;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.UserRepository;
import com.Solitude.Service.UserServiceImplementation;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceImplementation userServiceImplementation;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    // later on add another option on top of this option to make the process faster
    @PostMapping("/checkin")
    public boolean checkIn(@RequestBody ObjectNode objectNode) throws NullPointerException {
        try {
            String userEmail = objectNode.get("email").asText();
            String eventCreatorEmail = objectNode.get("creatorEmail").asText();
            String eventId = objectNode.get("eventId").asText();
            BookingEvent event = eventRepository.findByEventId(eventId);
            event.setCheckedIn(true);
            eventRepository.save(event);
            return userEmail.equals(eventCreatorEmail);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("/checkout")
    public boolean checkOut(@RequestBody ObjectNode objectNode) throws NullPointerException {
        try {
            if(objectNode.get("checkedIn").asBoolean()) {
                String userEmail = objectNode.get("email").asText();
                String eventCreatorEmail = objectNode.get("creatorEmail").asText();
                String eventId = objectNode.get("eventId").asText();
                BookingEvent event = eventRepository.findByEventId(eventId);
                event.setCheckedOut(true);
                eventRepository.save(event);
                return userEmail.equals(eventCreatorEmail);
            } else {
                // TODO replace this with Logger
                System.out.println("The user never checked in");
                return false;
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping()
    public Page<AppUser> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PostMapping()
    public AppUser createUser(@RequestBody UserDTO userRequest) {
        AppUser appUser = userServiceImplementation.convertToEntity(userRequest);
        return userRepository.save(appUser);
    }

    @PutMapping("/{userId}")
    public Optional<AppUser> updateUser(@PathVariable Long userId, @RequestBody UserDTO newUser) throws ResourceNotFoundException {
        return userRepository.findById(userId)
                .map(appUser -> {
                    appUser.setEmail(newUser.getEmail());
                    appUser.setDisplayName(newUser.getDisplayName());
                    return userRepository.save(appUser);
                });
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userRepository.findById(userId).map(appUser -> {
            userRepository.delete(appUser);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }
}
