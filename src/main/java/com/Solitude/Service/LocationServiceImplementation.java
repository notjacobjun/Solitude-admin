package com.Solitude.Service;

import com.Solitude.Entity.Location;
import com.Solitude.RESTHelper.LocationDTO;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LocationServiceImplementation implements LocationService {

    public final ModelMapper modelMapper = new ModelMapper();

    @Override
    public LocationDTO convertToDTO(Location location) {
        LocationDTO locationDTO = modelMapper.map(location, LocationDTO.class);
        locationDTO.setLocationID(location.getLocationID());
        locationDTO.setLocationName(location.getLocationName());
        locationDTO.setMaxCapacity(location.getMaxCapacity());
        locationDTO.setEvents(location.getEvents());
        locationDTO.setCurrentNumberOfAttendees(location.getCurrentNumberOfAttendees());
        return locationDTO;
    }

    @Override
    public Location convertToEntity(LocationDTO locationDTO) {
        Location location = modelMapper.map(locationDTO, Location.class);
        location.setLocationID(locationDTO.getLocationID());
        location.setLocationName(locationDTO.getLocationName());
        location.setEvents(locationDTO.getEvents());
        location.setCurrentNumberOfAttendees(locationDTO.getCurrentNumberOfAttendees());
        location.setMaxCapacity(locationDTO.getMaxCapacity());
        return location;
    }
}
