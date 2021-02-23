package com.Solitude.controllers;

import com.Solitude.Entity.Location;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/")
    public Page<Location> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @PostMapping("/")
    public Location createLocation(@Valid @RequestBody Location location) {
        return locationRepository.save(location);
    }

    @PutMapping("/{locationId}")
    public Location updateLocation(@PathVariable Long locationId, @Valid @RequestBody Location locationRequest) {
        return locationRepository.findById(locationId).map(location -> {
            location.setLocationName(locationRequest.getLocationName());
            location.setLocationID(locationRequest.getLocationID());
            location.setMaxCapacity(locationRequest.getMaxCapacity());
            return locationRepository.save(location);
        }).orElseThrow(() -> new ResourceNotFoundException("LocationId " + locationId + " not found"));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId) {
        return locationRepository.findById(locationId).map(location -> {
            locationRepository.delete(location);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("LocationId " + locationId + " not found"));
    }
}
