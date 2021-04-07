package com.Solitude.Controllers;

import com.Solitude.Entity.Location;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.RESTHelper.LocationDTO;
import com.Solitude.Repository.LocationRepository;
import com.Solitude.Service.LocationServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationRepository locationRepository;
    private final LocationServiceImplementation locationServiceImplementation;

    @GetMapping("/location")
    public Page<Location> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @PostMapping("/location")
    public Location createLocation(@RequestBody LocationDTO locationRequest) {
        Location location = locationServiceImplementation.convertToEntity(locationRequest);
        return locationRepository.save(location);
    }

    @PutMapping("/location/{locationId}")
    public Location updateLocation(@PathVariable Long locationId, @RequestBody LocationDTO newLocation) throws ResourceNotFoundException {
        return locationRepository.findById(locationId)
                .map(location -> {
                    location.setLocationID(locationId);
                    location.setLocationName(newLocation.getLocationName());
                    location.setMaxCapacity(newLocation.getMaxCapacity());
                    location.setEvents(newLocation.getEvents());
                    location.setCurrentNumberOfAttendees(newLocation.getCurrentNumberOfAttendees());
                    return locationRepository.save(location);
                })
                .orElseGet(() -> {
                    newLocation.setLocationID(locationId);
                    Location modifiedLocation = locationServiceImplementation.convertToEntity(newLocation);
                    return locationRepository.save(modifiedLocation);
                });
    }

    @DeleteMapping("/location/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId) {
        return locationRepository.findById(locationId).map(location -> {
            locationRepository.delete(location);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("LocationId " + locationId + " not found"));
    }
}
