package com.Solitude.Controllers;

import com.Solitude.Entity.Location;
import com.Solitude.Exception.ResourceNotFoundException;
import com.Solitude.RESTHelper.LocationDTO;
import com.Solitude.Repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class LocationController {

    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LocationController(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/locations")
    public Page<Location> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @PostMapping("/locations")
    public LocationDTO createLocation(@Valid @RequestBody LocationDTO locationRequest) {
        Location location = convertToEntity(locationRequest);
        Location locationCreated = locationRepository.save(location);
        return convertToDto(locationCreated);
    }

    @PutMapping("/locations")
    public Location updateLocation(@RequestBody LocationDTO locationRequest) throws ResourceNotFoundException {
        try{
            Location locationEntity = convertToEntity(locationRequest);
            return locationRepository.save(locationEntity);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId) {
        return locationRepository.findById(locationId).map(location -> {
            locationRepository.delete(location);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("LocationId " + locationId + " not found"));
    }

    private LocationDTO convertToDto(Location location) {
        LocationDTO locationDTO = modelMapper.map(location, LocationDTO.class);
        locationDTO.setLocationID(location.getLocationID());
        locationDTO.setLocationName(location.getLocationName());
        locationDTO.setCurrentNumberOfAttendees(location.getCurrentNumberOfAttendees());
        locationDTO.setEvents(location.getEvents());
        locationDTO.setMaxCapacity(location.getMaxCapacity());
        return locationDTO;
    }

    private Location convertToEntity(LocationDTO locationDTO) {
        Location location = modelMapper.map(locationDTO, Location.class);
        location.setLocationID(locationDTO.getLocationID());
        location.setLocationName(locationDTO.getLocationName());
        location.setCurrentNumberOfAttendees(locationDTO.getCurrentNumberOfAttendees());
        location.setEvents(locationDTO.getEvents());
        location.setMaxCapacity(locationDTO.getMaxCapacity());

        return location;
    }
}
